package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.location.impl.LocationStubImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 11/4/2016.
 */
public class InputOutputOperationsTest {

    InputOutputOperations inputOutputOperations;

    @Before
    public void setUp() throws Exception {
        inputOutputOperations = new InputOutputOperations();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void writeReadDevice() throws Exception {

        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();
        WifiRouter wifiRouter2 = createWifiRouter();
        wifiRouter2.setSecurityProtocol("   ");

        inputOutputOperations.writeDevice(battery, pipedWriter);
        inputOutputOperations.writeDevice(router, pipedWriter);
        inputOutputOperations.writeDevice(aSwitch, pipedWriter);
        inputOutputOperations.writeDevice(wifiRouter, pipedWriter);
        inputOutputOperations.writeDevice(wifiRouter2, pipedWriter);
        pipedWriter.close();

        Device result1 = inputOutputOperations.readDevice(pipedReader);
        Device result2 = inputOutputOperations.readDevice(pipedReader);
        Device result3 = inputOutputOperations.readDevice(pipedReader);
        Device result4 = inputOutputOperations.readDevice(pipedReader);
        Device result5 = inputOutputOperations.readDevice(pipedReader);
        pipedReader.close();

        assertEquals(Battery.class, result1.getClass());
        assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        assertWifiRouter(wifiRouter, (WifiRouter) result4);
        assertEquals(WifiRouter.class, result5.getClass());
        assertWifiRouter(wifiRouter2, (WifiRouter) result5);
    }

    @Test
    public void writeDeviceNull() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        inputOutputOperations.writeDevice(null, pipedWriter);
        pipedWriter.close();

        assertEquals(-1, pipedReader.read());
        pipedReader.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeDeviceStreamNull() throws Exception {
        inputOutputOperations.writeDevice(createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readDeviceStreamNull() throws Exception {
        inputOutputOperations.readDevice(null);
    }

    @Test
    public void outputInputDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();

        inputOutputOperations.outputDevice(battery, pipedOutputStream);
        inputOutputOperations.outputDevice(router, pipedOutputStream);
        inputOutputOperations.outputDevice(aSwitch, pipedOutputStream);
        inputOutputOperations.outputDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        Device result1 = inputOutputOperations.inputDevice(pipedInputStream);
        Device result2 = inputOutputOperations.inputDevice(pipedInputStream);
        Device result3 = inputOutputOperations.inputDevice(pipedInputStream);
        Device result4 = inputOutputOperations.inputDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(Battery.class, result1.getClass());
        assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        assertWifiRouter(wifiRouter, (WifiRouter) result4);
    }

    @Test
    public void outputDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        inputOutputOperations.outputDevice(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputDeviceStreamNull() throws Exception {
        inputOutputOperations.outputDevice(createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputDeviceStreamNull() throws Exception {
        inputOutputOperations.inputDevice(null);
    }

    @Test
    public void serializeDeserializeDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, 2048);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();

        inputOutputOperations.serializeDevice(battery, pipedOutputStream);
        inputOutputOperations.serializeDevice(router, pipedOutputStream);
        inputOutputOperations.serializeDevice(aSwitch, pipedOutputStream);
        inputOutputOperations.serializeDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        Device result1 = inputOutputOperations.deserializeDevice(pipedInputStream);
        Device result2 = inputOutputOperations.deserializeDevice(pipedInputStream);
        Device result3 = inputOutputOperations.deserializeDevice(pipedInputStream);
        Device result4 = inputOutputOperations.deserializeDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(Battery.class, result1.getClass());
        assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        assertWifiRouter(wifiRouter, (WifiRouter) result4);
    }

    @Test
    public void serializeDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        inputOutputOperations.serializeDevice(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void serializeDeviceStreamNull() throws Exception {
        inputOutputOperations.serializeDevice(createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deserializeDeviceStreamNull() throws Exception {
        inputOutputOperations.deserializeDevice(null);
    }

    static Battery createBattery() {
        Battery battery = new Battery();
        battery.setIn(4);
        battery.setManufacturer("");
        battery.setModel("qwerty");
        battery.setProductionDate(new Date());
        battery.setChargeVolume(5000);
        return battery;
    }

    static Router createRouter() {
        Router router = new Router();
        router.setManufacturer("D-link");
        router.setDataRate(1000);
        return router;
    }

    static Switch createSwitch() {
        Switch aSwitch = new Switch();
        aSwitch.setIn(7);
        aSwitch.setModel("null");
        aSwitch.setManufacturer("Cisco");
        aSwitch.setDataRate(1000000);
        aSwitch.setNumberOfPorts(16);
        return aSwitch;
    }

    static WifiRouter createWifiRouter() {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(7);
        wifiRouter.setModel(null);
        wifiRouter.setManufacturer("D-link");
        wifiRouter.setSecurityProtocol(" ");
        return wifiRouter;
    }

    static void assertDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getIn(), device.getIn());
        assertEquals(expDevice.getType(), device.getType());
        assertEquals(expDevice.getModel(), device.getModel());
        assertEquals(expDevice.getManufacturer(), device.getManufacturer());
        assertEquals(expDevice.getProductionDate(), device.getProductionDate());
    }

    static void assertBattery(Battery expBattery, Battery battery) throws Exception {
        assertDevice(expBattery, battery);
        assertEquals(expBattery.getChargeVolume(), battery.getChargeVolume());
    }

    static void assertRouter(Router expRouter, Router router) throws Exception {
        assertDevice(expRouter, router);
        assertEquals(expRouter.getDataRate(), router.getDataRate());
    }

    static void assertSwitch(Switch expSwitch, Switch aSwitch) throws Exception {
        assertRouter(expSwitch, aSwitch);
        assertEquals(expSwitch.getNumberOfPorts(), aSwitch.getNumberOfPorts());
    }

    static void assertWifiRouter(WifiRouter expWifiRouter, WifiRouter wifiRouter) throws Exception {
        assertRouter(expWifiRouter, wifiRouter);
        assertEquals(expWifiRouter.getSecurityProtocol(), wifiRouter.getSecurityProtocol());
    }

    @Test
    public void writeReadRack() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);
        Switch aSwitch = DeviceServiceImplTest.createSwitch();
        Router router = DeviceServiceImplTest.createRouter();
        router.setIn(5);
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        inputOutputOperations.writeRack(emptyRack, pipedWriter);
        inputOutputOperations.writeRack(partlyRack, pipedWriter);
        pipedWriter.close();

        Rack result1 = inputOutputOperations.readRack(pipedReader);
        Rack result2 = inputOutputOperations.readRack(pipedReader);
        pipedReader.close();

        assertRack(emptyRack, result1);
        assertRack(partlyRack, result2);
    }

    @Test
    public void writeRackRackNull() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        inputOutputOperations.writeRack(null, pipedWriter);
        pipedWriter.close();

        assertEquals(-1, pipedReader.read());
        pipedReader.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        inputOutputOperations.writeRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readRackNull() throws Exception {
        inputOutputOperations.readRack(null);
    }

    @Test
    public void outputInputRack() throws Exception {

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        Switch aSwitch = DeviceServiceImplTest.createSwitch();
        Router router = DeviceServiceImplTest.createRouter();
        router.setIn(5);
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        inputOutputOperations.outputRack(emptyRack, pipedOutputStream);
        inputOutputOperations.outputRack(partlyRack, pipedOutputStream);
        pipedOutputStream.close();

        Rack result1 = inputOutputOperations.inputRack(pipedInputStream);
        Rack result2 = inputOutputOperations.inputRack(pipedInputStream);
        pipedInputStream.close();

        assertRack(emptyRack, result1);
        assertRack(partlyRack, result2);
    }

    @Test
    public void outputRackRackNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        inputOutputOperations.outputRack(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        inputOutputOperations.outputRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputRackNull() throws Exception {
        inputOutputOperations.inputRack(null);
    }

    @Test
    public void serializeDeserializeRack() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, 4096);
        Switch aSwitch = DeviceServiceImplTest.createSwitch();
        Router router = DeviceServiceImplTest.createRouter();
        router.setIn(5);
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        inputOutputOperations.serializeRack(emptyRack, pipedOutputStream);
        inputOutputOperations.serializeRack(partlyRack, pipedOutputStream);
        pipedOutputStream.close();

        Rack result1 = inputOutputOperations.deserializeRack(pipedInputStream);
        Rack result2 = inputOutputOperations.deserializeRack(pipedInputStream);
        pipedInputStream.close();

        assertRack(emptyRack, result1);
        assertRack(partlyRack, result2);
    }

    @Test
    public void serializeRackRackNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        inputOutputOperations.serializeRack(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void serializeRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        inputOutputOperations.serializeRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deserializeRackNull() throws Exception {
        inputOutputOperations.deserializeRack(null);
    }

    static void assertRack(Rack expRack, Rack rack) throws Exception {
        if ((expRack.getLocation() == null) || (rack.getLocation() == null)) {
            assertEquals(expRack.getLocation(), rack.getLocation());
        } else {
            assertEquals(expRack.getLocation().getFullName(), rack.getLocation().getFullName());
            assertEquals(expRack.getLocation().getShortName(), rack.getLocation().getShortName());
        }
        assertEquals(expRack.getSize(), rack.getSize());
        assertEquals(expRack.getTypeOfDevices(), rack.getTypeOfDevices());
        for (int i = 0; i < expRack.getSize(); i++) {
            Device expDevice = expRack.getDevAtSlot(i);
            Device device = rack.getDevAtSlot(i);
            if (expDevice == null) {
                assertNull(device);
            } else {
                assertEquals(expDevice.getClass(), device.getClass());
                if (expDevice.getClass() == Battery.class) {
                    DeviceServiceImplTest.assertBattery((Battery) expDevice, (Battery) device);
                    continue;
                }
                if (expDevice.getClass() == Router.class) {
                    DeviceServiceImplTest.assertRouter((Router) expDevice, (Router) device);
                    continue;
                }
                if (expDevice.getClass() == Switch.class) {
                    DeviceServiceImplTest.assertSwitch((Switch) expDevice, (Switch) device);
                    continue;
                }
                if (expDevice.getClass() == WifiRouter.class) {
                    DeviceServiceImplTest.assertWifiRouter((WifiRouter) expDevice, (WifiRouter) device);
                    continue;
                }
            }
        }
    }

}