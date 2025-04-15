package com.example.steganography3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Steganography3DTest {
    public static void main(String[] args) throws Exception {
        OBJReader cubeReader = new OBJReader("cube.obj");
        Object3D cube = cubeReader.getObject3D();
        System.out.println(cube);

        String message = "Hi!";
        System.out.println(Steganographer.stringToDecimal(message));

        Object3D stegoCube = Steganographer.hideMessageInObject(message, cube);
        System.out.println(stegoCube);

        OBJWriter objWriter = new OBJWriter("stegoObject.obj");
        System.out.println(objWriter.writeObject3D(stegoCube));
    }
}
