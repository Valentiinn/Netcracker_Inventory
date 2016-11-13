package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.RackService;

import java.io.*;

class RackServiceImpl implements RackService {

    private InputOutputOperations inputOutputOperations = new InputOutputOperations();

    @Override
    public void writeRack(Rack rack, Writer writer) throws IOException {
        inputOutputOperations.writeRack(rack, writer);
    }

    @Override
    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
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
    public Rack deserializeRack(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        return inputOutputOperations.deserializeRack(inputStream);
    }
}
