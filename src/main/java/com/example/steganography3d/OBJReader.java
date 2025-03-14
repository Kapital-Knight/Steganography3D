package com.example.steganography3d;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class OBJReader {

    private String filePath;
    public String getFilePath () { return filePath; }
    private ArrayList<Double> points; // Every 3 doubles are the coordinate of 1 point
    public ArrayList<Double> getPoints () { return points; }
    private ArrayList<Double> normals; // Every 3 doubles are the coordinate of 1 point
    public ArrayList<Double> getNormals () { return normals; }
    private ArrayList<Double> texCoords; // Every 3 doubles are the coordinate of 1 point
    public ArrayList<Double> getTexCoords () { return texCoords; }
    private Scanner fileScanner;

    // Constructors
    public OBJReader(File file) throws Exception {
        this.filePath = file.getAbsolutePath();
        fileScanner = new Scanner(file);
        points = new ArrayList<Double>();
        normals = new ArrayList<Double>();
        texCoords = new ArrayList<Double>();
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
                extractDoubles(points, lineScanner);
            }
            else if (marker.equalsIgnoreCase("vn")) {
                extractDoubles(normals, lineScanner);
            }
            else if (marker.equalsIgnoreCase("vt")) {
                extractDoubles(texCoords, lineScanner);
            }
            else if (marker.equals("#")) {
                continue;
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
