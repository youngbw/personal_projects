/*
 * TCSS 305 - Easy Street
 */

package view;

/**
 * Runs the Easy Street program.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman
 * @author Alan Fowler (acfowler@u.washington.edu)
 * @version Autumn 2013
 */

public final class EasyStreetMain
{
    /**
     * Private constructor to prevent construction of instances.
     */
    private EasyStreetMain()
    {
        // do nothing
    }

    /**
     * Constructs the main GUI window frame.
     * 
     * @param the_args Command line arguments (ignored).
     */
    public static void main(final String... the_args)
    {
        // initialize GUI frame
        final EasyStreetGUI gui = new EasyStreetGUI();
        gui.setVisible(true);
    }
}

// end of class EasyStreetMain
