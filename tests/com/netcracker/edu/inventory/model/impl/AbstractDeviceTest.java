package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class AbstractDeviceTest {

    Device device;

    int in = 0;
    String type = "";
    String manufacturer = "";
    String model = "";
    Date productionDate = new Date();

    @Before
    public void before() throws Exception {
        device = new AbstractDevice() {};
    }

    @After
    public void after() throws Exception {
        device = null;
    }

    @Test
    public void setGetIn() throws Exception {
        device.setIn(in);
        int result = device.getIn();

        assertEquals(in, result);
    }

    @Deprecated
    @Test
    public void setGetTypeDeprecated() throws Exception {
        device.setType(type);
        String result = device.getType();

        assertEquals(type, result);
    }

    @Test
    public void setGetType() throws Exception {
        String result = device.getType();

        assertEquals(device.getClass().getSimpleName(), result);
    }

    @Test
    public void setGetManufacturer() throws Exception {
        device.setManufacturer(manufacturer);
        String result = device.getManufacturer();

        assertEquals(manufacturer, result);
    }

    @Test
    public void setGetModel() throws Exception {
        device.setModel(model);
        String result = device.getModel();

        assertEquals(model, result);
    }

    @Test
    public void setGetProductionDate() throws Exception {
        device.setProductionDate(productionDate);
        Date result = device.getProductionDate();

        assertEquals(productionDate, result);
    }

}