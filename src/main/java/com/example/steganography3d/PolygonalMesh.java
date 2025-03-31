package com.example.steganography3d;

import java.util.ArrayList;

public class PolygonalMesh {
    private ArrayList<Double> geometricVertices;
    private ArrayList<Double> textureCoordinates;
    private ArrayList<Integer> faces;
    private int textureDimensions;
    private String name;

    public PolygonalMesh() {
        geometricVertices = new ArrayList<>();
        textureCoordinates = new ArrayList<>();
        faces = new ArrayList<>();
        textureDimensions = 2;
        name = "New_object";
    }

    public ArrayList<Double> getGeometricVertices() {
        return geometricVertices;
    }
    public void setGeometricVertices(ArrayList<Double> geometricVertices) {
        this.geometricVertices = geometricVertices;
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
        String s = getName() + "\n" + "Geometric vertices:";
        String pointFormat = "[%9.1f %9.1f %9.1f]";
        for (int i = 0; i < geometricVertices.size(); i+= 3) {
            s += "\n";
            if (i + 2 >= geometricVertices.size()) {
                s += "{Incomplete vertex}";
                break;
            }
            s += String.format(pointFormat,
                    geometricVertices.get(i),
                    geometricVertices.get(i+1),
                    geometricVertices.get(i+3));
        }
        return s;
    }
}
