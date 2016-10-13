package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceImpl implements Service {

    static protected Logger LOGGER = Logger.getLogger(ServiceImpl.class.getName());

    @Override
    public void sortByIN(Device[] devices) {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation sortByIN not supported yet.", e);
        throw e;
    }

    @Override
    public void filtrateByType(Device[] devices, String type) {
        NotImplementedException e = new NotImplementedException();
        LOGGER.log(Level.SEVERE, "Operation filtrateByType not supported yet.", e);
        throw e;
    }
}
