package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;

public class Router extends AbstractDevice implements Device {

    protected int dataRate;

    public int getDataRate() {
        return dataRate;
    }

    public void setDataRate(int dataRate) {
        this.dataRate = dataRate;
    }

    @Override
    public void feelAllFields(Field[] fields) {
        super.feelAllFields(fields);
        setDataRate((Integer) fields[6].getValue());
    }

    @Override
    public Field[] getAllFields() {
        Field[] fields = new Field[7];
        System.arraycopy(super.getAllFields(), 0, fields, 0, 6);
        Field dataRate = new Field(Integer.class, getDataRate());
        fields[6] = dataRate;
        return fields;
    }
}

