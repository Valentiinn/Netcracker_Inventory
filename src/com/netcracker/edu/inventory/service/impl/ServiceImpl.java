package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.ConnectionService;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.inventory.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Override
    public <T extends Unique.PrimaryKey> Unique<T> getIndependentCopy(Unique<T> element) {
        if (element == null) {
            return null;
        }

        if (element.getClass().equals(Battery.class)) {
            Battery battery = (Battery) element;
            Battery copyBattery = new Battery();
            copyBattery.fillAllFields(battery.getAllFieldsList());
            return (Unique<T>) copyBattery;
        }
        if (element.getClass().equals(Router.class)) {
            Router router = (Router) element;
            Router copyRouter = new Router();
            copyRouter.fillAllFields(router.getAllFieldsList());
            return (Unique<T>) copyRouter;
        }
        if (element.getClass().equals(Switch.class)) {
            Switch switcher = (Switch) element;
            Switch copySwitch = new Switch();
            copySwitch.fillAllFields(switcher.getAllFieldsList());
            int i = 0;
            for (Connection connection : switcher.getAllPortConnections()) {
                copySwitch.setPortConnection(
                        (connection != null ? (ConnectionPK) connection.getPrimaryKey() : null), i++);
            }
            return (Unique<T>) copySwitch;
        }
        if (element.getClass().equals(WifiRouter.class)) {
            WifiRouter wifiRouter = (WifiRouter) element;
            WifiRouter copyWifiRouter = new WifiRouter();
            copyWifiRouter.fillAllFields(wifiRouter.getAllFieldsList());
            copyWifiRouter.setWireConnection((ConnectionPK) wifiRouter.getWireConnection().getPrimaryKey());
            copyWifiRouter.setWirelessConnection((ConnectionPK) wifiRouter.getWirelessConnection().getPrimaryKey());
            return (Unique<T>) copyWifiRouter;
        }

        if (element.getClass().equals(OpticFiber.class)) {
            OpticFiber opticFiber = (OpticFiber) element;
            OpticFiber copyOpticFiber = new OpticFiber();
            copyOpticFiber.fillAllFields(opticFiber.getAllFieldsList());
            copyOpticFiber.setAPoint(opticFiber.getAPoint().getPrimaryKey());
            copyOpticFiber.setBPoint(opticFiber.getBPoint().getPrimaryKey());
            return copyOpticFiber;
        }
        if (element.getClass().equals(ThinCoaxial.class)) {
            ThinCoaxial thinCoaxial = (ThinCoaxial) element;
            ThinCoaxial copyThinCoaxial = new ThinCoaxial();
            copyThinCoaxial.fillAllFields(thinCoaxial.getAllFieldsList());
            Set<Device> deviceSet = thinCoaxial.getAllDevices();
            for (Device device : deviceSet) {
                copyThinCoaxial.addDevice(device.getPrimaryKey());
            }
            return copyThinCoaxial;
        }
        if (element.getClass().equals(TwistedPair.class)) {
            TwistedPair twistedPair = (TwistedPair) element;
            TwistedPair copyTwistedPair = new TwistedPair();
            copyTwistedPair.fillAllFields(twistedPair.getAllFieldsList());
            copyTwistedPair.setAPoint(twistedPair.getAPoint().getPrimaryKey());
            copyTwistedPair.setBPoint(twistedPair.getBPoint().getPrimaryKey());
            return copyTwistedPair;
        }
        if (element.getClass().equals(Wireless.class)) {
            Wireless wireless = (Wireless) element;
            Wireless copyWireless = new Wireless();
            copyWireless.fillAllFields(wireless.getAllFieldsList());
            copyWireless.setAPoint(wireless.getAPoint().getPrimaryKey());
            List<Device> deviceList = wireless.getBPoints();
            List<Device> copyDeviceList = new ArrayList<Device>();
            for (Device device : deviceList) {
                copyDeviceList.add(device != null ? device.getPrimaryKey() : null);
            }
            copyWireless.setBPoints(copyDeviceList);
            return copyWireless;
        }

        if (element.getClass().equals(RackArrayImpl.class)) {
            RackArrayImpl rackArray = (RackArrayImpl) element;
            RackArrayImpl copyRackArray = new RackArrayImpl(rackArray.getSize(), rackArray.getTypeOfDevices());
            for (int i = 0; i < rackArray.getSize(); i++) {
                Device device = (Device) getIndependentCopy(rackArray.getDevAtSlot(i));
                if (device != null) {
                    copyRackArray.insertDevToSlot(device, i);
                }
            }
            return copyRackArray;
        }
        return element;
    }
}
