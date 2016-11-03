package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class RackServiceImpl implements RackService {
    static protected Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());

    @Override
    public void writeRack(Rack rack, Writer writer) throws IOException {
        if (writer == null) {
            IllegalArgumentException e = new IllegalArgumentException("OutputStream should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (rack != null) {
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


    @Override
    public Rack readRack(Reader reader) throws IOException {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Stream Reader can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

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

    @Override
    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack == null) {
            return;
        }

        if (outputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("OutputStream cannot be null");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage(), illegalArgumentException);
            throw illegalArgumentException;
        }

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

    @Override
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
        for (int i = 0; i < rack.getSize(); i++) {
            if (!dataInputStream.readUTF().equals("\n")) {
                Device device = deviceService.inputDevice(dataInputStream);
                rack.insertDevToSlot(device, i);
            }
        }
        return rack;
    }

    @Override
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

    @Override
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