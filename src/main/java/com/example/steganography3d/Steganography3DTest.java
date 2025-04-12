package com.example.steganography3d;

import java.io.File;
import java.io.FileNotFoundException;

public class Steganography3DTest {
    public static void main(String[] args) throws Exception {
        String original = " !\"#$%&'()*+,-./0123456789~";
        System.out.println(original);
        String decimcal = Steganographer.stringToDecimal(original);
        System.out.println(decimcal);
        String decoded = Steganographer.decimalToString(decimcal);
        System.out.println(decoded);
    }
}
