/*
 * TCSS 305C - Autumn 2013
 * Tetris B - youngbw-TetrisB
 */
package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * This class represents a menu bar containing a file, options, help, and
 * checkbox option menu.
 * @author BrentYoung
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MyMenuBar extends JMenuBar implements PropertyChangeListener {    

    /**
     * File name to draw text from for the help pane.
     */
    private static final String HELP_FILENAME = "./resources/about.txt";
    
    /**
     * String for two player representation.
     */
    private static final String MULTI = "two player";
    
    /**
     * String for quit game representation.
     */
    private static final String QUIT_STRING = "Quit";
    
    /**
     * String for New Game representation.
     */
    private static final String NEW_STRING = "New Game";
    
    /**
     * The help panel, separate due to needing to show and exit arbitrarily.
     */
    private MyHelpPanel myAboutPanel;
    
    /**
     * Pause menu item, used to pause and un-pause the game as the user chooses
     * and when the help menu it visible.
     */
    private JCheckBoxMenuItem myPauseItem;
    
    /**
     * Menu item to start a new game, reference for enabling/disabling.
     */
    private JMenuItem myNewGameItem;
    
    /**
     * Menu item to end the game, reference for setting enabled/disabled.
     * @see #myPauseItem
     */
    private JMenuItem myEndGameItem;
    
    /**
     * Quit menu item reference.
     */
    private JMenuItem myQuitMenuItem;
    
    /**
     * View option item reference.
     */
    private JCheckBoxMenuItem myViewOption;
    
    /**
     * Number of players currently playing.
     */
    private int myNumPlayers;
    
    /**
     * Number of games ended thus far.
     */
    private int myNumGamesEnded;
    
    /**
     * Constructor for this menu bar, initializes and adds all components
     * to the menu bar.
     * @param theFrame reference to the main frame
     * @param theBoardPanel reference to the panel containing the board
     * @param theSidePanel reference to the side panel for stats
     * @param theBackgroundPanel panel to have side panels added to it
     * @param theLayoutPanel panel to have main panels added to it
     * @param theSecondPanel panel to be added to the layout panel
     * @param theSecondSidePanel side panel to be added to the background panel
     */
    public MyMenuBar(final JFrame theFrame, final MyBoardPanel theBoardPanel,
                     final MySidePanel theSidePanel, final JPanel theLayoutPanel,
                     final MyBackgroundPanel theBackgroundPanel,
                     final MyBoardPanel theSecondPanel, final MySidePanel theSecondSidePanel) {
        myNumPlayers = 1;
        myNumGamesEnded = 0;
        myAboutPanel = new MyHelpPanel(HELP_FILENAME);
        myPauseItem = new JCheckBoxMenuItem("Pause");
        myNewGameItem = new JMenuItem(NEW_STRING);
        myEndGameItem = new JMenuItem("End Game");
        myQuitMenuItem = new JMenuItem(QUIT_STRING);
        initialize(theFrame, theBoardPanel, theBackgroundPanel, theSidePanel,
                   theLayoutPanel, theSecondPanel, theSecondSidePanel);
    }
    
    /**
     * Initializes all default states of instantiated objects.
     * @param theFrame frame reference used for file menu setup
     * @param theBoardPanel board panel reference used for file menu setup
     * @param theSidePanel side panel reference used for view option setup
     * @param theBackgroundPanel panel to have side panels added to it
     * @param theLayoutPanel panel to have main panels added to it
     * @param theSecondPanel panel to be added to the layout panel
     * @param theSecondSidePanel side panel to be added to the background panel
     * @see #MyMenuBar(JFrame, MyBoardPanel, JPanel)
     */
    private void initialize(final JFrame theFrame, final MyBoardPanel theBoardPanel,
                            final MyBackgroundPanel theBackgroundPanel,
                            final MySidePanel theSidePanel, final JPanel theLayoutPanel,
                            final MyBoardPanel theSecondPanel,
                            final MySidePanel theSecondSidePanel) {
        this.addPropertyChangeListener(this);
        final JMenu fileMenu = setupFileMenu(theFrame, theBoardPanel, theSecondPanel);
        myViewOption = setupViewOption(theFrame, theSidePanel, theBackgroundPanel);
        myNewGameItem.setEnabled(false);
        myViewOption.doClick();
        final JMenu optionsMenu = setupOptionsMenu(theFrame, theLayoutPanel,
                                                   theBackgroundPanel,
                                                   theSecondPanel,
                                                   theSecondSidePanel);
        add(fileMenu);
        add(optionsMenu);
        add(setupHelpMenu(theBoardPanel, theSecondPanel));
        add(myViewOption);
    }

    /**
     * Creates and returns the file menu with all action listeners necessary.
     * @param theFrame frame reference for the GUI
     * @param theBoardPanel reference to the panel containing the board
     * @param theSecondPanel reference to the second players panel
     * @return JMenu menu representing the file menu
     * @see #setupViewOption(JFrame, JPanel)
     */
    private JMenu setupFileMenu(final JFrame theFrame,
                                      final MyBoardPanel theBoardPanel,
                                      final MyBoardPanel theSecondPanel) {
        final JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);

        myNewGameItem.setMnemonic(KeyEvent.VK_N);
        myNewGameItem.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myPauseItem.isSelected()) {
                    myPauseItem.setSelected(false);
                    theBoardPanel.continueGame();
                    theSecondPanel.continueGame();
                }
                theBoardPanel.startGame();
                theSecondPanel.startGame();
            }
        });
        
        myPauseItem.setMnemonic(KeyEvent.VK_P);
        myPauseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                                                          ActionEvent.CTRL_MASK));
        myPauseItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myPauseItem.isSelected()) {
                    theBoardPanel.pauseGame();
                    theSecondPanel.pauseGame();
                } else {
                    theBoardPanel.continueGame();
                    theSecondPanel.continueGame();
                }
                theFrame.repaint();
            }
        });
        
        myEndGameItem.setMnemonic(KeyEvent.VK_E);
        myEndGameItem.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                theBoardPanel.endGame(); 
                theSecondPanel.endGame();
                theFrame.repaint();
            }
        });
        
        myQuitMenuItem.setMnemonic(KeyEvent.VK_Q);
        myQuitMenuItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                theBoardPanel.quitGame();
                theSecondPanel.quitGame();
                theFrame.dispose();
                System.exit(0);
            }
        });
        menu.add(myNewGameItem);
        menu.add(myPauseItem);
        menu.add(myEndGameItem);
        menu.addSeparator();
        menu.add(myQuitMenuItem);
        return menu;
    }

    /**
     * Creates and returns the help menu with all options and action listeners.
     * Pauses the game.
     * @param theBoardPanel panel reference for pausing game when help menu is up
     * @param theSecondBoardPanel second players panel
     * @return JMenu menu representing the help menu option
     * @see #setupFileMenu(JFrame, MyBoardPanel)
     */
    private JMenu setupHelpMenu(final MyBoardPanel theBoardPanel,
                                final MyBoardPanel theSecondBoardPanel) {
        final JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        
        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic(KeyEvent.VK_A);
        aboutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myAboutPanel.setVis();
                if (!myPauseItem.isSelected()) {
                    myPauseItem.doClick();
                }
                theBoardPanel.pauseGame();
                theSecondBoardPanel.pauseGame();
            }
        });
        help.add(aboutItem);
        return help;
    }
    
    /**
     * Sets up the two player option along with any options that may want to be
     * added in the future.
     * @param theFrame frame reference
     * @param theLayoutPanel layout panel reference, to have panels added
     * @param theBackgroundPanel background panel, for side panel additions
     * @param theAddedPanel panel to add to layout panel
     * @param theSecondSidePanel side panel to add to background panel
     * @return JMenu options menu fully stocked
     */
    private JMenu setupOptionsMenu(final JFrame theFrame,
                                   final JPanel theLayoutPanel,
                                   final MyBackgroundPanel theBackgroundPanel,
                                   final MyBoardPanel theAddedPanel,
                                   final MySidePanel theSecondSidePanel) {
        final JMenu options = new JMenu("Options");
        options.setMnemonic(KeyEvent.VK_O);
        
        final JCheckBoxMenuItem twoPlayer = new JCheckBoxMenuItem("Two Player Mode");
        twoPlayer.setMnemonic(KeyEvent.VK_T);
        twoPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (twoPlayer.isSelected()) {
                    myNumPlayers = 2;
                } else {
                    myNumPlayers = 1;
                }
                firePropertyChange(MULTI, !twoPlayer.isSelected(), twoPlayer.isSelected());
                firePropertyChange(NEW_STRING, true, true);
                if (twoPlayer.isSelected()) {
                    theLayoutPanel.add(theAddedPanel);
                    theBackgroundPanel.add(theSecondSidePanel, BorderLayout.EAST);
                } else {
                    theLayoutPanel.remove(theAddedPanel);
                    theBackgroundPanel.remove(theSecondSidePanel);
                }
                setLocale(theFrame);
            }
        });
        options.add(twoPlayer);
        return options;
    }
    
    /**
     * Resets the location of the frame.
     * @param theFrame frame to be reset
     */
    private void setLocale(final JFrame theFrame) {
        theFrame.pack();
        theFrame.setLocation(TetrisGUI.SCREEN_WIDTH / 2 - theFrame.getWidth() / 2, 
                             TetrisGUI.SCREEN_HEIGHT / 2 - theFrame.getHeight() / 2);
        theFrame.repaint();
    }

    /**
     * Creates and returns a check box menu item to hide the side panel.
     * @param theFrame frame reference to the GUI
     * @param thePanel board panel reference
     * @param theBackgroundPanel background panel to have componenets added to it
     * @return JCheckBoxMenuItem check box to show/hide the side panel
     * @see #setupHelpMenu()
     */
    private JCheckBoxMenuItem setupViewOption(final JFrame theFrame,
                                              final MySidePanel thePanel,
                                              final MyBackgroundPanel theBackgroundPanel) {
        final JCheckBoxMenuItem view = new JCheckBoxMenuItem("View side panel");
        view.setMnemonic(KeyEvent.VK_V);

        view.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (view.isSelected()) {
                    theBackgroundPanel.add(thePanel, BorderLayout.WEST);
                } else {
                    theBackgroundPanel.remove(thePanel);
                }
                theFrame.pack();
                theFrame.repaint();
            }
        });
        return view;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("EndGame".equals(theEvent.getPropertyName())) {
            myNumGamesEnded++;
            if (myNumPlayers == myNumGamesEnded) {
                myNewGameItem.setEnabled(true);
                myEndGameItem.setEnabled(false);
                myPauseItem.setEnabled(false);
                myNumGamesEnded = 0;
            }
            
        } else if (NEW_STRING.equals(theEvent.getPropertyName())) {
            myNewGameItem.setEnabled(false);
            myEndGameItem.setEnabled(true);
            myPauseItem.setEnabled(true);
        } else if (MULTI.equalsIgnoreCase(theEvent.getPropertyName())) {
            if (!myViewOption.isSelected()) {
                myViewOption.doClick();
            }
            if (myPauseItem.isSelected()) {
                myPauseItem.doClick();
            }
            myViewOption.setEnabled((boolean) theEvent.getOldValue());
        }
    }
}
