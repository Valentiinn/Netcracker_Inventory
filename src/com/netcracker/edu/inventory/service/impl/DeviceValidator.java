package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceValidator {
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
}
