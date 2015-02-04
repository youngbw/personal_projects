/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Defines the Tetris SPiece.
 * 
 * @author Alan Fowler
 * @version Autumn 2013
 */
public final class SPiece extends AbstractPiece {

    /**
     * The x and y-coordinates for all rotations of a SPiece.
     */
    private static final int[][][] MY_ROTATIONS = {{{1, 2}, {2, 2}, {0, 1}, {1, 1}},
                                                   {{1, 2}, {1, 1}, {2, 1}, {2, 0}},
                                                   {{1, 1}, {2, 1}, {0, 0}, {1, 0}},
                                                   {{0, 2}, {0, 1}, {1, 1}, {1, 0}}};

    /**
     * Creates a new S piece at the given coordinates.
     * 
     * @param theX The x coordinate of the Piece.
     * @param theY The y coordinate of the piece
     */
    public SPiece(final int theX, final int theY) {
        super(MY_ROTATIONS, theX, theY, Block.S);
    }
}
