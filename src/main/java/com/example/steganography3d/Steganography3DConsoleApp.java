package com.example.steganography3d;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class Steganography3DConsoleApp {

    public static void main(String[] args) throws Exception {
        Scanner userInput = new Scanner(System.in);
        final String[] OPTIONS = { "Hide message in object", "Read message from object", "Exit"};

        while (true) {
            System.out.println("Please select what you want to do.");
            int choice = promptOptions(OPTIONS, userInput);

            switch (choice) {
                case 1: // hide
                    userHideMessage(userInput);
                    break;
                case 2: // read
                    userReadMessage(userInput);
                    break;
                case 3: // exit
                    System.exit(0);
            }
        }
    }

    public static void userHideMessage(Scanner userInput) throws Exception {
        System.out.print("Please enter cover object file path (.obj): ");
        String coverFilePath = userInput.nextLine();
        Object3D coverObject = new OBJReader(coverFilePath).getObject3D();
        System.out.println("Cover object preview: " + coverObject.verticesToString(3));

        System.out.println("Please enter message you want to hide in object.");
        String message = userInput.nextLine();

        System.out.println("Please enter key.");
        String key = userInput.nextLine();

        Object3D stegoObject = Steganographer.hideMessageInObject(message, coverObject, key);
        System.out.println("Stego object preview: " + stegoObject.verticesToString(3));

        System.out.print("Please enter output file path (.obj): ");
        String stegoFilePath = userInput.nextLine();
        OBJWriter objWriter = new OBJWriter(stegoFilePath);
        objWriter.writeObject3D(stegoObject);

        System.out.printf("Successfully hid message in \"%s\"\n", stegoFilePath);
    }

    public static void userReadMessage(Scanner userInput) throws Exception {
        System.out.print("Please enter stego object file path (.obj): ");
        String objectFilePath = userInput.nextLine();
        Object3D stegoObject = new OBJReader(objectFilePath).getObject3D();

        System.out.println("Please enter key.");
        String key = userInput.nextLine();

        String message = Steganographer.readMessageInObject(stegoObject, key);
        System.out.printf("Message: \"%s\"\n", message);

        System.out.print("Please enter output file path (.txt): ");
        String outputFilePath = userInput.nextLine();
        PrintWriter messageWriter = new PrintWriter(outputFilePath);

        messageWriter.write(message);
        messageWriter.close();
        System.out.println("Successfully saved message to " + outputFilePath);
    }

    private static int promptOptions(String[] options, Scanner inputScanner) {
        for (int i = 0; i < options.length; i++) {
            System.out.printf("(%d) %s\n", i+1, options[i]);
        }
        int choice = inputScanner.nextInt();
        inputScanner.nextLine();
        return choice;
    }
}
