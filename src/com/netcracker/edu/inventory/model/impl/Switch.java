package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

public class Switch extends Router implements Device {

    protected int numberOfPorts;

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }

    @Override
    public void feelAllFields(Field[] fields) {
        super.feelAllFields(fields);
        setNumberOfPorts((Integer) fields[6].getValue());
    }

    @Override
    public Field[] getAllFields() {
        Field[] fields = new Field[7];
        System.arraycopy(super.getAllFields(), 0, fields, 0, 6);
        fields[6] = new Field(Integer.class, getNumberOfPorts());
        return fields;
    }
}
