/**
 @author Matthew Allgaier
 @since 4/17/2025
 Steganographer.java
 This class can either hide a String message in an object3D using least significant digit encoding, or read such a message.
 */
package com.example.steganography3d;

public class Steganographer {

    // Characters that can be hidden in a stego object
    public static final String LEGAL_CHARACTERS = "\t\r\n\s!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    // How many decimal characters are used to describe each original message character
    private static final int DIGITS_PER_CHARACTER = numberOfDigits(LEGAL_CHARACTERS.length());
    // 1 + the last legal character index indicates the end of a message
    private static final String END_DECIMAL_MESSAGE = "" + LEGAL_CHARACTERS.length();

    /**
     * @param message Must contain only characters contained in LEGAL_CHARACTERS to work properly.
     * @param coverObject If there are not enough vertices in coverObject, the full message may not be hidden.
     * @return A stego object nearly identical to coverObject,
     * except the least significant vertex digits contain message as a decimal.
     */
    public static Object3D hideMessageInObject (String message, Object3D coverObject) throws Exception {
        // Covert to decimal then add END_DECIMAL_MESSAGE to indicate when the message ends and repeats
        String decimalMessage = stringToDecimal(message) + END_DECIMAL_MESSAGE;
        Object3D stegoObject = (Object3D) coverObject.clone();

        int decimalIndex = 0;

        // For each line in the stego object
        for (int i = 0; i < stegoObject.numLines(); i++) {
            // Get the coordinates of line i
            String[] oldVertex = stegoObject.getCoordinates(i);
            String[] newVertex = new String[oldVertex.length];

            // Duplicate and modify each coordinate of oldVertex to create newVertex
            for (int j = 0; j < oldVertex.length; j++) {
                String oldCoordinate = oldVertex[j];
                // Replace the last digit of the old vertex with the current digit of the message
                char digit = decimalMessage.charAt(decimalIndex % decimalMessage.length());
                newVertex[j] = oldCoordinate.substring(0, oldCoordinate.length() - 1) + digit;

                decimalIndex ++;
            }

            // update stegoObject with newVertex
            stegoObject.setCoordinates(i, newVertex);
        }

        return stegoObject;
    }

    /**
     * Reads the least significant digits in stegoObject to decode a message already hidden using Steganographer
     * @param stegoObject An object that already has a message encoded in it using hideMessageInObject
     */
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

    /**
     * Converts a string of legal characters into a longer string of only decimal characters.
     * See characterToDecimal for more info.
     * @param original A string containing only characters found in LEGAL_CHARACTERS
     */
    private static String stringToDecimal (String original) throws Exception {
        String decimal = "";

        // Add two digits to decimal for every character in original
        for (int i = 0; i < original.length(); i++) {
            decimal += characterToDecimal(original.charAt(i));
        }

        return decimal;
    }

    /**
     * Converts a string of decimal characters into a string of characters from LEGAL_CHARACTERS.
     * @param decimal If one of the numbers in decimal is the END_DECIMAL_MESSAGE,
     *                then only the portion of the message before that is returned .
     */
    private static String decimalToString (String decimal) {
        String original = "";

        // Loop across each DIGITS_PER_CHARACTER-long substring of decimal
        for (int i = DIGITS_PER_CHARACTER-1; i < decimal.length(); i += DIGITS_PER_CHARACTER) {
            // The decimal number representing the index of a character in LEGAL_CHARACTERS
            String decimalCharacter = decimal.substring(i+1-DIGITS_PER_CHARACTER, i+1);

            // Return only previous parts of message if END_DECIMAL_MESSAGE is found
            if (decimalCharacter.equals(END_DECIMAL_MESSAGE)) {
                return original;
            }

            // Add character to message
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
        // Throw an exception if the character is not contained in LEGAL_CHARACTERS
        if (index < 0) {
            throw new Exception("Illegal character");
        }
        else {
            // pad with '0's so that the string has length DIGITS_PER_CHARACTER (e.g. 3 -> 03)
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

    /**
     * @param n
     * @return Minimum number of digits needed to write a number in decimal (e.g. 987 has 3 digits)
     */
    private static int numberOfDigits(int n) {
        // Convert n to a String, then return the length of the String
        return ("" + n).length();
    }
}
