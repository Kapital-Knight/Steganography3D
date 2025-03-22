package com.example.steganography3d;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;

public class MeshDemo {
    public static Scene demoTriangleScene () {
        Group group = new Group();
        Scene scene = new Scene(group, 800, 800);
        scene.setFill(Color.DARKGRAY);

        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);
        camera.translateXProperty().set(-scene.getWidth() / 2.0);
        camera.translateYProperty().set(-scene.getHeight() / 2.0);
        camera.translateZProperty().set(-100);
        System.out.println(camera.getTranslateZ());

//        Sphere sphere = new Sphere(100);
//        group.getChildren().add(sphere);

        TriangleMesh triangle = new TriangleMesh(VertexFormat.POINT_TEXCOORD);
        triangle.getPoints().addAll(
                -100f,   100f,    0f,
                100f,    100f,    0f,
                -100f,   -200f,   100f
        );
        triangle.getTexCoords().addAll(
                0f,  1f,
                1f,  0f,
                0f,  0f
        );
        // Order of vertices must go counterclockwise
        triangle.getFaces().addAll(
                0, 0, 1, 1, 2, 2
        );

        MeshView triangleView = new MeshView(triangle);
        triangleView.setMaterial(new PhongMaterial(Color.MAGENTA));
        group.getChildren().add(triangleView);

        return scene;
    }
}
