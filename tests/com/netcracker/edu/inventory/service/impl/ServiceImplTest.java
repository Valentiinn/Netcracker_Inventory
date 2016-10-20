package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class ServiceImplTest {

    Service service;

    @Before
    public void before() throws Exception {
        service = new ServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDeviceService() throws Exception {
        DeviceService deviceService = service.getDeviceService();

        assertNotNull(deviceService);
    }

    @Test
    public void sortByIN() throws Exception {
        Battery b0 = new Battery();
        Battery b1 = new Battery();
        b1.setIn(1);
        Battery b2 = new Battery();
        b2.setIn(2);
        Battery b3 = new Battery();
        b3.setIn(3);
        Device[] devices = new Device[] {null, b2, b1, b0, null, b3, b2, b0, null};
        Device[] expResult = new Device[] {b1, b2, b2, b3, b0, b0, null, null, null};

        service.sortByIN(devices);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType() throws Exception {
        Battery b = new Battery();
        Router r = new Router();
        Switch s = new Switch();
        WifiRouter wr = new WifiRouter();
        Device tn = new Battery() {
            @Override
            public String getType() {
                return null;
            }
        };
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        service.filtrateByType(devices, Router.class.getSimpleName());

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeNull() throws Exception {
        Battery b = new Battery();
        Router r = new Router();
        Switch s = new Switch();
        WifiRouter wr = new WifiRouter();
        Device tn = new Battery() {
            @Override
            public String getType() {
                return null;
            }
        };
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        service.filtrateByType(devices, null);

        assertArrayEquals(expResult, devices);
    }

}