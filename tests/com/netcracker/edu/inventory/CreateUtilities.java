package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.impl.*;

import java.util.Date;

/**
 * Created by oleksandr on 13.11.16.
 */
public class CreateUtilities {

    public static Battery createBattery() {
        Battery battery = new Battery();
        battery.setIn(4);
        battery.setManufacturer("");
        battery.setModel("qwerty");
        battery.setProductionDate(new Date());
        battery.setChargeVolume(5000);
        return battery;
    }

    public static Router createRouter() {
        Router router = new Router();
        router.setManufacturer("D-link");
        router.setDataRate(1000);
        return router;
    }

    public static Switch createSwitch() {
        Switch aSwitch = new Switch(ConnectorType.RJ45);
        aSwitch.setIn(7);
        aSwitch.setModel("null");
        aSwitch.setManufacturer("Cisco");
        aSwitch.setDataRate(1000000);
        aSwitch.setNumberOfPorts(16);
        aSwitch.setPortConnection(new TwistedPair(), 4);
        return aSwitch;
    }

    public static WifiRouter createWifiRouter() {
        WifiRouter wifiRouter = new WifiRouter("802.11g", ConnectorType.RJ45);
        wifiRouter.setIn(7);
        wifiRouter.setModel(null);
        wifiRouter.setManufacturer("D-link");
        wifiRouter.setSecurityProtocol(" ");
        return wifiRouter;
    }

    public static TwistedPair createTwistedPair() {
        TwistedPair twistedPair = new TwistedPair(TwistedPair.Type.UTP, 100);
        twistedPair.setStatus(Connection.READY);
        twistedPair.setAPoint(createRouter());
        return twistedPair;
    }

    public static OpticFiber createOpticFiber() {
        OpticFiber opticFiber = new OpticFiber(OpticFiber.Mode.single, 1000);
        opticFiber.setStatus(Connection.ON_BUILD);
        opticFiber.setBPoint(createWifiRouter());
        return opticFiber;
    }

    public static Wireless createWireless() {
        Wireless wireless = new Wireless(3, "802.11g");
        wireless.setStatus(Connection.USED);
        wireless.setAPoint(createWifiRouter());
        wireless.setBPoint(createWifiRouter(), 0);
        wireless.setBPoint(createWifiRouter(), 2);
        wireless.setProtocol("WPA");
        wireless.setVersion(2);
        return wireless;
    }

    public static ThinCoaxial createThinCoaxial() {
        ThinCoaxial thinCoaxial = new ThinCoaxial(5);
        thinCoaxial.setStatus(Connection.USED);
        thinCoaxial.addDevice(createRouter());
        thinCoaxial.addDevice(createSwitch());
        return thinCoaxial;
    }
}
