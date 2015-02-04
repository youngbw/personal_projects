/*
 * TCSS 305C - Autumn 2013
 * Tetris B - youngbw-TetrisB
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.Board;

/**
 * This class represents the side panel that displays the next piece that
 * will drop down, the current score, level, and lines cleared as well as
 * a theme picture.
 * @author BrentYoung
 * @version 1.0
 * @see PiecePanel
 * @see StatsPanel
 * @see PicturePanel
 */
@SuppressWarnings("serial")
public class MySidePanel extends JPanel implements Observer {
    
    /**
     * Default thickness for the border of piece drawing.
     */
    private static final int THICKNESS = 2;
    
    /**
     * Number of rows of panels to be added to the panel.
     */
    private static final int GRID_ROWS = 3;
    
    /**
     * Number of columns of panels to be added to the panel.
     */
    private static final int GRID_COLUMNS = 1;
    
    /**
     * Alpha value for side panel transparency.
     */
    private static final int ALPHA = 150;
    
    /**
     * Default Dimension for the preferred size of this panel.
     */
    private static final Dimension DEFAULT_DIMENSION = TetrisGUI.PREFERRED_SIZE;
    
    /**
     * Reference to the panel that will hold the next piece drawing.
     * @see PiecePanel
     * @see #myStatsPanel
     */
    private PiecePanel myPiecePanel;
    
    /**
     * Reference to the stats panel that will display the score, level, and lines cleared.
     * @see StatsPanel
     * @see #myPiecePanel
     */
    private StatsPanel myStatsPanel;
    
    /**
     * Constructor for this SidePanel, sets size, border, layout, and adds the piece,
     * stats, and picture panels.
     * @param theBoard reference to the board object in play
     * @param thePanel board panel reference to keep score for
     * @see MyBoardPanel
     */
    public MySidePanel(final Board theBoard, final MyBoardPanel thePanel) {
        initialize();
        myPiecePanel = new PiecePanel(theBoard);
        myStatsPanel = new StatsPanel(theBoard);
        myStatsPanel.addPropertyChangeListener(thePanel);
        thePanel.addPropertyChangeListener(myStatsPanel);
        theBoard.addObserver(this);
        add(myPiecePanel);
        add(myStatsPanel);
    }
    
    /**
     * Initializes values and sets borders and layouts.
     */
    private void initialize() {
        setPreferredSize(new Dimension(DEFAULT_DIMENSION.width / 2,
                                       DEFAULT_DIMENSION.height));
        setBackground(new Color(0, 0, 0, ALPHA));
        setBorder(new LineBorder(Color.BLACK, THICKNESS));
        setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS));
    }
 
    /**
     * Update method used to complete the observer pattern design.
     * @see Board
     * @param theO the object calling the notifyObservers() method
     * @param theArg arguments passed by theO
     */
    @Override
    public void update(final Observable theO, final Object theArg) {
        myPiecePanel.repaint();
        myStatsPanel.repaint();
    }    
}
