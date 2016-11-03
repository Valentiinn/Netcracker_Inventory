package com.netcracker.edu.main;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) {
        //StreamTokenizer streamTokenizer = new StreamTokenizer();
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
    }
//    public static void print(String[] args) {
//        for (String line : args) {
//            System.out.println(line);
//        }
//        System.out.println();
//    }
}
