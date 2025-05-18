/**
 @author Matthew Allgaier
 @since 5/17/2025
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Steganography3DJavaFXApp extends Application {
    // Background of app
    private static Background s_defaultBackground = new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY));
    // Distance between GUI elements
    private static double s_vBoxBuffer = 10.0;

    // Three different scenes the user can navigate between
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

        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // creates navigation menu and stores it in s_menuScene
    private static void initializeMenuScene(Stage stage) {
        // Screen size used to make scene a reasonable portion of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        VBox mainPane = new VBox(s_vBoxBuffer);
        s_menuScene = new Scene(mainPane, screenSize.width*0.3, screenSize.height*0.3);
        mainPane.setBackground(s_defaultBackground);

        Button helpButton = new Button("Help");
        helpButton.setOnAction(actionEvent -> {
            showNotification(Help.about(), "Help", false);
        });

        Button hideButton = new Button("Hide message");
        hideButton.setOnAction(actionEvent -> {
            stage.setScene(s_hideMessageScene);
        });

        // Navigation button to Read a message
        Button readButton = new Button("Read message");
        readButton.setOnAction(actionEvent -> {
            stage.setScene(s_readMessageScene);
        });

        mainPane.getChildren().addAll(helpButton, hideButton, readButton);
    }

    // read menu and stores it in s_readMessageScene
    private static void initializeReadMessageScene(Stage stage) {
        // A pane to contain other panes in case more are added later
        Pane mainPane = new StackPane();
        mainPane.setBackground(s_defaultBackground);
        s_readMessageScene = new Scene(mainPane);

        // Pane for all input and output fields
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
                showNotification(exception.getMessage(), "File not found", true);
            }
            catch (IllegalArgumentException exception) {
                showNotification(exception.getMessage(), "Illegal argument", true);
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

        // Save message button
        Button saveButton = new Button("Save message");
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Save message");
        saveFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));

        // Prompt the user to select a text file, then write the message to that file
        saveButton.setOnAction(actionEvent -> {
            File saveFile = saveFileChooser.showSaveDialog(stage);
            try (PrintWriter printWriter = new PrintWriter(saveFile)) {
                printWriter.print(messageText.getText());
            }
            catch (FileNotFoundException e) {
                showNotification(e.getMessage(), "File not found", true);
            }
            catch (NullPointerException e) {
                showNotification("No file selected, so message was not saved.", "Not saved", true);
            }
        });
        vBox.getChildren().add(saveButton);
    }

    // creates hiding menu and stores it in s_hideMessageScene
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

        // Load message button
        Button loadButton = new Button("Load message");
        FileChooser loadFileChooser = new FileChooser();
        loadFileChooser.setTitle("Load message");
        loadFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        loadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));

        // Prompt the user to select a text file, then write the message to that file
        loadButton.setOnAction(actionEvent -> {
            File loadFile = loadFileChooser.showOpenDialog(stage);
            try (Scanner messageScanner = new Scanner(loadFile)) {
                messageField.setText(messageField.getText() + messageScanner.nextLine());
                while (messageScanner.hasNextLine()) {
                    messageField.setText(messageField.getText() + "\n" + messageScanner.nextLine());
                }
            }
            catch (FileNotFoundException e) {
                showNotification(e.getMessage(), "File not found", true);
            }
            catch (NullPointerException e) {
                showNotification("No file selected, so message was not loaded.", "Not loaded", true);
            }
        });
        inputPane.getChildren().add(loadButton);

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
            // Try loading cover object, hiding message in it, and saving the resulting stego object
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
                showNotification(exception.getMessage(), "File not found", true);
            }
            catch (IllegalArgumentException exception) {
                showNotification(exception.getMessage(), "Illegal argument", true);
            }
            catch (IOException exception) {
                showNotification(exception.getMessage(), "IO exception", true);
            }
        });
        inputPane.getChildren().add(confirmButton);
    }

    // Shows temporary window to notify the user of error or successful operation
    private static void showNotification(String message, String subtitle, boolean isError) {
        Pane mainPane = new VBox();

        // Starts with red "ERROR" if notifying user of an error
        if (isError) {
            Text errorText = new Text("ERROR");
            errorText.setStroke(Color.DARKRED);
            mainPane.getChildren().add(errorText);
        }

        // Add notification message to scene
        Text notificationText = new Text(message);
        mainPane.getChildren().add(notificationText);
        Scene notificationScene = new Scene(mainPane);

        // Format and show stage
        Stage notificationStage = new Stage(StageStyle.UTILITY);
        notificationStage.setTitle("Steganography 3D | " + subtitle);
        notificationStage.setScene(notificationScene);
        notificationStage.show();
    }
    private  static void showNotification(String message) {
        showNotification(message, "Notification", false);
    }

    // Returns a button that takes user back to menu scene
    private static Button backButton(Stage stage) {
        // Button that takes user back to menu scene
        Button button = new Button("Back");
        button.setOnAction(actionEvent -> {
            stage.setScene(s_menuScene);
        });

        return button;
    }
}
