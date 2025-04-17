package com.example.steganography3d;

public abstract class Steganographer {

    public static final String LEGAL_CHARACTERS = "\t\r\n\s!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    // How many decimal characters are used to describe each original message character
    protected static final int DIGITS_PER_CHARACTER = numberOfDigits(LEGAL_CHARACTERS.length());
    // 1 + the last legal character index indicates the end of a message
    protected static final String END_DECIMAL_MESSAGE = "" + LEGAL_CHARACTERS.length();

    protected Object3D stegoObject;
    protected String message;

    protected static int numberOfDigits(int n) {
        return ("" + n).length();
    }
}
