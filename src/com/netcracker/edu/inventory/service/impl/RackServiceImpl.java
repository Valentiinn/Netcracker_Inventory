package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.RackService;

import java.io.*;
import java.util.logging.Logger;

class RackServiceImpl implements RackService {
    static protected Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());
    private InputOutputOperations inputOutputOperations;


    @Override
    public void writeRack(Rack rack, Writer writer) throws IOException {
        inputOutputOperations.writeRack(rack, writer);
    }


    @Override
    public Rack readRack(Reader reader) throws IOException {
        return inputOutputOperations.readRack(reader);
    }

    @Override
    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        inputOutputOperations.outputRack(rack, outputStream);
    }

    @Override
    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        return inputOutputOperations.inputRack(inputStream);
    }

    @Override
    public void serializeRack(Rack rack, OutputStream outputStream) throws IOException {
        inputOutputOperations.serializeRack(rack, outputStream);
    }

    @Override
    public Rack deserializeRack(InputStream inputStream)
            throws IOException, ClassCastException, ClassNotFoundException {
        return inputOutputOperations.deserializeRack(inputStream);
    }

}