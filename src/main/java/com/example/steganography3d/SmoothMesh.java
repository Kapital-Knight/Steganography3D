package com.example.steganography3d;

import java.util.ArrayList;

public class SmoothMesh extends PolygonalMesh {
    private ArrayList<Double> vertexNormals;

    public SmoothMesh() {
        super();
        vertexNormals = new ArrayList<>();
    }

    public ArrayList<Double> getVertexNormals() {
        return vertexNormals;
    }
    public void setVertexNormals(ArrayList<Double> vertexNormals) {
        this.vertexNormals = vertexNormals;
    }
}
