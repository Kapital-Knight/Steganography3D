package com.example.steganography3d;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, Exception {
        Scene scene = MeshDemo.demoTriangleScene();

        // if filePath isn't correct, try getClass().getResource("cube.obj").toString()
        String filePath = getClass().getResource("cube.obj").toString();
        filePath = filePath.replace('\\', '/');
        OBJReader reader = new OBJReader(filePath);
        ArrayList<Double> points = reader.getPoints();

        System.out.println(Arrays.toString(points.toArray()));

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}