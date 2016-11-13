package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 11/4/2016.
 */
public class DeviceValidatorTest {

    DeviceValidator deviceValidator;

    @Before
    public void setUp() throws Exception {
        deviceValidator = new DeviceValidator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isValidDeviceForInsertToRack() throws Exception {
        Battery battery = new Battery();
        battery.setIn(5);

        boolean result = deviceValidator.isValidDeviceForInsertToRack(battery);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_DeviceNull() throws Exception {
        boolean result = deviceValidator.isValidDeviceForInsertToRack(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_IN0() throws Exception {
        Battery battery = new Battery();

        boolean result = deviceValidator.isValidDeviceForInsertToRack(battery);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_TypeNull() throws Exception {
        Device deviceNoType = new Battery() {
            @Override
            public String getType() {
                return null;
            }
        };
        deviceNoType.setIn(5);

        boolean result = deviceValidator.isValidDeviceForInsertToRack(deviceNoType);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceValidator.isValidDeviceForInsertToRack(wifiRouter);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_DeviceNull() throws Exception {
        boolean result = deviceValidator.isValidDeviceForWriteToStream(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_DeviceAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super|Puper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceValidator.isValidDeviceForWriteToStream(wifiRouter);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_ChildAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super&Puper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("no|ne");

        boolean result = deviceValidator.isValidDeviceForWriteToStream(wifiRouter);

        assertFalse(result);
    }


}