package com.example.steganography3d;

public class SteganographyEncoder extends Steganographer {

    private Object3D coverObject;
    private boolean messageFullyEncoded; // true if full message fit in the object, false otherwise

    public SteganographyEncoder (String message, Object3D coverObject) {
        this.message = message;
        this.coverObject = coverObject;
    }

    public Object3D getStegoObject (String message, Object3D coverObject) throws Exception {
        String decimalMessage = stringToDecimal(message) + END_DECIMAL_MESSAGE;
        Object3D stegoObject = (Object3D) coverObject.clone();

        int decimalIndex = 0;

        // For each line in the stego object
        for (int i = 0; i < stegoObject.numLines(); i++) {
            // Get the coordinates of line i
            String[] oldVertex = stegoObject.getCoordinates(i);
            String[] newVertex = new String[oldVertex.length];

            // Set each coordinate
            for (int j = 0; j < oldVertex.length; j++) {
                String oldCoordinate = oldVertex[j];
                // Replace the last digit of the old vertex coordinate with the current digit of the message
                char digit = decimalMessage.charAt(decimalIndex % decimalMessage.length());
                newVertex[j] = oldCoordinate.substring(0, oldCoordinate.length() - 1) + digit;

                decimalIndex ++;
            }

            stegoObject.setCoordinates(i, newVertex);
        }

        return stegoObject;
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
}
