package com.example.steganography3d;

interface Object3DInfo {
    // Defines the form used to recognize Object3DInfo from a String
    static String getRegex ();
    // Format when printed
    String getFormat() {
        return "%s";
    }

    @Override
    public String toString() {
        return String.format(getFormat(), data);
    }
}
