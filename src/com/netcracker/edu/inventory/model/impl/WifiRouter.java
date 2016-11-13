package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

public class WifiRouter extends Router implements Device {

    protected String securityProtocol;

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    @Override
    public void feelAllFields(Field[] fields) {
        super.feelAllFields(fields);
        setSecurityProtocol((String) fields[6].getValue());
    }

    @Override
    public Field[] getAllFields() {
        Field[] fields = new Field[7];
        System.arraycopy(super.getAllFields(), 0, fields, 0, 6);
        fields[6] = new Field(Integer.class, getSecurityProtocol());
        return fields;
    }
}
