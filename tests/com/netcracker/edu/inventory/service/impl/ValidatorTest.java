package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test only methods are presents
 *
 * Created by makovetskyi on 11/4/2016.
 */
public class ValidatorTest {

    Validator validator;

    @Before
    public void setUp() throws Exception {
        validator = new Validator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isValidDeviceForInsertToRack() throws Exception {
        validator.isValidDeviceForInsertToRack(null);
    }

    @Test
    public void isValidDeviceForWriteToStream() throws Exception {
        validator.isValidDeviceForInsertToRack(null);
    }

    @Test
    public void isValidConnectionForWriteToStream() throws Exception {
        validator.isValidConnectionForWriteToStream(null);
    }

}