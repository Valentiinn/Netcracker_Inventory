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
public class SwitchTest {

    Switch aSwitch;

    int numberOfPorts = 0;

    @Before
    public void before() throws Exception {
        aSwitch = new Switch();
    }

    @After
    public void after() throws Exception {
        aSwitch = null;
    }

    @Test
    public void setGetNumberOfPorts() throws Exception {
        aSwitch.setNumberOfPorts(numberOfPorts);
        int result = aSwitch.getNumberOfPorts();

        assertEquals(numberOfPorts, result);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        aSwitch = DeviceServiceImplTest.createSwitch();

        Device result1 = new Switch();
        result1.feelAllFields(aSwitch.getAllFields());

        DeviceServiceImplTest.assertDevice(aSwitch, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyDevice() throws Exception {
        Device result1 = new Switch();
        result1.feelAllFields(aSwitch.getAllFields());

        DeviceServiceImplTest.assertDevice(aSwitch, result1);
    }

}