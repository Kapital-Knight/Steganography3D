package com.example.steganography3d;
import java.io.File;
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
        object3D = new Object3D();
        extractData();
    }
    public OBJReader(String filePath) throws Exception {
        this(new File(filePath));
    }

    // Methods

    private void extractData () throws Exception {
        Scanner fileScanner = new Scanner(file);
        Scanner lineScanner;
        while (fileScanner.hasNextLine()) {
            lineScanner = new Scanner(fileScanner.nextLine());

            if (!lineScanner.hasNext())
                continue;

            String marker = lineScanner.next();

            if (marker.equalsIgnoreCase("v")) {
                extractDoubles(object3D.getMesh().getVertices(), lineScanner);
            }
            else if (marker.equalsIgnoreCase("vt")) {
                extractDoubles(object3D.getMesh().getTextureCoordinates(), lineScanner);
            }
            else if (marker.equals("#")) {
                continue;
            }
            else if (marker.equalsIgnoreCase("o")) {
                object3D.getMesh().setName(lineScanner.next());
            }
        }
    }

    /**
     * Takes one geometry line of an obj file and add a vertex to points
     * @param lineScanner the "v" is already removed
     */
    private void extractDoubles(ArrayList destination, Scanner lineScanner) {
        while (lineScanner.hasNextDouble()) {
            destination.add(lineScanner.nextDouble());
        }
    }
}
