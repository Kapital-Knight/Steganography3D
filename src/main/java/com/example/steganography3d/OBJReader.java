package com.example.steganography3d;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class OBJReader {

    private String filePath;
    public String getFilePath () { return filePath; }
    private PolygonalMesh mesh;
    public PolygonalMesh getMesh () { return mesh; }
    private Scanner fileScanner;

    // Constructors
    public OBJReader(File file) throws Exception {
        this.filePath = file.getAbsolutePath();
        fileScanner = new Scanner(file);
        mesh = new PolygonalMesh();
        extractData();
    }
    public OBJReader(String filePath) throws Exception {
        this(new File(filePath));
    }

    // Methods

    public void extractData () throws Exception {
        Scanner lineScanner;
        int lineNumber = 0;
        while (fileScanner.hasNextLine()) {
            lineNumber++;
            lineScanner = new Scanner(fileScanner.nextLine());

            if (!lineScanner.hasNext())
                continue;

            String marker = lineScanner.next();

            if (marker.equalsIgnoreCase("v")) {
                extractDoubles(mesh.getGeometricVertices(), lineScanner);
            }
//            else if (marker.equalsIgnoreCase("vn")) {
//                extractDoubles(normals, lineScanner);
//            }
            else if (marker.equalsIgnoreCase("vt")) {
                extractDoubles(mesh.getTextureCoordinates(), lineScanner);
            }
//            else if (marker.equalsIgnoreCase("f")) {
//
//            }
            else if (marker.equals("#")) {
                continue;
            }
            else if (marker.equalsIgnoreCase("o")) {
                mesh.setName(lineScanner.next());
            }
            else {
//                throw new Exception("Something went wrong when interpreting OBJ line" + lineNumber);
                continue;
            }
        }
    }

    /**
     * Takes one geometry line of an obj file and add a vertex to points
     * @param lineScanner the "v" is already removed
     */
    private void extractDoubles(ArrayList arrayList, Scanner lineScanner) {
        while (lineScanner.hasNextDouble()) {
            arrayList.add(lineScanner.nextDouble());
        }
    }
}
