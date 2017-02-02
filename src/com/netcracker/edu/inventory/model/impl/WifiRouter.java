package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;

import java.util.List;

public class WifiRouter extends Router implements Device {

    protected String securityProtocol;
    protected String technologyVersion;
    protected Connection wirelessConnection;
    protected ConnectorType wirePortType;
    protected Connection wireConnection;

    public WifiRouter() {
        this(null, ConnectorType.need_init);
    }

    public WifiRouter(String technologyVersion, ConnectorType connectorType) {
        this.technologyVersion = technologyVersion;
        this.wirePortType = connectorType;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field securityProtocolField;
        Field technologyVersionField;
        Field wirePortTypeField;
        Field wireConnectionField;
        Field wirelessConnectionField;
        if (fields != null) {
            securityProtocolField = fields.get(fields.size() - 5);
            technologyVersionField = fields.get(fields.size() - 4);
            wirePortTypeField = fields.get(fields.size() - 3);
            wireConnectionField = fields.get(fields.size() - 2);
            wirelessConnectionField = fields.get(fields.size() - 1);
        } else
            return;
        setSecurityProtocol((String) securityProtocolField.getValue());
        if (technologyVersion == null) {
            technologyVersion = (String) technologyVersionField.getValue();
        }
        wirePortType = (ConnectorType) wirePortTypeField.getValue();
        wireConnection = (Connection) wireConnectionField.getValue();
        wirelessConnection = (Connection) wirelessConnectionField.getValue();
        super.fillAllFields(fields.subList(0, fields.size() - 3));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(String.class, getSecurityProtocol()));
        fields.add(new Field(String.class, getTechnologyVersion()));
        fields.add(new Field(ConnectorType.class, getWirePortType()));
        fields.add(new Field(Connection.class, getWireConnection()));
        fields.add(new Field(Connection.class, getWirelessConnection()));
        return fields;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public String getTechnologyVersion() {
        return technologyVersion;
    }

    public Connection getWirelessConnection() {
        return wirelessConnection;
    }

    public void setWirelessConnection(Connection wirelessConnection) {
        this.wirelessConnection = wirelessConnection;
    }

    public Connection getWireConnection() {
        return wireConnection;
    }

    public void setWireConnection(Connection wireConnection) {
        this.wireConnection = wireConnection;
    }

    public ConnectorType getWirePortType() {
        return wirePortType;
    }

}
