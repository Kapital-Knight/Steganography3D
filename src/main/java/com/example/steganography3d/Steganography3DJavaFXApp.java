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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Steganography3DJavaFXApp extends Application {

    private static Background s_defaultBackground = new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY));
    private static double s_vBoxBuffer = 10.0;

    private static Scene s_menuScene;
    private static Scene s_hideMessageScene;
    private static Scene s_readMessageScene;

    @Override
    public void start(Stage stage) throws IOException {
        initializeMenuScene(stage);
        initializeHideMessageScene(stage);
        initializeReadMessageScene(stage);

        stage.setTitle("Steganography 3D");
        stage.setScene(s_menuScene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void initializeMenuScene(Stage stage) {
        // Make scene half of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        VBox mainPane = new VBox(s_vBoxBuffer);
        s_menuScene = new Scene(mainPane, screenSize.width*0.3, screenSize.height*0.3);
        mainPane.setBackground(s_defaultBackground);

        Button hideButton = new Button("Hide message");
        hideButton.setOnAction(actionEvent -> {
            stage.setScene(s_hideMessageScene);
        });

        Button readButton = new Button("Read message");
        readButton.setOnAction(actionEvent -> {
            stage.setScene(s_readMessageScene);
        });

        mainPane.getChildren().addAll(hideButton, readButton);
    }

    private static void initializeReadMessageScene(Stage stage) {
        // Make scene half of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Pane mainPane = new StackPane();
        mainPane.setBackground(s_defaultBackground);
        s_readMessageScene = new Scene(mainPane);

        // Pane for all input fields
        Pane vBox = new VBox(s_vBoxBuffer);
        mainPane.getChildren().add(vBox);

        // Add a back button
        vBox.getChildren().add(backButton(stage));

        // Stego object field
        FileSelectionField stegoSelectionField = new FileSelectionField("Stego Object", stage, vBox, FileSelectionField.FileType.OBJ, false);

        // Key field
        TextArea keyField = new TextArea();
        keyField.maxWidthProperty().bind(s_readMessageScene.widthProperty().subtract(20));
        keyField.setPrefRowCount(3);
        Label keyLabel = new Label("Key: ", keyField);
        keyLabel.setContentDisplay(ContentDisplay.BOTTOM);
        vBox.getChildren().add(keyLabel);


        // Confirm button to read message
        Text messageText = new Text();
        Button confirmButton = new Button("Read message");
        confirmButton.setOnAction(actionEvent -> {
            try {
                Object3D stegoObject = new OBJReader(stegoSelectionField.pathProperty().getValue()).getObject3D();
                messageText.setText(Steganographer.readMessageInObject(stegoObject, keyField.getText()));
            }
            catch (FileNotFoundException exception) {
                showNotification(exception.getMessage(), "File not found");
            }
            catch (IllegalArgumentException exception) {
                showNotification(exception.getMessage(), "Illegal argument");
            }
        });
        vBox.getChildren().add(confirmButton);

        // Message output
        ScrollPane messageScrollPane = new ScrollPane(messageText);
        messageScrollPane.maxHeightProperty().bind(s_readMessageScene.heightProperty().subtract(200));
        messageScrollPane.maxWidthProperty().bind(s_readMessageScene.widthProperty().subtract(10));
        messageScrollPane.prefWidthProperty().bind(s_readMessageScene.widthProperty().subtract(10));
        Label messageLabel = new Label("Message:", messageScrollPane);
        messageLabel.setContentDisplay(ContentDisplay.BOTTOM);
        vBox.getChildren().add(messageLabel);
    }

    private static void initializeHideMessageScene(Stage stage) {
        // Make scene half of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Pane mainPane = new StackPane();
        mainPane.setBackground(s_defaultBackground);
        s_hideMessageScene = new Scene(mainPane);

        // Pane for all input fields
        Pane inputPane = new VBox(s_vBoxBuffer);
        mainPane.getChildren().add(inputPane);

        // Add a back button
        inputPane.getChildren().add(backButton(stage));

        // Cover object field
        FileSelectionField coverSelectionField = new FileSelectionField("Cover Object", stage, inputPane, FileSelectionField.FileType.OBJ, false);


        // Message field
        TextArea messageField = new TextArea();
        messageField.maxWidthProperty().bind(s_hideMessageScene.widthProperty().subtract(30));
        Label messageLabel = new Label("Message: ", messageField);
        messageLabel.setContentDisplay(ContentDisplay.BOTTOM);
        inputPane.getChildren().add(messageLabel);

        // Key field
        TextArea keyField = new TextArea();
        keyField.maxWidthProperty().bind(s_hideMessageScene.widthProperty().subtract(30));
        keyField.setPrefRowCount(3);
        Label keyLabel = new Label("Key: ", keyField);
        keyLabel.setContentDisplay(ContentDisplay.BOTTOM);
        inputPane.getChildren().add(keyLabel);

        // Stego object field
        FileSelectionField stegoSelectionField = new FileSelectionField("Stego Object (output)", stage, inputPane, FileSelectionField.FileType.OBJ, true);

        // Confirm button to hide message
        Button confirmButton = new Button("Hide message");
        confirmButton.setOnAction(actionEvent -> {
            try {
                if (messageField.textProperty().getValue().isEmpty()) {
                    throw new IllegalArgumentException("Type a message to hide.");
                }
                Object3D coverObject = new OBJReader(coverSelectionField.pathProperty().getValue()).getObject3D();
                Object3D stegoObject = Steganographer.hideMessageInObject(messageField.textProperty().getValue(), coverObject, keyField.getText());
                OBJWriter objWriter = new OBJWriter(stegoSelectionField.pathProperty().getValue());
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

    // Button that takes user back to menu scene
    private static Button backButton(Stage stage) {
        // Button that takes user back to menu scene
        Button button = new Button("Back");
        button.setOnAction(actionEvent -> {
            stage.setScene(s_menuScene);
        });

        return button;
    }
}
