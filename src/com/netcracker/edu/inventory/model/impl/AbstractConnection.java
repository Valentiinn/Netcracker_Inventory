package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.location.Trunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractConnection implements Connection {
    private String status;
    private Trunk trunk;
    private int serialNumber;

    public AbstractConnection() {
        this.status = PLANED;
    }

    @Override
    public Trunk getTrunk() {
        return trunk;
    }

    @Override
    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }

    private static Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    @Deprecated
    @Override
    public void feelAllFields(Field[] fields) {
        ArrayList<Field> fieldList = new ArrayList<Field>(Arrays.asList(fields));
        fillAllFields(fieldList);
    }

    @Deprecated
    @Override
    public Field[] getAllFields() {
        List<Field> fieldList = getAllFieldsList();
        return fieldList.toArray(new Field[fieldList.size()]);
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        status = (String) fields.get(0).getValue();
        serialNumber = (Integer) fields.get(1).getValue();
        trunk = (Trunk) fields.get(2).getValue();
    }

    @Override
    public List<Field> getAllFieldsList() {
        ArrayList<Field> fields = new ArrayList<Field>();
        fields.add(new Field(String.class, status));
        fields.add(new Field(int.class, serialNumber));
        fields.add(new Field(Trunk.class, trunk));
        return fields;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        if (serialNumber < 1) {
            IllegalArgumentException e = new IllegalArgumentException("in > 0 !");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        this.serialNumber = serialNumber;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        AbstractConnection that = (AbstractConnection) o;
        return serialNumber - that.serialNumber;
    }

    @Override
    public boolean isPrimaryKey() {
        return false;
    }

    @Override
    public ConnectionPK getPrimaryKey() {
        return (serialNumber != 0 && trunk != null) ? new ConnectionPK(trunk, serialNumber) : null;
    }
}
