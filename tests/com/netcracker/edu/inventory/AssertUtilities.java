package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by oleksandr on 13.11.16.
 */
public class AssertUtilities {

    public static void assertSomeDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getClass(), device.getClass());
        if (expDevice.getClass() == Battery.class) {
            assertBattery((Battery) expDevice, (Battery) device);
            return;
        }
        if (expDevice.getClass() == Router.class) {
            assertRouter((Router) expDevice, (Router) device);
            return;
        }
        if (expDevice.getClass() == Switch.class) {
            assertSwitch((Switch) expDevice, (Switch) device);
            return;
        }
        if (expDevice.getClass() == WifiRouter.class) {
            assertWifiRouter((WifiRouter) expDevice, (WifiRouter) device);
            return;
        }
    }

    public static void assertDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getIn(), device.getIn());
        assertEquals(expDevice.getType(), device.getType());
        assertEquals(expDevice.getModel(), device.getModel());
        assertEquals(expDevice.getManufacturer(), device.getManufacturer());
        assertEquals(expDevice.getProductionDate(), device.getProductionDate());
    }

    public static void assertBattery(Battery expBattery, Battery battery) throws Exception {
        assertDevice(expBattery, battery);
        assertEquals(expBattery.getChargeVolume(), battery.getChargeVolume());
    }

    public static void assertRouter(Router expRouter, Router router) throws Exception {
        assertDevice(expRouter, router);
        assertEquals(expRouter.getDataRate(), router.getDataRate());
    }

    public static void assertSwitch(Switch expSwitch, Switch aSwitch) throws Exception {
        assertRouter(expSwitch, aSwitch);
        assertEquals(expSwitch.getNumberOfPorts(), aSwitch.getNumberOfPorts());
    }

    public static void assertWifiRouter(WifiRouter expWifiRouter, WifiRouter wifiRouter) throws Exception {
        assertRouter(expWifiRouter, wifiRouter);
        assertEquals(expWifiRouter.getSecurityProtocol(), wifiRouter.getSecurityProtocol());
    }

    public static void assertRack(Rack expRack, Rack rack) throws Exception {
        if ((expRack.getLocation() == null) || (rack.getLocation() == null)) {
            assertEquals(expRack.getLocation(), rack.getLocation());
        } else {
            assertEquals(expRack.getLocation().getFullName(), rack.getLocation().getFullName());
            assertEquals(expRack.getLocation().getShortName(), rack.getLocation().getShortName());
        }
        assertEquals(expRack.getSize(), rack.getSize());
        assertEquals(expRack.getTypeOfDevices(), rack.getTypeOfDevices());
        for (int i = 0; i < expRack.getSize(); i++) {
            Device expDevice = expRack.getDevAtSlot(i);
            Device device = rack.getDevAtSlot(i);
            if (expDevice == null) {
                assertNull(device);
            } else {
                assertSomeDevice(expDevice, device);
            }
        }
    }

    public static void assertConnection(Connection expConnection, Connection connection) throws Exception {
        assertEquals(expConnection.getStatus(), connection.getStatus());
    }

    public static void assertOneToOneConnection(OneToOneConnection expOneToOneConnection, OneToOneConnection oneToOneConnection) throws Exception {
        assertConnection(expOneToOneConnection, oneToOneConnection);
        assertEquals(expOneToOneConnection.getAPointConnectorType(), oneToOneConnection.getAPointConnectorType());
        assertEquals(expOneToOneConnection.getBPointConnectorType(), oneToOneConnection.getBPointConnectorType());
        assertEquals(expOneToOneConnection.getAPoint(), oneToOneConnection.getAPoint());
        assertEquals(expOneToOneConnection.getBPoint(), oneToOneConnection.getBPoint());
    }

    public static void assertOneToManyConnection(OneToManyConnection expOneToManyConnection, OneToManyConnection oneToManyConnection) throws Exception {
        assertConnection(expOneToManyConnection, oneToManyConnection);
        assertEquals(expOneToManyConnection.getAPointConnectorType(), oneToManyConnection.getAPointConnectorType());
        assertEquals(expOneToManyConnection.getBPointConnectorType(), oneToManyConnection.getBPointConnectorType());
        assertEquals(expOneToManyConnection.getAPoint(), oneToManyConnection.getAPoint());
        assertEquals(expOneToManyConnection.getBCapacity(), oneToManyConnection.getBCapacity());
        assertEquals(expOneToManyConnection.getBPoints(), oneToManyConnection.getBPoints());
    }

    public static void assertAllToAllConnection(AllToAllConnection expAllToAllConnection, AllToAllConnection allToAllConnection) throws Exception {
        assertConnection(expAllToAllConnection, allToAllConnection);
        assertEquals(expAllToAllConnection.getConnectorType(), allToAllConnection.getConnectorType());
        assertEquals(expAllToAllConnection.getCurSize(), allToAllConnection.getCurSize());
        assertEquals(expAllToAllConnection.getMaxSize(), allToAllConnection.getMaxSize());
        assertEquals(expAllToAllConnection.getAllDevices(), allToAllConnection.getAllDevices());
    }

    public static void assertTwistedPair(TwistedPair expTwistedPair, TwistedPair twistedPair) throws Exception {
        assertOneToOneConnection(expTwistedPair, twistedPair);
        assertEquals(expTwistedPair.getType(), twistedPair.getType());
        assertEquals(expTwistedPair.getLength(), twistedPair.getLength());
    }

    public static void assertOpticFiber(OpticFiber expOpticFiber, OpticFiber opticFiber) throws Exception {
        assertOneToOneConnection(expOpticFiber, opticFiber);
        assertEquals(expOpticFiber.getMode(), opticFiber.getMode());
        assertEquals(expOpticFiber.getLength(), opticFiber.getLength());
    }

    public static void assertWireless(Wireless expWireless, Wireless wireless) throws Exception {
        assertOneToManyConnection(expWireless, wireless);
        assertEquals(expWireless.getTechnology(), wireless.getTechnology());
        assertEquals(expWireless.getProtocol(), wireless.getProtocol());
        assertEquals(expWireless.getVersion(), wireless.getVersion());
    }

    public static void assertThinCoaxial(ThinCoaxial expThinCoaxial, ThinCoaxial thinCoaxial) throws Exception {
        assertAllToAllConnection(expThinCoaxial, thinCoaxial);
    }
}
