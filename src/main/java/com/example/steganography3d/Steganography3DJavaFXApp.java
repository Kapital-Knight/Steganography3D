/**
 @author Matthew Allgaier
 @since 4/21/2025
 Steganography3DJavaFXApp.java
 Provides a GUI to use Steganography 3D and the Steganographer class.
 */
package com.example.steganography3d;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class Steganography3DJavaFXApp extends Application {

    static private StringProperty s_coverFilePath;
    static private StringProperty s_stegoFilePath;
    static private StringProperty s_textFilePath;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Steganography 3D");
        stage.setScene(hideMessageScene(stage));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static Scene taskSelectionScene() {
        Scene scene = new Scene(new Pane());

        return scene;
    }

    private static Scene hideMessageScene(Stage stage) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Pane mainPane = new StackPane();
        Scene scene = new Scene(mainPane, screenSize.width*0.5, screenSize.height*0.5);


        Pane inputPane = new VBox(10);
        inputPane.backgroundProperty().setValue(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().add(inputPane);

        FileSelectionField coverSelectionField = new FileSelectionField("Cover Object", stage, inputPane, FileSelectionField.FileType.OBJ);
        s_coverFilePath = coverSelectionField.pathProperty();

        TextField messageField = new TextField();
        Label messageLabel = new Label("Message: ", messageField);
        messageLabel.setContentDisplay(ContentDisplay.RIGHT);
        inputPane.getChildren().add(messageLabel);

        FileSelectionField stegoSelectionField = new FileSelectionField("Stego Object", stage, inputPane, FileSelectionField.FileType.OBJ);
        s_stegoFilePath = stegoSelectionField.pathProperty();

        return scene;
    }
}
