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
        if (fields[7].getType() == Integer.class) {
            numberOfPorts = (Integer) fields[7].getValue();
        }
    }

    @Override
    public Field[] getAllFields() {
        Field[] fields = new Field[8];
        System.arraycopy(super.getAllFields(), 0, fields, 0, 7);
        fields[7] = new Field(Integer.class, getNumberOfPorts());
        return fields;
    }
}
