package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;

public class RackArrayImpl implements Rack {

    private String type;
    private Device[] devices;

    public RackArrayImpl(int size, String type) {
        this.type = type;
        if (type.equals("Battery")) {
            devices = new Battery[size];

        } else {
            System.err.println("Wrong type");
        }
    }

    @Override
    public int getSize() {

    }

    @Override
    public int getFreeSize() {
    }

    @Override
    public Device getDevAtSlot(int index) {
        return null;
    }

    @Override
    public boolean insertDevToSlot(Device device, int index) {
        return false;
    }

    @Override
    public Device removeDevFromSlot(int index) {
        return null;
    }

    @Override
    public Device getDevByIN(int in) {
        return null;
    }

}

    public String getType() {
        return type;
    }

}