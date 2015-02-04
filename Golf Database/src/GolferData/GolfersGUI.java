package GolferData;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * GolfersGUI.java
 * A class representing the GUI used in a maintaining a golfers database.
 * <pre>
 *    Project: GolfersGUI Database
 *    Platform: jdk 1.6.0_14; NetBeans IDE 6.8; Mac OSx
 *    Course: CS 143
 *    Hours: 16 hours
 *    Created on May 12, 2013, 10:00:00 PM
 * </pre>
 *
 * @author	brent.young@go.shoreline.edu
 * @version 	%1% %3%
 * @see         java.awt.Toolkit
 * @see         java.util.ArrayList
 * @see         java.io.IOException
 * @see         AddEditFormJDialog
 * @see         Golfer
 */
public class GolfersGUI extends javax.swing.JFrame {
    
    private boolean changesMade = false;
    
    /**
     * ArrayList to hold golfers on team.
     * @see Golfer
     */
    protected static ArrayList<Golfer> myGolfers = new ArrayList<>();
    
    /**
     * File to be read to and written from, containing golfer information.
     * @see java.io.FileReader
     */
    private String fileName = "./src/resources/golferStats.txt";
    
    /**
     * Temporary Golfer for place holding purposes.
     * @see Golfer
     */
    public static Golfer tempNewGolfer = new Golfer();
    
    /**
     * Boolean value to mark whether or not the action of adding a new Golfer
     * to the list has been completed.
     * @see AddEditFormJDialog
     */
    public static boolean addNewGolferComplete;
    
    /**
     * File chooser object for file selection.
     * @see #filter
     */
    private JFileChooser chooser = new JFileChooser("./src/resources");
    
    /**
     * Sets the extension filter for a file chooser object.
     * @see #chooser
     */
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files only.", "txt");
        
    /**
     * Constructs the user interface for the GolfersGUI Database.
     * @see Golfer
     * @see AddEditFormJDialog
     */
    public GolfersGUI() {
        initComponents();
        this.getRootPane().setDefaultButton(addJButton);
        setLocationRelativeTo(null);
        
        orderRanksJMenuItem.setEnabled(false);
        editJMenuItem.setEnabled(false);
        editJButton.setEnabled(false);
        deleteJButton.setEnabled(false);
        deleteJMenuItem.setEnabled(false);
        saveJMenuItem.setEnabled(false);
        sortJMenu.setEnabled(false);
        searchJButton.setEnabled(false);
        searchJMenuItem.setEnabled(false);
        saveAsJMenuItem.setEnabled(false);
        setIcon("./src/resources/NoImage.jpeg");
        readFromFile(fileName, fileName);
        displayGolfers();
        showGolferData(golfersJList.getSelectedIndex());        
    }
    
