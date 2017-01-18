package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.impl.DeviceServiceImplTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class RouterTest {

    Router router;

    int dataRate = 0;

    @Before
    public void before() throws Exception {
        router = new Router();
    }

    @After
    public void after() throws Exception {
        router = null;
    }

    @Test
    public void setGetDataRate() throws Exception {
        router.setDataRate(dataRate);
        int result = router.getDataRate();

        assertEquals(dataRate, result);
    }

    @Deprecated
    @Test
    public void testGetAndFeelAllFieldsArray() throws Exception {
        router = CreateUtilities.createRouter();

        Device result1 = new Router();
        result1.feelAllFields(router.getAllFields());

        AssertUtilities.assertDevice(router, result1);
    }

    @Deprecated
    @Test
    public void testGetAndFeelAllFieldsArray_EmptyDevice() throws Exception {
        Device result1 = new Router();
        result1.feelAllFields(router.getAllFields());

        AssertUtilities.assertDevice(router, result1);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        router = CreateUtilities.createRouter();

        Router result1 = new Router();
        result1.fillAllFields(router.getAllFieldsList());

        AssertUtilities.assertRouter(router, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyDevice() throws Exception {
        Router result1 = new Router();
        result1.fillAllFields(router.getAllFieldsList());

        AssertUtilities.assertRouter(router, result1);
    }

}