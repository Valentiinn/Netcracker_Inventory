package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.impl.DeviceServiceImplTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class WifiRouterTest {

    WifiRouter wifiRouter;

    String securityProtocol = "";

    @Before
    public void before() throws Exception {
        wifiRouter = new WifiRouter();
    }

    @After
    public void after() throws Exception {
        wifiRouter = null;
    }

    @Test
    public void setGetSecurityProtocol() throws Exception {
        wifiRouter.setSecurityProtocol(securityProtocol);
        String result = wifiRouter.getSecurityProtocol();

        assertEquals(securityProtocol, result);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        wifiRouter = DeviceServiceImplTest.createWifiRouter();

        Device result1 = new WifiRouter();
        result1.feelAllFields(wifiRouter.getAllFields());

        DeviceServiceImplTest.assertDevice(wifiRouter, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyDevice() throws Exception {
        Device result1 = new WifiRouter();
        result1.feelAllFields(wifiRouter.getAllFields());

        DeviceServiceImplTest.assertDevice(wifiRouter, result1);
    }

}