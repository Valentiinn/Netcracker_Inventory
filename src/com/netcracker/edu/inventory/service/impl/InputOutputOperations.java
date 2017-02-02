package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.Service;
import com.netcracker.edu.location.impl.ServiceImpl;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;

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
    private List<FeelableEntity.Field> fieldList;

    private void outputDeviceConnection(Device device, Connection connection, OutputStream outputStream) throws IOException {
        if (device == null && connection == null) {
            return;
        }
        validator.validate(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (connection != null) {
            dataOutputStream.writeUTF(connection.getClass().getName());
            fieldList = connection.getAllFieldsList();
        } else {
            dataOutputStream.writeUTF(device.getClass().getName());
            fieldList = device.getAllFieldsList();
        }
        writeOutputStream(dataOutputStream);
    }

    private void writeWriter(Writer writer) throws IOException {
        for (FeelableEntity.Field field : fieldList) {
            if (field.getValue() == null) {
                writer.write(field.getType() == Date.class ? " -1 |" : " |");
            } else {
                if (fieldList.indexOf(field) != 0) {
                    writer.write(" ");
                }
                Class type = field.getType();
                if (fieldList.indexOf(field) == 0 && type == int.class) {
                    writer.write("[" + field.getValue() + "]");
                    continue;
                }
                if (field.getType() == Device.class) {
                    writer.write("");
                }
                if (field.getType() == Date.class) {
                    writer.write(((Date) field.getValue()).getTime() + "");
                } else if (field.getType() == ConnectorType.class) {
                    writer.write(((ConnectorType) field.getValue()).name());
                } else if (field.getType() == Connection.class) {
                    writer.write("");
                } else if (field.getType() == Array.class) {
                    Device[] devicesArray = (Device[]) field.getValue();
                    writer.write(devicesArray.length + " |");
                    for (Device device : devicesArray) {
                        writer.write(" |");
                    }
                } else if (field.getType() == Set.class) {
                    Set<Device> deviceSet = (Set<Device>) field.getValue();
                    writer.write(deviceSet.size() + " |");
                    for (Device device : deviceSet) {
                        writer.write(" |");
                    }
                } else if (field.getType() == List.class) {
                    List<Connection> connectionList = (List<Connection>) field.getValue();
                    writer.write(connectionList.size() + " |");
                    for (Connection connection : connectionList) {
                        writer.write(" |");
                    }
                } else {
                    writer.write(field.getValue().toString());
                }
                writer.write(" |");
            }
        }

        writer.write(LineSeparator.Windows);
    }

    private void writeOutputStream(DataOutputStream dataOutputStream) throws IOException {
        for (FeelableEntity.Field field : fieldList) {
            if (field.getValue() == null && field.getType() != Date.class) {
                dataOutputStream.writeUTF("\n");
            } else {
                Class type = field.getType();
                if (type == int.class) {
                    dataOutputStream.writeInt((Integer) field.getValue());
                } else if (field.getType() == Device.class) {
                    dataOutputStream.writeUTF("\n");
                } else if (field.getType() == Date.class) {
                    dataOutputStream.writeLong(field.getValue() == null ? -1 :
                            ((Date) field.getValue()).getTime());
                } else if (field.getType() == ConnectorType.class) {
                    dataOutputStream.writeUTF(((ConnectorType) field.getValue()).name());
                } else if (field.getType() == TwistedPair.Type.class) {
                    dataOutputStream.writeUTF(((TwistedPair.Type) field.getValue()).getFullName());
                } else if (field.getType() == OpticFiber.Mode.class) {
                    dataOutputStream.writeUTF(((OpticFiber.Mode) field.getValue()).getFullName());
                } else if (field.getType() == Array.class) {
                    Device[] devicesArray = (Device[]) field.getValue();
                    dataOutputStream.writeInt(devicesArray.length);
                    for (Device device : devicesArray) {
                        dataOutputStream.writeUTF("\n");
                    }
                } else if (field.getType() == Connection.class) {
                    dataOutputStream.writeUTF("\n");
                } else if (field.getType() == Set.class) {
                    Set<Device> deviceSet = (Set<Device>) field.getValue();
                    dataOutputStream.writeInt(deviceSet.size());
                    for (Device device : deviceSet) {
                        dataOutputStream.writeUTF("\n");
                    }
                } else if (field.getType() == List.class) {
                    List<Connection> connectionList = (List<Connection>) field.getValue();
                    dataOutputStream.writeInt(connectionList.size());
                    for (Connection connection : connectionList) {
                        dataOutputStream.writeUTF("\n");
                    }
                } else {
                    dataOutputStream.writeUTF(field.getValue().toString());
                }
            }
        }
    }

    private Connection getConnection(Class clazz, StringTokenizer stringTokenizer, DataInputStream dataInputStream) throws ClassNotFoundException, IOException {
        if (clazz == OpticFiber.class) {
            OpticFiber opticFiber = new OpticFiber();
            fieldList = opticFiber.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            opticFiber.fillAllFields(fieldList);
            return opticFiber;
        }
        if (clazz == ThinCoaxial.class) {
            ThinCoaxial thinCoaxial = new ThinCoaxial();
            fieldList = thinCoaxial.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            thinCoaxial.fillAllFields(fieldList);
            return thinCoaxial;
        }
        if (clazz == TwistedPair.class) {
            TwistedPair twistedPair = new TwistedPair();
            fieldList = twistedPair.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            twistedPair.fillAllFields(fieldList);
            return twistedPair;
        }
        if (clazz == Wireless.class) {
            Wireless wireless = new Wireless();
            fieldList = wireless.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            wireless.fillAllFields(fieldList);
            return wireless;
        }
        return null;
    }

    private Device getDevice(Class clazz, StringTokenizer stringTokenizer, DataInputStream dataInputStream) throws ClassNotFoundException, IOException {
        if (clazz == Battery.class) {
            Battery battery = new Battery();
            fieldList = battery.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            battery.fillAllFields(fieldList);
            return battery;
        }
        if (clazz == Router.class) {
            Router router = new Router();
            fieldList = router.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            router.fillAllFields(fieldList);
            return router;
        }
        if (clazz == Switch.class) {
            Switch switcher = new Switch();
            fieldList = switcher.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            switcher.fillAllFields(fieldList);
            return switcher;
        }
        if (clazz == WifiRouter.class) {
            WifiRouter wifiRouter = new WifiRouter();
            fieldList = wifiRouter.getAllFieldsList();
            parserUtils.choiceOfMethod(stringTokenizer, dataInputStream, fieldList);
            wifiRouter.fillAllFields(fieldList);
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
        if (rack == null) {
            return;
        }
        validator.validate(writer);
        com.netcracker.edu.location.impl.ServiceImpl locationService = new com.netcracker.edu.location.impl.ServiceImpl();
        locationService.writeLocation(rack.getLocation(), writer);
        writer.write(rack.getSize() + " ");
        writer.write(rack.getTypeOfDevices().getCanonicalName() + LineSeparator.Windows);
        for (int i = 0; i < rack.getSize(); i++) {
            Device device = rack.getDevAtSlot(i);
            if (device != null) {
                writeDevice(device, writer);
            } else {
                writer.write(LineSeparator.Windows);
            }
        }
    }

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        Rack rack;
        validator.validate(reader);
        com.netcracker.edu.location.impl.ServiceImpl locationService = new com.netcracker.edu.location.impl.ServiceImpl();
        Location location = locationService.readLocation(reader);
        StringTokenizer stringTokenizer = new StringTokenizer(parserUtils.lineRead(reader), " ");
        int in = Integer.parseInt(stringTokenizer.nextToken());
        Class clazz = Class.forName(stringTokenizer.nextToken());
        rack = new RackArrayImpl(in, clazz);
        rack.setLocation(location);
        for (int i = 0; i < rack.getSize(); i++) {
            Device device = readDevice(reader);
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

    public void writeConnection(Connection connection, Writer writer) throws IOException {
        if (connection == null) {
            return;
        }
        validator.validate(writer);
        if (!validator.isValidConnectionForWriteToStream(connection)) {
            IllegalArgumentException e = new IllegalArgumentException("Connection is not valid for write to stream");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        fieldList = connection.getAllFieldsList();
        writer.write(connection.getClass().getName() + LineSeparator.Windows);
        writeWriter(writer);
    }

    public Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        validator.validate(reader);
        String className = parserUtils.lineRead(reader);
        Class clazz;
        if (className.equals("")) {
            return null;
        } else {
            clazz = Class.forName(className);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(parserUtils.lineRead(reader), "|");
        return getConnection(clazz, stringTokenizer, null);
    }

    public void outputConnection(Connection connection, OutputStream outputStream) throws IOException {
        outputDeviceConnection(null, connection, outputStream);
    }

    public Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        validator.validate(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Class clazz = Class.forName(dataInputStream.readUTF());
        return getConnection(clazz, null, dataInputStream);
    }

    public void writeDevice(Device device, Writer writer) throws IOException {
        if (device == null) {
            return;
        }
        validator.validate(writer);
        if (!validator.isValidDeviceForWriteToStream(device)) {
            DeviceValidationException e = new DeviceValidationException("DeviceService.writeDevice", device);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        fieldList = device.getAllFieldsList();
        writer.write(device.getClass().getName() + LineSeparator.Windows);
        writeWriter(writer);
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        validator.validate(reader);
        String className = parserUtils.lineRead(reader);
        Class clazz;
        if (className.equals("")) {
            return null;
        } else {
            clazz = Class.forName(className);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(parserUtils.lineRead(reader), "[]|");
        return getDevice(clazz, stringTokenizer, null);
    }

    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        outputDeviceConnection(device, null, outputStream);
    }

    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        validator.validate(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Class clazz = Class.forName(dataInputStream.readUTF());
        return getDevice(clazz, null, dataInputStream);
    }
}
