package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.FeelableEntity;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DevicePK implements DevicePrimaryKey {
    private int primaryKey;

    private static Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    public DevicePK(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public boolean isPrimaryKey() {
        return true;
    }

    @Override
    public DevicePrimaryKey getPrimaryKey() {
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
    public int getIn() {
        return primaryKey;
    }

    @Override
    public void setIn(int in) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public String getType() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public String getManufacturer() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public String getModel() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public void setModel(String model) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Date getProductionDate() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public void setProductionDate(Date productionDate) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        DevicePK that = (DevicePK) o;
        return primaryKey - that.primaryKey;
    }

    @Override
    public boolean equals(Object o) {
        DevicePK devicePK = (DevicePK) o;
        return primaryKey == devicePK.primaryKey;
    }
}
