/*
 * TCSS 305C - Autumn 2013
 * Tetris B - youngbw-TetrisB
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import model.AbstractPiece;
import model.Block;
import model.Board;

/**
 * This class represents the visual display of the tetris board.
 * @author BrentYoung
 * @version 1.0
 * @see Board
 */
@SuppressWarnings("serial")
public class MyBoardPanel extends JPanel implements PropertyChangeListener {
    
    /**
     * Default width of the panel.
     * @see TetrisGUI
     */
//    public static final int DEFAULT_WIDTH = 400;
    
    /**
     * Default height of the panel.
     * @see TetrisGUI
     */
//    public static final int DEFAULT_HEIGHT = 600;
    
    /**
     * Width of the game board.
     * @see #GAME_HEIGHT
     */
    public static final int GAME_WIDTH = 10;
    
    /**
     * Height of the game board.
     * @see #GAME_WIDTH
     */
    public static final int GAME_HEIGHT = 20;
    
    /**
     * Default Dimension for this panel.
     * @see #DEFAULT_HEIGHT
     * @see #DEFAULT_WIDTH
     */
    private static final Dimension DEFAULT_DIMENSION = new Dimension(TetrisGUI.PREFERRED_SIZE);
    
    /**
     * Thickness the borders for each block will be drawn in.
     */
    private static final int THICKNESS = 2;
    
    /**
     * Initial delay the timer will be set to wait before
     * dropping the first piece of the game.
     * @see #myBoardTimer
     */
    private static final int DEFAULT_INITIAL_DELAY = 2000;
    
    /**
     * Default interval at which the timer will move the current
     * piece down one space.
     * @see #DEFAULT_INITIAL_DELAY
     */
    private static final int DEFAULT_DROP_INTERVAL = 750;
    
    /**
     * Amount to decrease delay on timers after completed level.
     */
    private static final int DEFAULT_DELAY_REDUCTION = 75;
    
    /**
     * Lowest delay the timers will be set at.
     */
    private static final int MINIMUM_DROP_INTERVAL = 150;
    
    /**
     * Alpha value for screen hazing during pause and end game.
     */
    private static final int ALPHA = 200;
    
    /**
     * Gray color value for screen hazing during pause and end game.
     */
    private static final int GRAY = 50;
    
    /**
     * Font size for String drawing during screen hazing.
     */
    private static final int FONT_SIZE = 18;
    
    /**
     * Color comprised of GRAY and ALPHA values for screen hazing.
     * @see #GRAY
     * @see #ALPHA
     */
    private static final Color GRAY_OUT = new Color(GRAY, GRAY, GRAY, ALPHA);
    
    /**
     * Reference to the board.
     * @see Board
     */
    private Board myBoard;
    
    /**
     * Reference to the timer for the board.
     * @see #setupTimerListeners()
     */
    private Timer myBoardTimer;
    
    /**
     * Listener for the board timer, reference used to add and remove action
     * listener as needed.
     */
    private MyTimerListener myListener;
    
    /**
     * Boolean flag for whether the game is currently paused or not.
     * @see #pauseGame()
     */
    private boolean myIsPaused;
    
    
    /**
     * Constructor for this board panel, initializes the board and adds
     * all listener objects.
     * @param theBoard reference to the board
     * @see Board
     */
    public MyBoardPanel(final Board theBoard) {
        myIsPaused = false;
        myBoard = theBoard;
        myListener = new MyTimerListener();
        myBoardTimer = new Timer(DEFAULT_INITIAL_DELAY, null);
        initialize();
    }
    
    /**
     * Initializes all default states for instantiated objects.
     * @see #MyBoardPanel(Board)
     */
    private void initialize() {
        setPreferredSize(DEFAULT_DIMENSION);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(new LineBorder(Color.BLACK, THICKNESS));
        myBoardTimer.setDelay(DEFAULT_DROP_INTERVAL);
        myBoardTimer.setRepeats(true);
        myBoardTimer.addActionListener(myListener);
    }
    
    
    /**
     * Starts a new game on the current board.
     * @see Board.#newGame(int, int, List)
     */
    public void startGame() {
        myBoard.newGame(GAME_WIDTH, GAME_HEIGHT, null);
        firePropertyChange("New Game", false, true);
        myBoardTimer.restart();
        repaint();
    }
    
    /**
     * Boolean query to check if the game is paused.
     * @return boolean whether the game is paused or not
     * @see #pauseGame()
     */
    public boolean isGamePaused() {
        return myIsPaused;
    }
    
    /**
     * Resumes the current game in progress.
     * @see #pauseGame()
     */
    public void continueGame() {
        myIsPaused = false;
        myBoardTimer.start();
    }
    
