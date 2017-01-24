package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;

import java.util.ArrayList;
import java.util.List;

public class Router extends AbstractDevice implements Device {

    protected int dataRate;

    public int getDataRate() {
        return dataRate;
    }

    public void setDataRate(int dataRate) {
        this.dataRate = dataRate;
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
            dataRate = (Integer) f.getValue();
        }
        fields.remove(f);
        super.fillAllFields(fields);
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(int.class, dataRate));
        return fields;
    }
}

