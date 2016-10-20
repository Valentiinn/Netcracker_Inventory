package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDevice implements Device {

    protected int in;
    protected final String type = getClass().getSimpleName();
    protected String manufacturer;
    protected String model;
    protected Date productionDate;

    static protected Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    @Override
    public int getIn() {
        return in;
    }

    @Override
    public void setIn(int in) {
        this.in = in;
    }

    @Override
    public String getType() {
        return type;
    }

    @Deprecated
    @Override
    public void setType(String type) {
        LOGGER.log(Level.WARNING, "<setType(String)> method was marked as deprecated.");
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public Date getProductionDate() {
        return productionDate;
    }

    @Override
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }


}
