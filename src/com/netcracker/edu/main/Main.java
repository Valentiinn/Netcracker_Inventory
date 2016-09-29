package com.netcracker.edu.main;

public class Main {

    public static void main(String[] args) {
        print(args);
    }

    public static void print(String[] args) {
        for (String line : args) {
            System.out.println(line);
        }
    }
}
