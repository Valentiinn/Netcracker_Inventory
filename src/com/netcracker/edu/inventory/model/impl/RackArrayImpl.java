package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RackArrayImpl<T extends Device> implements Rack<T>, Serializable {

    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());
    private final Class<T> clazz;
    private T[] devices;
    private Location location;

    public RackArrayImpl(int size, Class<T> clazz) {
        setRackSize(size);
        if (clazz == null) {
            IllegalArgumentException e = new IllegalArgumentException("Device class should not be null");
            LOGGER.log(Level.SEVERE, "Invalid device class.", e);
            throw e;
        }
        devices = (T[]) (new Device[size]);
        this.clazz = clazz;
    }


    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int getSize() {
        return devices.length;
    }

    @Override
    public int getFreeSize() {
        int freeSize = 0;
        for (Device device : devices) {
            if (device == null) {
                freeSize++;
            }
        }
        return freeSize;
    }

    @Override
    public Class<T> getTypeOfDevices() {
        return clazz;
    }

    @Override
    public T getDevAtSlot(int index) {
        checkIndexLimit(index);
        return devices[index];
    }

    @Override
    public boolean insertDevToSlot(T device, int index) {
        checkIndexLimit(index);
        if (device == null || device.getType() == null || device.getIn() == 0) {
            DeviceValidationException e = new DeviceValidationException("Rack.insertDevToSlot", device);
            LOGGER.log(Level.SEVERE, "Correctly add a device", e);
            throw e;
        }
        if (getDevAtSlot(index) != null || !clazz.isInstance(device)) {
            return false;
        }
        devices[index] = device;
        return true;
    }

    @Override
    public T removeDevFromSlot(int index) {
        checkIndexLimit(index);
        if (devices[index] == null) {
            LOGGER.log(Level.WARNING, "Can not remove from empty slot " + index);
            return null;
        }
        Device device = devices[index];
        devices[index] = null;
        return (T) device;
    }

    @Override
    public T getDevByIN(int in) {
        for (int i = 0; i < getSize(); i++) {
            if (devices[i] != null && (devices[i].getIn() == in)) {
                return devices[i];
            }
        }
        return null;
    }

    @Override
    public T[] getAllDeviceAsArray() {
        ArrayList<Device> list = new ArrayList<Device>();

        for (int i = 0; i < devices.length; i++) {
            if (devices[i] != null) {
                list.add(devices[i]);
            }
        }

        Device[] result = new Device[list.size()];
        list.toArray(result);

        return (T[]) result;
    }

    private void checkIndexLimit(int index) {
        if (index > devices.length - 1 || index < 0) {
            IndexOutOfBoundsException e = new IndexOutOfBoundsException("The entered index " + index + " is invalid!" +
                    "\nValid range from 0 to " + devices.length);
            LOGGER.log(Level.SEVERE, "Invalid slot index.", e);
            throw e;
        }
    }

    private void setRackSize(int size) {
        if (size < 0) {
            IllegalArgumentException e = new IllegalArgumentException("Rack size should not be negative");
            LOGGER.log(Level.SEVERE, "Incorrect rack size", e);
            throw e;
        } else {
            this.devices = (T[]) new Device[size];
        }
    }
}