    /**
     * Method: setIcon
     * Sets the Icon of the player to the given String path. Stretches or
     * shrinks the Icon to 190 x 325. The closer the source image is to this
     * size the less distortion there will be.
     * @param picFile absolute path of jpeg or jpg file
     * @see #showGolferData(int) 
     */
    private void setIcon(String picFile) {
        try {
            ImageIcon icon = new ImageIcon();
            if (picFile.equals("")) {
                icon = new ImageIcon("./src/resources/NoImage.jpeg");
            } else {
                icon = new ImageIcon(picFile);
            }
            icon.setImage(getScaledImage(icon.getImage(), 190, 325));
            playerPicJLabel.setIcon(icon);
        } catch (NullPointerException ex) {
            playerPicJLabel.setIcon(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Image Not Found.",
                    "Unable to find Image or Image is too large.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Scales the passed image to fit in the playerPicJLabel panel.
     * @param srcImg image to be scaled
     * @param w width to scale to
     * @param h heigth to scale to
     * @return Image of size [w,h]
     * @see #setIcon(java.lang.String) 
     */
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
    
    /**
     * Method: readFromFile
     * Reads golfers from a text file that is comma delimited and
     * creates an instance of the Golfer class with the data read.
     * Then the newly created golfer is added to the golfers database.
     * Uses an object from the ReadFile class to read record.
     * @parem file: String
     * @return void
     * pre-condition: a valid file name, GolferStats.txt is expected
     * for input with comma separated text fields (but no spaces, except in the
     * case of a non-existent email, in which case one space is allotted) for
     * golfer first name, last name, email, phone number, and rank.
     * pre-condition: null values represented by blank space for non-requried fields.
     * post-condition: a new Golfer is created with the read fields
     * and added to the ArrayList golfers
     * @see ReadFile
     * @see Golfer
     * @see #fileName
     */
     private void readFromFile(String fileName, String previousFile) {     
            try {
                ArrayList<Golfer> array = new ArrayList<>();
                ReadFile rf = new ReadFile(fileName);
                String input = rf.readRecord();

                String field = "";
                StringTokenizer st;

                //read five fields per line into a Golfer object
                while (input != null) {
                    st = new StringTokenizer(input, ",");
                    Golfer player = new Golfer();
                    while (st.hasMoreElements()) {
                        field = st.nextElement().toString();
                        player.setFirstName(field);
                        field = st.nextElement().toString();
                        player.setLastName((field));
                        field = Validation.isEmail(st.nextElement().toString(), false);
                        player.setEmail(field);
                        field = st.nextElement().toString();
                        player.setPhone(field);
                        field = st.nextElement().toString();
                        player.setLevel(Integer.parseInt(field));
                        field = st.nextElement().toString();
                        player.setIcon(field);
                    }
                    array.add(player);
                    input = rf.readRecord();
                }
                rf.close();
                myGolfers = new ArrayList<>();
                myGolfers.addAll(array);
                displayGolfers();
                showGolferData(golfersJList.getSelectedIndex());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid File", "File Error", JOptionPane.ERROR_MESSAGE);
                this.fileName = previousFile;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid File", "File Error", JOptionPane.ERROR_MESSAGE);
                this.fileName = previousFile;
            }
            
        //sets proper button and menu states
        if (myGolfers.isEmpty()) {
            editJButton.setEnabled(false);
            editJMenuItem.setEnabled(false);
            deleteJButton.setEnabled(false);
            deleteJMenuItem.setEnabled(false);
            sortJMenu.setEnabled(false);
        } else {
            enableJButtons(true);
            saveJMenuItem.setEnabled(true);
            sortJMenu.setEnabled(true);
            saveAsJMenuItem.setEnabled(true);
        }
        if (myGolfers.size() < 2) {
            orderRanksJMenuItem.setEnabled(false);
            swapRanksJMenuItem.setEnabled(false);
        } else {
            orderRanksJMenuItem.setEnabled(true);
            swapRanksJMenuItem.setEnabled(true);
        }
    }
    
     /**
     * Method: displayGolfers
     * Displays golfers in JList sorted by level = 0 using selection sort
     * algorithm or last name = 1 using the insertion sort algorithm.
     * pre-condition: Uses the ArrayList golfers.
     * post-condition: golfers ArrayList is sorted and displayed either by
     * level or last name.
     * @see #selectionSort
     * @see #insertionSort
     */     
    public void displayGolfers() {
        String[] golfersList = new String[myGolfers.size()];
        //name only
        if (rankJRadioButtonMenuItem.isSelected()) {
            selectionSort(myGolfers);
            for (int index = 0; index < myGolfers.size(); index++) {
                golfersList[index] = myGolfers.get(index).getLevel() + ", " +
                        myGolfers.get(index).getFirstName() + " " +
                        myGolfers.get(index).getLastName();
            }
        } else {
            insertionSort(myGolfers);
            for (int index = 0; index < myGolfers.size(); index++) {
                golfersList[index] = myGolfers.get(index).getLastName() + 
                        ", " + myGolfers.get(index).getFirstName();
            }
        }
        golfersJList.setListData(golfersList);
        golfersJList.setSelectedIndex(0);
    }
    
    /**
     * Method: insertionSort
     * Sorts ArrayList golfers in ascending order by Last Name. Uses the insertion
     * sort algorithm which inserts golfer at correct position and shuffles
     * everyone else below that position.
     * @parem ArrayList: golfers
     * pre-condition: ArrayList golfers filled-in with Golfer objects.
     * post-condition: golfers ArrayList is sorted by name.
     */
    public static void insertionSort(ArrayList<Golfer>  golfers) {
        int i, j;
        for (i = 0; i < golfers.size(); i++) {
            Golfer temp = golfers.get(i);
            j = i - 1;
            while (j >= 0 && golfers.get(j).getLastName().compareToIgnoreCase(temp.getLastName()) > 0) {
                golfers.set(j + 1, golfers.get(j));
                j--;
            }
            golfers.set(j + 1, temp);
        }
    }

    /**
     * Method: selectionSort
     * Sorts ArrayList golfers in descending order by rank. Calls
     * findMinimum to find golfer with maximum population in each pass
     * and swap to exchange golfers when necessary.
     * @parem ArrayList: golfers
     * pre-condition: ArrayList golfers filled-in with Golfer objects.
     * post-condition: golfers ArrayList is sorted by population.
     */
      public void selectionSort(ArrayList<Golfer> golfers) {
           for (int i = 0; i< golfers.size(); i++) {
               int max = findMinimum(golfers, i);
               swap(golfers, i, max);
           }
    }       

    /**
     * Method: findMinimum
     * Called by selectionSort to find the index of the member with the minimum
     * level from a given index to the beginning of the ArrayList
     * @parem ArrayList: golfers
     * @parem  int i: index from which to search for the min <= 0
     * @return int: position or index  where maximum is located
     * pre-condition: ArrayList golfers filled-in with golfers objects, int i >= 0.
     * post-condition: golfers ArrayList is sorted by level.
     */
    public int findMinimum(ArrayList<Golfer> golfers, int i) {
        
        int j, min = i;
        for (j = i + 1; j < golfers.size(); j++) {
            if (golfers.get(j).getLevel() < golfers.get(min).getLevel()) {
                min = j;
            }
        }
        return min;
    }

    /**
     * Method: swap
     * Called by selectionSort to find the index of the member with the maximum
     * level from a given index to the end of the ArrayList
     * @parem ArrayList: golfers
     * @parem  int i: index of element to be swapped >= 0
     * @parem  int j: index of element to be swapped >= 0
     * @return void
     * pre-condition: ArrayList golfers filled-in with Golfer objects, int i, j >= 0.
     * post-condition: golfers ArrayList with two golfers swapped.
     */
    public static void swap(ArrayList<Golfer> golfers, int i, int j) {
        Golfer temp = golfers.get(i);
        golfers.set(i, golfers.get(j));
        golfers.set(j, temp);
    }
    
    /**
     * Displays Golfer information in the JTetFields and displays an associated
     * player portrait.
     * @see #displayGolfers() 
     */
    private void showGolferData(int index) {
        if (!myGolfers.isEmpty()) {
            enableJButtons(true);
            firstNameJTextField.setText(myGolfers.get(index).getFirstName());
            lastNameJTextField.setText(myGolfers.get(index).getLastName());
            emailJTextField.setText((myGolfers.get(index).getEmail()));
            phoneJTextField.setText((myGolfers.get(index).getPhone()));
            levelJTextField.setText("" + (myGolfers.get(index).getLevel()));
            playerPicJTextField.setText(myGolfers.get(index).getIcon());
            setIcon(myGolfers.get(index).getIcon());
        } else {
            editJButton.setEnabled(false);
            editJMenuItem.setEnabled(false);
            deleteJButton.setEnabled(false);
            deleteJMenuItem.setEnabled(false);
            clearAll();
            setIcon("");
        }
    }
    
     /**
     * Method: writeToFile
     * Write golfers to a text file that is comma delimited.
     * @parem file: String
     * @return void
     * pre-condition: a valid file name, golferStats.txt is expected
     * post-condition: a new text file is created with the current golfers
     * in the database
     * @see WriteFile
     * @see Golfer
     */
     private void writeToFile() throws IOException {
        try {
            WriteFile wf = new WriteFile(fileName);
            for (int index = 0; index < myGolfers.size(); index++) {
                Golfer tempGolfer = myGolfers.get(index);
                String output = tempGolfer.getFirstName() + "," + tempGolfer.getLastName()
                        + "," + tempGolfer.getEmail()+ "," + tempGolfer.getPhone()
                        + "," + tempGolfer.getLevel();
                if (tempGolfer.getIcon().equals("")) {
                    output += ", ";
                } else {
                    output += "," + tempGolfer.getIcon();
                }
                wf.write(output);
            }
            wf.close();
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "File Error, Could not save.",
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
        
     }
     
     /**
      * Saves contents of current golfers database to an external file.
      * @param fileName file to be written to.
      * @see Golfer
      * @see #writeToFile(java.lang.String) 
      */
      private void saveGolfers() {
          try {
              writeToFile();
          } catch (IOException ex) {
              JOptionPane.showMessageDialog(null, "File Error, Could not save.",
                    "File Error", JOptionPane.ERROR_MESSAGE);
          }
      }
    
    /**
     * Deletes the selected Golfer from the golfers Database.
     * @see javax.swing.JOptionPane
     * @see #saveGolfers(java.lang.String) 
     * @see #displayGolfers() 
     * @see #myGolfers
     */
    private void deleteGolfer() {
       int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete Golfer", 
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
       if (result == JOptionPane.YES_OPTION) {
           changesMade = true;
           int index = golfersJList.getSelectedIndex();
           myGolfers.remove(index);
           displayGolfers();
           if (myGolfers.size() < 2) {
               sortJMenu.setEnabled(false);
               orderRanksJMenuItem.setEnabled(false);
               swapRanksJMenuItem.setEnabled(false);
               searchJMenuItem.setEnabled(false);
               searchJButton.setEnabled(false);
           }
       }
    }
    
    /**
     * Enables/Disables all buttons and menuItems that requite changing at
     * some point during use of the Form.
     * @param status boolean to turn buttons and menus on or off
     * @see #enableJTextFields(boolean) 
     */
    private void enableJButtons(boolean status) {
        addJButton.setEnabled(status);
        editJButton.setEnabled(status);
        deleteJButton.setEnabled(status);
        searchJButton.setEnabled(status);
        
        addJMenuItem.setEnabled(status);
        editJMenuItem.setEnabled(status);
        printJMenuItem.setEnabled(status); 
        searchJMenuItem.setEnabled(status); 
        deleteJMenuItem.setEnabled(status);
        
        lastNameJRadioButtonMenuItem.setEnabled(status);
        rankJRadioButtonMenuItem.setEnabled(status);
    }
    
    /**
     * Enables/Disables textFields.
     * @param status boolean to turn fields on or off
     * @see #enableJButtons(boolean) 
     */
    private void enableJTextFields(boolean status) {
        firstNameJTextField.setEditable(status);
        lastNameJTextField.setEditable(status);
        emailJTextField.setEditable(status);
        phoneJTextField.setEditable(status);
        levelJTextField.setEditable(status);
    }
     
    /**
     * Adds a new member to the GolfersGUI Array List assuming that all values
     * entered are valid.
     * @param newMember: Member to be added to database
     * @param isCompleted: Boolean y/n if the add member prompt was successful
     * Pre-condition: A valid member object and a boolean value y/n
     * Post-condition: tempNewMember now holds the member object to be added to
     * the database as well as a boolean variable indicating if the adding process
     * was completed.
     */
    
    private void addNewMember() {
        sort(myGolfers);
        quitJButton.setEnabled(true);
        Golfer p = new Golfer();
        AddEditFormJDialog pane = new AddEditFormJDialog(this, true, myGolfers, p);
        pane.setVisible(true);
        pane.isEditForm = false;
        if (addNewGolferComplete) {
            tempNewGolfer = pane.tempNewGolfer;
            changesMade = true;
            if (!golferExists(tempNewGolfer)) {
                myGolfers.add(tempNewGolfer);
            } else {
                int result = JOptionPane.showConfirmDialog(null, tempNewGolfer.getFirstName() 
                        + " " + tempNewGolfer.getLastName() + " already exists.\nWould you like to replace?",
                        "Add new member", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    replaceGolfer(1, p, tempNewGolfer);
                }
            }
            displayGolfers();
            addNewGolferComplete = false;
        }
        if (!myGolfers.isEmpty()) {
            enableJButtons(true);
            sortJMenu.setEnabled(true);
            orderRanksJMenuItem.setEnabled(true);
            swapRanksJMenuItem.setEnabled(true);
            searchJButton.setEnabled(true);
            searchJMenuItem.setEnabled(true);
            saveAsJMenuItem.setEnabled(true);
        }
    }
    
    /**
     * Determines if the golfer passed already exists in the golfers Database,
     * based on name.
     * @param golfer Golfer to be added to Database
     * @return boolean value representing whether or not the golfer already exists
     * @see Golfer
     * @see #replaceGolfer(GolferData.Golfer) 
     * @see #addNewMember() 
     * @see #addNewGolferComplete
     */
     private boolean golferExists(Golfer golfer) {
        boolean isSame = false;
        Iterator<Golfer> golfIt = myGolfers.iterator();
        Golfer g = new Golfer();
        while (golfIt.hasNext() && isSame == false) {
            g = golfIt.next();
            if (g.equals(golfer)) {
                isSame = true;
            }
        }
        return isSame;
    }
     
     /**
     * Replaces the golfer at the appropriate index with the newly created golfer.
     * @param newGolfer golfer you would like saved over the existing golfer
     * @see #addNewGolfer() 
     * @see #golferExists(GolferData.Golfer) 
     * @see #addNewGolferComplete
     */
    private void replaceGolfer(int caseValue, Golfer oldGolfer, Golfer newGolfer) {
        switch (caseValue) {
            case 1:
                Iterator<Golfer> golfIt = myGolfers.iterator();
                while (golfIt.hasNext()) {
                    Golfer myGolfGuy = golfIt.next();
                    if (myGolfGuy.equals(newGolfer)) {
                        myGolfers.set(myGolfers.indexOf(myGolfGuy), newGolfer);
                    }
                }
                break;
            case 2:
                Iterator<Golfer> golfIter = myGolfers.iterator();
                while (golfIter.hasNext()) {
                    Golfer myGolfGuy = golfIter.next();
                    if (myGolfGuy.equals(oldGolfer)) {
                        myGolfers.set(myGolfers.indexOf(myGolfGuy), newGolfer);
                    }
                }
                break;
        }
    }
     
     /**
     * Searches the golfers database to locate a specified Golfer name,
     * partial name, or rank, should it exist.
     * @see javax.swing.JOptionPane
     * @see java.util.Iterator
     * @see Golfer
     */
     private void searchGolfer() {
        boolean golferFound = false;
        try {
            String searchName = JOptionPane.showInputDialog(null,
                    "Enter either the first and/or last name or the Team Rank of a Golfer",
                    "Search Golfers", JOptionPane.QUESTION_MESSAGE);
            String[] searchArray = searchName.split(" ", 2);
            int i;
            Iterator<Golfer> golfIT = myGolfers.iterator();
            while (golfIT.hasNext()) {
                Golfer myGolfer = golfIT.next();
                if (Validation.isNumber(searchName)) {
                    i = Integer.parseInt(searchName);
                    if(i == myGolfer.getLevel()) {
                        showGolferData(myGolfers.indexOf(myGolfer));
                        rankJRadioButtonMenuItem.setSelected(true);
                        lastNameJRadioButtonMenuItem.setSelected(false);
                        displayGolfers();
                        golfersJList.setSelectedIndex(myGolfers.indexOf(myGolfer));
                        golferFound = true;
                    }
                } else {
                    if(myGolfer.getFirstName().equalsIgnoreCase(searchArray[0]) &&
                        myGolfer.getLastName().equalsIgnoreCase(searchArray[searchArray.length - 1])) {

                    showGolferData(myGolfers.indexOf(myGolfer));
                    golfersJList.setSelectedIndex(myGolfers.indexOf(myGolfer));
                    golferFound = true;

                    } else if (myGolfer.getFirstName().equalsIgnoreCase(searchArray[0]) ||
                            myGolfer.getLastName().equalsIgnoreCase(searchArray[searchArray.length - 1])) {

                        showGolferData(myGolfers.indexOf(myGolfer));
                        golfersJList.setSelectedIndex(myGolfers.indexOf(myGolfer));
                        golferFound = true;
                    } else if (myGolfer.getFirstName().toLowerCase().contains(searchName.toLowerCase()) ||
                            myGolfer.getLastName().toLowerCase().contains(searchName.toLowerCase())) {
                        showGolferData(myGolfers.indexOf(myGolfer));
                        golfersJList.setSelectedIndex(myGolfers.indexOf(myGolfer));
                        golferFound = true;
                    }
                }
                
            }
            if (!golferFound) {
                JOptionPane.showMessageDialog(null, searchName + " not found.", "Search Golfers", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException ex) {
            
        }
    }
     
     /**
      * Method: findGolfer
      * Locates the desired Golfer in the myGolfers ArrayList.
      * @param match Golfer to be found
      * @return Golfer if one matches desired golfer, null otherwise
      * @see #editForm() 
      */
     private Golfer findGolfer(Golfer match) {
         Iterator<Golfer> golfIt = myGolfers.iterator();
         Golfer g = new Golfer();
         while (golfIt.hasNext()) {
             g = golfIt.next();
             if (g.equals(match)) {
                 return g;
             }
         }
         return null;
     }
    
    /**
     * Calls a separate window and passes a boolean value to let the window know
     * a golfer already exists, filling the text fields with the golfers values.
     * @see AddEditFormJDialog
     * @see Golfer
     * @see #addNewGolferComplete
     * @see AddEditFormJDialog.#isEditForm
     */
    private void editForm() {
        Golfer g = myGolfers.get(golfersJList.getSelectedIndex());
        
        ArrayList<Golfer> sortedList = new ArrayList<>();
        sortedList.addAll(myGolfers);
        sort(sortedList);
        
        AddEditFormJDialog pane = new AddEditFormJDialog(this, true, sortedList, g);        
        pane.isEditForm = true;
        pane.editForm(g);
        pane.setVisible(true);
        
        pane.setLocationRelativeTo(null);
        if (addNewGolferComplete) {
            tempNewGolfer = pane.tempNewGolfer;
            changesMade = true;
            if (golferExists(tempNewGolfer) && !tempNewGolfer.equals(g)) {
                int result = JOptionPane.showConfirmDialog(null, tempNewGolfer.getFirstName() 
                        + " " + tempNewGolfer.getLastName() + " already exists.\nWould you like to replace?",
                        "Add new member", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                
                if (result == JOptionPane.YES_OPTION) {
                    replaceGolfer(2, myGolfers.get(myGolfers.indexOf(findGolfer(tempNewGolfer))), tempNewGolfer);
                }
            } else {
                replaceGolfer(2, myGolfers.get(myGolfers.indexOf(g)), tempNewGolfer);
            }
        }
        displayGolfers();
        addNewGolferComplete = false;
    }
    
    /**
     * Clears all text fields.
     * @see AddEditFormJDialog.#clearJMenuItem
     */
    private void clearAll() {
        firstNameJTextField.setText(null);
        lastNameJTextField.setText(null);
        emailJTextField.setText(null);
        phoneJTextField.setText(null);
        levelJTextField.setText(null);
        playerPicJTextField.setText(null);
    }
    
    /**
     * Creates a new Team of players and prompts the user for the file to which
     * they would like to save the new team.
     * @see #addNewMember() 
     * @see #saveAs() 
     */
    private void addTeam() {
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
        int result = fileExists();
        if (returnVal == JFileChooser.APPROVE_OPTION && result == JOptionPane.YES_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            if (!fileName.contains(".txt")) {
                fileName += ".txt";
            }
            myGolfers = new ArrayList<>();
        }
        saveGolfers();
        displayGolfers();
        showGolferData(golfersJList.getSelectedIndex());
        saveJMenuItem.setEnabled(true);
        if (myGolfers.size() < 2) {
            orderRanksJMenuItem.setEnabled(false);
            swapRanksJMenuItem.setEnabled(false);
            searchJMenuItem.setEnabled(false);
            searchJButton.setEnabled(false);
        }
    }
    
    /**
     * Saves the currently displayed team of golfers to a file of the user's choice,
     * if the file already exists prompts user for overwrite.
     * @see javax.swing.JFileChooser
     * @see #saveJMenuIteme
     * @see #addTeam()
     */
    private void saveAs() {
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
        int result = fileExists();
        if (returnVal == JFileChooser.APPROVE_OPTION && result == JOptionPane.YES_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            if (!fileName.contains(".txt")) {
                fileName += ".txt";
            }
        }
        saveGolfers();
        saveJMenuItem.setEnabled(true);
    }
    
    /**
     * Determines if the chosen file already exists in the directory.
     * @return int representing approval or denial of unchecked file save
     * @see #saveAs()
     * @see #addTeam()
     */
    private int fileExists() {
        File f = chooser.getSelectedFile();
        try {
            //checks for extension, creates new file with extension if extension not found
            if (!f.getName().contains(".txt")) {
                f = new File(chooser.getSelectedFile().getAbsolutePath() + ".txt");
            }

            //checks for existing files with same name as File f
            if(f.exists() && (chooser.getDialogType() == JFileChooser.SAVE_DIALOG)){
                int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
                switch(result){
                    case JOptionPane.YES_OPTION:
                        chooser.approveSelection();
                        return result;
                    case JOptionPane.NO_OPTION:
                        return result;
                    case JOptionPane.CLOSED_OPTION:
                        return result;
                    case JOptionPane.CANCEL_OPTION:
                        chooser.cancelSelection();
                        return result;
                }
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "File Error.",
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return JOptionPane.YES_OPTION;
    }
    
    /**
     * Sets the team ranks of the current team to start at 1 and increase by a
     * value of 1 incrementally. Gets rid of the gaps in ranks should the user
     * desire.
     * @see #sort(java.util.ArrayList) 
     * @see #swapRanks() 
     */
    private void setRanks() {
        int currentMemberRank = 0;
        int lastSetRank = 0;
        Iterator<Golfer> golfIt = myGolfers.iterator();
        Golfer g = new Golfer();
        while (golfIt.hasNext()) {
            g = golfIt.next();
            if (g.getLevel() > lastSetRank + 1) {
                g.setLevel(lastSetRank + 1);
                changesMade = true;
            }
            lastSetRank = g.getLevel();
        }
        displayGolfers();
    }
    
    /**
     * Sorts the ArrayList passed by team rank.
     * @param v Array List of players with value level or rank to be sorted by
     * @see #setRanks() 
     */
    public static void sort (ArrayList v) {
    if (!v.isEmpty())
      for (int gap = v.size()/2; gap > 0; gap /= 2)
	for (int i = gap; i < v.size(); ++ i)
	  for (int j = i-gap; j >= 0; j -= gap) {
	    Golfer a = (Golfer)v.get(j);
	    Golfer b = (Golfer)v.get(j+gap);
	    if (a.getLevel() > b.getLevel()) {
	      swap(myGolfers, i, j);
	      swap(myGolfers, i, j+gap);
	    } else
	      break;
	  }
    }
    
    /**
     * Switches the currently selected player's rank with the desired rank,
     * should it already exist on the same team, and displays the new team in
     * the JList.
     * @see #sort(java.util.ArrayList) 
     * @see #setRanks() 
     */
    private void swapRanks() {
        try {
            Golfer tempGolfer = myGolfers.get(golfersJList.getSelectedIndex());
            int myRank = myGolfers.get(golfersJList.getSelectedIndex()).getLevel();
            int result = -1;
            try {
                result = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Which rank would you like to switch " + 
                        myGolfers.get(golfersJList.getSelectedIndex()).getFirstName() + 
                        myGolfers.get(golfersJList.getSelectedIndex()).getLastName() + "to?", 0));
            } catch (NumberFormatException ex) {
                throw new Exception();
            }
            boolean found = false;
            Iterator<Golfer> golfIt = myGolfers.iterator();
            Golfer g = new Golfer();
            while (golfIt.hasNext()) {
                g = golfIt.next();
                if (g.getLevel() == result) {
                    g.setLevel(myRank);
                    tempGolfer.setLevel(result);
                    found = true;
                    changesMade = true;
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "Rank not Found",
                        "Rank not Found.", JOptionPane.ERROR_MESSAGE);
            }
            displayGolfers();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Rank",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid Rank",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleJPanel = new javax.swing.JPanel();
        iconJLabel = new javax.swing.JLabel();
        titleJLabel = new javax.swing.JLabel();
        displayJPanel = new javax.swing.JPanel();
        firstNameJLabel = new javax.swing.JLabel();
        firstNameJTextField = new javax.swing.JTextField();
        lastNameJLabel = new javax.swing.JLabel();
        lastNameJTextField = new javax.swing.JTextField();
        emailJLabel = new javax.swing.JLabel();
        emailJTextField = new javax.swing.JTextField();
        phoneJLabel = new javax.swing.JLabel();
        phoneJTextField = new javax.swing.JTextField();
        levelJLabel = new javax.swing.JLabel();
        levelJTextField = new javax.swing.JTextField();
        controlPanel = new javax.swing.JPanel();
        addJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        deleteJButton = new javax.swing.JButton();
        searchJButton = new javax.swing.JButton();
        quitJButton = new javax.swing.JButton();
        listJPanel = new javax.swing.JPanel();
        llistJScrollPane = new javax.swing.JScrollPane();
        golfersJList = new javax.swing.JList();
        iconJPanel = new javax.swing.JPanel();
        playerPicJLabel = new javax.swing.JLabel();
        playerPicJTextField = new javax.swing.JTextField();
        citiesJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        newJMenuItem = new javax.swing.JMenuItem();
        openJMenuItem = new javax.swing.JMenuItem();
        saveJMenuItem = new javax.swing.JMenuItem();
        saveAsJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        fileJSeparator = new javax.swing.JPopupMenu.Separator();
        exitJMenuItem = new javax.swing.JMenuItem();
        sortJMenu = new javax.swing.JMenu();
        lastNameJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        rankJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        actionJMenu = new javax.swing.JMenu();
        addJMenuItem = new javax.swing.JMenuItem();
        editJMenuItem = new javax.swing.JMenuItem();
        deleteJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        orderRanksJMenuItem = new javax.swing.JMenuItem();
        swapRanksJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Team Database");
        setResizable(false);

        iconJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GolferData/golficon.jpeg"))); // NOI18N

        titleJLabel.setFont(new java.awt.Font("Tahoma", 2, 32)); // NOI18N
        titleJLabel.setForeground(new java.awt.Color(51, 0, 0));
        titleJLabel.setText("Golf Database");

        firstNameJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        firstNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        firstNameJLabel.setText("First Name*:");

        firstNameJTextField.setEditable(false);
        firstNameJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        firstNameJTextField.setToolTipText("Press Enter to update");

        lastNameJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lastNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lastNameJLabel.setText("Last Name*:");

        lastNameJTextField.setEditable(false);
        lastNameJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lastNameJTextField.setToolTipText("Press Enter to update");

        emailJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emailJLabel.setText("Email Address:");

        emailJTextField.setEditable(false);
        emailJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        emailJTextField.setToolTipText("Press Enter to update");

        phoneJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        phoneJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        phoneJLabel.setText("Phone Number*:");

        phoneJTextField.setEditable(false);
        phoneJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        phoneJTextField.setToolTipText("Enter a valid US phone number and Press Enter to update");

        levelJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        levelJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        levelJLabel.setText("Level*:");

        levelJTextField.setEditable(false);
        levelJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        levelJTextField.setToolTipText("Enter a non-negative integer for team rank and Press Enter to update");

        org.jdesktop.layout.GroupLayout displayJPanelLayout = new org.jdesktop.layout.GroupLayout(displayJPanel);
        displayJPanel.setLayout(displayJPanelLayout);
        displayJPanelLayout.setHorizontalGroup(
            displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(displayJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(phoneJLabel)
                    .add(emailJLabel)
                    .add(lastNameJLabel)
                    .add(firstNameJLabel)
                    .add(levelJLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(firstNameJTextField)
                    .add(lastNameJTextField)
                    .add(emailJTextField)
                    .add(displayJPanelLayout.createSequentialGroup()
                        .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(levelJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(phoneJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 164, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        displayJPanelLayout.setVerticalGroup(
            displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(displayJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(firstNameJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(firstNameJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lastNameJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lastNameJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(emailJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(emailJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(phoneJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(levelJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(5, 5, 5))
        );

        controlPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setMinimumSize(new java.awt.Dimension(299, 45));
        controlPanel.setLayout(new java.awt.GridLayout(1, 5, 5, 2));

        addJButton.setBackground(new java.awt.Color(204, 255, 204));
        addJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addJButton.setMnemonic('A');
        addJButton.setText("Add");
        addJButton.setToolTipText("Add new Golfer");
        addJButton.setMinimumSize(new java.awt.Dimension(57, 45));
        addJButton.setPreferredSize(new java.awt.Dimension(57, 35));
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(addJButton);

        editJButton.setBackground(new java.awt.Color(204, 255, 204));
        editJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        editJButton.setMnemonic('E');
        editJButton.setText("Edit");
        editJButton.setToolTipText("Edit Golfer. Press Enter in any of the JTextFields to confirm changes.");
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(editJButton);

        deleteJButton.setBackground(new java.awt.Color(204, 255, 204));
        deleteJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        deleteJButton.setMnemonic('D');
        deleteJButton.setText("Delete");
        deleteJButton.setToolTipText("Delete Golfer");
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(deleteJButton);

        searchJButton.setBackground(new java.awt.Color(204, 255, 204));
        searchJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        searchJButton.setMnemonic('h');
        searchJButton.setText("Search");
        searchJButton.setToolTipText("Search for a Golfer by name.");
        searchJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(searchJButton);

        quitJButton.setBackground(new java.awt.Color(204, 255, 204));
        quitJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        quitJButton.setMnemonic('t');
        quitJButton.setText("Quit");
        quitJButton.setToolTipText("Exit application");
        quitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(quitJButton);

        golfersJList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        golfersJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                golfersJListMouseClicked(evt);
            }
        });
        golfersJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                golfersJListValueChanged(evt);
            }
        });
        llistJScrollPane.setViewportView(golfersJList);

        playerPicJLabel.setFocusable(false);
        playerPicJLabel.setMaximumSize(new java.awt.Dimension(200, 500));

        playerPicJTextField.setEditable(false);

        org.jdesktop.layout.GroupLayout iconJPanelLayout = new org.jdesktop.layout.GroupLayout(iconJPanel);
        iconJPanel.setLayout(iconJPanelLayout);
        iconJPanelLayout.setHorizontalGroup(
            iconJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, iconJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(iconJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(playerPicJTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .add(playerPicJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        iconJPanelLayout.setVerticalGroup(
            iconJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(iconJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(playerPicJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(playerPicJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout listJPanelLayout = new org.jdesktop.layout.GroupLayout(listJPanel);
        listJPanel.setLayout(listJPanelLayout);
        listJPanelLayout.setHorizontalGroup(
            listJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, listJPanelLayout.createSequentialGroup()
                .add(llistJScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 194, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE)
                .add(iconJPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        listJPanelLayout.setVerticalGroup(
            listJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, listJPanelLayout.createSequentialGroup()
                .add(listJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(iconJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(llistJScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout titleJPanelLayout = new org.jdesktop.layout.GroupLayout(titleJPanel);
        titleJPanel.setLayout(titleJPanelLayout);
        titleJPanelLayout.setHorizontalGroup(
            titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(titleJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, titleJPanelLayout.createSequentialGroup()
                        .add(iconJLabel)
                        .add(35, 35, 35)
                        .add(titleJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 223, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(28, 28, 28))
                    .add(controlPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                    .add(displayJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(listJPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0))
        );
        titleJPanelLayout.setVerticalGroup(
            titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(titleJPanelLayout.createSequentialGroup()
                .add(18, 18, 18)
                .add(titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(iconJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(titleJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(displayJPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(controlPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
            .add(titleJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(listJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(titleJPanel, java.awt.BorderLayout.PAGE_START);

        fileJMenu.setText("File");
        fileJMenu.setToolTipText("File Menu");

        newJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newJMenuItem.setMnemonic('N');
        newJMenuItem.setText("New");
        newJMenuItem.setToolTipText("Create New Team");
        newJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(newJMenuItem);

        openJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openJMenuItem.setMnemonic('O');
        openJMenuItem.setText("Open");
        openJMenuItem.setToolTipText("Open new Team File");
        openJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(openJMenuItem);

        saveJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveJMenuItem.setMnemonic('S');
        saveJMenuItem.setText("Save");
        saveJMenuItem.setToolTipText("Save to current file");
        saveJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(saveJMenuItem);

        saveAsJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAsJMenuItem.setMnemonic('v');
        saveAsJMenuItem.setText("Save As");
        saveAsJMenuItem.setToolTipText("Save to Another File");
        saveAsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(saveAsJMenuItem);

        printJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("Print entire form");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);
        fileJMenu.add(fileJSeparator);

        exitJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        exitJMenuItem.setMnemonic('x');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Exits the application.");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        citiesJMenuBar.add(fileJMenu);

        sortJMenu.setText("Sort");
        sortJMenu.setToolTipText("Sort members");

        lastNameJRadioButtonMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        lastNameJRadioButtonMenuItem.setMnemonic('l');
        lastNameJRadioButtonMenuItem.setSelected(true);
        lastNameJRadioButtonMenuItem.setText("Sort by Last Name");
        lastNameJRadioButtonMenuItem.setToolTipText("Sort by last name and display only name");
        lastNameJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(lastNameJRadioButtonMenuItem);

        rankJRadioButtonMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        rankJRadioButtonMenuItem.setMnemonic('r');
        rankJRadioButtonMenuItem.setText("Sort by Rank");
        rankJRadioButtonMenuItem.setToolTipText("Sort by team rank and display team rank and full name.");
        rankJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(rankJRadioButtonMenuItem);

        citiesJMenuBar.add(sortJMenu);

        actionJMenu.setText("Action");
        actionJMenu.setToolTipText("Interact with members");

        addJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        addJMenuItem.setMnemonic('A');
        addJMenuItem.setText("Add New Golfer");
        addJMenuItem.setToolTipText("Add New Member to current team");
        addJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(addJMenuItem);

        editJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        editJMenuItem.setMnemonic('i');
        editJMenuItem.setText("Edit Golfer");
        editJMenuItem.setToolTipText("Edit Golfer Information");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(editJMenuItem);

        deleteJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        deleteJMenuItem.setMnemonic('D');
        deleteJMenuItem.setText("Delete Golfer");
        deleteJMenuItem.setToolTipText("Delete Golfer");
        deleteJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(deleteJMenuItem);

        searchJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        searchJMenuItem.setMnemonic('h');
        searchJMenuItem.setText("Search Golfer");
        searchJMenuItem.setToolTipText("Search for a golfer by name");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(searchJMenuItem);

        orderRanksJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        orderRanksJMenuItem.setMnemonic('k');
        orderRanksJMenuItem.setText("Order Ranks");
        orderRanksJMenuItem.setToolTipText("Sets the order of the team ranks building from 1 and increasing by 1 each time, maintaining the true order of members.");
        orderRanksJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderRanksJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(orderRanksJMenuItem);

        swapRanksJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        swapRanksJMenuItem.setMnemonic('w');
        swapRanksJMenuItem.setText("Swap Ranks");
        swapRanksJMenuItem.setToolTipText("Swap the team rank of the selected member for another");
        swapRanksJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swapRanksJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(swapRanksJMenuItem);

        citiesJMenuBar.add(actionJMenu);

        helpJMenu.setText("Help");
        helpJMenu.setToolTipText("Display About Form");

        aboutJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        aboutJMenuItem.setMnemonic('b');
        aboutJMenuItem.setText("About");
        aboutJMenuItem.setToolTipText("Displays help form");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        citiesJMenuBar.add(helpJMenu);

        setJMenuBar(citiesJMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action Event fire when closing the application.
     * @param evt quit JButton pressed
     * @see System
     */
    private void quitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitJButtonActionPerformed
        if (!changesMade) {
            int closeValue = JOptionPane.showConfirmDialog(null, "Are you sure", "Quit Application", JOptionPane.YES_NO_OPTION);
            if (closeValue == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            int result = JOptionPane.showConfirmDialog(null, "Would you like to"
                    + " save before quitting?");
            if (result == JOptionPane.YES_OPTION) {
                saveGolfers();
                System.exit(0);
            } else if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }//GEN-LAST:event_quitJButtonActionPerformed

    /**
     * Action event fired when search function is clicked.
     * @param evt search JButton clicked
     * @see #searchJMenuItemActionPerformed(java.awt.event.ActionEvent) 
     */
    private void searchJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJButtonActionPerformed
        searchGolfer();
    }//GEN-LAST:event_searchJButtonActionPerformed

    /**
     * Action event fired when delete function is clicked.
     * @param evt delete JButton clicked
     * @see #deleteJMenuItemActionPerformed(java.awt.event.ActionEvent) 
     */
    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJButtonActionPerformed
        deleteGolfer();
    }//GEN-LAST:event_deleteJButtonActionPerformed

    /**
     * Action event fired when edit function is clicked.
     * @param evt edit JButton clicked
     * @see #editJMenuItemActionPerformed(java.awt.event.ActionEvent)  
     */
    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJButtonActionPerformed
        editForm();        
    }//GEN-LAST:event_editJButtonActionPerformed

    /**
     * Action event fired when add function is clicked.
     * @param evt add JButton clicked
     * @see #addJMenuItemActionPerformed(java.awt.event.ActionEvent)  
     */
    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed
        addNewMember();
    }//GEN-LAST:event_addJButtonActionPerformed

    /**
     * Action event fired when the value of the selected index in JList is
     * changed.
     * @param evt event value in JList changed
     * @see #golfersJListMouseClicked(java.awt.event.MouseEvent)  
     */
    private void golfersJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_golfersJListValueChanged
        int index = golfersJList.getSelectedIndex();
        if (index == -1) index = 0; // if nothing is selected, then select the first golfer.
        showGolferData(index);
    }//GEN-LAST:event_golfersJListValueChanged

    /**
     * Action event fired when the value of the selected index in JList is
     * changed.
     * @param evt event value in JList clicked
     * @see #golfersJListValueChanged(javax.swing.event.ListSelectionEvent)   
     */
    private void golfersJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_golfersJListMouseClicked
        int index = golfersJList.getSelectedIndex();
        if (index == -1) index = 0; // if nothing is selected, then select the first golfer.
        showGolferData(index);
    }//GEN-LAST:event_golfersJListMouseClicked

    /**
     * Action event fired when order ranks function is clicked.
     * @param evt order ranks JMenuItem clicked
     * @see #setRanks()   
     */
    private void orderRanksJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderRanksJMenuItemActionPerformed
        selectionSort(myGolfers);
        setRanks();
    }//GEN-LAST:event_orderRanksJMenuItemActionPerformed

    /**
     * Action event fired when print function is clicked.
     * @param evt print JMenuItem clicked
     * @see PrintUtilities
     * @see PrintUtilities.#print() 
     */
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        try {
            PrintUtilities.printComponent(this);
        } catch (Exception ex) {
            
        }
        
    }//GEN-LAST:event_printJMenuItemActionPerformed

    /**
     * Action event fired when quit function is clicked.
     * @param evt exit JMenuItem clicked
     * @see #quitJButtonActionPerformed(java.awt.event.ActionEvent)  
     */
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        quitJButtonActionPerformed(evt);
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    /**
     * Action event fired when lastNameJRadioButton is chosen.
     * @param evt lastName JRdioButtonMenuItem clicked
     * @see #displayGolfers()   
     */
    private void lastNameJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameJRadioButtonMenuItemActionPerformed
        rankJRadioButtonMenuItem.setSelected(false);
        displayGolfers();
    }//GEN-LAST:event_lastNameJRadioButtonMenuItemActionPerformed

    /**
     * Action event fired when rankJRadioButton is chosen.
     * @param evt rank JRdioButtonMenuItem clicked
     * @see #displayGolfers()   
     */
    private void rankJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankJRadioButtonMenuItemActionPerformed
        lastNameJRadioButtonMenuItem.setSelected(false);
        displayGolfers();
    }//GEN-LAST:event_rankJRadioButtonMenuItemActionPerformed

    /**
     * Action event fired when add function is clicked.
     * @param evt add JMenuItem clicked
     * @see #addJButtonActionPerformed(java.awt.event.ActionEvent)   
     */
    private void addJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJMenuItemActionPerformed
        addJButtonActionPerformed(evt);
    }//GEN-LAST:event_addJMenuItemActionPerformed

    /**
     * Action event fired when delete function is clicked.
     * @param evt delete JMenuItem clicked
     * @see #deleteJButtonActionPerformed(java.awt.event.ActionEvent)   
     */
    private void deleteJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJMenuItemActionPerformed
        deleteJButtonActionPerformed(evt);
    }//GEN-LAST:event_deleteJMenuItemActionPerformed

    /**
     * Action event fired when edit function is clicked.
     * @param evt edit JMenuItem clicked
     * @see #editJButtonActionPerformed(java.awt.event.ActionEvent)   
     */
    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
        editJButtonActionPerformed(evt);
    }//GEN-LAST:event_editJMenuItemActionPerformed

    /**
     * Action event fired when search function is clicked.
     * @param evt search JMenuItem clicked
     * @see #searchJButtonActionPerformed(java.awt.event.ActionEvent)   
     */
    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
        searchGolfer();
    }//GEN-LAST:event_searchJMenuItemActionPerformed

    /**
     * Action event fired when about function is clicked.
     * @param evt about JMenuItem clicked
     * @see UtilPane
     * @see UtilPane.#printFileToPane(java.lang.String) 
     */
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        UtilPane pane = new UtilPane();
        pane.setLocationRelativeTo(null);
        try {
            pane.printFileToPane("./src/About/About.txt");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File was not Found.",
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
        pane.setVisible(true);
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    /**
     * Action event fired when new function is clicked.
     * @param evt new JMenuItem clicked
     * @see #addTeam()   
     */
    private void newJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newJMenuItemActionPerformed
        if (!changesMade) {
            addTeam();
        } else {
            int result = JOptionPane.showConfirmDialog(null, "Would you like to"
                    + " save before continuing?");
            if (result == JOptionPane.YES_OPTION) {
                saveGolfers();
            }
            if (result != JOptionPane.CANCEL_OPTION) {
                addTeam();
            }
            //else if (result == JOptionPane.NO_OPTION) {
              //  addTeam();
        }
        changesMade = false;
    }//GEN-LAST:event_newJMenuItemActionPerformed

    /**
     * Action event fired when open function is clicked.
     * @param evt open JMenuItem clicked
     * @see #readFromFile(java.lang.String, java.lang.String)   
     */
    private void openJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openJMenuItemActionPerformed
       int returnVal;
        if (changesMade) {
            int result = JOptionPane.showConfirmDialog(null, "Would you like to"
                    + " save before continuing?");
            switch (result) {
                case JOptionPane.YES_OPTION:
                    saveGolfers();
                    chooser.setFileFilter(filter);
                    returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        //myGolfers = new ArrayList<>();
                        String prevFile = fileName;
                        fileName = chooser.getSelectedFile().getAbsolutePath(); 
                        readFromFile(fileName, prevFile);
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    chooser.setFileFilter(filter);
                    returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        //myGolfers = new ArrayList<>();
                        String prevFile = fileName;
                        fileName = chooser.getSelectedFile().getAbsolutePath(); 
                        readFromFile(fileName, prevFile);
                    }
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
                case JOptionPane.CLOSED_OPTION:
                    break;
            }
        } else {
            chooser.setFileFilter(filter);
            returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //myGolfers = new ArrayList<>();
                String prevFile = fileName;
                fileName = chooser.getSelectedFile().getAbsolutePath(); 
                readFromFile(fileName, prevFile);
            }
            
            
            
//            int chose = JOptionPane.showConfirmDialog(null, "Are you sure you would like to continue?",
//                    "Open File", JOptionPane.YES_NO_OPTION);
//            switch(chose) {
//                case JOptionPane.YES_OPTION:
//                    chooser.setFileFilter(filter);
//                    returnVal = chooser.showOpenDialog(null);
//                    if (returnVal == JFileChooser.APPROVE_OPTION) {
//                        myGolfers = new ArrayList<>();
//                        String prevFile = fileName;
//                        fileName = chooser.getSelectedFile().getAbsolutePath(); 
//                        readFromFile(fileName, prevFile);
//                    }
//                    break;
//                case JOptionPane.NO_OPTION:
//                    break;
//            }
        }
        if (myGolfers.size() < 2) {
           sortJMenu.setEnabled(false);
           orderRanksJMenuItem.setEnabled(false);
           swapRanksJMenuItem.setEnabled(false);
           searchJMenuItem.setEnabled(false);
           searchJButton.setEnabled(false);
        }
        changesMade = false;
    }//GEN-LAST:event_openJMenuItemActionPerformed

    /**
     * Action event fired when save function is clicked.
     * @param evt save JMenuItem clicked
     * @see #saveGolfers()   
     */
    private void saveJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJMenuItemActionPerformed
        saveGolfers();
        changesMade = false;
    }//GEN-LAST:event_saveJMenuItemActionPerformed

    /**
     * Action event fired when saveAs function is clicked.
     * @param evt saveAs JMenuItem clicked
     * @see #saveAs()   
     */
    private void saveAsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsJMenuItemActionPerformed
        saveAs();
        changesMade = false;
    }//GEN-LAST:event_saveAsJMenuItemActionPerformed

    /**
     * Action event fired when swapRanks function is clicked.
     * @param evt swapRanks JMenuItem clicked
     * @see #swapRanks()   
     */
    private void swapRanksJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swapRanksJMenuItemActionPerformed
        swapRanks();
    }//GEN-LAST:event_swapRanksJMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GolfersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GolfersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GolfersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GolfersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GolfersGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JMenu actionJMenu;
    private javax.swing.JButton addJButton;
    private javax.swing.JMenuItem addJMenuItem;
    private javax.swing.JMenuBar citiesJMenuBar;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JMenuItem deleteJMenuItem;
    private javax.swing.JPanel displayJPanel;
    private javax.swing.JButton editJButton;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JLabel emailJLabel;
    private javax.swing.JTextField emailJTextField;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JPopupMenu.Separator fileJSeparator;
    private javax.swing.JLabel firstNameJLabel;
    private javax.swing.JTextField firstNameJTextField;
    private javax.swing.JList golfersJList;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JLabel iconJLabel;
    private javax.swing.JPanel iconJPanel;
    private javax.swing.JLabel lastNameJLabel;
    private javax.swing.JRadioButtonMenuItem lastNameJRadioButtonMenuItem;
    private javax.swing.JTextField lastNameJTextField;
    private javax.swing.JLabel levelJLabel;
    private javax.swing.JTextField levelJTextField;
    private javax.swing.JPanel listJPanel;
    private javax.swing.JScrollPane llistJScrollPane;
    private javax.swing.JMenuItem newJMenuItem;
    private javax.swing.JMenuItem openJMenuItem;
    private javax.swing.JMenuItem orderRanksJMenuItem;
    private javax.swing.JLabel phoneJLabel;
    private javax.swing.JTextField phoneJTextField;
    private javax.swing.JLabel playerPicJLabel;
    private javax.swing.JTextField playerPicJTextField;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JButton quitJButton;
    private javax.swing.JRadioButtonMenuItem rankJRadioButtonMenuItem;
    private javax.swing.JMenuItem saveAsJMenuItem;
    private javax.swing.JMenuItem saveJMenuItem;
    private javax.swing.JButton searchJButton;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.JMenu sortJMenu;
    private javax.swing.JMenuItem swapRanksJMenuItem;
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel titleJPanel;
    // End of variables declaration//GEN-END:variables
}
