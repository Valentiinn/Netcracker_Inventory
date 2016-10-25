package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    public void writeDevice(Device device, Writer writer) throws IOException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation writeDevice not supported yet");
        throw e;
    }

    @Override
    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation readDevice not supported yet");
        throw e;
    }

    @Override
    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (device == null) {
            return;
        }

        if (outputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("OutputStream cannot be null");
            LOGGER.log(Level.SEVERE, "Thrown exception", illegalArgumentException);
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
            LOGGER.log(Level.SEVERE, "InputStream cannot be null", illegalArgumentException);
            throw illegalArgumentException;
        }

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Class clazz = Class.forName(dataInputStream.readUTF());
        if (clazz.isInstance(new Battery())) {
            Battery battery = new Battery();

            int in = dataInputStream.readInt();
            String type = dataInputStream.readUTF(dataInputStream);
            String module = dataInputStream.readUTF(dataInputStream);
            String manufacturer = dataInputStream.readUTF(dataInputStream);
            long productionDate = dataInputStream.readLong();
            int chargeVolume = dataInputStream.readInt();

            if (in > 0) {
                battery.setIn(in);
            }

            battery.setType(type);
            battery.setModel(module.equals("\n") ? null : module);
            battery.setManufacturer(manufacturer.equals("\n") ? null : manufacturer);
            battery.setProductionDate(productionDate == -1 ? null : new Date(productionDate));
            battery.setChargeVolume(chargeVolume);

            return battery;
        }
        if (clazz.isInstance(new Router())) {
            Router router = new Router();

            int in = dataInputStream.readInt();
            String type = dataInputStream.readUTF(dataInputStream);
            String module = dataInputStream.readUTF(dataInputStream);
            String manufacturer = dataInputStream.readUTF(dataInputStream);
            long productionDate = dataInputStream.readLong();
            int dateRate = dataInputStream.readInt();

            if (in > 0) {
                router.setIn(in);
            }

            router.setType(type);
            router.setModel(module.equals("\n") ? null : module);
            router.setManufacturer(manufacturer.equals("\n") ? null : manufacturer);
            router.setProductionDate(productionDate == -1 ? null : new Date(productionDate));
            router.setDataRate(dateRate);

            return router;

        }
        if (clazz.isInstance(new Switch())) {
            Switch switcher = new Switch();
            int in = dataInputStream.readInt();
            String type = dataInputStream.readUTF(dataInputStream);
            String module = dataInputStream.readUTF(dataInputStream);
            String manufacturer = dataInputStream.readUTF(dataInputStream);
            long productionDate = dataInputStream.readLong();
            int dateRate = dataInputStream.readInt();
            int numberOfPorts = dataInputStream.readInt();

            if (in > 0) {
                switcher.setIn(in);
            }

            switcher.setType(type);
            switcher.setModel(module.equals("\n") ? null : module);
            switcher.setManufacturer(manufacturer.equals("\n") ? null : manufacturer);
            switcher.setProductionDate(productionDate == -1 ? null : new Date(productionDate));
            switcher.setDataRate(dateRate);
            switcher.setNumberOfPorts(numberOfPorts);
            return switcher;

        }
        if (clazz.isInstance(new WifiRouter())) {
            WifiRouter wifiRouter = new WifiRouter();
            int in = dataInputStream.readInt();
            String type = dataInputStream.readUTF(dataInputStream);
            String module = dataInputStream.readUTF(dataInputStream);
            String manufacturer = dataInputStream.readUTF(dataInputStream);
            long productionDate = dataInputStream.readLong();
            int dateRate = dataInputStream.readInt();
            String securityProtocol = dataInputStream.readUTF();

            if (in > 0) {
                wifiRouter.setIn(in);
            }

            wifiRouter.setType(type);
            wifiRouter.setModel(module.equals("\n") ? null : module);
            wifiRouter.setManufacturer(manufacturer.equals("\n") ? null : manufacturer);
            wifiRouter.setProductionDate(productionDate == -1 ? null : new Date(productionDate));
            wifiRouter.setDataRate(dateRate);
            wifiRouter.setSecurityProtocol(securityProtocol.equals("\n") ? null : securityProtocol);
            return wifiRouter;
        }
        return null;
    }

    @Override
    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation serializeDevice not supported yet");
        throw e;
    }

    @Override
    public Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation deserializeDevice not supported yet");
        throw e;
    }
}