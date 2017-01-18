package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.ConnectionService;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.inventory.service.Service;

public class ServiceImpl implements Service {

    private Utilities utilities = new Utilities();

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
    public ConnectionService getConnectionService() {
        return new ConnectionServiceImpl();
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
