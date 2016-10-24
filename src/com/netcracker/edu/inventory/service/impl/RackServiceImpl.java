package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.RackArrayImpl;
import com.netcracker.edu.inventory.service.RackService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class RackServiceImpl implements RackService {

    static private Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());

    @Override
    public void writeRack(Rack rack, Writer writer) throws IOException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation writeRack not supported yet");
        throw e;
    }

    @Override
    public Rack readRack(Reader reader) throws IOException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation readerRack not supported yet");
        throw e;
    }

    @Override
    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("outputStream should not be null");
            LOGGER.log(Level.SEVERE, "OutputStream should not be null", e);
            throw e;
        }
        if (rack != null) {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(rack.getSize());
            dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());
            DeviceServiceImpl deviceService = new DeviceServiceImpl();
            for (int i = 0; i < rack.getSize(); i++) {
                if (rack.getDevAtSlot(i) == null) {
                    dataOutputStream.writeUTF("null");
                } else {
                    dataOutputStream.writeUTF("device");
                    deviceService.outputDevice(rack.getDevAtSlot(i), dataOutputStream);
                }
            }
        }
    }

    @Override
    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("InputStream cannot be null");
            LOGGER.log(Level.SEVERE, "InputStream cannot be null", illegalArgumentException);
            throw illegalArgumentException;
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DeviceServiceImpl deviceService = new DeviceServiceImpl();
        int size = dataInputStream.readInt();
        Class typeClazz = Class.forName(dataInputStream.readUTF());
        Rack rack = new RackArrayImpl(size, typeClazz);
        for (int i = 0; i < rack.getSize(); i++) {
            if (!dataInputStream.readUTF().equals("device")) {
                Device device = deviceService.inputDevice(dataInputStream);
                rack.insertDevToSlot(device, i);
            }
        }
        return rack;
    }

    @Override
    public void serializeRack(Rack rack, OutputStream outputStream) throws IOException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation serializeRack not supported yet");
        throw e;
    }

    @Override
    public Rack deserializeRack(InputStream inputStream) throws IOException, ClassCastException {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation deserializeRack not supported yet");
        throw e;
    }
}
