package com.example.steganography3d;

import java.util.ArrayList;
import java.util.Arrays;

public class Object3D implements Cloneable {
    private ArrayList<String> lines;
    // regex for any 3-dimensional geometric vertex
    public static final String VERTEX_3D_REGEX = "\\s*v(\\s+-?[0-9]+\\.?[0-9]*){3}\\s*";

    public Object3D (ArrayList<String> lines) {
        this.lines = lines;
    }

    public int numLines() {
        return lines.size();
    }
    public String getLine (int index) {
        return lines.get(index);
    }

    public String[] getCoordinates(int lineIndex) {
        String line = getLine(lineIndex);
        if (!line.matches(VERTEX_3D_REGEX)) {
            return new String[0];
        }
        String[] tokens = line.split("\\s+");
        String[] coordinates = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, coordinates, 0, coordinates.length);
        return coordinates;
    }

    public boolean setCoordinates(int lineIndex, String[] newCoordinates) {
        if (newCoordinates == null) {
            return false;
        }

        String[] coordinates = getCoordinates(lineIndex);

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

    public String verticesToString() {
        String s = "{";
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.matches(VERTEX_3D_REGEX)) {
                s += "\n " + Arrays.toString(getCoordinates(i));
            }
        }
        return s + "\n}";
    }

    public void printLines () {
        for (int i = 0; i < numLines(); i++) {
            System.out.println(i + ": " + lines.get(i));
        }
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
        return super.toString() + " " + verticesToString();
    }
}
