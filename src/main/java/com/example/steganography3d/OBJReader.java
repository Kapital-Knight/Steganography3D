package com.example.steganography3d;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OBJReader {

    private File file;
    private Object3D object3D;

    public File getFile () { return file; }
    public Object3D getObject3D () { return object3D; }

    // Constructors
    public OBJReader(File objFile) throws Exception {
        this.file = objFile;
        extractData();
    }
    public OBJReader(String filePath) throws Exception {
        this(new File(filePath));
    }

    // Methods

    private void extractData () throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);
        String fileContents = "";
        while (fileScanner.hasNextLine()) {
            fileContents += fileScanner.nextLine() + '\n';
        }
        object3D = new Object3D(fileContents.split("\\v"));
    }
}
