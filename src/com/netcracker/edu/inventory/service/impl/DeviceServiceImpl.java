package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.*;
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

    }

    @Override
    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("OutputStream should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (device != null) {
            String massage = "null";
            dataOutputStream.writeUTF(device.getClass().getName());
            dataOutputStream.writeInt(device.getIn());
            dataOutputStream.writeUTF(device.getType());
            dataOutputStream.writeUTF(device.getModel() == null ? "null" : device.getModel());
            dataOutputStream.writeUTF(device.getManufacturer() == null ? "null" : device.getManufacturer());
            dataOutputStream.writeLong(device.getProductionDate() == null ? (long) -1 : (long) device.getProductionDate().getTime());
        }
    }

    @Override
    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {

    }

    @Override
    public Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException {
        return null;
    }
}
