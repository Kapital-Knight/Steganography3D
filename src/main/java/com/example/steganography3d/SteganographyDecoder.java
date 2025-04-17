package com.example.steganography3d;

public class SteganographyDecoder extends Steganographer {

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
     * @param decimal a two-digit number assumed to be between 00 and LAST_ALLOWED_CHARACTER_INT
     * @return the corresponding character between FIRST_ALLOWED_CHARACTER and LAST_ALLOWED_CHARACTER
     */
    private static Character decimalToCharacter (String decimal) {
        int index = Integer.parseInt(decimal);
        return LEGAL_CHARACTERS.charAt(index);
    }
}
