/**
 @author Matthew Allgaier
 @since 4/21/2025
 Steganography3DJavaFXApp.java
 Provides a GUI to use Steganography 3D and the Steganographer class.
 */
package com.example.steganography3d;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Steganography3DJavaFXApp extends Application {

    static private StringProperty s_coverFilePath;
    static private StringProperty s_stegoFilePath;
    static private StringProperty s_textFilePath;

    @Override
    public void start(Stage stage) throws IOException {
        Pane mainPane = new VBox();
        Scene scene = new Scene(mainPane);
        stage.setTitle("Steganography 3D");
        stage.setScene(scene);

        FileSelectionField coverSelectionField = new FileSelectionField("Cover Object", stage);
        s_coverFilePath = coverSelectionField.pathProperty();

        FileSelectionField stegoSelectionField = new FileSelectionField("Stego Object", stage);
        s_stegoFilePath = stegoSelectionField.pathProperty();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
