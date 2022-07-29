package com.practice.vehiclebooking;

import com.practice.vehiclebooking.orchestrator.RequestDispatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        RequestDispatcher requestDispatcher = new RequestDispatcher();
        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            // returns true if there is another line to read
            while (sc.hasNextLine()) {
               requestDispatcher.identifyAndDispatchRequest(sc.nextLine());
            }
            sc.close(); // closes the scanner
        } catch (IOException e) {
        }

	}
}
