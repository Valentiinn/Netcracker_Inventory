package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.impl.LocationStubImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by oleksandr on 19.10.16.
 */
public class DeviceServiceImplTest {

    DeviceService deviceService;

    @Before
    public void setUp() throws Exception {
        deviceService = new DeviceServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isCastableTo() throws Exception {
        Battery battery = new Battery();
        Switch aSwitch = new Switch();

        boolean result1 = new DeviceServiceImpl().isCastableTo(battery, Battery.class);
        boolean result2 = new DeviceServiceImpl().isCastableTo(battery, Router.class);
        boolean result3 = new DeviceServiceImpl().isCastableTo(battery, Device.class);
        boolean result4 = new DeviceServiceImpl().isCastableTo(aSwitch, Router.class);
        boolean result5 = new DeviceServiceImpl().isCastableTo(null, Device.class);
        boolean result6 = new DeviceServiceImpl().isCastableTo(battery, null);

        assertTrue(result1);
        assertFalse(result2);
        assertTrue(result3);
        assertTrue(result4);
        assertFalse(result5);
        assertFalse(result6);
    }

    @Test
    public void isValidDeviceForInsertToRack() throws Exception {
        Battery battery = new Battery();
        battery.setIn(5);

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForInsertToRack(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_IN0() throws Exception {
        Battery battery = new Battery();

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

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

        boolean result = deviceService.isValidDeviceForInsertToRack(deviceNoType);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForInsertToRack(wifiRouter);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForWriteToStream(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_DeviceAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super|Puper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForWriteToStream(wifiRouter);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForWriteToStream_ChildAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super&Puper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("no|ne");

        boolean result = deviceService.isValidDeviceForWriteToStream(wifiRouter);

        assertFalse(result);
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

        deviceService.writeDevice(battery, pipedWriter);
        deviceService.writeDevice(router, pipedWriter);
        deviceService.writeDevice(aSwitch, pipedWriter);
        deviceService.writeDevice(wifiRouter, pipedWriter);
        deviceService.writeDevice(wifiRouter2, pipedWriter);
        pipedWriter.close();

        Device result1 = deviceService.readDevice(pipedReader);
        Device result2 = deviceService.readDevice(pipedReader);
        Device result3 = deviceService.readDevice(pipedReader);
        Device result4 = deviceService.readDevice(pipedReader);
        Device result5 = deviceService.readDevice(pipedReader);
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

        writeToFile("testOut.txt");
    }

    @Test
    public void writeDeviceNull() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        deviceService.writeDevice(null, pipedWriter);
        pipedWriter.close();

        assertEquals(-1, pipedReader.read());
        pipedReader.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeDeviceStreamNull() throws Exception {
        deviceService.writeDevice(createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readDeviceStreamNull() throws Exception {
        deviceService.readDevice(null);
    }

    @Test
    public void outputInputDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();

        deviceService.outputDevice(battery, pipedOutputStream);
        deviceService.outputDevice(router, pipedOutputStream);
        deviceService.outputDevice(aSwitch, pipedOutputStream);
        deviceService.outputDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        Device result1 = deviceService.inputDevice(pipedInputStream);
        Device result2 = deviceService.inputDevice(pipedInputStream);
        Device result3 = deviceService.inputDevice(pipedInputStream);
        Device result4 = deviceService.inputDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(Battery.class, result1.getClass());
        assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        assertWifiRouter(wifiRouter, (WifiRouter) result4);

//        outputToFile("testOut.bin");
    }

    @Test
    public void outputDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        deviceService.outputDevice(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputDeviceStreamNull() throws Exception {
        deviceService.outputDevice(createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputDeviceStreamNull() throws Exception {
        deviceService.inputDevice(null);
    }

    @Test
    public void serializeDeserializeDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, 2048);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();

        deviceService.serializeDevice(battery, pipedOutputStream);
        deviceService.serializeDevice(router, pipedOutputStream);
        deviceService.serializeDevice(aSwitch, pipedOutputStream);
        deviceService.serializeDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        Device result1 = deviceService.deserializeDevice(pipedInputStream);
        Device result2 = deviceService.deserializeDevice(pipedInputStream);
        Device result3 = deviceService.deserializeDevice(pipedInputStream);
        Device result4 = deviceService.deserializeDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(Battery.class, result1.getClass());
        assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        assertWifiRouter(wifiRouter, (WifiRouter) result4);

//        serializeToFile("testOut.obj");
    }

    @Test
    public void serializeDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        deviceService.serializeDevice(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void serializeDeviceStreamNull() throws Exception {
        deviceService.serializeDevice(createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deserializeDeviceStreamNull() throws Exception {
        deviceService.deserializeDevice(null);
    }

    public static Battery createBattery() {
        Battery battery = new Battery();
        battery.setIn(4);
        battery.setManufacturer("");
        battery.setModel("qwerty");
        battery.setProductionDate(new Date());
        battery.setChargeVolume(5000);
        return battery;
    }

    public static Router createRouter() {
        Router router = new Router();
        router.setManufacturer("D-link");
        router.setDataRate(1000);
        return router;
    }

    public static Switch createSwitch() {
        Switch aSwitch = new Switch();
        aSwitch.setIn(7);
        aSwitch.setModel("null");
        aSwitch.setManufacturer("Cisco");
        aSwitch.setDataRate(1000000);
        aSwitch.setNumberOfPorts(16);
        return aSwitch;
    }

    public static WifiRouter createWifiRouter() {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(7);
        wifiRouter.setModel(null);
        wifiRouter.setManufacturer("D-link");
        wifiRouter.setSecurityProtocol(" ");
        return wifiRouter;
    }

    public static void assertDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getIn(), device.getIn());
        assertEquals(expDevice.getType(), device.getType());
        assertEquals(expDevice.getModel(), device.getModel());
        assertEquals(expDevice.getManufacturer(), device.getManufacturer());
        assertEquals(expDevice.getProductionDate(), device.getProductionDate());
    }

    public static void assertBattery(Battery expBattery, Battery battery) throws Exception {
        assertDevice(expBattery, battery);
        assertEquals(expBattery.getChargeVolume(), battery.getChargeVolume());
    }

    public static void assertRouter(Router expRouter, Router router) throws Exception {
        assertDevice(expRouter, router);
        assertEquals(expRouter.getDataRate(), router.getDataRate());
    }

    public static void assertSwitch(Switch expSwitch, Switch aSwitch) throws Exception {
        assertRouter(expSwitch, aSwitch);
        assertEquals(expSwitch.getNumberOfPorts(), aSwitch.getNumberOfPorts());
    }

    public static void assertWifiRouter(WifiRouter expWifiRouter, WifiRouter wifiRouter) throws Exception {
        assertRouter(expWifiRouter, wifiRouter);
        assertEquals(expWifiRouter.getSecurityProtocol(), wifiRouter.getSecurityProtocol());
    }

    public void outputToFile(String fileName) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();

        deviceService.outputDevice(battery, fileOutputStream);
        deviceService.outputDevice(router, fileOutputStream);
        deviceService.outputDevice(aSwitch, fileOutputStream);
        deviceService.outputDevice(wifiRouter, fileOutputStream);
        fileOutputStream.close();
    }

    public void writeToFile(String fileName) throws Exception {
        FileWriter fileWriter = new FileWriter(fileName);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();

        deviceService.writeDevice(battery, fileWriter);
        deviceService.writeDevice(router, fileWriter);
        deviceService.writeDevice(aSwitch, fileWriter);
        deviceService.writeDevice(wifiRouter, fileWriter);
        fileWriter.close();
    }

    public void serializeToFile(String fileName) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        Battery battery = createBattery();
        Router router = createRouter();
        Switch aSwitch = createSwitch();
        WifiRouter wifiRouter = createWifiRouter();

        deviceService.serializeDevice(battery, fileOutputStream);
        deviceService.serializeDevice(router, fileOutputStream);
        deviceService.serializeDevice(aSwitch, fileOutputStream);
        deviceService.serializeDevice(wifiRouter, fileOutputStream);
        fileOutputStream.close();
    }

}