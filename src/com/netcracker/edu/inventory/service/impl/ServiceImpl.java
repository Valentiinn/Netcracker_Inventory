package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.inventory.service.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceImpl implements Service {

    static private Logger LOGGER = Logger.getLogger(ServiceImpl.class.getName());
    private Utilities utilities;

    @Override
    public DeviceService getDeviceService() {
        DeviceService deviceService = new DeviceServiceImpl();
        return deviceService;
    }

    @Override
    public RackService getRackService() {
        RackServiceImpl rackService = new RackServiceImpl();
        return rackService;
    }

    @Override
    public void sortByIN(Device[] devices) {
        utilities.sortByIN(devices);
    }

    @Override
    public void filtrateByType(Device[] devices, String type) {
        utilities.filtrateByType(devices, type);
    }
}
