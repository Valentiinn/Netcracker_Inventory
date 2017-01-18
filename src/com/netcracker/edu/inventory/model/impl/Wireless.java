package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.OneToManyConnection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wireless<A extends Device, B extends Device> extends AbstractConnection implements OneToManyConnection {

    private A aDevice;
    private Device[] bDevice;
    private String protocol;
    private String technology;
    private int version;

    public Wireless() {
        this(0, null);
    }

    public Wireless(int size, String technology) {
        this.technology = technology;
        bDevice = new Device[size];
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field aPointField = fields.get(fields.size() - 1);
        aDevice = (A) aPointField.getValue();

        Field bPointsField = fields.get(fields.size() - 2);
        bDevice = (B[]) bPointsField.getValue();

        if (technology == null) {
            Field technologyField = fields.get(fields.size() - 3);
            technology = (String) technologyField.getValue();
        }

        Field protocolField = fields.get(fields.size() - 4);
        protocol = (String) protocolField.getValue();

        Field versionField = fields.get(fields.size() - 5);
        version = (Integer) versionField.getValue();

        super.fillAllFields(fields.subList(0, fields.size() - 5));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();

        fields.add(new Field(Integer.class, version));
        fields.add(new Field(String.class, protocol));
        fields.add(new Field(String.class, technology));
        fields.add(new Field(Array.class, bDevice));
        fields.add(new Field(Device.class, aDevice));

        return fields;
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return ConnectorType.Wireless;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return ConnectorType.Wireless;
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
    public List<B> getBPoints() {
        return new ArrayList<B>(Arrays.asList((B[]) bDevice));
    }

    @Override
    public void setBPoints(List devices) {
        bDevice = ((List<B>) devices).toArray(new Device[devices.size()]);
    }

    @Override
    public int getBCapacity() {
        return bDevice.length;
    }

    @Override
    public B getBPoint(int deviceNumber) {
        return (B) bDevice[deviceNumber];
    }

    @Override
    public void setBPoint(Device device, int deviceNumber) {
        bDevice[deviceNumber] = device;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTechnology() {
        return technology;
    }
}
