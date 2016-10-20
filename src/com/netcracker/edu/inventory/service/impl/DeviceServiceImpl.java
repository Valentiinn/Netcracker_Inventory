package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.DeviceService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceServiceImpl implements DeviceService {

    static protected Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

    @Override
    public boolean isCastableTo(Device device, Class clazz) {
        if (device == null || clazz == null) {
            return false;
        }
        return clazz.isInstance(device);
    }

    @Override
    public boolean isValidDeviceForInsertToRack(Device device) {
        if (device == null || device.getIn() == 0 || device.getType() == null) {
            LOGGER.log(Level.SEVERE, "This device is incorrect to add");
            return false;
        }
        return true;
    }
}
