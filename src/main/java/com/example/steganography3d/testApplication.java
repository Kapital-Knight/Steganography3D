package com.example.steganography3d;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class testApplication extends Application {

    public static final String folderName = "OBJFiles";

    @Override
    public void start(Stage stage) throws IOException, Exception {
//        Scene scene = new Scene(new Group(), 800, 800);

        String fileName = "cube.obj";

        File file = new File(folderName + "/" + fileName);
        System.out.println(file.getAbsolutePath());
        System.out.println("Can read file: " + file.canRead());
        if (!file.canRead()) {
            return;
        }
        OBJReader reader = new OBJReader(file);

//        System.out.println(reader.getMesh());

        Help.about();
        reader.getMesh().about();

//        stage.setTitle("Steganography 3D");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}