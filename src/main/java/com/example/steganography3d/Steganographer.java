package com.example.steganography3d;

public class Steganographer {
    public Object3D hideMessageInObject (String message, Object3D coverObject) {
        Object3D stegoObject = new Object3D();
        return stegoObject;
    }

    public String readMessageFromMesh (PolygonalMesh stegoMesh) {
        String message = "";
        return message;
    }

    protected String stringToDecimal (String original) throws Exception {
        // All latin characters fall between these two in Unicode (except tab and newline)
        char firstAllowedCharacter = ' ';
        char lastAllowedCharacter = '~';

        if (!original.matches("[ -~]*")) {
            throw new Exception("Argument contains character outside of accepted range (" + firstAllowedCharacter + "," + lastAllowedCharacter + ")");
        }

        return "Not implemented";
    }


}
