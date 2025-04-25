/**
 @author Matthew Allgaier
 @since 4/24/2025
 FileSelectionField.java
 An object allows user to select a file of type .obj or .txt and displays their selection.
 Used in Steganography3DPaneApp.java
 */

package com.example.steganography3d;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileSelectionField {
    final private static String DEFAULT_FILE_PATH_MESSAGE_FORMAT = "Select %s";

    public enum FileType {
        OBJ, TXT
    }

    private Label pathLabel;
    private Button chooseButton;
    private FileType fileType;

    /**
     * @param stage Assumes the scene is not null and that the scene root is of type Pane
     * @return Label and button for selecting an object file
     */
     public FileSelectionField(String title, Stage stage, Pane pane, FileType fileType, boolean allowNewFiles) {
        chooseButton = new Button("Choose File");
        this.fileType = fileType;

        pathLabel = new Label(String.format(DEFAULT_FILE_PATH_MESSAGE_FORMAT, title), chooseButton);
        pathLabel.setContentDisplay(ContentDisplay.LEFT);

        // Add pathLabel to the stage, assuming its scene root is a Pane
        pane.getChildren().add(pathLabel);


        FileChooser fileChooser = objectFileChooser(title);

        chooseButton.setOnAction( event -> {
            File file;
            if (allowNewFiles) {
                file = fileChooser.showSaveDialog(stage);
            }
            else {
                file = fileChooser.showOpenDialog(stage);
            }
            try {
                pathLabel.textProperty().setValue(file.getAbsolutePath());
                fileChooser.setInitialDirectory(file.getParentFile());
                System.out.println(file.getName());
            } catch (NullPointerException exception) {}
        });
     }

    public StringProperty pathProperty () { return pathLabel.textProperty(); }
    public DoubleBinding widthBinding () {return pathLabel.prefWidthProperty().add(chooseButton.maxWidthProperty()); }

    private FileChooser objectFileChooser(String title) {
        // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm#CCHJAJBH
        FileChooser objectFileChooser = new FileChooser();
        objectFileChooser.setTitle(title);
        objectFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        if (this.fileType == FileType.OBJ) {
            objectFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("3D Objects (Wavefront)", "*.obj"));
        }
        else if (this.fileType == FileType.TXT) {
            objectFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
        }
        return objectFileChooser;
    }
}
