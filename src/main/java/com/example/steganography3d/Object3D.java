/**
 @author Matthew Allgaier
 @since 4/17/2025
 Object3D.java
 Used to store the information of a 3D object in .obj format.
 Each line of information is stored as a String.
 */

package com.example.steganography3d;

import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;

import java.util.ArrayList;
import java.util.Arrays;

public class Object3D implements Cloneable {
    // Each line of a .obj file stored as separate strings
    private ArrayList<String> lines;
    // regex for any 3-dimensional geometric vertex
    public static final String VERTEX_3D_REGEX = "\\s*v(\\s+-?[0-9]+\\.?[0-9]*){3}\\s*";
    public static final String TEXTURE_2D_REGEX = "\\s*vt(\\s+-?[0-9]+\\.?[0-9]*){3}\\s*";
    public static final String NORMAL_3D_REGEX = "\\s*vt(\\s+-?[0-9]+\\.?[0-9]*){3}\\s*";
    public static final String FACE_REGEX = "\\s*f(\\s+[0-9]+\\/[0-9]?\\/[0-9]+){3,}\\s*";

    public Object3D (ArrayList<String> lines) {
        this.lines = lines;
    }

    public int numLines() {
        return lines.size();
    }
    public String getLine (int index) {
        return lines.get(index);
    }


    public int numVertices() {
        int vertexCount = 0;
        // For each line in this object3D
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            // If currect line is a vertex
            if (line.matches(VERTEX_3D_REGEX)) {
                vertexCount++;
            }
        }
        return vertexCount;
    }

    /**
     * @param lineIndex index of which line you want coordinate information from. May or may not be a vertex.
     * @return Array of only the numeric substrings of a 3D vertex.
     * (e.g. "v 1233 -234 13.5" becomes ["1233", "-234", "13.5"])
     * Returns empty array if specified line is not a 3D vertex
     */
    public String[] getCoordinates(int lineIndex, String regex) {
        String line = getLine(lineIndex);
        //Returns empty array if specified line is not the correct type of coordinates
        if (!line.matches(regex)) {
            return new String[0];
        }
        return stringToCoordinates(line);
    }

    /*
     * @param lineIndex index indicating which line to set the coordinate information of.
     * @param newCoordinates array of the same number of coordinates as the specified line.
     * @return True if operation was successful.
     * False if the specified line wasn't a vertex or newCoordinates was incorrect.
     */
    public boolean setCoordinates(int lineIndex, String[] newCoordinates) {
        if (newCoordinates == null) {
            return false;
        }

        String[] coordinates = getCoordinates(lineIndex, VERTEX_3D_REGEX);

        if (coordinates.length != newCoordinates.length || coordinates.length == 0) {
            return false;
        }

        String line = lines.get(lineIndex).toLowerCase();
        String newLine = "v";
        for (int i = 0; i < coordinates.length; i++) {
            newLine += " " + newCoordinates[i];
        }

        lines.set(lineIndex, newLine);
        return true;
    }

    public String verticesToString() {return verticesToString(10);}

    /**
     * Converts all vertices a string that is easier for humans to read (not .obj format).
     * @param maxLines The method will only output a number of vertices up to this specified limit.
     */
    public String verticesToString(int maxLines) {
        String s = "{";
        int vertexCount = 0;
        // For each line in this object3D
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            // If currect line is a vertex
            if (line.matches(VERTEX_3D_REGEX)) {
                vertexCount++;
                // If too many vertices have been added to the string
                if (vertexCount > maxLines) {
                    // Return with a "..." to indicate that more vertices remain
                    return s + "...\n}";
                }
                // otherwise, add the coordinates of this vertex to s
                s += "\n " + Arrays.toString(getCoordinates(i, VERTEX_3D_REGEX));
            }
        }
        return s + "\n}";
    }

    // Prints the lines of the .obj file this object3D represents
    public void printLines () {
        for (int i = 0; i < numLines(); i++) {
            System.out.println(i + ": " + lines.get(i));
        }
    }
    
    public TriangleMesh toTriangleMesh () {
        TriangleMesh mesh = new TriangleMesh(VertexFormat.POINT_NORMAL_TEXCOORD);

        for (String line : lines) {
            if (line.matches(VERTEX_3D_REGEX)) {
                String[] coordinates = stringToCoordinates(line);
                for (String coordinate : coordinates) {
                    mesh.getPoints().addAll(Float.parseFloat(coordinate));
                }
            }
            else if (line.matches(TEXTURE_2D_REGEX)) {
                String[] coordinates = stringToCoordinates(line);
                for (String coordinate : coordinates) {
                    mesh.getTexCoords().addAll(Float.parseFloat(coordinate));
                }

            }
            else if (line.matches(NORMAL_3D_REGEX)) {
                String[] coordinates = stringToCoordinates(line);
                for (String coordinate : coordinates) {
                    mesh.getNormals().addAll(Float.parseFloat(coordinate));
                }
            } else if (line.matches(FACE_REGEX)) {
                mesh.getFaces().addAll(stringToFaces(line));
            }
        }


        for
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + verticesToString(3);
    }



    private static String[] stringToCoordinates(String line) {
        // Split line by whitespace ("v 1233 -234 13.5" becomes ["v", "1233", "-234", "13.5"])
        String[] tokens = line.split("\\s+"); // example: ["v", "1233", "-234", "13.5"]
        // The coordinates array contains only the numbers. example: ["1233", "-234", "13.5"]
        String[] coordinates = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, coordinates, 0, coordinates.length);
        return coordinates;
    }

    private static int[] stringToFaces(String line) {

        // OBJ files v/t/n
        // Javafx is v/n/t

        String[] tokens = line.trim().split("\\s+");

        String[] points = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, points, 0, points.length);

        int[] vertexIndices = new int[points.length];
        int[] textureIndices = new int[points.length];
        int[] normalIndices = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            String[] indices = points[i].split("/");
            vertexIndices[i] = Integer.parseInt(indices[0]);
            textureIndices[i] = Integer.parseInt(indices[1]);
            normalIndices[i] = Integer.parseInt(indices[2]);
        }

        int numTriangles = points.length - 2;
        int[] faces = new int[numTriangles * 9];
        for (int i = 0; i < numTriangles; i++) {
            // Add a single triangle using three indices from each

        }
    }
}
