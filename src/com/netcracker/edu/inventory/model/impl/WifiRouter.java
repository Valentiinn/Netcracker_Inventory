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
        if (fields[8].getType() == Integer.class) {
            securityProtocol = (String) fields[8].getValue();
        }
    }

    @Override
    public Field[] getAllFields() {
        Field[] fields = new Field[9];
        System.arraycopy(super.getAllFields(), 0, fields, 0, 8);
        fields[8] = new Field(Integer.class, getSecurityProtocol());
        return fields;
    }
}
