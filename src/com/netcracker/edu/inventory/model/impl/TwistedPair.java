package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;

import java.util.List;


public class TwistedPair<A extends Device, B extends Device> extends AbstractConnectionOneToOne<A, B> {

    public enum Type {
        need_init("<need_init>"),
        UTP("UTP"),
        FTP("FTP"),
        STP("STP"),
        S_FTP("S/FTP"),
        SFTP("SFTP");

        private String fullName;

        Type(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }
    }

    private Type type;

    public TwistedPair() {
        this(Type.need_init, 0);
    }

    public TwistedPair(Type type, int length) {
        super(length);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        if (type == Type.need_init) {
            Field typeField = fields.get(fields.size() - 1);
            type = (Type) typeField.getValue();
        }
        super.fillAllFields(fields.subList(0, fields.size() - 1));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(Type.class, type));
        return fields;
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return ConnectorType.RJ45;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return ConnectorType.RJ45;
    }


}
