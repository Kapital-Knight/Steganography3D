package com.example.steganography3d;

public class Object3D {
    private String materialLibrary;
    private String materialName;
    private PolygonalMesh mesh;

    public Object3D () {
        this.mesh = new PolygonalMesh();
    }

    public String getMaterialLibrary() {
        return materialLibrary;
    }
    public void setMaterialLibrary(String materialLibrary) {
        this.materialLibrary = materialLibrary;
    }

    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public PolygonalMesh getMesh() {
        return mesh;
    }
    public void setMesh(PolygonalMesh mesh) {
        this.mesh = mesh;
    }
}
