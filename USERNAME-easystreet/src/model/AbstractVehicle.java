package model;

import java.util.Map;

public abstract class AbstractVehicle implements Vehicle {

    private int myX;
    private int myY;
    private Direction myDir;
    private boolean myLife;
    private int numPokes;
    
    protected AbstractVehicle(final int theX, final int theY, final Direction theDir) {
        myX = theX;
        myY = theY;
        myDir = theDir;
        myLife = true;
        numPokes = 0;
    }
    
    
    @Override
    public boolean canPass(Terrain the_terrain, Light the_light) {
        return false;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> the_neighbors,
                                     final Light the_light) {
        return null;
    }

    @Override
    public void collide(final Vehicle theOther) {
        
        if (theOther.getDeathTime() < getDeathTime()) {
            myLife = false;
        }
    }

    @Override
    public int getDeathTime() {
//        final int deathTime;
        final String className = "./support_files/" + getClass().getName().toLowerCase();
        switch(className) {
            case "human": 
                return 50;
            case "car": 
                return 10;
            case "bicycle": 
                return 20;
            default:
                return 0;
        }
//        if (getClass().getName().toLowerCase().equals("human"))
//        {
//            deathTime = 50;
//        }
//        else if (getClass().getName().toLowerCase().equals("car"))
//        {
//            deathTime = 10;
//        }
//        else if (getClass().getName().toLowerCase().equals("bicycle"))
//        {
//            deathTime = 20;
//        }
//        else
//        {
//            deathTime = 0;
//        }
//        return deathTime;
    }

    @Override
    public String getImageFileName() {
        String fileName;
        if (isAlive()) {
            fileName = "./support_files/" + getClass().getName().toLowerCase() + ".gif";
        } else {
            fileName = "./support_files/" + getClass().getName().toLowerCase() + "_dead.gif";
        }
        return fileName;
    }

    @Override
    public Direction getDirection() {
        return myDir;
    }

    @Override
    public int getX() {
        return myX;
    }

    @Override
    public int getY() {
        return myY;
    }

    @Override
    public boolean isAlive() {
        return myLife;
    }

    @Override
    public void poke() {
        numPokes++;
        if (numPokes == getDeathTime()) {
            myLife = true;
            myDir = Direction.random();
            numPokes = 0;
        }

    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDirection(final Direction the_dir) {
        myDir = the_dir;
    }

    @Override
    public void setX(final int the_x) {
        myX = the_x;
    }

    @Override
    public void setY(final int the_y) {
        myY = the_y;
    }

}
