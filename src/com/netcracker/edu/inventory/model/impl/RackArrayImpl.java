package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;

public class RackArrayImpl implements Rack {

    private String type;
    private Device[] devices;

    public RackArrayImpl(int size, String type) {
        this.type = type;
        devices = new Device[size];
    }

    @Override
    public int getSize() {
        return devices.length;
    }

    @Override
    public int getFreeSize() {
        int freeSlots = devices.length;
        for (Device device : devices) {
            if (device == null) {
                freeSlots++;
            }
        }
        return freeSlots;
    }

    @Override
    public Device getDevAtSlot(int index) {
        if (index >= getFreeSize() || index < 0) {
            return null;
        }
        if (devices[index] == null) {
            return null;
        }
        return devices[index];
    }

    @Override
    public boolean insertDevToSlot(Device device, int index) {
        if (index >= getSize() || index < 0) {
            return false;
        }
        if (getDevAtSlot(index) != null) {
            return false;
        }
        if (!type.equals(device.getType())) {
            return false;
        }
        devices[index] = device;
        return true;
    }

    @Override
    public Device removeDevFromSlot(int index) {
        if (index >= getFreeSize() || index < 0) {
            return null;
        }
        if (devices[index] == null) {
            return null;
        }
        Device device = devices[index];
        devices[index] = null;
        return device;
    }

    @Override
    public Device getDevByIN(int in) {
        for (Device device : devices) {
            if (device != null && device.getIn() == in) {
                return device;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

}