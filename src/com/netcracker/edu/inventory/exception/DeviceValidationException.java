package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.model.Device;

public class DeviceValidationException extends RuntimeException {

    private Object notValidDevice;

    public DeviceValidationException(String massage, Object notValidDevice) {
        super("Device is not valid for operation. " + massage);
        this.notValidDevice = notValidDevice;
    }

    public Object getNotValidDevice() {
        return notValidDevice;
    }

    public void setNotValidDevice(Object notValidDevice) {
        this.notValidDevice = notValidDevice;
    }
}
