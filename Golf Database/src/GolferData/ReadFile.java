package GolferData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ReadFile.java
 * A class that reads a file passed to it.
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
 * @see     	javax.swing
 * @see         java.io.BufferedReader
 * @see         java.io.FileNotFoundException
 * @see         java.io.IOException
 * @see         java.io.FileReader
 * @see         WriteFile
 */
public class ReadFile {
    
    
    private FileReader inputFile;
    private BufferedReader input;
    
    
    /**
     * Constructor that builds a ReadFile object onto the passed file.
     * @param inFileName file name to have ReadFile object built onto it.
     */
    public ReadFile(String inFileName) {
        try {
            inputFile = new FileReader(inFileName);
            input = new BufferedReader(inputFile);
            
        } catch (FileNotFoundException ex) {
            System.out.print("File open error: " + ex.getMessage());
        }
    }
    
    /**
     * Reads a line using the constructed ReadFile object.
     * @return String representing one line of the desired file.
     * @see #ReadFile(java.lang.String) 
     */
    public String readRecord() {
        String line = "";
        try {
            line = input.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return line;
    }
    
    /**
     * Closes the ReadFile object.
     * @see #ReadFile(java.lang.String) 
     * @see #readRecord() 
     */
    public void close() {
        try {
            inputFile.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
