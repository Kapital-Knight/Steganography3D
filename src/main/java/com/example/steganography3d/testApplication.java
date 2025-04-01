package com.example.steganography3d;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class testApplication extends Application {

    public static final String folderName = "OBJFiles";

    @Override
    public void start(Stage stage) throws IOException, Exception {
        Scene scene = MeshDemo.demoTriangleScene();

        String fileName = "Shading Testobj.obj";

        File file = new File(folderName + "/" + fileName);
        System.out.println(file.getAbsolutePath());
        System.out.println("Can read file: " + file.canRead());
        if (!file.canRead()) {
            return;
        }
        OBJReader reader = new OBJReader(file);

        System.out.println(reader.getMesh());

//        Help.about();
        
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}