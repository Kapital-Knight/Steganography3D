package com.example.steganography3d;

import javax.swing.*;
import java.util.ArrayList;

public class PolygonalMesh {
    private ArrayList<Double> vertices;
    private ArrayList<Double> textureCoordinates;
    private ArrayList<Integer> faces;
    private int textureDimensions;
    private String name;

    public PolygonalMesh() {
        vertices = new ArrayList<>();
        textureCoordinates = new ArrayList<>();
        faces = new ArrayList<>();
        textureDimensions = 2;
        name = "New_object";
    }

    public ArrayList<Double> getVertices() {
        return vertices;
    }
    public void setVertices(ArrayList<Double> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Double> getTextureCoordinates() {
        return textureCoordinates;
    }
    public void setTextureCoordinates(ArrayList<Double> textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }

    public ArrayList<Integer> getFaces() {
        return faces;
    }
    public void setFaces(ArrayList<Integer> faces) {
        this.faces = faces;
    }

    public int getTextureDimensions() {
        return textureDimensions;
    }
    public void setTextureDimensions(int textureDimensions) {
        this.textureDimensions = textureDimensions;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String s = super.toString()
                + "\nName: \"" + getName() + "\""
                + "\nGeometric vertices: "
                + "\n" + verticesToString(8);
        return s;
    }

    public String verticesToString() {
        return verticesToString(0);
    }
    public String verticesToString(int maxPoints) {
        int maxIndex = (maxPoints == 0) ? Integer.MAX_VALUE : maxPoints * 3;
        String s = "{ ";
        String pointFormat = "[%9f %9f %9f]";
        for (int i = 0; i < vertices.size() && i < maxIndex; i+= 3) {
            if (i + 2 >= vertices.size()) {
                s += "{Incomplete vertex}";
                break;
            }
            s += String.format(pointFormat,
                    vertices.get(i),
                    vertices.get(i+1),
                    vertices.get(i+2));
        }
        s = s.replace("][", "]\n  [");
        int numPointsHidden = (vertices.size() - maxIndex) / 3;
        if (numPointsHidden > 0) {
            s += "\n  ... and " + numPointsHidden + " more";
        }
        s += " }";
        return s;
    }

    public void about () {
        JOptionPane.showMessageDialog(null, toString(), name + " mesh", JOptionPane.INFORMATION_MESSAGE);
    }
}
