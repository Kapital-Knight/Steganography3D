package com.example.steganography3d;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OBJWriter {
    private FileWriter fileWriter;
    private String filePath;

    public OBJWriter (String filePath) throws IOException {
        setFilePath(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) throws IOException {
        this.filePath = filePath;
        this.fileWriter = new FileWriter(filePath);
    }
}
