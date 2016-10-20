package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.model.Device;

public class DeviceValidationException extends RuntimeException {

    private Device notValidDevice;

    public DeviceValidationException(String massage, Device notValidDevice) {
        super("Device is not valid for operation" + massage);
        this.notValidDevice = notValidDevice;
    }

    public Device getNotValidDevice() {
        return notValidDevice;
    }

    public void setNotValidDevice(Device notValidDevice) {
        this.notValidDevice = notValidDevice;
    }
}
