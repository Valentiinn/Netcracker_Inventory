package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;

import java.util.List;


public class OpticFiber<A extends Device, B extends Device> extends AbstractConnectionOneToOne<A, B> {

    public enum Mode {
        need_init("<need_init>"),
        single("single"),
        multi("multi");
        private String fullName;

        Mode(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }
    }

    private Mode mode;

    public OpticFiber() {
        this(Mode.need_init, 0);
    }

    public OpticFiber(Mode mode, int length) {
        super(length);
        this.mode = mode;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        if (mode == Mode.need_init) {
            Field modeField = fields.get(fields.size() - 1);
            mode = (Mode) modeField.getValue();
        }
        super.fillAllFields(fields.subList(0, fields.size() - 1));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();
        fields.add(new Field(Mode.class, mode));
        return fields;
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return ConnectorType.FiberConnector_FC;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return ConnectorType.FiberConnector_FC;
    }

    public Mode getMode() {
        return mode;
    }
}
