/**
 @author Matthew Allgaier
 @since 5/17/2025
 Help.java
 Describes purpose of app Steganography 3D
 */
package com.example.steganography3d;

import javax.swing.*;

public class Help {
    // Describes the purpose of this app -- Steganography3D -- using a graphical message
    public static String about() {
        return """
                Steganography 3D allows the user to discretely encode a messages in 3D objects.
                Simply type in a message and import a 3D object file (.obj format).
                The output stego-object is nearly identical to the original object.
                You can use Steganography 3D to read the hidden message from the stego-object.""";
    }
}
