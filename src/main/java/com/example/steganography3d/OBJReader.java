/**
 @author Matthew Allgaier
 @since 4/17/2025
 OBJReader.java
 Used to read a .obj file and output an Object3D Object
 */

package com.example.steganography3d;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OBJReader {

    private File file;
    private Object3D object3D;

    public File getFile () { return file; }
    public Object3D getObject3D () { return object3D; }

    // Constructors

    /**
     * @param objFile Should be .obj format
     */
    public OBJReader(File objFile) throws FileNotFoundException {
        this.file = objFile;
        extractData();
    }
    public OBJReader(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    // Methods

    // Stores .obj data from file into object3D so it doesn't have to be processed everytime getObject3D is called
    private void extractData () throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);
        ArrayList<String> fileContents = fileToArrayList(fileScanner, new ArrayList<>());
        object3D = new Object3D(fileContents);
    }

    // Converts a text file into an ArrayList<String> by recursion
    private static ArrayList<String> fileToArrayList (Scanner fileScanner, ArrayList<String> result) {
        // Base case: There are no more lines in the scanner
        if (!fileScanner.hasNextLine()) {
            // Return the result, there is nothing to add
            return result;
        }
        // There are more lines
        else {
            // Add the next line to the result
            result.add(fileScanner.nextLine());
            // Recursive call
            return fileToArrayList(fileScanner, result);
        }

    }
}
