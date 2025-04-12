package com.example.steganography3d;

import java.util.Arrays;

public class Steganographer {

//    private static final char[] LEGAL_CHARACTERS =
//            { '\t', '\r', '\n', ' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
//            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//            ':', ';', '<', '=', '>', '?', '@',
//            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
//            '[', '\\', ']', '^', '_', '`',
//            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
//            '{', '|', '}', '~' };
    private static final String LEGAL_CHARACTERS = "\t\r\n !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    public static Object3D hideMessageInObject (String message, Object3D coverObject) {
        Object3D stegoObject = new Object3D();
        return stegoObject;
    }

    public static String readMessageFromMesh (PolygonalMesh stegoMesh) {
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
     * @param c a character assumed to be between FIRST_ALLOWED_CHARACTER and LAST_ALLOWED_CHARACTER
     * @return a two-digit decimal representation of c between 00 and LAST_ALLOWED_CHARACTER_INT
     */
    private static String characterToDecimal (char c) throws Exception {
        int index = LEGAL_CHARACTERS.indexOf(c);
        if (index < 0) {
            throw new Exception("Illegal character");
        }
        else {
            return (index < 10 ? "0" : "") + index;
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


}
