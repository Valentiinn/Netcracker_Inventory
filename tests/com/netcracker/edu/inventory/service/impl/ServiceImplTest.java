package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.impl.AbstractDevice;
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

    @Test(expected = NotImplementedException.class)
    public void sortByIN() throws Exception {
        service.sortByIN(null);
    }

    @Test(expected = NotImplementedException.class)
    public void filtrateByType() throws Exception {
        service.filtrateByType(null, null);
    }

}