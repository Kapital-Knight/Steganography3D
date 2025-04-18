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

    /**
     * @param objFile Should be .obj format
     */
    public OBJReader(File objFile) throws Exception {
        this.file = objFile;
        extractData();
    }
    public OBJReader(String filePath) throws Exception {
        this(new File(filePath));
    }

    // Methods

    // Stores .obj data from file into object3D so it doesn't have to be processed everytime getObject3D is called
    private void extractData () throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);
        ArrayList<String> fileContents = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            fileContents.add( fileScanner.nextLine() );
        }
        object3D = new Object3D(fileContents);
    }
}
