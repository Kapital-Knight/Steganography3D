/**
 @author Matthew Allgaier
 @since 4/21/2025
 Steganography3DJavaFXApp.java
 Provides a GUI to use Steganography 3D and the Steganographer class.
 */
package com.example.steganography3d;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Steganography3DJavaFXApp extends Application {

    private static final StringProperty s_coverFilePath = new SimpleStringProperty();
    private static final StringProperty s_stegoFilePath = new SimpleStringProperty();
    private static final StringProperty s_message = new SimpleStringProperty();


    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Steganography 3D");
        stage.setScene(readMessageScene(stage));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static Scene taskSelectionScene() {
        Scene scene = new Scene(new Pane());

        return scene;
    }

    private static Scene readMessageScene(Stage stage) {
        // Make scene half of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Pane mainPane = new StackPane();
        Scene scene = new Scene(mainPane, screenSize.width*0.5, screenSize.height*0.5);

        // Pane for all input fields
        Pane inputPane = new VBox(10);
        inputPane.backgroundProperty().setValue(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().add(inputPane);

        // Stego object field
        FileSelectionField stegoSelectionField = new FileSelectionField("Stego Object", stage, inputPane, FileSelectionField.FileType.OBJ, false);
        s_stegoFilePath.bind(stegoSelectionField.pathProperty());

        // Confirm button to read message
        Button confirmButton = new Button("Read message");
        confirmButton.setOnAction(actionEvent -> {
            try {
                Object3D stegoObject = new OBJReader(s_stegoFilePath.getValue()).getObject3D();
                s_message.set(Steganographer.readMessageInObject(stegoObject));
            }
            catch (FileNotFoundException exception) {
                showNotification(exception.getMessage(), "File not found");
            }
            catch (IllegalArgumentException exception) {
                showNotification(exception.getMessage(), "Illegal argument");
            }
        });
        inputPane.getChildren().add(confirmButton);

        // Message output
        Text messageText = new Text();
        messageText.textProperty().bind(s_message);
        Label messageLabel = new Label("Message", messageText);
        messageLabel.setContentDisplay(ContentDisplay.BOTTOM);
        mainPane.getChildren().add(messageLabel);

        return scene;
    }

    private static Scene hideMessageScene(Stage stage) {
        // Make scene half of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Pane mainPane = new StackPane();
        Scene scene = new Scene(mainPane, screenSize.width*0.5, screenSize.height*0.5);

        // Pane for all input fields
        Pane inputPane = new VBox(10);
        inputPane.backgroundProperty().setValue(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().add(inputPane);

        // Cover object field
        FileSelectionField coverSelectionField = new FileSelectionField("Cover Object", stage, inputPane, FileSelectionField.FileType.OBJ, false);
        s_coverFilePath.bind(coverSelectionField.pathProperty());

        // Message field
        TextField messageField = new TextField();
        Label messageLabel = new Label("Message: ", messageField);
        messageLabel.setContentDisplay(ContentDisplay.RIGHT);
        inputPane.getChildren().add(messageLabel);
        s_message.bind(messageField.textProperty());

        // Stego object field
        FileSelectionField stegoSelectionField = new FileSelectionField("Stego Object", stage, inputPane, FileSelectionField.FileType.OBJ, true);
        s_stegoFilePath.bind(stegoSelectionField.pathProperty());

        // Confirm button to hide message
        Button confirmButton = new Button("Hide message");
        confirmButton.setOnAction(actionEvent -> {
            try {
                if (s_message.getValue().isEmpty()) {
                    throw new IllegalArgumentException("Type a message to hide.");
                }
                Object3D coverObject = new OBJReader(s_coverFilePath.getValue()).getObject3D();
                Object3D stegoObject = Steganographer.hideMessageInObject(s_message.getValue(), coverObject);
                OBJWriter objWriter = new OBJWriter(s_stegoFilePath.getValue());
                objWriter.writeObject3D(stegoObject);
                showNotification("Successfully saved to " + objWriter.getFilePath());
            }
            catch (FileNotFoundException exception) {
                showNotification(exception.getMessage(), "File not found");
            }
            catch (IllegalArgumentException exception) {
                showNotification(exception.getMessage(), "Illegal argument");
            }
            catch (IOException exception) {
                showNotification(exception.getMessage(), "IO exception");
            }
        });
        inputPane.getChildren().add(confirmButton);

        return scene;
    }

    private static void showNotification(String message, String subtitle) {
        Pane mainPane = new StackPane();
        Text notificationText = new Text(message);
        mainPane.getChildren().add(notificationText);

        Scene notificationScene = new Scene(mainPane);

        Stage notificationStage = new Stage(StageStyle.UTILITY);
        notificationStage.setTitle("Steganography 3D | " + subtitle);
        notificationStage.setScene(notificationScene);
        notificationStage.show();
    }
    private  static void showNotification(String message) {
        showNotification(message, "Notification");
    }
}
