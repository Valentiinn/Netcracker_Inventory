package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;

import java.util.ArrayList;
import java.util.List;

public class Battery extends AbstractDevice implements Device {

    protected int chargeVolume;

    public int getChargeVolume() {
        return chargeVolume;
    }

    public void setChargeVolume(int chargeVolume) {
        this.chargeVolume = chargeVolume;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field f;
        if (fields != null) {
            f = fields.get(fields.size() - 1);
        } else {
            return;
        }
        if (f != null && f.getType().equals(int.class)) {
            chargeVolume = (Integer) f.getValue();
        }
        fields.remove(f);
        super.fillAllFields(fields);
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(int.class, chargeVolume));
        return fields;
    }
}
