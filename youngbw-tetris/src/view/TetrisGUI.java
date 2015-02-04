/*
 * TCSS 305C - Autumn 2013
 * Tetris B - youngbw-TetrisB
 */
package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Board;

/**
 * This class represents the graphical user interface for the Tetris program,
 * displaying a user-interactive JFrame and its components.
 * @author BrentYoung
 * @version 1.0
 * @see MyBoardPanel
 * @see MySidePanel
 */
public class TetrisGUI extends KeyAdapter implements Observer, PropertyChangeListener {
    
    /**
     * Toolkit to obtain the screen size for centering of the GUI.
     * @see Toolkit
     */
    public static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /**
     * Variable to hold the size of the screen.
     * @see Dimension
     */
    public static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /**
     * Width of the screen in pixels.
     * @see Dimension
     */
    public static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /**
     * Height of the screen.
     * @see  Dimension
     */
    public static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    
    /**
     * Preferred size dimension object.
     */
    public static final Dimension PREFERRED_SIZE = new Dimension(SCREEN_WIDTH
                                                                 / 2 / 2, SCREEN_HEIGHT / 2);
    
    /**
     * Frame reference, holds key listener and observer.
     * @see #myPanel
     */
    private JFrame myFrame;
    
    /**
     * MyBoardPanel reference, holds the board variable and supports
     * its functionality.
     * @see Board
     * @see #myBoard
     */
    private MyBoardPanel myPanel;
    
    /**
     * Panel reference for the panel that holds the drawing of the next
     * piece to drop and the stats for the current game.
     * @see MySidePanel
     * @see #myPanel
     */
    private MySidePanel mySidePanel;
    
    /**
     * Menu bar reference used to add property change listener to this
     * menu bar object.
     */
    private MyMenuBar myMenu;
    
    /**
     * Board reference, instantiated here and passed to necessary panels.
     * @see MyBoardPanel
     * @see MySidePanel
     */
    private Board myBoard;
    
    /**
     * For use in laying an image behind the Game.
     */
    private MyBackgroundPanel myBackgroundPanel;
    
    /**
     * For use in adding an extra game panel to the frame.
     * @see MyMenuBar
     */
    private JPanel myLayoutPanel;
    
    /**
     * A second board to be used in two player mode.
     */
    private Board mySecondBoard;
    
    /**
     * Second board panel to be used in two player mode.
     */
    private MyBoardPanel mySecondPanel;
    
    /**
     * Second side panel to be used in two player mode to display stats.
     */
    private MySidePanel mySecondSidePanel;
    
    /**
     * Boolean Value to determine whether or not the two player case needs to
     * be handled.
     */
    private boolean mySecondPlayer;

    /**
     * Constructor for this GUI, initializes the board and main frame as
     * well as the panel to hold the board.
     * @see MyBoardPanel
     * @see #myFrame
     */
    TetrisGUI() {
        myBoard = new Board();
        mySecondBoard = new Board();
        mySecondPlayer = false;
        myFrame = new JFrame("TCSS 305C Tetris");
        myFrame.addKeyListener(this);
        mySecondPanel = new MyBoardPanel(mySecondBoard);
        myPanel = new MyBoardPanel(myBoard);
        myLayoutPanel = new JPanel();
        myBackgroundPanel = new MyBackgroundPanel();
    }

    /**
     * Sets all initial values for initialized variables.
     * @see #myFrame
     * @see #myPanel
     */
    public final void start() {
        myFrame.setResizable(false);
        myFrame.setSize(PREFERRED_SIZE);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocation(SCREEN_WIDTH / 2 - myFrame.getWidth() / 2, 
                        SCREEN_HEIGHT / 2 - myFrame.getHeight() / 2);
        myLayoutPanel.setBackground(Color.WHITE);
        myLayoutPanel.setBackground(new Color(0, 0, 0, 0));
        
        mySecondSidePanel = new MySidePanel(mySecondBoard, mySecondPanel);
        mySidePanel = new MySidePanel(myBoard, myPanel);
        myMenu = new MyMenuBar(myFrame, myPanel, mySidePanel, myLayoutPanel,
                               myBackgroundPanel, mySecondPanel, mySecondSidePanel);
        myMenu.addPropertyChangeListener(this);
        
        myFrame.setJMenuBar(myMenu);
        
        mySecondPanel.addPropertyChangeListener(myMenu);
        myPanel.addPropertyChangeListener(myMenu);
        
        myLayoutPanel.add(myPanel);
        myBackgroundPanel.add(myLayoutPanel, BorderLayout.CENTER);
        
        myBoard.addObserver(this);
        mySecondBoard.addObserver(this);
        
        myFrame.add(myBackgroundPanel);
        myFrame.pack();
        myFrame.setVisible(true);
        myPanel.startGame();
    }

    /**
     * Updates the GUI based on the state of the board.
     * @param theO object that called the notifyObservers method.
     * @param theArg argument passed by theO
     * @see Board
     */
    @Override
    public void update(final Observable theO, final Object theArg) {
        myFrame.repaint();
//        mySidePanel.repaint();
    }
    
    /**
     * Key released event for movement of pieces as they
     * descend down the board.
     * @param theEvent event fired by the Key Listener
     * @see #myBoard
     */
    @Override
    public void keyReleased(final KeyEvent theEvent) {
        if (!myPanel.isGamePaused()) {
            final int code = theEvent.getKeyCode();
            if (code == KeyEvent.VK_A) {
                myBoard.moveLeft();
            } else if (code == KeyEvent.VK_S) {
                myBoard.moveDown();
            } else if (code == KeyEvent.VK_D) {
                myBoard.moveRight();
            } else if (code == KeyEvent.VK_E) {
                myBoard.hardDrop();
            } else if (code == KeyEvent.VK_W) {
                myBoard.rotate();
            }
        }
        if (mySecondPlayer && !myPanel.isGamePaused()) {
            final int code = theEvent.getKeyCode();
            if (code == KeyEvent.VK_LEFT) {
                mySecondBoard.moveLeft();
            } else if (code == KeyEvent.VK_DOWN) {
                mySecondBoard.moveDown();
            } else if (code == KeyEvent.VK_RIGHT) {
                mySecondBoard.moveRight();
            } else if (code == KeyEvent.VK_SPACE) {
                mySecondBoard.hardDrop();
            } else if (code == KeyEvent.VK_SHIFT) {
                mySecondBoard.rotate();
            }
        }
    }

    /**
     * Handles the case in which two player mode has been activated, starting a new game
     * on each board or ending it in the case that one person finishes their game.
     * @param theEvent propertychange event used for two player cases
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("two player".equalsIgnoreCase(theEvent.getPropertyName())) {
            mySecondPlayer = (boolean) theEvent.getNewValue();
            myFrame.pack();
            myFrame.repaint();
            if (mySecondPlayer) {
                myPanel.startGame();
                mySecondPanel.startGame();
            } else {
                if (!myBoard.isGameOver()) {
                    myPanel.endGame();
                }
                if (!mySecondBoard.isGameOver()) {
                    mySecondPanel.endGame();
                }
            }
            
        } 
    }
}
