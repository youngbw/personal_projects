/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Defines the Tetris ZPiece.
 * 
 * @author Alan Fowler
 * @version Autumn 2013
 */
public final class ZPiece extends AbstractPiece {

    /**
     * The x and y-coordinates for all rotations of a ZPiece.
     */
    private static final int[][][] MY_ROTATIONS = {{{0, 2}, {1, 2}, {1, 1}, {2, 1}},
                                                   {{2, 2}, {1, 1}, {2, 1}, {1, 0}},
                                                   {{0, 1}, {1, 1}, {1, 0}, {2, 0}},
                                                   {{1, 2}, {0, 1}, {1, 1}, {0, 0}}};
    
    /**
     * Creates a new Z piece at the given coordinates.
     * 
     * @param theX The x coordinate of the Piece.
     * @param theY The y coordinate of the piece
     */
    public ZPiece(final int theX, final int theY) {
        super(MY_ROTATIONS, theX, theY, Block.Z);
    }

}
