package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.Service;
import com.netcracker.edu.location.impl.ServiceImpl;

import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class InputOutputOperations {

    private static Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());
    private DeviceValidator validator = new DeviceValidator();
    private Service service = new ServiceImpl();

    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (device == null) {
            return;
        }

        if (outputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("OutputStream cannot be null");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage(), illegalArgumentException);
            throw illegalArgumentException;
        }

        FeelableEntity.Field[] fields = device.getAllFields();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(device.getClass().getName());
        dataOutputStream.writeInt((Integer) fields[0].getValue());
        dataOutputStream.writeUTF((String) fields[1].getValue());
        dataOutputStream.writeUTF(fields[3].getValue() == null ? "\\" : (String) fields[3].getValue());
        dataOutputStream.writeUTF(fields[2].getValue() == null ? "\\" : (String) fields[2].getValue());
        dataOutputStream.writeLong(fields[4].getValue() == null ? -1 : ((Date) fields[4].getValue()).getTime());

        if (Battery.class.isInstance(device)) {
            dataOutputStream.writeInt((Integer) fields[5].getValue());
        }
        if (Router.class.isInstance(device)) {
            dataOutputStream.writeInt((Integer) fields[5].getValue());
        }
        if (Switch.class.isInstance(device)) {
            dataOutputStream.writeInt((Integer) fields[6].getValue());
        }
        if (WifiRouter.class.isInstance(device)) {
            dataOutputStream.writeUTF(fields[6].getValue() == null ? "\n" : (String) fields[6].getValue());
        }
    }

    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("InputStream should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Class clazz = Class.forName(dataInputStream.readUTF());
        if (clazz.getName().equals(Battery.class.getName())) {
            Battery battery = new Battery();
            FeelableEntity.Field[] fields = new FeelableEntity.Field[6];
            System.arraycopy(setDevValues(dataInputStream), 0, fields, 0, 5);
            int chargeVolume = dataInputStream.readInt();
            fields[5] = new FeelableEntity.Field(Integer.class, chargeVolume);
            battery.feelAllFields(fields);
            return battery;
        } else if (clazz.getName().equals(Router.class.getName())) {
            Router router = new Router();
            FeelableEntity.Field[] fields = new FeelableEntity.Field[6];
            System.arraycopy(setDevValues(dataInputStream), 0, fields, 0, 5);
            int dateRate = dataInputStream.readInt();
            fields[5] = new FeelableEntity.Field(Integer.class, dateRate);
            router.feelAllFields(fields);
            return router;
        } else if (clazz.getName().equals(Switch.class.getName())) {
            Switch aSwitch = new Switch();
            FeelableEntity.Field[] fields = new FeelableEntity.Field[7];
            System.arraycopy(setDevValues(dataInputStream), 0, fields, 0, 5);
            int dateRate = dataInputStream.readInt();
            fields[5] = new FeelableEntity.Field(Integer.class, dateRate);
            int numberOfPorts = dataInputStream.readInt();
            fields[6] = new FeelableEntity.Field(Integer.class, numberOfPorts);
            aSwitch.feelAllFields(fields);
            return aSwitch;
        } else if (clazz.getName().equals(WifiRouter.class.getName())) {
            WifiRouter wifiRouter = new WifiRouter();
            FeelableEntity.Field[] fields = new FeelableEntity.Field[7];
            System.arraycopy(setDevValues(dataInputStream), 0, fields, 0, 5);
            int dateRate = dataInputStream.readInt();
            fields[5] = new FeelableEntity.Field(Integer.class, dateRate);
            String securityProtocol = dataInputStream.readUTF();
            fields[6] = new FeelableEntity.Field(String.class, securityProtocol.equals("\\") ? null : securityProtocol);
            wifiRouter.feelAllFields(fields);
            return wifiRouter;
        }
        return null;
    }

    public void writeDevice(Device device, Writer writer) throws IOException {
        if (device != null) {
            if (validator.isValidDeviceForWriteToStream(device) != true) {
                DeviceValidationException e = new DeviceValidationException("DeviceService.writeDevice", device);
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            } else if (writer == null) {
                IllegalArgumentException e = new IllegalArgumentException("Writer should not be null");
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }
            FeelableEntity.Field[] fields = device.getAllFields();
            writer.write(device.getClass().getName());
            writer.write("\n" + "[" + fields[0].getValue() + "] ");
            writer.write(fields[1].getValue() + " | ");
            writer.write(fields[3].getValue() == null ? "| " : (String) fields[3].getValue() + " | ");
            writer.write(fields[2].getValue() == null ? "| " : (String) fields[2].getValue() + " | ");
            writer.write(device.getProductionDate() == null ? -1 + " | " : ((Date) fields[4].getValue()).getTime() + " | ");
            if (Battery.class.isInstance(device)) {
                writer.write(fields[5].getValue() + " |");
            } else if (Router.class.isInstance(device)) {
                Router router = (Router) device;
                writer.write(fields[5].getValue() + " |");
                if (WifiRouter.class.isInstance(router)) {
                    writer.write(" ");
                    writer.write(fields[6].getValue() == null ? "|" : (String) fields[6].getValue() + " |");
                } else if (Switch.class.isInstance(router)) {
                    writer.write(" " + fields[6].getValue() + " |");
                }
            }
            writer.write("\n");
        }
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Reader should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        Class clazz = null;
        String clazzName = null;
        String value = null;
        boolean flag = true;
        StringBuilder clazzBuilder = new StringBuilder();
        StringBuilder objectBuilder = new StringBuilder();
        while (flag) {
            char x = (char) reader.read();
            if (x == '\n') break;
            clazzBuilder.append(x);
        }
        clazzName = clazzBuilder.toString();
        if (!"".equals(clazzName)) {
            clazz = Class.forName(clazzName);
        } else {
            return null;
        }
        while (flag) {
            char x = (char) reader.read();
            if (x == '\n') break;
            objectBuilder.append(x);
        }
        value = objectBuilder.toString();
        FeelableEntity.Field[] fields = setDevValues(value, clazz);
        if (clazz.getName().equals(Battery.class.getName())) {
            Battery battery = new Battery();
            battery.feelAllFields(fields);
            return battery;
        }
        if (clazz.getName().equals(Router.class.getName())) {
            Router router = new Router();
            router.feelAllFields(fields);
            return router;
        }
        if (clazz.getName().equals(Switch.class.getName())) {
            Switch aSwitch = new Switch();
            aSwitch.feelAllFields(fields);
            return aSwitch;
        }
        if (clazz.getName().equals(WifiRouter.class.getName())) {
            WifiRouter wifiRouter = new WifiRouter();
            wifiRouter.feelAllFields(fields);
            return wifiRouter;
        }
        ClassNotFoundException e = new ClassNotFoundException("Class not found");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        if (device == null) {
            return;
        }

        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("OutputStream can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(device);
    }

    public Device deserializeDevice(InputStream inputStream)
            throws IOException, ClassCastException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("InputStream can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        ObjectInputStream in = new ObjectInputStream(inputStream);
        Device device = (Device) in.readObject();

        return device;
    }

    public void serializeRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack == null) {
            return;
        }

        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("OutputStream can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(rack);
    }

    public Rack deserializeRack(InputStream inputStream)
            throws IOException, ClassCastException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("InputStream can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ObjectInputStream in = new ObjectInputStream(inputStream);
        Rack rack = (Rack) in.readObject();
        return rack;
    }

    public void writeRack(Rack rack, Writer writer) throws IOException {
        if (writer == null) {
            IllegalArgumentException e = new IllegalArgumentException("OutputStream should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (rack != null) {
            service.writeLocation(rack.getLocation(), writer);
            writer.write(rack.getSize() + " " + rack.getTypeOfDevices().getName() + "\n");
            DeviceService deviceService = new DeviceServiceImpl();
            for (int i = 0; i < rack.getSize(); i++) {
                if (rack.getDevAtSlot(i) == null) {
                    if (i != rack.getSize() - 1) writer.write("\n");
                } else {
                    deviceService.writeDevice(rack.getDevAtSlot(i), writer);
                }
            }
        }
    }

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Stream Reader can't be null");
            LOGGER.log(Level.SEVERE, "an exception was thrown", e);
            throw e;
        }
        Location location = service.readLocation(reader);
        String rackSize = "";
        String rackType = "";
        int data = reader.read();
        while (data != ' ') {
            rackSize += (char) data;
            data = reader.read();
        }
        while (data != '\n') {
            rackType += (char) data;
            data = reader.read();
        }
        int size = Integer.parseInt(rackSize);
        rackType = rackType.trim();
        DeviceService deviceService = new DeviceServiceImpl();
        RackArrayImpl rack = new RackArrayImpl(size, Class.forName(rackType));
        rack.setLocation(location);
        for (int i = 0; i < size; i++) {
            Device device = deviceService.readDevice(reader);
            if (device != null) {
                rack.insertDevToSlot(device, i);
            }
        }
        return rack;
    }

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack == null) {
            return;
        }
        if (outputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("OutputStream cannot be null");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage(), illegalArgumentException);
            throw illegalArgumentException;
        }
        service.outputLocation(rack.getLocation(), outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(rack.getSize());
        dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());

        DeviceServiceImpl deviceService = new DeviceServiceImpl();

        for (int i = 0; i < rack.getSize(); i++) {
            if (rack.getDevAtSlot(i) == null) {
                dataOutputStream.writeUTF("\n");
            } else {
                dataOutputStream.writeUTF("\t");
                deviceService.outputDevice(rack.getDevAtSlot(i), dataOutputStream);
            }
        }
    }

    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("InputStream cannot be null");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage(), illegalArgumentException);
            throw illegalArgumentException;
        }
        Location location = service.inputLocation(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DeviceServiceImpl deviceService = new DeviceServiceImpl();
        int size = dataInputStream.readInt();
        Class typeClazz = Class.forName(dataInputStream.readUTF());
        Rack rack = new RackArrayImpl(size, typeClazz);
        rack.setLocation(location);
        for (int i = 0; i < rack.getSize(); i++) {
            if (!dataInputStream.readUTF().equals("\n")) {
                Device device = null;
                try {
                    device = deviceService.inputDevice(dataInputStream);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                rack.insertDevToSlot(device, i);
            }
        }
        return rack;
    }

    private FeelableEntity.Field[] setDevValues(DataInputStream dataInputStream) throws IOException {
        int in = dataInputStream.readInt();
        String type = dataInputStream.readUTF(dataInputStream);
        String module = dataInputStream.readUTF(dataInputStream);
        String manufacturer = dataInputStream.readUTF(dataInputStream);
        long productionDate = dataInputStream.readLong();
        FeelableEntity.Field[] fields = new FeelableEntity.Field[5];
        fields[0] = new FeelableEntity.Field(Integer.class, in);
        fields[3] = new FeelableEntity.Field(String.class, module.equals("\\") ? null : module);
        fields[2] = new FeelableEntity.Field(String.class, manufacturer.equals("\\") ? null : manufacturer);
        fields[4] = new FeelableEntity.Field(Date.class, productionDate == -1 ? null : new Date(productionDate));
        return fields;
    }

    private FeelableEntity.Field[] setDevValues(String objectValue, Class clazz) throws IOException {
        FeelableEntity.Field[] fields = new FeelableEntity.Field[7];
        String[] valueOfDevice = objectValue.split("\\|");
        String inTypeValue = null;
        int i = 0;
        for (; i < valueOfDevice.length; i++) {
            if (valueOfDevice[i].contains("[")) {
                inTypeValue = valueOfDevice[i].concat(" ").concat(valueOfDevice[i + 1]);
                break;
            }
        }
        CharArrayReader charArrayReader = new CharArrayReader(inTypeValue.toCharArray());
        StreamTokenizer streamTokenizer = new StreamTokenizer(charArrayReader);
        streamTokenizer.whitespaceChars(91, 93);
        if (streamTokenizer.nextToken() != StreamTokenizer.TT_EOL || streamTokenizer.ttype == 32) {
            fields[0] = new FeelableEntity.Field(Integer.class, (int) streamTokenizer.nval);
        }
        for (; i < valueOfDevice.length; i++) {
            if (i == 1) {
                fields[3] = parsStringReader(valueOfDevice[1]);
            }
            if (i == 2) {
                fields[2] = parsStringReader(valueOfDevice[2]);
            }
            if (i == 3) {
                fields[4] = parsDateReader(valueOfDevice[3]);
                i++;
                break;
            }
        }
        if (clazz.getName().equals(Battery.class.getName())) {
            fields[5] = parsIntReader(valueOfDevice[i]);
            return fields;
        }
        if (clazz.getName().equals(Router.class.getName())) {
            fields[5] = parsIntReader(valueOfDevice[i]);
            return fields;
        }
        if (clazz.getName().equals(Switch.class.getName())) {
            fields[5] = parsIntReader(valueOfDevice[i]);
            i++;
            fields[6] = parsIntReader(valueOfDevice[i]);
            return fields;
        }
        if (clazz.getName().equals(WifiRouter.class.getName())) {
            fields[5] = parsIntReader(valueOfDevice[i]);
            i++;
            fields[6] = parsStringReader(valueOfDevice[i]);
            return fields;
        }
        return fields;
    }

    private FeelableEntity.Field parsDateReader(String valueField) {
        if (valueField.contains("-1")) {
            return new FeelableEntity.Field(Date.class, null);
        } else {
            return new FeelableEntity.Field(Date.class, new Date((long) Long.parseLong(valueField.trim())));
        }
    }

    private FeelableEntity.Field parsStringReader(String valueField) {
        if (valueField.equals(" ")) {
            return new FeelableEntity.Field(String.class, null);
        } else if (valueField.length() == 2) {
            return new FeelableEntity.Field(String.class, valueField.trim());
        } else {
            return new FeelableEntity.Field(String.class, valueField.substring(1, valueField.length() - 1));
        }
    }

    private FeelableEntity.Field parsIntReader(String valueField) {
        return new FeelableEntity.Field(Integer.class, Integer.parseInt(valueField.trim()));
    }
}