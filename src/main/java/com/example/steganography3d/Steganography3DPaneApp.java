/**
 @author Matthew Allgaier
 @since 4/24/2025
 Steganography3DPaneApp.java
 Provides a GUI to user Steganography 3D and the Steganographer class.
 */

package com.example.steganography3d;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class Steganography3DPaneApp {

    public static void main(String[] args) throws Exception {
        // Different tasks the user can do
        final String[] OPTIONS = { "Hide message in object", "Read message from object", "Exit"};

        // Repeat until user exits
        while (true) {
            int choice = promptOptions("Please select what you want to do.", OPTIONS);

            switch (choice) {
                case 0: // hide
                    userHideMessage();
                    break;
                case 1: // read
                    userReadMessage();
                    break;
                case 2: // exit
                    System.exit(0);
            }
        }
    }

    public static void userHideMessage() throws Exception {
        // Load the cover object from user input
        String coverFilePath = promptSingleLine("Please enter cover object file path (.obj): ");
        Object3D coverObject = new OBJReader(coverFilePath).getObject3D();
        JOptionPane.showMessageDialog(null, "Cover object preview: " + coverObject.verticesToString(3));

        // Get message from user input
        String message = promptMultipleLines("Please enter message you want to hide in object.");

        // Create stego object from cover and message
        Object3D stegoObject = Steganographer.hideMessageInObject(message, coverObject);
        JOptionPane.showMessageDialog(null, "Stego object preview: " + stegoObject.verticesToString(3));

        // Save stego object to user-specified location
        String stegoFilePath = promptSingleLine("Please enter output file path (.obj): ");
        OBJWriter objWriter = new OBJWriter(stegoFilePath);
        objWriter.writeObject3D(stegoObject);

        // Confirmation message
        JOptionPane.showMessageDialog(null, String.format("Successfully hid message in \"%s\"\n", stegoFilePath));
    }

    public static void userReadMessage() throws Exception {
        // Load the stego object from user input
        String objectFilePath = promptSingleLine("Please enter stego object file path (.obj): ");
        Object3D stegoObject = new OBJReader(objectFilePath).getObject3D();

        // Read message from stego object
        String message = Steganographer.readMessageInObject(stegoObject);
        final int MAX_LENGTH = 200;
        String messagePreview = (message.length() > MAX_LENGTH) ? message.substring(0, MAX_LENGTH - 3) + "..." : message;
        JOptionPane.showMessageDialog(null, String.format("Message: \n\"%s\"\n", messagePreview));

        // Save message to user-specified location
        String outputFilePath = promptSingleLine("Please enter output file path (.txt): ");
        PrintWriter messageWriter = new PrintWriter(outputFilePath);
        messageWriter.write(message);
        messageWriter.close();

        // Confirmation message
        JOptionPane.showMessageDialog(null, "Successfully saved message to " + outputFilePath);
    }

    // Prompts user for text input with only one line and returns input
    private static String promptSingleLine(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }

    // Prompts user to choose one of the given options and returns its index
    private static int promptOptions(String prompt, String[] options) {
        return JOptionPane.showOptionDialog(null, prompt, "Input", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    // Prompts user for text input that may contain multiple lines
    private static String promptMultipleLines(String prompt) {
        // Setup up JOptionPane
        JTextArea textArea = new JTextArea(20, 100);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Get whether user clicked ok or something else
        int ok = JOptionPane.showConfirmDialog(null, scrollPane, prompt, JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            // Return user input text
            return textArea.getText();
        }
        else {
            // Close program
            System.exit(0);
            return "Exiting";
        }
    }
}
