package com.example.steganography3d;

public class Steganographer {

    public static final String LEGAL_CHARACTERS = "\t\r\n\s!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    // How many decimal characters are used to describe each original message character
    private static final int DIGITS_PER_CHARACTER = numberOfDigits(LEGAL_CHARACTERS.length());
    // 1 + the last legal character index indicates the end of a message
    private static final String END_DECIMAL_MESSAGE = "" + LEGAL_CHARACTERS.length();

    public static Object3D hideMessageInObject (String message, Object3D coverObject) throws Exception {
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
                // Replace the last digit of the old vertex with the current digit of the message
                char digit = decimalMessage.charAt(decimalIndex % decimalMessage.length());
                newVertex[j] = oldCoordinate.substring(0, oldCoordinate.length() - 1) + digit;

                decimalIndex ++;
            }

            stegoObject.setCoordinates(i, newVertex);
        }

        return stegoObject;
    }

    public static String readMessageInObject (Object3D stegoObject) {
        String decimalMessage = "";
        // String together the least significant digit of every vertex
        for (int i = 0; i < stegoObject.numLines(); i++) {
            // Length of vertex array will be 0 if the line is not a vertex
            String[] vertex = stegoObject.getCoordinates(i);

            // Concatenate the least significant digit of each coordinate in the vertex
            for (String coordinate : vertex) {
                char leastSignificantDigit = coordinate.charAt(coordinate.length() - 1);
                decimalMessage += leastSignificantDigit;
            }
        }

        // Ensure there is no odd set of digits at the end
        int extraDigits = decimalMessage.length() % DIGITS_PER_CHARACTER;
        decimalMessage = decimalMessage.substring(0, decimalMessage.length() - extraDigits);

        // Return the string version of the messsage
        try {
            return decimalToString(decimalMessage);
        }
        catch (StringIndexOutOfBoundsException e) {
            return "ERROR: Could not read message from object because it contained undefined characters.";
        }

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
    public static String decimalToString (String decimal) {
        String original = "";

        for (int i = DIGITS_PER_CHARACTER-1; i < decimal.length(); i += DIGITS_PER_CHARACTER) {
            String decimalCharacter = decimal.substring(i+1-DIGITS_PER_CHARACTER, i+1);

            if (decimalCharacter.equals(END_DECIMAL_MESSAGE)) {
                return original;
            }

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
