/*
 * TCSS 305C - Autumn 2013
 * Tetris A - youngbw-TetrisA
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class represents the help pane, reading from a text file to
 * display a help message.
 * @author BrentYoung
 * @version 1.0
 * @see TetrisGUI
 */
public final class MyHelpPanel {

    /**
     * Toolkit to obtain the screen size for centering of the GUI.
     * @see Toolkit
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /**
     * Variable to hold the size of the screen.
     * @see Dimension
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /**
     * Width of the screen in pixels.
     * @see Dimension
     */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /**
     * Height of the screen.
     * @see  Dimension
     */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    
    /**
     * JFrame object for this help frame.
     */
    private JFrame myFrame;
    
    /**
     * Constructor for this Help Frame, initializes all default values and
     * locations.
     * @param theFileName file from which to draw text from
     * @see #createTextArea(String)
     */
    public MyHelpPanel(final String theFileName) {
        myFrame = new JFrame("Tetris -- Help");
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setPreferredSize(new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));
        final JTextArea text = createTextArea(theFileName);
        final JScrollPane scroll = new JScrollPane(text);
        myFrame.add(scroll);
        
        myFrame.pack();
        myFrame.setLocation((SCREEN_WIDTH / 2) - (myFrame.getWidth() / 2),
                            (SCREEN_HEIGHT / 2) - (myFrame.getHeight() / 2));
    }
    
    /**
     * Creates a new JTextArea and places the desired text from fileName onto the area.
     * @param theFileName file to draw text from for this help frame.
     * @return JTextArea text area for help menu text
     */
    private JTextArea createTextArea(final String theFileName) {
        final JTextArea area = new JTextArea();
        area.setSize(new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        
        try {
            final Scanner fileScanner = new Scanner(new File(theFileName));
            String line = new String();
            if (!theFileName.isEmpty()) {
                line = fileScanner.nextLine();
                while (fileScanner.hasNextLine()) {
                    line += "\n" + fileScanner.nextLine();
                }
                fileScanner.close();
                area.setText(line);
            }
            
        } catch (final FileNotFoundException ex) {
            ex.printStackTrace();
        }        
        return area;
    }
    
    /**
     * Sets this frame as visible, separate due to not wanting this
     * to always been seen.
     */
    public void setVis() {
        myFrame.setVisible(true);
    }
}
