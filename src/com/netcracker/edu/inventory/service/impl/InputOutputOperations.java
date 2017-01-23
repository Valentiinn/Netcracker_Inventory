package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.FeelableEntity.Field;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.Service;
import com.netcracker.edu.location.impl.ServiceImpl;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class InputOutputOperations {
    private static Logger LOGGER = Logger.getLogger(InputOutputOperations.class.getName());

    private Service service = new ServiceImpl();
    private Validator validator = new Validator();
    private ParserUtils parserUtils = new ParserUtils();

    public void writeDevice(Device device, Writer writer) throws IOException {
        if (device != null) {
            if (!validator.isValidDeviceForWriteToStream(device)) {
                DeviceValidationException e = new DeviceValidationException("DeviceService.writeDevice", device);
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            } else {
                validator.validate(writer);
            }
            List<Field> fields = device.getAllFieldsList();
            writer.write(device.getClass().getName());
            for (int i = 0; i < fields.size(); i++) {
                if (i == 0) {
                    writer.write("\n" + "[" + fields.get(i).getValue() + "] ");
                } else {
                    if (fields.get(i).getType() == List.class || fields.get(i).getType() == Set.class || fields.get(i).getType() == Connection.class) {
                        writeObjDevice(fields.get(i), writer);
                    } else {
                        writeField(fields.get(i), writer);
                    }
                }
            }
            writer.write("\n");
        }
    }

    public void writeConnection(Connection connection, Writer writer) throws IOException {
        if (connection != null) {
            if (!validator.isValidConnectionForWriteToStream(connection)) {
                IllegalArgumentException e = new IllegalArgumentException("Connection is not valid for write to stream");
                LOGGER.log(Level.SEVERE, e.getMessage());
                throw e;
            } else {
                validator.validate(writer);
            }
            List<Field> fields = connection.getAllFieldsList();
            writer.write(connection.getClass().getName() + "\n");

            writer.write(fields.get(0).getValue() + " |");
            for (int i = 1; i < fields.size(); i++) {
                writer.write(parserUtils.getPresentationOfPropertyForWriter(fields.get(i)) + "|");
            }
            writer.write("\n");
        }
    }

    public Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        String className = className(inputStream);
        Class clazz = clazz(className);
        return getConnection(clazz, dataInputStream(inputStream));
    }

    public Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        validator.validate(reader);

        Class clazz;
        String className = readPropertyLine(reader);
        clazz = Class.forName(className);
        String propertyLine = readPropertyLine(reader);

        if (clazz.getName().equals(TwistedPair.class.getName())) {
            TwistedPair twistedPair = new TwistedPair();
            twistedPair.fillAllFields(parseFields(propertyLine, twistedPair));
            return twistedPair;
        }

        if (clazz.getName().equals(OpticFiber.class.getName())) {
            OpticFiber opticFiber = new OpticFiber();
            opticFiber.fillAllFields(parseFields(propertyLine, opticFiber));
            return opticFiber;
        }

        if (clazz.getName().equals(Wireless.class.getName())) {
            Wireless wireless = new Wireless();
            wireless.fillAllFields(parseFields(propertyLine, wireless));
            return wireless;
        }

        if (clazz.getName().equals(ThinCoaxial.class.getName())) {
            ThinCoaxial thinCoaxial = new ThinCoaxial();
            thinCoaxial.fillAllFields(parseFields(propertyLine, thinCoaxial));
            return thinCoaxial;
        }
        return null;
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        validator.validate(reader);
        Class clazz;
        String className = readPropertyLine(reader);
        if (className.length() == 0) {
            return null;
        }
        clazz = Class.forName(className);
        String propertyLine = readPropertyLine(reader);
        if (clazz.getName().equals(Battery.class.getName())) {
            Battery battery = new Battery();
            battery.fillAllFields(parseFields(propertyLine, battery));
            return battery;
        }
        if (clazz.getName().equals(Router.class.getName())) {
            Router router = new Router();
            router.fillAllFields(parseFields(propertyLine, router));
            return router;
        }
        if (clazz.getName().equals(Switch.class.getName())) {
            Switch aSwitch = new Switch();
            aSwitch.fillAllFields(parseFields(propertyLine, aSwitch));
            return aSwitch;
        }
        if (clazz.getName().equals(WifiRouter.class.getName())) {
            WifiRouter wifiRouter = new WifiRouter();
            wifiRouter.fillAllFields(parseFields(propertyLine, wifiRouter));
            return wifiRouter;
        }
        return null;
    }

    public void serializeRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack == null) {
            return;
        }
        validator.validate(outputStream);
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(rack);
    }

    public Rack deserializeRack(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        validator.validate(inputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        RackArrayImpl rack = (RackArrayImpl) objectInputStream.readObject();
        return rack;
    }


    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        if (device == null) {
            return;
        }
        if (outputStream == null) {
            validator.validate(outputStream);
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(device);
    }

    public Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        if (inputStream == null) {
            validator.validate(inputStream);
        }
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Device device = (Device) objectInputStream.readObject();
        return device;
    }


    public void writeRack(Rack rack, Writer writer) throws IOException {
        if (rack != null) {
            validator.validate(writer);
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
        validator.validate(reader);
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

    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        validator.validate(inputStream);
        Location location = service.inputLocation(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DeviceServiceImpl deviceService = new DeviceServiceImpl();
        int size = dataInputStream.readInt();
        Class typeClazz = Class.forName(dataInputStream.readUTF());
        Rack rack = new RackArrayImpl(size, typeClazz);
        rack.setLocation(location);
        for (int i = 0; i < rack.getSize(); i++) {
            if (!dataInputStream.readUTF().equals("\n")) {
                Device device = deviceService.inputDevice(dataInputStream);
                rack.insertDevToSlot(device, i);
            }
        }
        return rack;
    }

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack == null) {
            return;
        }
        validator.validate(outputStream);
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

    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (device != null) {
            validator.validate(outputStream);

            List<Field> fields = device.getAllFieldsList();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeUTF(device.getClass().getName());

            for (Field field : fields) {
                if (field.getType() == Integer.class) {
                    dataOutputStream.writeInt(parserUtils.getPresentationOfProperty((Integer) field.getValue()));
                }
                if (field.getType() == String.class) {
                    dataOutputStream.writeUTF(parserUtils.getPresentationOfProperty((String) field.getValue()));
                }
                if (field.getType() == Date.class) {
                    dataOutputStream.writeLong(parserUtils.getPresentationOfProperty((Date) field.getValue()));
                }
                if (field.getType() == ConnectorType.class) {
                    dataOutputStream.writeUTF(parserUtils.getPresentationOfProperty(((ConnectorType) field.getValue()).name()));
                }
                if (field.getType() == Connection.class) {
                    dataOutputStream.writeUTF(parserUtils.getPresentationOfProperty((Connection) field.getValue()));
                }
                if (field.getType() == Array.class) {
                    dataOutputStream.writeInt(parserUtils.getPresentationOfProperty((Connection[]) field.getValue()));
                }
            }
        }
    }

    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        String className = className(inputStream);
        Class clazz = clazz(className);
        return getDevice(clazz, dataInputStream(inputStream));
    }

    public void outputConnection(Connection connection, OutputStream outputStream) throws IOException {
        if (connection != null) {

            validator.validate(outputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeUTF(connection.getClass().getName());

            List<Field> fields = connection.getAllFieldsList();

            for (Field field : fields) {
                if (field.getType() == Integer.class) {
                    dataOutputStream.writeInt(parserUtils.getPresentationOfProperty((Integer) field.getValue()));
                }
                if (field.getType() == String.class) {
                    dataOutputStream.writeUTF(parserUtils.getPresentationOfProperty((String) field.getValue()));
                }
                if (field.getType() == Date.class) {
                    dataOutputStream.writeLong(parserUtils.getPresentationOfProperty((Date) field.getValue()));
                }
                if (field.getType() == TwistedPair.Type.class) {
                    dataOutputStream.writeUTF(parserUtils.getPresentationOfProperty(((TwistedPair.Type) field.getValue()).name()));
                }
                if (field.getType() == OpticFiber.Mode.class) {
                    dataOutputStream.writeUTF(parserUtils.getPresentationOfProperty(((OpticFiber.Mode) field.getValue()).name()));
                }
                if (field.getType() == Device.class) {
                    dataOutputStream.writeUTF("[ ]");
                }
                if (field.getType() == List.class) {
                    List<Device> devices = (List<Device>) field.getValue();
                    dataOutputStream.writeInt(devices.size());
                    for (Device device : devices) {
                        dataOutputStream.writeUTF("[ ]");
                    }
                }
                if (field.getType() == Set.class) {
                    Set<Device> devices = (Set<Device>) field.getValue();
                    dataOutputStream.writeInt(devices.size());
                    for (Device device : devices) {
                        dataOutputStream.writeUTF("[ ]");
                    }
                }
            }
        }
    }

    private Device getDevice(Class clazz, DataInputStream dataInputStream) throws IOException {
        if (clazz == Battery.class) {
            Battery battery = new Battery();
            battery.fillAllFields(getFieldsFromDataInputStream(dataInputStream, battery));
            return battery;
        }
        if (clazz == Router.class) {
            Router router = new Router();
            router.fillAllFields(getFieldsFromDataInputStream(dataInputStream, router));
            return router;
        }
        if (clazz == Switch.class) {
            Switch aSwitch = new Switch();
            aSwitch.fillAllFields(getFieldsFromDataInputStream(dataInputStream, aSwitch));
            return aSwitch;
        }
        if (clazz == WifiRouter.class) {
            WifiRouter wifiRouter = new WifiRouter();
            wifiRouter.fillAllFields(getFieldsFromDataInputStream(dataInputStream, wifiRouter));
            return wifiRouter;
        }
        return null;
    }

    private Connection getConnection(Class clazz, DataInputStream dataInputStream) throws IOException, ClassNotFoundException {
        if (clazz == OpticFiber.class) {
            OpticFiber opticFiber = new OpticFiber();
            opticFiber.fillAllFields(getFieldsFromDataInputStream(dataInputStream, opticFiber));
            return opticFiber;
        }
        if (clazz == TwistedPair.class) {
            TwistedPair twistedPair = new TwistedPair();
            twistedPair.fillAllFields(getFieldsFromDataInputStream(dataInputStream, twistedPair));
            return twistedPair;
        }
        if (clazz == Wireless.class) {
            Wireless wireless = new Wireless();
            wireless.fillAllFields(getFieldsFromDataInputStream(dataInputStream, wireless));
            return wireless;
        }
        if (clazz == ThinCoaxial.class) {
            ThinCoaxial thinCoaxial = new ThinCoaxial();
            thinCoaxial.fillAllFields(getFieldsFromDataInputStream(dataInputStream, thinCoaxial));
            return thinCoaxial;
        }
        return null;
    }


    private DataInputStream dataInputStream(InputStream inputStream) {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        return dataInputStream;
    }

    private String className(InputStream inputStream) throws IOException {
        validator.validate(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String className = dataInputStream.readUTF();
        return className;
    }

    private Class clazz(String className) throws ClassNotFoundException {
        Class clazz = Class.forName(className);
        return clazz;
    }

    private List<FeelableEntity.Field> getFieldsFromDataInputStream(DataInputStream dataInputStream, Device device) throws IOException {
        List<FeelableEntity.Field> fields = device.getAllFieldsList();
        for (FeelableEntity.Field field : fields) {
            if (field.getType() == Integer.class) {
                field.setValue(getIntFromDataInputStream(dataInputStream));
            }

            if (field.getType() == String.class) {
                field.setValue(getStringFromDataInputStream(dataInputStream));
            }

            if (field.getType() == Date.class) {
                field.setValue(getDateFromDataInputStream(dataInputStream));
            }

            if (field.getType() == ConnectorType.class) {
                field.setValue(ConnectorType.valueOf(getStringFromDataInputStream(dataInputStream)));
            }

            if (field.getType() == Connection.class) {
                dataInputStream.readUTF();
                field.setValue(null);
            }

            if (field.getType() == Array.class) {
                field.setValue(new Connection[dataInputStream.readInt()]);
            }
        }
        return fields;
    }

    private List<Field> getFieldsFromDataInputStream(DataInputStream dataInputStream, Connection connection) throws IOException, ClassNotFoundException {
        List<Field> fields = connection.getAllFieldsList();
        for (Field field : fields) {
            if (field.getType() == Integer.class) {
                field.setValue(getIntFromDataInputStream(dataInputStream));
            }

            if (field.getType() == String.class) {
                field.setValue(getStringFromDataInputStream(dataInputStream));
            }

            if (field.getType() == TwistedPair.Type.class) {
                field.setValue(TwistedPair.Type.valueOf(getStringFromDataInputStream(dataInputStream)));
            }

            if (field.getType() == OpticFiber.Mode.class) {
                field.setValue(OpticFiber.Mode.valueOf(getStringFromDataInputStream(dataInputStream)));
            }

            if (field.getType() == Device.class) {
                dataInputStream.readUTF();
            }

            if (field.getType() == List.class) {
                int size = dataInputStream.readInt();
                List<Device> devices = new ArrayList<Device>(size);
                for (int i = 0; i < size - 1; i++) {
                    dataInputStream.readUTF();
                }
                field.setValue(devices);
            }

            if (field.getType() == Set.class) {
                int size = dataInputStream.readInt();
                Set<Device> devices = new HashSet<Device>(size);
                for (int i = 0; i < size; i++) {
                    dataInputStream.readUTF();
                }
                field.setValue(devices);
            }
        }
        return fields;
    }

    private int getIntFromDataInputStream(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readInt();
    }

    private String getStringFromDataInputStream(DataInputStream dataInputStream) throws IOException {
        String stringValue = dataInputStream.readUTF();
        if (stringValue.equals("[ ]")) {
            return null;
        }
        return stringValue;
    }


    private Date getDateFromDataInputStream(DataInputStream dataInputStream) throws IOException {
        long time = dataInputStream.readLong();
        if (time != -1) {
            return new Date(time);
        }
        return null;
    }


    private String readPropertyLine(Reader reader) throws IOException {
        StringBuilder propertyLine = new StringBuilder();
        for (char c = 0; c != '\n'; c = (char) reader.read()) {
            propertyLine.append(c);
        }
        return propertyLine.toString().trim();
    }

    private List<Field> parseFields(String propertyLine, Connection connection) {
        List<Field> fields = connection.getAllFieldsList();

        String[] properties = new String[fields.size()];

        for (int i = 0; i < fields.size(); i++) {
            properties[i] = propertyLine.split("\\|")[i];
        }

        fields.get(0).setValue(properties[0].trim());

        for (int i = 1; i < fields.size(); i++) {
            Field field = fields.get(i);

            if (field.getType() == Integer.class) {
                field.setValue(parserUtils.parseInteger(properties[i]));
            }

            if (field.getType() == String.class) {
                field.setValue(parserUtils.parseString(properties[i]));
            }

            if (field.getType() == Date.class) {
                field.setValue(parserUtils.parseDate(properties[i]));
            }

            if (field.getType() == Device.class) {
                field.setValue(parserUtils.parseDevice(properties[i]));
            }

            if (field.getType() == Set.class) {
                field.setValue(parserUtils.parseSet(properties[i]));
            }

            if (field.getType() == List.class) {
                field.setValue(parserUtils.parseList(properties[i]));
            }

            if (field.getType() == TwistedPair.Type.class) {
                field.setValue(parserUtils.parseType(properties[i]));
            }

            if (field.getType() == OpticFiber.Mode.class) {
                field.setValue(parserUtils.parseMode(properties[i]));
            }

            if (field.getType() == Array.class) {
                field.setValue(parserUtils.parseArray(properties[i]));
            }
        }
        return fields;
    }

    private List<Field> parseFields(String propertyLine, Device device) {
        List<Field> fields = device.getAllFieldsList();

        String[] properties = new String[fields.size()];

        properties[0] = propertyLine.split(" ")[0];
        properties[1] = propertyLine.split("\\|")[0].split(" ")[1];

        for (int i = 2; i < fields.size(); i++) {
            properties[i] = propertyLine.split("\\|")[i - 1];
        }

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);

            if (field.getType() == Integer.class) {
                field.setValue(parserUtils.parseInteger(properties[i]));
            }

            if (field.getType() == String.class) {
                field.setValue(parserUtils.parseString(properties[i]));
            }

            if (field.getType() == Date.class) {
                field.setValue(parserUtils.parseDate(properties[i]));
            }

            if (field.getType() == Array.class) {
                field.setValue(parserUtils.parseArray(properties[i]));
            }

            if (field.getType() == ConnectorType.class) {
                field.setValue(parserUtils.parseConnectorType(properties[i]));
            }

            if (field.getType() == Connection.class) {
                field.setValue(null);
            }
        }
        return fields;
    }

    private void writeObjDevice(Field field, Writer writer) throws IOException {
        if (field.getType() == List.class) {
            writer.write(((List<Connection>) field.getValue()).size() + " | ");
            for (Connection connection : ((List<Connection>) field.getValue())) {
                writeConnection(null, writer);
            }
        }
        if (field.getType() == Connection.class) {
            writer.write("| ");
        }
    }

    private void writeField(Field field, Writer writer) throws IOException {
        if (field != null) {
            if (field.getType() == Date.class) {
                writer.write(field.getValue() == null ? -1 + " | " : ((Date) field.getValue()).getTime() + " | ");
            }
            if (field.getType() == String.class) {
                writer.write(field.getValue() == null ? "| " : field.getValue() + " | ");
            }
            if (field.getType() == Integer.class) {
                writer.write(field.getValue() + " | ");
            }
            if (field.getType() == OpticFiber.Mode.class) {
                writer.write((field.getValue()) + " | ");
            }
            if (field.getType() == TwistedPair.Type.class) {
                writer.write((field.getValue()) + " | ");
            }
            if (field.getType() == ConnectorType.class) {
                writer.write(field.getValue() == null ? "| " : field.getValue() + " | ");
            }
        }
    }
}
