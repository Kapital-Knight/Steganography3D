package com.example.steganography3d;

public class FlatMesh {
    private double[] geometricVertices;
    private double[] textureCoordinates;
    private int[] faces;

    public FlatMesh(int numVertices, int numFaces) {
        
    }

    public double[] getGeometricVertices() {
        return geometricVertices;
    }

    public double[] getTextureCoordinates() {
        return textureCoordinates;
    }

    public int[] getFaces() {
        return faces;
    }
}
