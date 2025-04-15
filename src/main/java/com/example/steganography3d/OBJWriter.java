package com.example.steganography3d;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class OBJWriter {
    private PrintWriter printWriter;
    private String filePath;

    public OBJWriter (String filePath) throws IOException {
        setFilePath(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) throws IOException {
        this.filePath = filePath;
        this.printWriter = new PrintWriter(filePath);
    }
    
    public void writeObject3D (Object3D object3D) {
        for (int i = 0; i < object3D.numLines(); i++) {
            printWriter.println(object3D.getLine(i));
        }
        printWriter.close();
    }
}
