/*
 * TCSS 305C - Autumn 2013
 * Tetris B - youngbw-TetrisB
 */
package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The Panel that is to be used as the background underlying image,
 * done by calling the paintComponents method on this panel.
 * @author BrentYoung
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MyBackgroundPanel extends JPanel {

    /**
     * File path to the desired image to be painted.
     */
    private static final String FILE_PATH = "./resources/tetris.jpg";
    
    /**
     * Image reference for the image to be used as the background.
     */
    private ImageIcon myImage;
    
    /**
     * Panel to be used as the background picture panel.
     * @see TetrisGUI
     */
    public MyBackgroundPanel() {
        setLayout(new BorderLayout());
        myImage = new ImageIcon(FILE_PATH);
    }
    
    /**
     * Paints the image on the panel.
     * @param theGraphics graphics to be used for painting the image
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2D = (Graphics2D) theGraphics;
        g2D.drawImage(myImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
    
}
