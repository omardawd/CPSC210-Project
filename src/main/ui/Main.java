package ui;

import model.Listing;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new ThriftStoreApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found!");
        }
    }
}
