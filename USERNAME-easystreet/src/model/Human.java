/**
 * 
 */
package model;

/**
 * @author BrentYoung
 * @version 1.0
 */
public class Human extends AbstractVehicle {

    private Terrain myTerrain;
    
    public Human(final int theX, final int theY, final Direction theDir,
                 final Terrain theTerrain) {
        super(theX, theY, theDir);
        myTerrain = theTerrain;
    }
    
}
