package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.OneToOneConnection;

import java.util.List;


public class AbstractConnectionOneToOne<A extends Device, B extends Device> extends AbstractConnection implements OneToOneConnection {

    private int length;
    private A aDevice;
    private B bDevice;

    public AbstractConnectionOneToOne(int length) {
        this.length = length;
    }

    public AbstractConnectionOneToOne() {
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
        return aDevice;
    }

    @Override
    public void setAPoint(Device device) {
        aDevice = (A) device;
    }

    @Override
    public B getBPoint() {
        return bDevice;
    }

    @Override
    public void setBPoint(Device device) {
        bDevice = (B) device;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field aPointDeviceField = fields.get(fields.size() - 3);
        Field bPointDeviceField = fields.get(fields.size() - 2);
        Field lengthField = fields.get(fields.size() - 1);

        length = (Integer) lengthField.getValue();
        aDevice = (A) aPointDeviceField.getValue();
        bDevice = (B) bPointDeviceField.getValue();

        super.fillAllFields(fields.subList(0, fields.size() - 3));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(Device.class, aDevice));
        fields.add(new Field(Device.class, bDevice));
        fields.add(new Field(Integer.class, length));
        return fields;
    }
}
