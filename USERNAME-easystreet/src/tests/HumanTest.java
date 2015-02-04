/*
 * TCSS 305 - Easy Street
 */

package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.Human;
import model.Light;
import model.Terrain;

import org.junit.Test;

/**
 * Unit tests for class Human.
 * 
 * @author Alan Fowler (acfowler@uw.edu)
 * @version Autumn 2013
 */
public class HumanTest
{

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    /**
     * Test method for {@link Human#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass()
    {
        for (Terrain test_terrain : Terrain.values())
        {
            if (test_terrain == Terrain.WALL)
            {
                continue; // Humans do not start on Walla
            }
            final Human human = new Human(0, 0, Direction.CENTER, test_terrain);
            for (Terrain t : Terrain.values())
            {
                for (Light l : Light.values())
                {
                    if ((t == test_terrain)
                        || (t == Terrain.LIGHT && test_terrain == Terrain.STREET)
                        || (t == Terrain.STREET && test_terrain == Terrain.LIGHT))
                    {
                        // humans can pass the terrain they start on under any
                        // light
                        // conditions, and can also pass lights if they start on
                        // streets and vice-versa

                        assertTrue("Human started on " + test_terrain
                                           + " should be able to pass " + t + ", with light "
                                           + l,
                                   human.canPass(t, l));
                    }
                    else
                    {
                        // humans can't leave their terrain

                        assertFalse("Human started on " + test_terrain
                                            + " should NOT be able to pass " + t
                                            + ", with light " + l, human.canPass(t, l));
                    }
                }
            }
        }
    }

    /**
     * Test method for {@link Human#chooseDirection(java.util.Map, Light)}.
     */
    @Test
    public void testChooseDirection()
    {
        // humans need to stay on their own terrain, but otherwise can
        // do anything

        // Humans can not choose Center. Choosing Center would result in no
        // movement.

        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.WALL);
        neighbors.put(Direction.NORTH, Terrain.WALL);

        for (Terrain t : Terrain.values())
        {
            if (t != Terrain.WALL)
            { // Humans don't start on Walls.
                neighbors.put(Direction.CENTER, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                final Human human = new Human(0, 0, Direction.CENTER, t);
                for (Light l : Light.values())
                {
                    int tries = 0;
                    boolean seen_south = false;
                    boolean seen_east = false;
                    while (tries < TRIES_FOR_RANDOMNESS && (!seen_south || !seen_east))
                    {
                        tries = tries + 1;
                        final Direction dir = human.chooseDirection(neighbors, l);
                        assertTrue("invalid dir chosen, should be east or south, was " + dir,
                                   dir == Direction.EAST || dir == Direction.SOUTH);
                        seen_south = seen_south || dir == Direction.SOUTH;
                        seen_east = seen_east || dir == Direction.EAST;
                    }
                    assertTrue("human randomness", seen_south && seen_east);
                }
            }
        }
    }

}
