package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;

public class Battery extends AbstractDevice implements Device {

    protected int chargeVolume;

    public int getChargeVolume() {
        return chargeVolume;
    }

    public void setChargeVolume(int chargeVolume) {
        this.chargeVolume = chargeVolume;
    }

    @Override
    public void feelAllFields(Field[] fields) {
        super.feelAllFields(fields);
        setChargeVolume((Integer) fields[5].getValue());
    }

    @Override
    public Field[] getAllFields() {
        Field[] fields = new Field[6];
        System.arraycopy(super.getAllFields(), 0, fields, 0, 5);
        fields[5] = new Field(Integer.class, getChargeVolume());
        return fields;
    }
}
