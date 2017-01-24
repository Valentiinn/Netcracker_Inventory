package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.AllToAllConnection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThinCoaxial <A extends Device> extends AbstractConnection implements AllToAllConnection {

    private Set<A> devices;
    private int maxSize;

    public ThinCoaxial(int size) {
        this();
        maxSize = size;
    }

    public ThinCoaxial() {
        devices = new HashSet<A>();
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field maxSizeField = fields.get(fields.size() - 2);
        Field devicesField = fields.get(fields.size() - 1);

        maxSize = (Integer) maxSizeField.getValue();
        devices = (Set<A>) devicesField.getValue();

        super.fillAllFields(fields.subList(0, fields.size() - 2));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(int.class, maxSize));
        fields.add(new Field(Set.class, devices));
        return fields;
    }

    @Override
    public ConnectorType getConnectorType() {
        return ConnectorType.TConnector;
    }

    @Override
    public boolean addDevice(Device device) {
        return devices.size() < maxSize && devices.add((A) device);
    }

    @Override
    public boolean removeDevice(Device device) {
        return devices.remove(device);
    }

    @Override
    public boolean containDevice(Device device) {
        return devices.contains(device);
    }

    @Override
    public Set getAllDevices() {
        return new HashSet<A>(devices);
    }

    @Override
    public int getCurSize() {
        return devices.size();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }
}
