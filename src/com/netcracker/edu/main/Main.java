package com.netcracker.edu.main;

public class Main {

    public static void main(String[] args) {
        print(args);
        sort(args);
        print(args);
    }

    public static void print(String[] args) {
        for (String line : args) {
            System.out.println(line);
        }
        System.out.println();
    }

    public static void sort(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < args.length; j++) {
                if (args[j].compareTo(args[i]) < 0) {
                    index = j;
                    String smallerLine = args[index];
                    args[index] = args[i];
                    args[i] = smallerLine;
                }
            }
        }
    }
}
