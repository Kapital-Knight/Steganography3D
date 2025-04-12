package com.example.steganography3d;

public class Steganographer {

    // All latin characters fall between these two in Unicode (except tab and newline)
    private static final char FIRST_ALLOWED_CHARACTER = ' '; //
    private static final char LAST_ALLOWED_CHARACTER = '~';
    private static final int LAST_ALLOWED_CHARACTER_INT = Integer.parseInt(characterToDecimal(LAST_ALLOWED_CHARACTER));

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
        String regex = "[" + FIRST_ALLOWED_CHARACTER + "-" + LAST_ALLOWED_CHARACTER + "]*";

        // Throw an exception if the original string contains illegal chracters (outside the allowed range)
        if (!original.matches(regex)) {
            throw new Exception("Argument contains character outside of the accepted range [" + FIRST_ALLOWED_CHARACTER + "," + LAST_ALLOWED_CHARACTER + "]");
        }

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

            if (character > LAST_ALLOWED_CHARACTER) {
                throw new Exception("Argument contains character outside of the accepted range [00, " + LAST_ALLOWED_CHARACTER_INT + "]");
            }

            original += character;
        }

        return original;
    }

    /**
     * @param c a character assumed to be between FIRST_ALLOWED_CHARACTER and LAST_ALLOWED_CHARACTER
     * @return a two-digit decimal representation of c between 00 and LAST_ALLOWED_CHARACTER_INT
     */
    private static String characterToDecimal (char c) {
        // The int value of each allowed character, starting with FIRST_ALLOWED_CHARACTER = 0
        int decimal =  c - FIRST_ALLOWED_CHARACTER;

        // Add a "0" if the characterCode is only 1 digit
        if (decimal <= 9) {
            return "0" + decimal;
        }
        else {
            return "" + decimal;
        }
    }

    /**
     * @param decimal a two-digit number assumed to be between 00 and LAST_ALLOWED_CHARACTER_INT
     * @return the corresponding character between FIRST_ALLOWED_CHARACTER and LAST_ALLOWED_CHARACTER
     */
    private static Character decimalToCharacter (String decimal) {
        int unicode = Integer.parseInt(decimal) + FIRST_ALLOWED_CHARACTER;
        return (char)unicode;
    }


}
