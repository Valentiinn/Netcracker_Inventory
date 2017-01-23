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
        Field chargeVolumeField = fields.get(fields.size() - 1);
        setChargeVolume((Integer) chargeVolumeField.getValue());
        super.fillAllFields(fields.subList(0, fields.size() - 1));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(Integer.class, getChargeVolume()));
        return fields;
    }
}
