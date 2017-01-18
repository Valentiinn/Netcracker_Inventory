package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.model.impl.Wireless;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

class Validator {

    static protected Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

    public boolean isValidDeviceForInsertToRack(Device device) {
        if (device == null || device.getIn() == 0 || device.getType() == null) {
            LOGGER.log(Level.SEVERE, "This device is incorrect to add");
            return false;
        }
        return true;
    }

    public boolean isValidDeviceForWriteToStream(Device device) {
        if (device == null) {
            return false;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(device.getType());
        builder.append(device.getModel());
        builder.append(device.getManufacturer());

        if (device instanceof WifiRouter) {
            WifiRouter wifiRouter = (WifiRouter) device;
            builder.append(wifiRouter.getSecurityProtocol());
        }
        if (builder.toString().contains("|")) {
            return false;
        }
        return true;
    }

    public boolean isValidConnectionForWriteToStream(Connection connection) {
        if (connection == null) {
            return false;
        }

        if (connection instanceof Wireless) {
            Wireless wireless = (Wireless) connection;

            if (wireless.getTechnology() != null && wireless.getTechnology().contains("|")) {
                return false;
            }

            if (wireless.getProtocol() != null && wireless.getProtocol().contains("|")) {
                return false;
            }
        }

        return true;
    }

    public void validate(OutputStream outputStream) {
        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("OutputStream should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    public void validate(InputStream inputStream) {
        if (inputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("InputStream should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    public void validate(Reader reader) {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Reader should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    public void validate(Writer writer) {
        if (writer == null) {
            IllegalArgumentException e = new IllegalArgumentException("Writer should not be null");
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }
}
