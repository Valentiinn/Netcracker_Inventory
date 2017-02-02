package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.DevicePrimaryKey;

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
    protected List<Field> fields;

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

    @Override
    public DevicePrimaryKey getPrimaryKey() {
        return in != 0 ? new DevicePK(in) : null;
    }

    @Override
    public boolean isPrimaryKey() {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        AbstractDevice that = (AbstractDevice) o;
        return in - that.in;
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
        if (fields == null) {
            return;
        }
        if (fields.get(0).getType().equals(int.class) && ((Integer) fields.get(0).getValue()) > 1) {
            in = (Integer) fields.get(0).getValue();
        }
        if (fields.get(1) != null && fields.get(1).getType().equals(String.class)) {
            model = (String) fields.get(1).getValue();
        }
        if (fields.get(2) != null && fields.get(2).getType().equals(String.class)) {
            manufacturer = (String) fields.get(2).getValue();
        }
        if (fields.get(3) != null && fields.get(3).getType().equals(Date.class)) {
            productionDate = (Date) fields.get(3).getValue();
        }
    }

    @Override
    public List<Field> getAllFieldsList() {
        fields = new ArrayList<Field>();
        fields.add(new Field(int.class, in));
        fields.add(new Field(String.class, model));
        fields.add(new Field(String.class, manufacturer));
        fields.add(new Field(Date.class, productionDate));
        return fields;
    }
}
