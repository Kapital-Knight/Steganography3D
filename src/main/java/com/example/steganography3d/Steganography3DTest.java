package com.example.steganography3d;

import java.io.File;
import java.io.FileNotFoundException;

public class Steganography3DTest {
    public static void main(String[] args) throws Exception{

        String fileName = "cube.obj";

        File file = new File(fileName);
        System.out.println(file.getAbsolutePath());
        System.out.println("Can read file: " + file.canRead());
        if (!file.canRead()) {
            throw new FileNotFoundException("Cannot read file \"" + fileName + "\"");
        }

        OBJReader reader = new OBJReader(file);

        Help.about();
        reader.getObject3D().getMesh().about();
    }
}
