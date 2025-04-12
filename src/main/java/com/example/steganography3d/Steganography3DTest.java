package com.example.steganography3d;

import java.io.File;
import java.io.FileNotFoundException;

public class Steganography3DTest {
    public static void main(String[] args) throws Exception {
        String original = " !\"#$%&'()*+,-./0123456789~";
        System.out.println(original);
        String decimal = Steganographer.stringToDecimal(original);
        System.out.println(decimal);
        String decoded = Steganographer.decimalToString(decimal);
        System.out.println(decoded);
    }
}
