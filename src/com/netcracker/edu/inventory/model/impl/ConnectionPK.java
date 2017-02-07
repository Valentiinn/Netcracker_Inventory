package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.ConnectionPrimaryKey;
import com.netcracker.edu.location.Trunk;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConnectionPK implements ConnectionPrimaryKey {
    private int serialNumber;
    private Trunk trunk;

    private static Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    public ConnectionPK(Trunk trunk, int serialNumber) {
        this.serialNumber = serialNumber;
        this.trunk = trunk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionPK that = (ConnectionPK) o;

        if (serialNumber != that.serialNumber) return false;
        return trunk != null ? trunk.equals(that.trunk) : that.trunk == null;
    }

    @Override
    public boolean isPrimaryKey() {
        return true;
    }

    @Override
    public PrimaryKey getPrimaryKey() {
        return this;
    }

    @Override
    public void feelAllFields(Field[] fields) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Field[] getAllFields() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public List<Field> getAllFieldsList() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Trunk getTrunk() {
        return trunk;
    }

    @Override
    public void setTrunk(Trunk trunk) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public String getStatus() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public void setStatus(String status) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        ConnectionPK that = (ConnectionPK) o;
        return serialNumber - that.serialNumber;
    }
}
