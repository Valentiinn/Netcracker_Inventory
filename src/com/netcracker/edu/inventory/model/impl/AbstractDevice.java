package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractDevice implements Device, Serializable {

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
        if (in <= 0) {
            IllegalArgumentException e = new IllegalArgumentException("in should not be less than 0");
            LOGGER.log(Level.SEVERE, "In  parameter  should not be less than  0");
            throw e;
        }
        this.in = in;
    }

    @Override
    public String getType() {
        return type;
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
        if ((Integer) fields.get(0).getValue() > 0) {
            setIn((Integer) fields.get(0).getValue());
        }
        setManufacturer((String) fields.get(2).getValue());
        setModel((String) fields.get(3).getValue());
        setProductionDate((Date) fields.get(4).getValue());
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = new ArrayList<Field>();
        fields.add(new Field(Integer.class, getIn()));
        fields.add(new Field(String.class, getType()));
        fields.add(new Field(String.class, getManufacturer()));
        fields.add(new Field(String.class, getModel()));
        fields.add(new Field(Date.class, getProductionDate()));
        return fields;
    }
}
