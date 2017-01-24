package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.OneToManyConnection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wireless<A extends Device, B extends Device>
        extends AbstractConnection implements OneToManyConnection {

    private int version;
    private String technology;
    private String protocol;
    private A deviceA;
    private Device[] devicesArray;

    public Wireless() {
        this(0, null);
    }

    public Wireless(int size, String technology) {
        this.technology = technology;
        devicesArray = new Device[size];
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field versionField = fields.get(fields.size() - 5);
        version = (Integer) versionField.getValue();

        if (technology == null) {
            Field technologyField = fields.get(fields.size() - 4);
            technology = (String) technologyField.getValue();
        }

        Field protocolField = fields.get(fields.size() - 3);
        protocol = (String) protocolField.getValue();

        Field aPointField = fields.get(fields.size() - 2);
        deviceA = (A) aPointField.getValue();

        Field bPointsField = fields.get(fields.size() - 1);
        devicesArray = (B[]) bPointsField.getValue();

        super.fillAllFields(fields.subList(0, fields.size() - 5));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();

        fields.add(new Field(int.class, version));
        fields.add(new Field(String.class, technology));
        fields.add(new Field(String.class, protocol));
        fields.add(new Field(Device.class, deviceA));
        fields.add(new Field(Array.class, devicesArray));

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
        return deviceA;
    }

    @Override
    public void setAPoint(Device device) {
        deviceA = (A) device;
    }

    @Override
    public List<B> getBPoints() {
        return new ArrayList<B>(Arrays.asList((B[]) devicesArray));
    }

    @Override
    public void setBPoints(List devices) {
        devicesArray = ((List<B>) devices).toArray(new Device[devices.size()]);
    }

    @Override
    public int getBCapacity() {
        return devicesArray.length;
    }

    @Override
    public B getBPoint(int deviceNumber) {
        return (B) devicesArray[deviceNumber];
    }

    @Override
    public void setBPoint(Device device, int deviceNumber) {
        devicesArray[deviceNumber] = device;
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
