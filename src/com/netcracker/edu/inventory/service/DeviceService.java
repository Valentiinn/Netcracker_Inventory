package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.Device;

/**
 * Created by admin on 18.10.2016.
 */
public interface DeviceService {

    /**
     * Method check device for castability to specified type
     *
     * @param device - checked device
     * @param clazz - type for cast
     * @return true - if device is castable
     *         false - if device is not castable
     */
    boolean isCastableTo(Device device, Class clazz);

    /**
     * Method check validity of device for insert to rack
     *
     * @param device - validated device
     * @return true - if device is valid
     *         false - if device is not valid
     */
    boolean isValidDeviceForInsertToRack(Device device);


}
