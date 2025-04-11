package com.example.steganography3d;

import java.math.BigDecimal;

public class Steganographer {
    public static Object3D hideMessageInObject (String message, Object3D coverObject) {
        Object3D stegoObject = new Object3D();
        return stegoObject;
    }

    public static String readMessageFromMesh (PolygonalMesh stegoMesh) {
        String message = "";
        return message;
    }

    public static String stringToDecimal (String original) throws Exception {
        // All latin characters fall between these two in Unicode (except tab and newline)
        char firstAllowedCharacter = ' ';
        char lastAllowedCharacter = '~';

        if (!original.matches("[ -~]*")) {
            throw new Exception("Argument contains character outside of accepted range (" + firstAllowedCharacter + "," + lastAllowedCharacter + ")");
        }

        String decimal = "";

        for (int i = 0; i < original.length(); i++) {
            int characterCode = original.charAt(i) - firstAllowedCharacter;
            if (characterCode <= 9) {
                decimal += "0" + characterCode;
            }
            else {
                decimal += characterCode;
            }
        }

        return decimal;
    }


}
