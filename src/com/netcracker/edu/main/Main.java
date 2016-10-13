package com.netcracker.edu.main;

import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.RackArrayImpl;

public class Main {

    public static void main(String[] args) {
        RackArrayImpl rackArray = new RackArrayImpl(4, "Battary");
        rackArray.getDevByIN(12);
    }

    public static void print(String[] args) {
        for (String line : args) {
            System.out.println(line);
        }
        System.out.println();
    }
}
