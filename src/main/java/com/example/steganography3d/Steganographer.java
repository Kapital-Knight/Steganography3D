/**
 @author Matthew Allgaier
 @since 4/17/2025
 Steganographer.java
 This class can either hide a String message in an object3D using least significant digit encoding, or read such a message.
 */
package com.example.steganography3d;

public class Steganographer {
    
    // How many decimal characters are used to describe each original message character
    private static final int DIGITS_PER_CHARACTER = 5;
    // A character (undefined in Unicode) which Steganographer uses to mark the end of a message
    private static final String END_MESSAGE = characterToDecimal('\uFFF0', '\u0000');


    /**
     * @param coverObject If there are not enough vertices in coverObject, the full message may not be hidden.
     * @return A stego object nearly identical to coverObject,
     * except the least significant vertex digits contain message as a decimal.
     */
    public static Object3D hideMessageInObject (String message, Object3D coverObject, String key) throws IllegalArgumentException {
        // Make sure key has at least one character
        key = key.isEmpty() ? "\0000" : key;
        // Covert to decimal then add END_DECIMAL_MESSAGE to indicate when the message ends and repeats
        String decimalMessage = stringToDecimal(message, key);
        decimalMessage += END_MESSAGE;
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
    public static String readMessageInObject (Object3D stegoObject, String key) {
        // Make sure key has at least one character
        key = key.isEmpty() ? "\0000" : key;
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
            return decimalToString(decimalMessage, key);
        }
        catch (StringIndexOutOfBoundsException e) {
            return "ERROR: Could not read message from object because it contained undefined characters.";
        }

    }

    /**
     * Converts a string of legal characters into a longer string of only decimal characters.
     * See characterToDecimal for more info.
     */
    private static String stringToDecimal (String original, String key) {
        String decimal = "";

        // Add DIGITS_PER_CHARACTER digits to decimal string for every character in original
        for (int i = 0; i < original.length(); i++) {
            decimal += characterToDecimal(original.charAt(i), key.charAt(i % key.length()));
        }

        return decimal;
    }

    /**
     * Converts a string of decimal characters into a string of characters
     * @param decimal If one of the numbers in decimal is the END_DECIMAL_MESSAGE,
     *                then only the portion of the message before that is returned .
     */
    private static String decimalToString (String decimal, String key) {
        String original = "";

        // Loop across each DIGITS_PER_CHARACTER-long substring of decimal
        for (int i = DIGITS_PER_CHARACTER-1; i < decimal.length(); i += DIGITS_PER_CHARACTER) {
            // The decimal number with length DIGITS_PER_CHARACTER
            String decimalCharacter = decimal.substring(i+1-DIGITS_PER_CHARACTER, i+1);

            // Return only previous parts of message if END_DECIMAL_MESSAGE is found
            if (decimalCharacter.equals(END_MESSAGE)) {
                return original;
            }

            // Add character to message
            char keyCharacter = key.charAt((i / DIGITS_PER_CHARACTER) % key.length());
            char character = decimalToCharacter(decimalCharacter, keyCharacter);
            original += character;
        }

        return original;
    }

    /**
     * @return the index of c in the String LEGAL_CHARACTERS,
     * padded with 0's on the left so that every index has the same number of digits.
     * Throws error if character is not contained in LEGAL_CHARACTERS.
     */
    private static String characterToDecimal (char c, char key) {
        // Apply key to c using XOR
        int transformedC = key ^ c;
        
        String decimal = "";
        // Add each component of three bits to decimal, leaving out the first bit on the left
        for (int i = DIGITS_PER_CHARACTER-1; i >= 0; i--) {
            // Select only 3 bits at a time, starting with the leftmost three bits
            int component = (transformedC >> 3*i) & 0b111;
            decimal += component;
        }
        return decimal;
    }

    /**
     * @param decimal a five-digit number, with each digit being between 0 and 7
     * @return the corresponding character between FIRST_ALLOWED_CHARACTER and LAST_ALLOWED_CHARACTER
     */
    private static Character decimalToCharacter (String decimal, char key) {
        int character = 0b0;

        for (int i = 0; i < DIGITS_PER_CHARACTER; i++) {
            // Convert character to int value it represents
            int value = decimal.charAt(i) & 0b1111;
            // Shift left to appropriate position
            int component = value << 3 * (4-i);

            character += component;
        }

        return (char)(character ^ key);
    }
}
