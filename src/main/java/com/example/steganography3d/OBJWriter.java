/**
 @author Matthew Allgaier
 @since 4/17/2025
 OBJWriter.java
 Used to write Object3D objects to a .obj file
 */

package com.example.steganography3d;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class OBJWriter {
    private PrintWriter printWriter;
    private String filePath;

    // Instantiates OBJWriter with filePath and printWriter
    public OBJWriter (String filePath) throws IOException {
        setFilePath(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    // sets both filePath and printWriter based on filePath
    public void setFilePath(String filePath) throws IOException {
        this.filePath = filePath;
        this.printWriter = new PrintWriter(filePath);
    }

    // Write object3D to filePath in .obj format
    public void writeObject3D (Object3D object3D) {
        // Print each line stored in object3D, since it is already in .obj format
        for (int i = 0; i < object3D.numLines(); i++) {
            printWriter.println(object3D.getLine(i));
        }
        // Close writer so nothing is lost
        printWriter.close();
    }
}
