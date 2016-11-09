package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.impl.DeviceServiceImplTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class BatteryTest {

    Battery battery;

    int chargeVolume = 0;

    @Before
    public void before() throws Exception {
        battery = new Battery();
    }

    @After
    public void after() throws Exception {
        battery = null;
    }

    @Test
    public void setGetChargeVolume() throws Exception {
        battery.setChargeVolume(chargeVolume);
        int result = battery.getChargeVolume();

        assertEquals(chargeVolume, result);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        battery = DeviceServiceImplTest.createBattery();

        Device result1 = new Battery();
        result1.feelAllFields(battery.getAllFields());

        DeviceServiceImplTest.assertDevice(battery, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyDevice() throws Exception {
        Device result1 = new Battery();
        result1.feelAllFields(battery.getAllFields());

        DeviceServiceImplTest.assertDevice(battery, result1);
    }

}