    /**
     * Pauses the current game in progress.
     * @see #continueGame()
     * @see #isGamePaused()
     */
    public void pauseGame() {
        myIsPaused = true;
        myBoardTimer.stop();
    }
    
    /**
     * Ends the current game in progress.
     * @see #myBoardTimer
     */
    public void endGame() {
        myBoardTimer.stop();
        myBoard.setEndGame();
        firePropertyChange("EndGame", false, true);
    }
    
    /**
     * Stops the timer so the program can quit completely.
     * @see #endGame()
     */
    public void quitGame() {
        myBoardTimer.stop();
        myBoardTimer.removeActionListener(myListener);
    }
    
    /**
     * {@inheritDoc}
     * Overridden paint component method, draws the current piece in motion,
     * the frozen blocks and any other graphical components seen on the Board Panel.
     * @param theGraphics graphics object for drawing on the panel
     * @see Board
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
               
        final double pieceHeight = this.getHeight() / GAME_HEIGHT;
        final double pieceWidth = this.getWidth() / GAME_WIDTH;
        super.paintComponent(theGraphics);
        final Graphics2D g2D = (Graphics2D) theGraphics;
        
        
        
        final int[][] pieceCoord = ((AbstractPiece) myBoard.getCurrentPiece()).
                getBoardCoordinates();
        final List<Block[]> frozenBlocks = myBoard.getFrozenBlocks();
        
        for (int i = 0; i < pieceCoord.length; i++) {
            final Rectangle2D.Double shape = new Rectangle2D.Double(pieceCoord[i][0] 
                    * pieceWidth, pieceCoord[i][1] * (-1 * pieceHeight) 
                    + (getHeight() - pieceHeight), pieceWidth, pieceHeight);
            g2D.setStroke(new BasicStroke(THICKNESS));
            g2D.setColor(((AbstractPiece) myBoard.getCurrentPiece()).getBlock().getColor());
            g2D.fill(shape);
            g2D.setColor(Color.BLACK);
            g2D.draw(shape);
        }
        
        if (!frozenBlocks.isEmpty()) {
            for (int i = 0; i < frozenBlocks.size(); i++) {
                final Block[] row = frozenBlocks.get(i);
                for (int j = 0; j < row.length; j++) {
                    final Block block = row[j];
                    if (block != Block.EMPTY) {
                        final Rectangle2D.Double shape = new Rectangle2D.Double(j * pieceWidth,
                                  -i * pieceHeight + (getHeight() - pieceHeight),
                                  pieceWidth, pieceHeight);
                        g2D.setColor(block.getColor());
                        g2D.fill(shape);
                        g2D.setColor(Color.BLACK);
                        g2D.draw(shape);
                    }
                }
            }
        }
        
        if (myIsPaused || myBoard.isGameOver()) {
            g2D.setColor(GRAY_OUT);
            g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2D.setFont(new Font("Times New Roman", Font.BOLD, FONT_SIZE));
            g2D.setColor(Color.WHITE);
            String line;
            if (myBoard.isGameOver()) {
                line = "Game Over! How did you do?!";
                
            } else {
                line = "Game Paused (Press Ctrl + Y to Resume)";
            }
            g2D.drawString(line, 2, this.getHeight() - FONT_SIZE);
        }
    }
    
    /**
     * Returns the width of the current game board.
     * @return int game board width
     * @see #getGameHeight()
     */
    public int getGameWidth() {
        return GAME_WIDTH;
    }
    
    /**
     * Returns the height of the current game board.
     * @return int game board height
     * @see #getGameWidth()
     */
    public int getGameHeight() {
        return GAME_HEIGHT;
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("Level-up".equalsIgnoreCase(theEvent.getPropertyName())) {
            final int delay = DEFAULT_DROP_INTERVAL - DEFAULT_DELAY_REDUCTION
                    * (int) theEvent.getNewValue();
            if (delay > MINIMUM_DROP_INTERVAL) {
                myBoardTimer.setDelay(delay);
            } else {
                myBoardTimer.setDelay(MINIMUM_DROP_INTERVAL);
            }
        }
    }
    
    /**
     * Action Listener class for the board timer.
     * @author BrentYoung
     * @version 1.0
     * @see MyBoardPanel
     */
    private class MyTimerListener implements ActionListener {

        /**
         * Adds the action listener to the board timer and handles the step logic therein.
         * @param theEvent event fired by this listener
         * @see #myBoardTimer
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myBoard.isGameOver()) {
                myBoard.step();
            } else {
                endGame();
            }
        }
    }
}
