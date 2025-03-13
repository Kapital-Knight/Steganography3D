package com.example.steganography3d;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class OBJReader {

    private String filePath;
    public String getFilePath () { return filePath; }
    private ArrayList<Double> points; // Every 3 doubles are the coordinate of 1 point
    public ArrayList<Double> getPoints () { return points; }
    private Scanner fileScanner;

    // Constructors
    public OBJReader(String filePath) throws Exception {
        this.filePath = filePath;
        File file = new File(filePath);
        fileScanner = new Scanner(file);
        points = new ArrayList<Double>();
        extractData();
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
            System.out.println(marker);

            if (marker.equalsIgnoreCase("v")) {
                extractGeometricVertex(lineScanner);
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
    private void extractGeometricVertex(Scanner lineScanner) {
        System.out.println("Geometric vertex found");
        while (lineScanner.hasNextDouble()) {
            points.add(lineScanner.nextDouble());
        }
    }
}
