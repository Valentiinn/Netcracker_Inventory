package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.location.Service;
import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class InputOutputOperations {

    static protected Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());
    private Service service;

    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (device == null) {
            return;
        }

        if (outputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("OutputStream cannot be null");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage(), illegalArgumentException);
            throw illegalArgumentException;
        }

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(device.getClass().getName());
        dataOutputStream.writeInt(device.getIn());
        dataOutputStream.writeUTF(device.getType());
        dataOutputStream.writeUTF(device.getModel() == null ? "\n" : device.getModel());
        dataOutputStream.writeUTF(device.getManufacturer() == null ? "\n" : device.getManufacturer());
        dataOutputStream.writeLong(device.getProductionDate() == null ? -1 : device.getProductionDate().getTime());

        if (Battery.class.isInstance(device)) {
            Battery battery = (Battery) device;
            dataOutputStream.writeInt(battery.getChargeVolume());
        }
        if (Router.class.isInstance(device)) {
            Router router = (Router) device;
            dataOutputStream.writeInt(router.getDataRate());
        }
        if (Switch.class.isInstance(device)) {
            Switch aSwitch = (Switch) device;
            dataOutputStream.writeInt(aSwitch.getNumberOfPorts());
        }
        if (WifiRouter.class.isInstance(device)) {
            WifiRouter wifiRouter = (WifiRouter) device;
            dataOutputStream.writeUTF(wifiRouter.getSecurityProtocol() == null ? "\n" : wifiRouter.getSecurityProtocol());
        }


    }


    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("InputStream cannot be null");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage(), illegalArgumentException);
            throw illegalArgumentException;
        }

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Class clazz = Class.forName(dataInputStream.readUTF());
        if (clazz.isInstance(new Battery())) {
            Battery battery = new Battery();
            battery = (Battery) this.setDevValues(battery, dataInputStream);
            int chargeVolume = dataInputStream.readInt();
            battery.setChargeVolume(chargeVolume);
            return battery;
        }
        if (clazz.isInstance(new Router())) {
            Router router = new Router();
            router = (Router) this.setDevValues(router, dataInputStream);
            int dateRate = dataInputStream.readInt();
            router.setDataRate(dateRate);
            return router;

        }
        if (clazz.isInstance(new Switch())) {
            Switch switcher = new Switch();
            switcher = (Switch) this.setDevValues(switcher, dataInputStream);
            int dateRate = dataInputStream.readInt();
            int numberOfPorts = dataInputStream.readInt();
            switcher.setDataRate(dateRate);
            switcher.setNumberOfPorts(numberOfPorts);
            return switcher;

        }
        if (clazz.isInstance(new WifiRouter())) {
            WifiRouter wifiRouter = new WifiRouter();
            wifiRouter = (WifiRouter) this.setDevValues(wifiRouter, dataInputStream);
            int dateRate = dataInputStream.readInt();
            String securityProtocol = dataInputStream.readUTF();
            wifiRouter.setDataRate(dateRate);
            wifiRouter.setSecurityProtocol(securityProtocol.equals("\n") ? null : securityProtocol);
            return wifiRouter;
        }
        return null;
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

    public void writeDevice(Device device, Writer writer) throws IOException {
        if (device == null) {
            return;
        }

        if (writer == null) {
            IllegalArgumentException e = new IllegalArgumentException("Stream Writer can't be null");
            LOGGER.log(Level.SEVERE, "an exception was thrown", e);
            throw e;
        }
        writer.write(device.getClass().getName() + "\n");
        writer.write("[" + device.getIn() + "]| ");
        writer.write(formatString(device.getType()));
        writer.write(formatString(device.getModel()));
        writer.write(formatString(device.getManufacturer()));
        writer.write(" " + String.valueOf((device.getProductionDate() == null ? -1 : device.getProductionDate().getTime())) + " |");


        if (device instanceof Battery) {
            Battery battery = (Battery) device;
            writer.write(" " + battery.getChargeVolume() + " |");
        }

        if (device instanceof Router) {
            Router router = (Router) device;
            writer.write(" " + router.getDataRate() + " |");
        }

        if (device instanceof Switch) {
            Switch devSwitch = (Switch) device;
            writer.write(" " + devSwitch.getNumberOfPorts() + " |");
        }

        if (device instanceof WifiRouter) {
            WifiRouter wifiRouter = (WifiRouter) device;
            writer.write(formatString(wifiRouter.getSecurityProtocol()));
        }
        writer.write("\n");
    }


    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Stream Reader can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        String className = readNameOfClass(reader);
        if (!className.equals("")) {

            if (className.equals(Battery.class.getName())) {
                Battery battery = new Battery();
                readDevices(battery, reader);
                battery.setChargeVolume(Integer.parseInt(readDevValue(reader)));
                reader.read();
                return battery;
            }

            if (className.equals(Router.class.getName())) {
                Router router = new Router();
                readDevices(router, reader);
                router.setDataRate(Integer.parseInt(readDevValue(reader)));
                reader.read();
                return router;
            }

            if (className.equals(Switch.class.getName())) {
                Switch aSwitch = new Switch();
                readDevices(aSwitch, reader);
                aSwitch.setDataRate(Integer.parseInt(readDevValue(reader)));
                aSwitch.setNumberOfPorts(Integer.parseInt(readDevValue(reader)));
                reader.read();
                return aSwitch;
            }

            if (className.equals(WifiRouter.class.getName())) {
                WifiRouter wifiRouter = new WifiRouter();
                readDevices(wifiRouter, reader);
                wifiRouter.setDataRate(Integer.parseInt(readDevValue(reader)));
                String securityProtocol = readDevValue(reader);
                wifiRouter.setSecurityProtocol(securityProtocol);
                reader.read();
                return wifiRouter;
            }
        }
        ClassNotFoundException e = new ClassNotFoundException("Class not found");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private String readNameOfClass(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int read = reader.read();
        while (read != '\n') {
            stringBuilder.append((char) read);
            read = reader.read();
        }
        return stringBuilder.toString();
    }

    private String formatString(String string) {
        if (string == null)
            return " |";
        return " " + string + " |";
    }

    private Device setDevValues(Device device, DataInputStream dataInputStream) throws IOException {
        int in = dataInputStream.readInt();
        String module = dataInputStream.readUTF(dataInputStream);
        String manufacturer = dataInputStream.readUTF(dataInputStream);
        long productionDate = dataInputStream.readLong();

        if (in > 0) {
            device.setIn(in);
        }
        device.setModel(module.equals("\n") ? null : module);
        device.setManufacturer(manufacturer.equals("\n") ? null : manufacturer);
        device.setProductionDate(productionDate == -1 ? null : new Date(productionDate));

        return device;
    }


    private void readDevices(Device device, Reader reader) throws IOException {
        Integer in = Integer.parseInt(readDevValue(reader));
        readDevValue(reader);
        String model = readDevValue(reader);
        String manufacture = readDevValue(reader);
        Long productionDate = Long.parseLong(readDevValue(reader));
        if (in != 0) {
            device.setIn(in);
        }
        device.setModel(model);
        device.setManufacturer(manufacture);
        device.setProductionDate(productionDate == -1 ? null : new Date(productionDate));
    }

    private String readDevValue(Reader reader) {
        StringBuilder stringBuilder = new StringBuilder();
        int read = 0;
        try {
            read = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (read != '|') {
            stringBuilder.append((char) read);
            try {
                read = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.deleteCharAt(0);
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } else {
            return null;
        }
        return stringBuilder.toString();
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


    public Rack readRack(Reader reader) throws IOException {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Stream Reader can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        service.readLocation(reader);
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
        RackArrayImpl rack = null;
        try {
            rack = new RackArrayImpl(size, Class.forName(rackType));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < size; i++) {
            Device device = null;
            try {
                device = deviceService.readDevice(reader);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DeviceServiceImpl deviceService = new DeviceServiceImpl();
        int size = dataInputStream.readInt();
        Class typeClazz = Class.forName(dataInputStream.readUTF());
        Rack rack = new RackArrayImpl(size, typeClazz);
        service.inputLocation(inputStream);
        for (int i = 0; i < rack.getSize(); i++) {
            if (!dataInputStream.readUTF().equals("\n")) {
                Device device = deviceService.inputDevice(dataInputStream);
                rack.insertDevToSlot(device, i);
            }
        }
        return rack;
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
}
