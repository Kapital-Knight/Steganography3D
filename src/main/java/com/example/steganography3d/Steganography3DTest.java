package com.example.steganography3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Steganography3DTest {
    public static void main(String[] args) throws Exception {
        // User input eventually
        String filePath = "cube.obj";
        String message = "Hello world!";
        String stegoFilePath = "stegoObject.obj";

        // Hide message
        OBJReader cubeReader = new OBJReader(filePath);
        Object3D cube = cubeReader.getObject3D();
        OBJWriter objWriter = new OBJWriter(stegoFilePath);
        boolean success = objWriter.writeObject3D(Steganographer.hideMessageInObject(message, cube));
        System.out.println("Sucessfully saved: " + success);

        // Read message
        Object3D stegoCube = new OBJReader(stegoFilePath).getObject3D();
        System.out.println(Steganographer.readMessageInObject(stegoCube));
    }
}
