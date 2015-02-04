/*
 * TCSS 305C - Autumn 2013
 * Tetris B - youngbw-TetrisB
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import model.AbstractPiece;
import model.Board;
import model.IPiece;
import model.OPiece;

/**
 * This panel displays the next piece that will be dropped down
 * onto the board.
 * @author BrentYoung
 * @version 1.0
 * @see Board
 */
@SuppressWarnings("serial")
public class PiecePanel extends JPanel {
    
    /**
     * Default thickness in which the graphics object will draw on this panel.
     */
    private static final int THICKNESS = 2;
    
    /**
     * Reference to the board in order to get the next piece.
     * @see Board.#getNextPiece()
     */
    private Board myBoard;
    
    /**
     * Constructor for this panel, sets the reference to the Board and the
     * background color.
     * @param theBoard reference to the board object
     * @see SidePanel
     */
    public PiecePanel(final Board theBoard) {
        setBackground(new Color(0, 0, 0, 0));
        myBoard = theBoard;
    }

    /**
     * Overridden paint component method, paints the next piece onto the panel.
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2D = (Graphics2D) theGraphics;
        
        final int pieceHeight = TetrisGUI.PREFERRED_SIZE.height / MyBoardPanel.GAME_HEIGHT;
        final int pieceWidth = TetrisGUI.PREFERRED_SIZE.width / MyBoardPanel.GAME_WIDTH;
        final int buffer;
        if (myBoard.getNextPiece() instanceof IPiece
                || myBoard.getNextPiece() instanceof OPiece) {
            buffer = pieceWidth / 2;
        } else {
            buffer = pieceWidth;
        }
        
        final int[][] rotateCoord = ((AbstractPiece) myBoard.getNextPiece()).getRotation();
        
        for (int i = 0; i < rotateCoord.length; i++) {

            final Rectangle2D.Double shape = new Rectangle2D.Double(buffer 
                  + rotateCoord[i][0] * pieceWidth, rotateCoord[i][1] * (-1 * pieceHeight) 
                  + (getHeight() - pieceHeight - 2 * buffer),
                  pieceWidth, pieceHeight);
            g2D.setStroke(new BasicStroke(THICKNESS));
            g2D.setColor(((AbstractPiece) myBoard.getNextPiece()).getBlock().getColor());
            g2D.fill(shape);
            g2D.setColor(Color.WHITE);
            g2D.draw(shape);
        }        
    }
}
