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

        Label coverLabel = fileFieldLabel("Cover Object", stage);
        s_coverFilePath = coverLabel.textProperty();

        Label stegoLabel = fileFieldLabel("Stego Object", stage);
        s_stegoFilePath = stegoLabel.textProperty();

        stage.show();
    }

    private static void userSelectObject3D(String title, Stage stage, StringProperty outputStringProperty) {
        FileChooser coverFileChooser = objectFileChooser(title);
        File coverFile = coverFileChooser.showOpenDialog(stage);
        outputStringProperty.setValue(coverFile.getAbsolutePath());
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * @param stage Assumes the scene is not null and that the scene root is of type Pane
     * @return Label and button for selecting an object file
     */
    private static Label fileFieldLabel(String title, Stage stage) {
        Button button = new Button("Choose File");

        Label label = new Label("Select " + title.toLowerCase(), button);
        label.setContentDisplay(ContentDisplay.LEFT);

        // Add label to the stage, assuming its scene root is a Pane
        ((Pane)stage.getScene().getRoot()).getChildren().add(label);

        button.setOnAction( e -> userSelectObject3D(title, stage, label.textProperty()));

        return label;
    }

    private static FileChooser objectFileChooser(String title) {
        // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm#CCHJAJBH
        FileChooser objectFileChooser = new FileChooser();
        objectFileChooser.setTitle(title);
        objectFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        objectFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("3D Objects (Wavefront)", "*.obj"));
        return objectFileChooser;
    }

}
