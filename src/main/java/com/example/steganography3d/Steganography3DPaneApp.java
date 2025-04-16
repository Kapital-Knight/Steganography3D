package com.example.steganography3d;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class Steganography3DPaneApp {

    public static void main(String[] args) throws Exception {
        final String[] OPTIONS = { "Hide message in object", "Read message from object", "Exit"};

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
        String coverFilePath = JOptionPane.showInputDialog("Please enter cover object file path (.obj): ");
        Object3D coverObject = new OBJReader(coverFilePath).getObject3D();
        JOptionPane.showMessageDialog(null, "Cover object preview: " + coverObject.verticesToString(3));

        String message = JOptionPane.showInputDialog("Please enter message you want to hide in object.");
        Object3D stegoObject = Steganographer.hideMessageInObject(message, coverObject);
        JOptionPane.showMessageDialog(null, "Stego object preview: " + stegoObject.verticesToString(3));

        String stegoFilePath = JOptionPane.showInputDialog("Please enter output file path (.obj): ");
        OBJWriter objWriter = new OBJWriter(stegoFilePath);
        objWriter.writeObject3D(stegoObject);

        JOptionPane.showMessageDialog(null, String.format("Successfully hid message in \"%s\"\n", stegoFilePath));
    }

    public static void userReadMessage() throws Exception {
        String objectFilePath = JOptionPane.showInputDialog("Please enter cover object file path (.obj): ");
        Object3D stegoObject = new OBJReader(objectFilePath).getObject3D();
        String message = Steganographer.readMessageInObject(stegoObject);
        JOptionPane.showMessageDialog(null, String.format("Message: \"%s\"\n", message));

        String outputFilePath = JOptionPane.showInputDialog("Please enter output file path (.txt): ");
        PrintWriter messageWriter = new PrintWriter(outputFilePath);

        messageWriter.write(message);
        messageWriter.close();
        JOptionPane.showMessageDialog(null, "Successfully saved message to " + outputFilePath);
    }

    private static int promptOptions(String prompt, String[] options) {
        return JOptionPane.showOptionDialog(null, prompt, "Input", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
}
