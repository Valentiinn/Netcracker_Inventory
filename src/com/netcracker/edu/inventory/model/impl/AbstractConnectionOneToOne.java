package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.OneToOneConnection;

import java.util.List;


public class AbstractConnectionOneToOne<A extends Device, B extends Device> extends AbstractConnection implements OneToOneConnection {

    private int length;
    private A deviceA;
    private B deviceB;

    public AbstractConnectionOneToOne(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return null;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return null;
    }

    @Override
    public A getAPoint() {
        return deviceA;
    }

    @Override
    public void setAPoint(Device device) {
        deviceA = (A) device;
    }

    @Override
    public B getBPoint() {
        return deviceB;
    }

    @Override
    public void setBPoint(Device device) {
        deviceB = (B) device;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field lengthField = fields.get(fields.size() - 3);
        Field aPointDeviceField = fields.get(fields.size() - 2);
        Field bPointDeviceField = fields.get(fields.size() - 1);
        length = (Integer) lengthField.getValue();
        deviceA = (A) aPointDeviceField.getValue();
        deviceB = (B) bPointDeviceField.getValue();
        super.fillAllFields(fields.subList(0, fields.size() - 3));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(int.class, length));
        fields.add(new Field(Device.class, deviceA));
        fields.add(new Field(Device.class, deviceB));
        return fields;
    }
}
