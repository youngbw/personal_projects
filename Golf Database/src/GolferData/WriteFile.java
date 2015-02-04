package GolferData;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * WriteFile.java
 * A class that writes to a file passed to it.
 * <pre>
 *    Project: Golfers Database
 *    Platform: jdk 1.6.0_14; NetBeans IDE 6.8; Mac OSx
 *    Course: CS 143
 *    Hours: 1 hours
 *    Created on May 12, 2013, 10:00:00 PM
 * </pre>
 *
 * @author	brent.young@go.shoreline.edu
 * @version 	%1% %3%
 * @see         java.io.BufferedReader
 * @see         java.io.FileNotFoundException
 * @see         java.io.IOException
 * @see         java.io.FileWriter
 * @see         java.io.PrintWriter
 * @see         ReadFile
 */
public class WriteFile {
    
    private FileWriter filename;
    
    /**
     * Constructs a new WriteFile object on the file passed.
     * @param inputFilename fileName to be written to.
     * @see ReadFile
     * @see #write(java.lang.String) 
     */
    public WriteFile(String inputFilename) {
        try {
            filename = new FileWriter(inputFilename, false);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Closes the file that the WriteFile object has been placed on.
     * @see ReadFile.#close()
     */
    public void close() {
        try {
            filename.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Prints the passed String to the desired file.
     * @param item String to be written
     * @see ReadFile
     * @see #WriteFile(java.lang.String) 
     * @see ReadFile.#readRecord() 
     */
    public void write(String item) {
        PrintWriter output = new PrintWriter(filename);
        output.println(item);
    }
}