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
        Field dataRate = fields.get(fields.size() - 1);
        setDataRate((Integer) dataRate.getValue());
        super.fillAllFields(fields.subList(0, fields.size() - 1));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(Integer.class, getDataRate()));
        return fields;
    }
}

