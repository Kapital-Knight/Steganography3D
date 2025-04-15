package com.example.steganography3d;

import java.util.Arrays;

public class Steganographer {

    public static final String LEGAL_CHARACTERS = "\t\r\n\s!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    private static final int DIGITS_PER_CHARACTER = numberOfDigits(LEGAL_CHARACTERS.length());

    public static Object3D hideMessageInObject (String message, Object3D coverObject) throws Exception {
        String decimalMessage = stringToDecimal(message);
        Object3D stegoObject = (Object3D) coverObject.clone();

        int decimalIndex = 0;

        for (int i = 0; i < stegoObject.numLines(); i++) {
            String[] oldCoordinates = stegoObject.getCoordinates(i);
            String[] newCoordinates = new String[oldCoordinates.length];

            for (int j = 0; j < oldCoordinates.length; j++) {
                String oldCoordinate = oldCoordinates[j];
                char digit = decimalMessage.charAt(decimalIndex % decimalMessage.length());
                decimalIndex ++;

                newCoordinates[j] = oldCoordinate.substring(0, oldCoordinate.length() - 1) + digit;
            }

            stegoObject.setCoordinates(i, newCoordinates);
        }

        return stegoObject;
    }

    public static String readMessageFromMesh (Object3D stegoObject) {
        String message = "";
        return message;
    }

    // Make private later
    public static String stringToDecimal (String original) throws Exception {
        String decimal = "";

        // Add two digits to decimal for every character in original
        for (int i = 0; i < original.length(); i++) {
            decimal += characterToDecimal(original.charAt(i));
        }

        return decimal;
    }

    // Make private later
    public static String decimalToString (String decimal) throws Exception {
        String original = "";

        for (int i = 1; i < decimal.length(); i += 2) {
            String decimalCharacter = decimal.substring(i-1, i+1);

            char character = decimalToCharacter(decimalCharacter);

            original += character;
        }

        return original;
    }

    /**
     * @return the index of c in the String LEGAL_CHARACTERS,
     * padded with 0's on the left so that every index has the same number of digits.
     * Throws error if character is not contained in LEGAL_CHARACTERS.
     */
    private static String characterToDecimal (char c) throws Exception {
        int index = LEGAL_CHARACTERS.indexOf(c);
        if (index < 0) {
            throw new Exception("Illegal character");
        }
        else {
            int numZeros = DIGITS_PER_CHARACTER - numberOfDigits(index);
            return "0".repeat(numZeros) + index;
        }
    }

    /**
     * @param decimal a two-digit number assumed to be between 00 and LAST_ALLOWED_CHARACTER_INT
     * @return the corresponding character between FIRST_ALLOWED_CHARACTER and LAST_ALLOWED_CHARACTER
     */
    private static Character decimalToCharacter (String decimal) {
        int index = Integer.parseInt(decimal);
        return LEGAL_CHARACTERS.charAt(index);
    }

    private static int numberOfDigits(int n) {
        return ("" + n).length();
    }
}
