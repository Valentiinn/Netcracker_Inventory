package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class DeviceServiceImpl implements DeviceService {

    static protected Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

    @Override
    public boolean isCastableTo(Device device, Class clazz) {
        if (device == null || clazz == null) {
            return false;
        }
        return clazz.isInstance(device);
    }

    @Override
    public boolean isValidDeviceForInsertToRack(Device device) {
        if (device == null || device.getIn() == 0 || device.getType() == null) {
            LOGGER.log(Level.SEVERE, "This device is incorrect to add");
            return false;
        }
        return true;
    }

    @Override
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

    @Override
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


    @Override
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

    @Override
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

    @Override
    public boolean isValidDeviceForWriteToStream(Device device) {
        if (device == null) {
            return false;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(device.getType());
        builder.append(device.getModel());
        builder.append(device.getManufacturer());

        if (device instanceof WifiRouter) {
            WifiRouter wifiRouter = (WifiRouter) device;
            builder.append(wifiRouter.getSecurityProtocol());
        }
        if (builder.toString().contains("|")) {
            return false;
        }
        return true;
    }

    @Override
    public void writeDevice(Device device, Writer writer) throws IOException {
        if (device == null) {
            return;
        }
        if (writer == null) {
            IllegalArgumentException e = new IllegalArgumentException("Writer can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        if (!isValidDeviceForWriteToStream(device)) {
            DeviceValidationException e = new DeviceValidationException("DeviceService.writeDevice", device);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        DeviceService deviceService = new DeviceServiceImpl();

        bufferedWriter.write(device.getClass().getName());
        bufferedWriter.write("\n");
        bufferedWriter.write("[" + device.getIn() + "] ");
        bufferedWriter.write(device.getType() + " |");
        bufferedWriter.write(device.getModel() == null ? " |" : " " + device.getModel() + " |");
        bufferedWriter.write(device.getManufacturer() == null ? " |" : " " + device.getManufacturer() + " |");
        bufferedWriter.write(device.getProductionDate() == null ? " -1 |" : " " + device.getProductionDate().getTime() + " |");


        if (deviceService.isCastableTo(device, Battery.class)) {
            bufferedWriter.write(" " + ((Battery) device).getChargeVolume() + " |");
        }

        if (deviceService.isCastableTo(device, Router.class)) {
            bufferedWriter.write(" " + ((Router) device).getDataRate() + " |");
        }

        if (deviceService.isCastableTo(device, WifiRouter.class)) {
            String securityProtocol = ((WifiRouter) device).getSecurityProtocol();
            bufferedWriter.write(" " + securityProtocol == null ? " |" : securityProtocol + " |");
        }

        if (deviceService.isCastableTo(device, Switch.class)) {
            bufferedWriter.write(" " + ((Switch) device).getNumberOfPorts() + " |");
        }

        bufferedWriter.write("\n");
        bufferedWriter.flush();

    }

    @Override
    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Reader should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        DeviceServiceImpl deviceService = new DeviceServiceImpl();

        Class clazz = Class.forName(readNameOfClass(reader));

        if (deviceService.isCastableTo(new Battery(), clazz)) {
            Battery battery = new Battery();
            addDefParametrsDevFromReader(battery, reader);
            battery.setChargeVolume(Integer.valueOf(readNextValueFromReader(reader)));
            reader.read();
            return battery;
        }

        if (deviceService.isCastableTo(new Router(), clazz)) {
            Router router = new Router();
            addDefParametrsDevFromReader(router, reader);
            router.setDataRate(Integer.valueOf(readNextValueFromReader(reader)));
            reader.read();
            return router;
        }

        if (deviceService.isCastableTo(new Switch(), clazz)) {
            Switch aSwitch = new Switch();
            addDefParametrsDevFromReader(aSwitch, reader);
            aSwitch.setDataRate(Integer.valueOf(readNextValueFromReader(reader)));
            aSwitch.setNumberOfPorts(Integer.valueOf(readNextValueFromReader(reader)));
            reader.read();
            return aSwitch;
        }

        if (deviceService.isCastableTo(new WifiRouter(), clazz)) {
            WifiRouter wifiRouter = new WifiRouter();
            addDefParametrsDevFromReader(wifiRouter, reader);
            wifiRouter.setDataRate(Integer.valueOf(readNextValueFromReader(reader)));
            String securityProtocol = readNextValueFromReader(reader);
            wifiRouter.setSecurityProtocol(securityProtocol.isEmpty() ? " " : readNextValueFromReader(reader));
            reader.read();
            return wifiRouter;
        }

        ClassNotFoundException e = new ClassNotFoundException("Class not found");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private String readNameOfClass(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char value;
        boolean flag = true;
        while (flag) {
            value = (char) reader.read();
            if (value == '\n') break;
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }

    private String readNextValueFromReader(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char value;
        reader.read();
        boolean flag = true;
        while (flag) {
            value = (char) reader.read();
            if (value == '|') break;
            stringBuilder.append(value);
        }
        return stringBuilder.toString().trim();
    }

    private void addDefParametrsDevFromReader(Device device, Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char value;
        reader.read();
        boolean flag = true;
        while (flag) {
            value = (char) reader.read();
            if (value == ']') break;
            stringBuilder.append(value);
        }
        int in = Integer.parseInt(stringBuilder.toString());
        if (in > 0) {
            device.setIn(in);
        }
        device.setType(readNextValueFromReader(reader));

        String model = readNextValueFromReader(reader);
        String manufacturer = readNextValueFromReader(reader);
        Long productionDate = Long.parseLong(readNextValueFromReader(reader));
        if (model.isEmpty() == false)
            device.setModel(model);
        device.setManufacturer(manufacturer.isEmpty() == false ? manufacturer : "");
        device.setProductionDate(productionDate == -1 ? null : new Date(productionDate));
    }

    private Device setDevValues(Device device, DataInputStream dataInputStream) throws IOException {
        int in = dataInputStream.readInt();
        String type = dataInputStream.readUTF(dataInputStream);
        String module = dataInputStream.readUTF(dataInputStream);
        String manufacturer = dataInputStream.readUTF(dataInputStream);
        long productionDate = dataInputStream.readLong();

        if (in > 0) {
            device.setIn(in);
        }
        device.setType(type);
        device.setModel(module.equals("\n") ? null : module);
        device.setManufacturer(manufacturer.equals("\n") ? null : manufacturer);
        device.setProductionDate(productionDate == -1 ? null : new Date(productionDate));

        return device;
    }
}
