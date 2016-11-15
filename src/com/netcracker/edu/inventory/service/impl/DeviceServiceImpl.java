package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.*;
import java.util.logging.Logger;

class DeviceServiceImpl implements DeviceService {

    private DeviceValidator deviceValidator = new DeviceValidator();
    private InputOutputOperations inputOutputOperations = new InputOutputOperations();

    @Override
    public boolean isCastableTo(Device device, Class clazz) {
        if (device != null && clazz != null) {
            if (clazz.isInstance(device)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidDeviceForInsertToRack(Device device) {
        return deviceValidator.isValidDeviceForInsertToRack(device);
    }

    @Override
    public boolean isValidDeviceForWriteToStream(Device device) {
        return deviceValidator.isValidDeviceForWriteToStream(device);
    }

    @Override
    public void writeDevice(Device device, Writer writer) throws IOException {
        inputOutputOperations.writeDevice(device, writer);
    }

    @Override
    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        return inputOutputOperations.readDevice(reader);
    }

    @Override
    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        inputOutputOperations.outputDevice(device, outputStream);
    }

    @Override
    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException, NoSuchFieldException {
        return inputOutputOperations.inputDevice(inputStream);
    }

    @Override
    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        inputOutputOperations.serializeDevice(device, outputStream);
    }

    @Override
    public Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        return inputOutputOperations.deserializeDevice(inputStream);
    }
}