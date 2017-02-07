package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.WrongPKMethodException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.location.Location;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class RackPK<T extends Device> implements com.netcracker.edu.inventory.model.RackPrimaryKey {
    private Location location;

    private static Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    public RackPK(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        RackPK<?> that = (RackPK<?>) o;
        return location != null ? location.equals(that.location) : that.location == null;
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
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public int getSize() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public int getFreeSize() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Class getTypeOfDevices() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Device getDevAtSlot(int index) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public boolean insertDevToSlot(Device device, int index) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Device removeDevFromSlot(int index) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Device getDevByIN(int in) {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Device[] getAllDeviceAsArray() {
        WrongPKMethodException e = new WrongPKMethodException("Wrong method !");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }
}
