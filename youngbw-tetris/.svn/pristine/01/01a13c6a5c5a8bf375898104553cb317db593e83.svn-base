/*
 * TCSS 305C - Autumn 2013
 * Tetris B - youngbw-TetrisB
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Board;

/**
 * This panel displays the stats for the current game graphically.
 * @author BrentYoung
 * @version 1.0
 * @see MySidePanel
 */
@SuppressWarnings("serial")
public class StatsPanel extends JPanel implements Observer, PropertyChangeListener {
    
    /**
     * Default font size for the text to be written in.
     */
    private static final int FONT_SIZE = 20;
    
    /**
     * X value for the graphical text to be translated over.
     * Keeps String from writing on the border.
     */
    private static final int MY_X = 10;
    
    /**
     * Number of lines to clear a level.
     */
    private static final int NUM_LINES_PER_LEVEL = 10;
    
    /**
     * Default score multiplier.
     */
    private static final int SCORE_MULTIPLIER = 5;
    
    /**
     * Max number of lines possible to clear at one time.
     */
    private static final int MAX_LINES_CLEARABLE = 4;
    
    /**
     * String representation of leveling up, used for propertychange.
     */
    private static final String LEVEL_UP = "Level-up";
    
    /**
     * Font style to be used.
     */
    private static final String FONT_STYLE = "Times New Roman";
        
    /**
     * Number of lines cleared from the current game.
     * @see #myLevel
     */
    private int myNumLines;
    
    /**
     * Score for the current game.
     * @see #myNumLines
     */
    private int myScore;
    
    /**
     * Current level of the game.
     * @see #myScore
     */
    private int myLevel;
    
    /**
     * Constructor for this panel, initializes all stats values, sets the
     * layout and background color.
     * @param theBoard reference to the board object, may need to be used later for stats
     */
    public StatsPanel(final Board theBoard) {
        myNumLines = 0;
        myScore = 0;
        myLevel = 1;
        theBoard.addObserver(this);
        setLayout(new GridLayout());
        setBackground(new Color(0, 0, 0, 0));
    }
    
    /**
     * {@inheritDoc}
     * Overridden paint component method, draws all of the statistical
     * values onto the stats panel.
     * @see MyBoardPanel.#paintComponent(Graphics)
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2D = (Graphics2D) theGraphics;
        final String lineString = "Lines Cleared: " + myNumLines;
        final String levelString = "Level: " + myLevel;
        final String scoreString = "Score: " + myScore;
        
        g2D.setStroke(new BasicStroke(2));
        g2D.setFont(new Font(FONT_STYLE, Font.BOLD, FONT_SIZE));
        g2D.setColor(Color.WHITE);
        
        g2D.drawString(scoreString, MY_X, getHeight() / (2 * 2));
        g2D.drawString(levelString, MY_X, getHeight() / 2);
        g2D.drawString(lineString, MY_X, getHeight() - getHeight() / (2 * 2));
    }

    @Override
    public void update(final Observable theO, final Object theArg) {
        if (theArg != null) {
            if (theArg instanceof Integer) {
                final int lines = (int) theArg;
                myNumLines += lines;
                if (lines < MAX_LINES_CLEARABLE) {
                    myScore += SCORE_MULTIPLIER * lines * myLevel;
                } else {
                    myScore += SCORE_MULTIPLIER * 2 * lines * myLevel;
                }
            } else if ("freeze".equalsIgnoreCase((String) theArg.toString())) {
                myScore += 1 * myLevel;
            }
        }
        if (myNumLines / NUM_LINES_PER_LEVEL >= myLevel) {
            firePropertyChange(LEVEL_UP, myLevel, myLevel + 1);
            myLevel++;
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("NewGame".equalsIgnoreCase(theEvent.getPropertyName())) {
            myScore = 0;
            myLevel = 1;
            myNumLines = 0;
            firePropertyChange(LEVEL_UP, myLevel, 0);
        }
    }
}
