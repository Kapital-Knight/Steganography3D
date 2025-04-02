package com.example.steganography3d;

import javax.swing.*;

public class Help {
    public static void about() {
        JOptionPane.showMessageDialog(null, """
                Steganography 3D allows the user to discretely encode a messages in 3D objects.
                Simply type in a message and import a 3D object file (.obj format).
                The output stego-object is nearly identical to the original object.
                You can use Steganography 3D to read the hidden message from the stego-object.""");
    }
}
