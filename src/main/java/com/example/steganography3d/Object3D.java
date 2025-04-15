package com.example.steganography3d;

import java.util.Arrays;

public class Object3D implements Cloneable {
    private String[] lines;
    // regex for any 3-dimensional geometric vertex
    private final String vertex3DRegex = "v(\\s+-?[0-9]+\\.?[0-9]*){3}";

    public Object3D (String[] lines) {
        this.lines = lines;
    }

    public int numLines() {
        return lines.length;
    }

    public String[] getCoordinates(int lineIndex) {
        String line = lines[lineIndex];
        if (!line.matches(vertex3DRegex)) {
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

        if (coordinates.length != newCoordinates.length) {
            return false;
        }

        String line = lines[lineIndex].toLowerCase();
        String newLine = "v";
        for (int i = 0; i < coordinates.length; i++) {
            newLine += " " + newCoordinates[i];
        }

        lines[lineIndex] = newLine;
        return true;
    }

    public String verticesToString() {
        String s = "{";
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.matches(vertex3DRegex)) {
                s += "\n" + Arrays.toString(getCoordinates(i));
            }
        }
        return s + "\n}";
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
        return super.toString() + verticesToString();
    }
}
