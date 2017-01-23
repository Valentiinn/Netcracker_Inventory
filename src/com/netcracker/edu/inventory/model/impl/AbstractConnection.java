package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.FeelableEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractConnection implements Connection {
    private String status;

    public AbstractConnection() {
        this.status = PLANED;
    }

    @Deprecated
    @Override
    public void feelAllFields(Field[] fields) {
        ArrayList<Field> list = new ArrayList<Field>(Arrays.asList(fields));
        fillAllFields(list);
    }

    @Deprecated
    @Override
    public Field[] getAllFields() {
        List<Field> list = getAllFieldsList();
        return list.toArray(new Field[list.size()]);
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field statusField = fields.get(0);
        status = (String) statusField.getValue();
    }

    @Override
    public List<Field> getAllFieldsList() {
        ArrayList<Field> fields = new ArrayList<Field>();
        fields.add(new Field(String.class, status));
        return fields;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
