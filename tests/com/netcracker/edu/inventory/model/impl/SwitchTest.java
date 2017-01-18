package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.impl.DeviceServiceImplTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class SwitchTest {

    Switch defaultSwitch;
    Switch aSwitch;

    int numberOfPorts = 0;

    @Before
    public void before() throws Exception {
        defaultSwitch = new Switch();
        aSwitch = CreateUtilities.createSwitch();
    }

    @After
    public void after() throws Exception {
        aSwitch = null;
    }

    @Test
    public void getPortsType() throws Exception {
        assertEquals(ConnectorType.need_init, defaultSwitch.getPortsType());
        assertEquals(ConnectorType.RJ45, aSwitch.getPortsType());
    }

    @Test
    public void setGetNumberOfPorts() throws Exception {
        aSwitch.setNumberOfPorts(numberOfPorts);
        int result = aSwitch.getNumberOfPorts();

        assertEquals(numberOfPorts, result);
    }

    @Test
    public void setGetPortConnection() throws Exception {
        TwistedPair twistedPair = CreateUtilities.createTwistedPair();
        twistedPair.setLength(10);

        aSwitch.setPortConnection(twistedPair, 1);
        TwistedPair result = (TwistedPair) aSwitch.getPortConnection(1);

        AssertUtilities.assertTwistedPair(twistedPair, result);
    }

    @Test
    public void defaultAllPortConnections() throws Exception {
        List<Connection> result = defaultSwitch.getAllPortConnections();

        assertEquals(new ArrayList<Connection>(), result);
    }

    @Test
    public void getAllPortConnections() throws Exception {
        List<Connection> expList = new ArrayList<Connection>(6);
        TwistedPair twistedPair1 = CreateUtilities.createTwistedPair();
        TwistedPair twistedPair2 = CreateUtilities.createTwistedPair();
        TwistedPair twistedPair3 = CreateUtilities.createTwistedPair();
        expList.add(twistedPair1);
        expList.add(null);
        expList.add(null);
        expList.add(twistedPair2);
        expList.add(null);
        expList.add(twistedPair3);
        aSwitch.setNumberOfPorts(6);
        aSwitch.setPortConnection(twistedPair1, 0);
        aSwitch.setPortConnection(twistedPair2, 3);
        aSwitch.setPortConnection(twistedPair1, 0);
        aSwitch.setPortConnection(twistedPair3, 5);

        List<Connection> result = aSwitch.getAllPortConnections();

        assertEquals(expList, result);
    }

    @Test
    public void testGetAndFeelAllFieldsArray() throws Exception {
        aSwitch = CreateUtilities.createSwitch();

        Device result1 = new Switch();
        result1.feelAllFields(aSwitch.getAllFields());

        AssertUtilities.assertDevice(aSwitch, result1);
    }

    @Test
    public void testGetAndFeelAllFieldsArray_EmptyDevice() throws Exception {
        Device result1 = new Switch();
        result1.feelAllFields(aSwitch.getAllFields());

        AssertUtilities.assertDevice(aSwitch, result1);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        aSwitch = CreateUtilities.createSwitch();

        Switch result1 = new Switch();
        result1.fillAllFields(aSwitch.getAllFieldsList());

        AssertUtilities.assertSwitch(aSwitch, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyDevice() throws Exception {
        Switch result1 = new Switch();
        result1.fillAllFields(aSwitch.getAllFieldsList());

        AssertUtilities.assertSwitch(aSwitch, result1);
    }

}