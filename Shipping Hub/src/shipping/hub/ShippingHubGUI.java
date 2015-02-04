package shipping.hub;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
*  Class:	<b>ShippingHubGUI</b>
*  File:	ShippingHubGUI.java
* <pre>
*  Description:	This application stores parcel information from receivced
* parcels that are scanned into the applicationand allows those parcels to 
* be printed, searched, edited, or removed.
*  @author	<i>Brent Young<i/>
*  Environment:	Mac OSx, jdk1.7_17, NetBeans 7.3
*  Date:	4/17/2013
*  @version	%1% %0%
*  @see       	javax.swing.JFrame
*  @see         java.awt.Toolkit
*  @see         java.util.Date
*  @see         java.util.ArrayList
*  @see         java.io.BufferedReader
*  @see         java.io.FileReader 
*  @see         javax.swing.DefaultListModel
*  @see         Parcel
*  @see         UtilPane
*  @see         Search
* </pre>
*  History Log:	Created on April 1, 2013, 1:12:19 AM
*/

public class ShippingHubGUI extends javax.swing.JFrame {
    
    /**
     * List model used to store temporary information for display in the JList.
     * @see DefaultListModel
     * @see javax.swing.JList
     * @see javax.swing.JTextField
     */
    DefaultListModel listmodel = new DefaultListModel();
    
    /**
     * A reference parcel used as a placeholder for scrolling through parcels.
     * @see java.util.ArrayList
     * @see Parcel
     * @see #findPreviousParcel(shipping.hub.Parcel) 
     * @see #findNextParcel(int) 
     */
    private Parcel placeHolderParcel;
    
    /**
     * A reference parcel used for addition and subtraction of parcels into
     * the parcelArrayList.
     * @see java.util.ArrayList
     * @see Parcel
     * @see #makeParcel() 
     */
    private Parcel myParcel;
    
    /**
     * ArrayList of sent parcels in order of parcel ID.
     * @see java.util.ArrayList
     * @see Parcel
     * @see #makeParcel()
     */
    private ArrayList<Parcel> parcelArrayList = new ArrayList<>();
    
    /**
     * Array List of text fields for ease of access.
     * @see java.util.ArrayList
     * @see javax.swing.JTextField
     * @see #setFields(boolean) 
     * @see #addFieldsArrayList() 
     */
    private ArrayList<JTextField> fields = new ArrayList<>();
    
    /**
     * Reference variable for a text field used for placement into ArrayList of
     * fields.
     * @see javax.swing.JTextField
     * @see java.util.Iterator
     * @see java.util.ArrayList
     */
    private JTextField myTextField;
    
    /**
     * Text file of states to be read into combo boxes.
     * @see javax.swing.JComboBox
     * @see java.io.FileReader
     * @see java.io.BufferedReader
     * @see java.util.Scanner
     */
    private String filename = "src/resources/states.txt";
    
//    //object input automatically for j option pane
//    private Object output;
    
    /**
     * Creates new form ShippingHubGUI, the user interface shipping hub.
     * @see Parcel
     * @see UtilPane
     * @see Search
     * @see javax.swing.JFrame
     * @see javax.swing.JTextField
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     */
    public ShippingHubGUI() {
        //this.setLocationRelativeTo(null);
        this.setLocation(500, 250);
        initComponents();
        initialize();
    }
    
    /**
     * Sets the initial state of the user Interface, activating the appropriate
     * buttons and menu items, and text fields.
     * @see javax.swing.JTextField
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see java.util.ArrayList
     */
    private void initialize() {
        addFieldsArrayList();
        setButtons(false);
        setFields(false);
        setInitialMenuItems();
        this.getRootPane().setDefaultButton(scanNewJButton);
        scanNewJButton.setEnabled(true);
        arrivedAtJTextField.setEnabled(false);
        parcelIDJTextField.setEnabled(false);
        readStates(filename);
    }
    
    /**
     * Sets the arrival time (time parcel is scanned into the system)
     * to the current time.
     * @return current time at time of method call
     * @see java.text.SimpleDateFormat
     */
    private String setArrivalTime() {
        SimpleDateFormat simp = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
        Calendar cal = new GregorianCalendar();
        return simp.format(cal.getTime());
    }
    
    /**
     * Determines whether or not a proper zip code has been entered.
     * @return true if zip contains only 5 digits and does not begin with 0
     * @see #validateFields
     * @see #errorWindow
     * @see javax.swing.JFormattedTextField
     */
    private boolean validateZip() {
        String index = zipJTextField.getText();
        for (int i = 0; i < index.length(); i++) {
            if (index.charAt(i) == ' ' || (i == 0 && index.charAt(i) == '0')) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Displays an option pane with an error message.
     * @see javax.swing.JOptionPane
     * @see java.lang.String
     * @see #validateFields
     * @see #validateZip
     */
    private void errorWindow() {
//        UtilPane userError = new UtilPane();
//        userError.setVisible(true);
//        userError.printTextToPane("All fields must be filled in properly.");
//        JOptionPane.showMessageDialog( null, output,
//         "Brent Young Shipping Service",
//         JOptionPane.INFORMATION_MESSAGE );
        JOptionPane.showMessageDialog(null, "All fields must be filled in properly."
                + "\n\n(See the help menu for acceptable input.)");
    }
    
    /**
     * Makes sure that a valid entry has been made into each text field.
     * @return whether or not all fields have acceptable input
     * @see UtilPane
     * @see javax.swing.JOptionPane
     * @see #validateZip
     */
    private boolean validateFields() {
        Iterator<JTextField> textIt = fields.iterator();
        while (textIt.hasNext()) {
            myTextField = textIt.next();
            if (myTextField.getText().equals("")) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Sets the initial state of the menu items at startup of the user
     * interface.
     * @see javax.swing.JMenuItem
     */
    private void setInitialMenuItems() {
        addJMenuItem.setEnabled(false);
        editJMenuItem.setEnabled(false);
        updateJMenuItem.setEnabled(false);
        removeJMenuItem.setEnabled(false);
        backJMenuItem.setEnabled(false);
        nextJMenuItem.setEnabled(false);
    }
    
    /**
     * Enables/Disables all Text Fields for mass changes invoked by user action.
     * @param vis true to make fields editable.
     */
    private void setFields(boolean vis) {
        Iterator<JTextField> myFields = fields.iterator();
        while (myFields.hasNext()) {
            myTextField = myFields.next();
            myTextField.setEnabled(vis);
        }
        stateSelectJComboBox.setEnabled(vis);
    }
    
    /**
     * Enables/Disables all JButtons for mass changes invoked by user actions.
     * @param vis true to enable all buttons.
     * @see javax.swing.JButton
     */
    private void setButtons(boolean vis) {
        scanNewJButton.setEnabled(vis);
        addJButton.setEnabled(vis);
        removeJButton.setEnabled(vis);
        editJButton.setEnabled(vis);
        updateJButton.setEnabled(vis);
        backJButton.setEnabled(vis);
        nextJButton.setEnabled(vis);
    }
    
    /**
     * Sets the default list model to contain the elements (parcel ID's),
     * in numerical order, belonging to the state selected.
     * @see javax.swing.JComboBox
     * @see DefaultListModel
     * @see ArrayList
     * @see java.util.Iterator
     */
    private void setList() {
        listmodel.clear();
        Iterator<Parcel> parcelForState = parcelArrayList.iterator();
        String comboState = stateJComboBox.getSelectedItem().toString();
        while (parcelForState.hasNext()) {
             Parcel myParcel = parcelForState.next();
            String currentState = myParcel.getState();
            if (currentState.equals(comboState)) {
                listmodel.addElement(myParcel.getParcelID());
            }
        }
        stateJList.setModel(listmodel);
    }
    
    /**
     * Adds all text fields apart from arrival time and parcel ID to an Array
     * List to make subsequent editing simpler.
     * @see javax.swing.JTextField
     * @see java.util.ArrayList
     * @see Parcel
     */
    private void addFieldsArrayList() {
        fields.add(nameJTextField);
        fields.add(addressJTextField);
        fields.add(cityJTextField);
        fields.add(zipJTextField);
    }
    
    /**
     * Reads state abbreviations into combo boxes from an external file.
     * @param filename file from which states are to be read.
     * @see java.io.FileReader
     * @see java.io.BufferedReader
     * @see javax.swing.JComboBox
     */
    private void readStates(String filename) {
        FileReader inputFile;
        BufferedReader input;        
        boolean fileExists = true;
        
        try {
            inputFile = new FileReader(filename);
            input = new BufferedReader(inputFile);
            String lineOfInput = input.readLine();
            String name = "";
            fileExists = true;
            while (lineOfInput != null) {
                StringTokenizer token = new StringTokenizer(lineOfInput, ",");
                while (token.hasMoreElements()) {
                    name = token.nextElement().toString();
                    stateSelectJComboBox.addItem(name);
                    stateJComboBox.addItem(name);
                }
                lineOfInput = input.readLine();
            }
            stateJComboBox.setEditable(false);
            stateSelectJComboBox.setEditable(false);
        } catch (FileNotFoundException ex) {
            
        } catch (Exception ex) {
            
        }
    }
    
    /**
     * Sets all text fields in the fields array list to null.
     * @see #fields
     * @see #setFields(boolean) 
     */
    private void setFieldsBlank() {
        Iterator<JTextField> myFields = fields.iterator();
        while (myFields.hasNext()) {
            myTextField = myFields.next();
            myTextField.setText(null);
        }
    }
    
    /**
     * Creates new parcel object and adds that parcel to an ArrayList of all
     * previously scanned parcels.
     * @see javax.swing.JTextField
     * @see Parcel
     * @see java.util.ArrayList
     * @see #parcelArrayList
     */
    private void makeParcel() {
        String userArrivalTime = arrivedAtJTextField.getText();
        String userName = nameJTextField.getText();
        String userAddress = addressJTextField.getText();
        String userCity = cityJTextField.getText();
        String userState = stateSelectJComboBox.getSelectedItem().toString();
        int userZip = Integer.parseInt(zipJTextField.getText());
        int userParcelID = Parcel.overParcelID;
        myParcel = new Parcel(userArrivalTime, userParcelID, userName, userAddress, userCity,
                userState, userZip);
        parcelArrayList.add(myParcel);
    }
    
    /**
     * Sets all buttons and fields to the proper state once the user has
     * invoked the Scan New function, and increments the parcel ID field.
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see Parcel
     */
    private void scanNewAction() {
        setButtons(false);
        addJButton.setEnabled(true);
        addJMenuItem.setEnabled(true);
        scanNewJMenuItem.setEnabled(false);
        nameJTextField.requestFocus();
        stateJList.setEnabled(false);
        
        setFieldsBlank();
        setFields(true);
        
        Parcel.overParcelID++;
        arrivedAtJTextField.setText(setArrivalTime());
        parcelIDJTextField.setText("" + Parcel.overParcelID);
        this.getRootPane().setDefaultButton(addJButton);
    }
    
    /**
     * Sets the buttons and menu items to the proper state once the user has
     * invoked the Add function.
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see javax.swing.JList
     * @see #setList
     */
    private void setAddButtonMenu() {
        stateJList.setEnabled(true);
        scanNewJButton.setEnabled(true);
        scanNewJMenuItem.setEnabled(true);
        addJButton.setEnabled(false);
        addJMenuItem.setEnabled(false);
        removeJButton.setEnabled(true);
        removeJMenuItem.setEnabled(true);
        editJButton.setEnabled(true);
        editJMenuItem.setEnabled(true);
        updateJMenuItem.setEnabled(false);
        stateJComboBox.setSelectedItem(stateSelectJComboBox.getSelectedItem());
        setList();
        stateJList.setSelectedValue(Integer.parseInt(parcelIDJTextField.getText()), true);
        this.getRootPane().setDefaultButton(scanNewJButton);
        scanNewJButton.requestFocus();
    }
    
    /**
     * Sets the structure and order for the Add function invoked by the user.
     * @see #makeParcel
     * @see Parcel
     * @see javax.swing.JButton
     * @see #setScroll
     * @see #validateFields
     */
    private void addAction() {
        boolean fieldError = validateFields();
        boolean zipError = validateZip();
        if (fieldError == false || zipError == false) {
            errorWindow();
        } else {
            makeParcel();
            setAddButtonMenu();
            setFields(false);
            setScroll();
        }
    }

    /**
     * Changes the content of all text fields to match the currently selected
     * Parcel.
     * @see java.util.Iterator
     * @see java.util.ArrayList
     * @see Parcel
     * @see #changeFields
     */
    private void setSelectedFields() {
        Iterator<Parcel> parcelIt = parcelArrayList.iterator();
        try {
            while (parcelIt.hasNext()) {
                myParcel = parcelIt.next();
                if (myParcel.getParcelID() == stateJList.getSelectedValue()) {
                    changeFields(myParcel);
                }
            }
        } catch (ConcurrentModificationException ex) {
            
        } catch (Exception ex) {
            
        }
        
    }
    
    /**
     * Sets all buttons and menu items to the proper state once the user has
     * invoked the edit parcel function.
     * @see Parcel
     * @see java.util.ArrayList
     * @see Integer
     * @see #updateParcelButtonMenus()
     */
    private void editParcelActionButtonMenus() {
        stateJList.setSelectedValue(Integer.parseInt(parcelIDJTextField.getText()), true);
        removeJButton.setEnabled(false);
        scanNewJButton.setEnabled(false);
        updateJButton.setEnabled(true);
        editJButton.setEnabled(false);
        nextJButton.setEnabled(false);
        backJButton.setEnabled(false);
        
        nameJTextField.requestFocus();
        
        removeJMenuItem.setEnabled(false);
        scanNewJMenuItem.setEnabled(false);
        updateJMenuItem.setEnabled(true);
        editJMenuItem.setEnabled(false);
        backJMenuItem.setEnabled(false);
        nextJMenuItem.setEnabled(false);
        
        Parcel removeParcel = new Parcel();
        removeParcel = findParcelID(Integer.parseInt(parcelIDJTextField.getText()));
        parcelArrayList.remove(removeParcel);
        setFields(true);
        Parcel.overParcelID++;
        parcelIDJTextField.setText("" + Parcel.overParcelID);
        stateJList.setEnabled(false);
        
        
    }

    /**
     * Enables the appropriate buttons and menus once the user has invoked the
     * update parcel function.
     * @see #updateParcel
     * @see Parcel
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     */
    private void updateParcelButtonMenus() {
        setFields(false);
        setButtons(true);
        setScroll();
        addJButton.setEnabled(false);
        updateJButton.setEnabled(false);
        updateJMenuItem.setEnabled(false);
        scanNewJMenuItem.setEnabled(true);
        editJMenuItem.setEnabled(true);
        removeJMenuItem.setEnabled(true);
        stateJList.setEnabled(true);
        scanNewJButton.requestFocus();
    }
    
    /**
     * Creates the edited parcel and places it at the end of the parcelArrayList.
     * @see #makeParcel
     * @see #validateFields
     * @see #setList
     */
    private void updateParcel() {
        boolean fieldError = validateFields();
        boolean zipError = validateZip();
        if (fieldError == false || zipError == false) {
            errorWindow();
        } else {
            makeParcel();
            stateJComboBox.setSelectedItem(stateSelectJComboBox.getSelectedItem());
            setList();
            stateJList.setSelectedValue(Integer.parseInt(parcelIDJTextField.getText()), true);
            updateParcelButtonMenus();
        }
    }
    
    /**
     * Changes the text in all text fields to match the currently selected
     * Parcel's fields.
     * @param myParcel the parcel that will be displayed
     * @see Parcel
     * @see javax.swing.JTextField
     * @see #updateParcel() 
     */
    private void changeFields(Parcel myParcel) {
        arrivedAtJTextField.setText(myParcel.getArrivalTime());
        parcelIDJTextField.setText("" + myParcel.getParcelID());
        nameJTextField.setText(myParcel.getName());
        addressJTextField.setText(myParcel.getAddress());
        cityJTextField.setText(myParcel.getCity());
        zipJTextField.setText("" + myParcel.getZip());
        stateJComboBox.setSelectedItem(myParcel.getState());
        stateSelectJComboBox.setSelectedIndex(stateJComboBox.getSelectedIndex());

    }
    
    /**
     * Finds the parcel at the index just before the passed parcel.
     * @param currentParcel currently selected parcel
     * @return parcel at -1 + current parcel index
     * @see Parcel
     * @see java.util.Iterator
     * @see java.util.ArrayList
     * @see #findNextParcel(int) 
     * @see #findParcelID(int) 
     */
    private Parcel findPreviousParcel(Parcel currentParcel) {
        Iterator<Parcel> prevParcel = parcelArrayList.iterator();
        Parcel placeHolder = new Parcel();
        Parcel prevHolder = new Parcel();
        while (prevParcel.hasNext()) {
            placeHolder = prevParcel.next();
            if (placeHolder == currentParcel) {
                return prevHolder;
            }
            prevHolder = placeHolder;
        }
        return prevHolder;
    }
    
    /**
     * Finds the parcel at the index just after the currently selected Parcel.
     * @param ID index of the currently selected parcel
     * @return parcel at 1 + currently selected parcel index
     * @see Parcel
     * @see java.util.Iterator
     * @see java.util.ArrayList
     * @see #findPreviousParcel(shipping.hub.Parcel) 
     */
    private Parcel findNextParcel(int ID) {
        Iterator<Parcel> nextParcel = parcelArrayList.iterator();
        Parcel returnParcel = new Parcel();
        while (nextParcel.hasNext()) {
            returnParcel = nextParcel.next();
            if (parcelArrayList.indexOf(returnParcel) > ID) {
                return returnParcel;
            }
        }
        returnParcel = parcelArrayList.get(0);
        return returnParcel;
    }
    
    /**
     * Finds the parcel with the desired parcel ID by iterating through an
     * ArrayList.
     * @param correctID parcel ID to be found
     * @return parcel with the desired ID, or an empty parcel if a match is not
     * found.
     * @see #findNextParcel
     * @see #findPreviousParcel
     * @see java.util.ArrayList
     * @see java.util.Iterator
     */
    private Parcel findParcelID(int correctID) {
        Iterator<Parcel> myIT = parcelArrayList.iterator();
        Parcel myParcel = new Parcel();
        while (myIT.hasNext()) {
                myParcel = myIT.next();
                if (myParcel.getParcelID() == correctID) {
                    return myParcel;
                }
        }
        Parcel emptyParcel = new Parcel();
        return emptyParcel;
    }
    
    /**
     * Scrolls to display the parcel with the next higher parcel ID, or the
     * first parcel if the current parcel is at index parcelArrayList.size().
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see javax.swing.JList
     * @see Parcel
     * @see #backButton()
     */
    private void nextButton() {
        int index = Integer.parseInt(parcelIDJTextField.getText());
        Parcel scrollParcel = new Parcel();
        myParcel = findParcelID(index);
        if (parcelArrayList.indexOf(myParcel) == parcelArrayList.size() - 1) {
            scrollParcel = parcelArrayList.get(0);
        } else {
            scrollParcel = findNextParcel(parcelArrayList.indexOf(myParcel));
        }
        arrivedAtJTextField.setText(scrollParcel.getArrivalTime());
        parcelIDJTextField.setText("" + scrollParcel.getParcelID());
        nameJTextField.setText(scrollParcel.getName());
        addressJTextField.setText(scrollParcel.getAddress());
        cityJTextField.setText(scrollParcel.getCity());
        zipJTextField.setText("" + scrollParcel.getZip());
        stateSelectJComboBox.setSelectedItem(scrollParcel.getState());
        stateJComboBox.setSelectedItem(scrollParcel.getState());
        setList();
        stateJList.setSelectedValue(scrollParcel.getParcelID(), true);   
    }
    
    /**
     * Scrolls to display the parcel with the next lower parcel ID, or the
     * last parcel if the current parcel is at index 0.
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see javax.swing.JList
     * @see Parcel 
     */
    private void backButton() {
        int index = Integer.parseInt(parcelIDJTextField.getText());
        Parcel scrollParcel = new Parcel();
        Parcel firstParcel = parcelArrayList.get(0);
        Parcel lastParcel = parcelArrayList.get(parcelArrayList.size() - 1);
        myParcel = findParcelID(index);
        
        if (parcelArrayList.indexOf(myParcel) == parcelArrayList.indexOf(firstParcel)) {
            scrollParcel = lastParcel;
        } else {
            scrollParcel = findPreviousParcel(myParcel);
        }
        arrivedAtJTextField.setText(scrollParcel.getArrivalTime());
        parcelIDJTextField.setText("" + scrollParcel.getParcelID());
        nameJTextField.setText(scrollParcel.getName());
        addressJTextField.setText(scrollParcel.getAddress());
        cityJTextField.setText(scrollParcel.getCity());
        zipJTextField.setText("" + scrollParcel.getZip());
        stateSelectJComboBox.setSelectedItem(scrollParcel.getState());
        stateJComboBox.setSelectedItem(scrollParcel.getState());
        setList();
        stateJList.setSelectedValue(scrollParcel.getParcelID(), true);
    }
    
    /**
     * Enables/Disables the next and back jbuttons according the size of
     * parcelArrayList.
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see #parcelArrayList
     * @see Parcel
     * @see java.util.Iterator
     */
    private void setScroll() {
        if (parcelArrayList.size() > 1) {
            backJButton.setEnabled(true);
            backJMenuItem.setEnabled(true);
            nextJButton.setEnabled(true);
            nextJMenuItem.setEnabled(true);
        } else {
            backJButton.setEnabled(false);
            nextJButton.setEnabled(false);
            backJMenuItem.setEnabled(false);
            nextJMenuItem.setEnabled(false);
        }
    }
    
    /**
     * Upon invoking of the about function, displays a separate window with
     * informational text about the application.
     * @throws FileNotFoundException 
     * @see UtilPane
     * @see Parcel
     * @see javax.swing.JMenuItem
     * @see java.util.Scanner
     */
    private void aboutMenu() throws FileNotFoundException {
        UtilPane aboutPane = new UtilPane();
        aboutPane.setVisible(true);
        aboutPane.printFileToPane("src/resources/About.txt");
    }
    
    /**
     * Sets the structure to print the currently selected parcel to a 
     * separate window.
     * @see UtilPane
     * @see Parcel
     * @see java.io.PrintStream
     */
    private void printButtonMenu() {
        UtilPane printPane = new UtilPane();
        printPane.setVisible(true);
        try {
            printToPanel(printPane);
        } catch (FileNotFoundException ex) {
        }
    }
    
    /**
     * Prints the current parcel to a separate window.
     * @param pane text pane to place the desired text
     * @throws FileNotFoundException 
     * @see UtilPane
     * @see Parcel
     * @see java.io.PrintStream
     */
    private void printToPanel (UtilPane pane) throws FileNotFoundException {
        Iterator<Parcel> printIt = parcelArrayList.iterator();
        int index = (int) stateJList.getSelectedValue();
        while (printIt.hasNext()) {
            myParcel = printIt.next();
            if (myParcel.getParcelID() == index) {
                pane.printTextToPane(myParcel.toString());
            }
        }
    }
    
    /**
     * Removes the currently selected parcel from the parcelArrayList and Jlist
     * display.
     * @see javax.swing.JList
     * @see java.util.ArrayList
     * @see Parcel
     * @see #setList
     * @see Integer
     */
    private void removeParcel() {
        int removeID = Integer.parseInt(parcelIDJTextField.getText());
        myParcel = findParcelID(removeID);
        parcelArrayList.remove(myParcel);
        setList();
    }
    
    /**
     * Enables the proper buttons and menus when the user invokes the remove
     * parcel function.
     * @see #removeParcel
     * @see Parcel
     * @see DefaultListModel
     */
    private void removeButtonMenu() {
        Parcel myParcel;
        removeParcel();
        setList();
        setScroll();
        if (listmodel.isEmpty()) {
            myParcel = parcelArrayList.get(0);
            changeFields(myParcel);
            stateJList.setSelectedValue(myParcel.getParcelID(), true);
        }
        stateJList.setSelectedIndex(0);
    }
    
    /**
     * Selects the appropriate parcel ID in the J List when the user selects
     * it from the list.
     * @see javax.swing.JList
     * @see #changeFields
     * @see Parcel
     */
    private void jListClicked() {
        int value = (int) stateJList.getSelectedValue();
        myParcel = findParcelID(value);
        changeFields(myParcel);
        stateJList.setSelectedValue(myParcel.getParcelID(), true);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        parcelInfoJPanel = new javax.swing.JPanel();
        parcelIDJLable = new javax.swing.JLabel();
        nameJLabel = new javax.swing.JLabel();
        addressJLabel = new javax.swing.JLabel();
        cityJLabel = new javax.swing.JLabel();
        parcelIDJTextField = new javax.swing.JTextField();
        nameJTextField = new javax.swing.JTextField();
        addressJTextField = new javax.swing.JTextField();
        cityJTextField = new javax.swing.JTextField();
        stateJLabel = new javax.swing.JLabel();
        stateSelectJComboBox = new javax.swing.JComboBox();
        zipJLabel = new javax.swing.JLabel();
        zipJTextField = new javax.swing.JFormattedTextField();
        stateInfoJPanel = new javax.swing.JPanel();
        stateJComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        stateJList = new javax.swing.JList();
        actionJPanel = new javax.swing.JPanel();
        scanNewJButton = new javax.swing.JButton();
        addJButton = new javax.swing.JButton();
        removeJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        updateJButton = new javax.swing.JButton();
        backJButton = new javax.swing.JButton();
        nextJButton = new javax.swing.JButton();
        arrivalJPanel = new javax.swing.JPanel();
        arrivedAtJLabel = new javax.swing.JLabel();
        arrivedAtJTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        JMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        clearJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        actionJMenu = new javax.swing.JMenu();
        scanNewJMenuItem = new javax.swing.JMenuItem();
        addJMenuItem = new javax.swing.JMenuItem();
        removeJMenuItem = new javax.swing.JMenuItem();
        editJMenuItem = new javax.swing.JMenuItem();
        updateJMenuItem = new javax.swing.JMenuItem();
        backJMenuItem = new javax.swing.JMenuItem();
        nextJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Brent Young Parcel Shipping Service");
        setResizable(false);

        parcelInfoJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcel Information"));

        parcelIDJLable.setText("Parcel ID:");

        nameJLabel.setText("Name:");

        addressJLabel.setText("Address:");

        cityJLabel.setText("City:");

        nameJTextField.setActionCommand("<Not Set>");

        stateJLabel.setText("State:");

        zipJLabel.setText("Zip:");

        try {
            zipJTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        zipJTextField.setToolTipText("Hit enter to move cursor to 'Add' Button.");
        zipJTextField.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        zipJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zipJTextFieldActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout parcelInfoJPanelLayout = new org.jdesktop.layout.GroupLayout(parcelInfoJPanel);
        parcelInfoJPanel.setLayout(parcelInfoJPanelLayout);
        parcelInfoJPanelLayout.setHorizontalGroup(
            parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(parcelInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(parcelIDJLable)
                    .add(nameJLabel)
                    .add(addressJLabel)
                    .add(cityJLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, parcelInfoJPanelLayout.createSequentialGroup()
                        .add(cityJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 155, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(stateJLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(stateSelectJComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(zipJLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(zipJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 149, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(addressJTextField)
                    .add(nameJTextField)
                    .add(parcelIDJTextField))
                .addContainerGap())
        );
        parcelInfoJPanelLayout.setVerticalGroup(
            parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(parcelInfoJPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(parcelIDJLable)
                    .add(parcelIDJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nameJLabel)
                    .add(nameJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addressJLabel)
                    .add(addressJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(parcelInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cityJLabel)
                    .add(cityJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(stateJLabel)
                    .add(stateSelectJComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(zipJLabel)
                    .add(zipJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(17, 17, 17))
        );

        stateInfoJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcel by State"));

        stateJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateJComboBoxActionPerformed(evt);
            }
        });

        stateJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stateJListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(stateJList);

        org.jdesktop.layout.GroupLayout stateInfoJPanelLayout = new org.jdesktop.layout.GroupLayout(stateInfoJPanel);
        stateInfoJPanel.setLayout(stateInfoJPanelLayout);
        stateInfoJPanelLayout.setHorizontalGroup(
            stateInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(stateInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(stateInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(stateJComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        stateInfoJPanelLayout.setVerticalGroup(
            stateInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(stateInfoJPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(stateJComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 134, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        scanNewJButton.setMnemonic('S');
        scanNewJButton.setText("Scan New");
        scanNewJButton.setToolTipText("Enter new parcel information.");
        scanNewJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanNewJButtonActionPerformed(evt);
            }
        });

        addJButton.setMnemonic('A');
        addJButton.setText("Add");
        addJButton.setToolTipText("Add current parcel to list of sent parcels.");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });

        removeJButton.setMnemonic('R');
        removeJButton.setText("Remove");
        removeJButton.setToolTipText("Remove selected parcel from list of sent parcels.");
        removeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeJButtonActionPerformed(evt);
            }
        });

        editJButton.setMnemonic('E');
        editJButton.setText("Edit");
        editJButton.setToolTipText("Edit currently selected parcel's information.");
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });

        updateJButton.setMnemonic('U');
        updateJButton.setText("Update");
        updateJButton.setToolTipText("Saves selected edited parcel in list of sent parcels.");
        updateJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateJButtonActionPerformed(evt);
            }
        });

        backJButton.setMnemonic('B');
        backJButton.setText("Back");
        backJButton.setToolTipText("Scroll backwards through all sent parcels.");
        backJButton.setActionCommand("< Back");
        backJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backJButtonActionPerformed(evt);
            }
        });

        nextJButton.setMnemonic('N');
        nextJButton.setText("Next");
        nextJButton.setToolTipText("Scrolls forward through all sent parcels.");
        nextJButton.setActionCommand("Next >");
        nextJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextJButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout actionJPanelLayout = new org.jdesktop.layout.GroupLayout(actionJPanel);
        actionJPanel.setLayout(actionJPanelLayout);
        actionJPanelLayout.setHorizontalGroup(
            actionJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(actionJPanelLayout.createSequentialGroup()
                .add(scanNewJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(removeJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(editJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(updateJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(backJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(nextJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        actionJPanelLayout.setVerticalGroup(
            actionJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, actionJPanelLayout.createSequentialGroup()
                .add(actionJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, nextJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, scanNewJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, addJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, removeJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, editJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, updateJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(backJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        arrivedAtJLabel.setText("Arrived at:");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mediumparcelimage.jpeg"))); // NOI18N

        org.jdesktop.layout.GroupLayout arrivalJPanelLayout = new org.jdesktop.layout.GroupLayout(arrivalJPanel);
        arrivalJPanel.setLayout(arrivalJPanelLayout);
        arrivalJPanelLayout.setHorizontalGroup(
            arrivalJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(arrivalJPanelLayout.createSequentialGroup()
                .add(87, 87, 87)
                .add(arrivedAtJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(arrivedAtJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel1)
                .add(32, 32, 32))
        );
        arrivalJPanelLayout.setVerticalGroup(
            arrivalJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(arrivalJPanelLayout.createSequentialGroup()
                .add(arrivalJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(arrivalJPanelLayout.createSequentialGroup()
                        .add(17, 17, 17)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                    .add(arrivalJPanelLayout.createSequentialGroup()
                        .add(48, 48, 48)
                        .add(arrivalJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(arrivedAtJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(arrivedAtJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");

        clearJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        clearJMenuItem.setMnemonic('C');
        clearJMenuItem.setText("Clear");
        clearJMenuItem.setToolTipText("Clear all fields other than parcel ID and Arrival Time.");
        clearJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(clearJMenuItem);

        searchJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        searchJMenuItem.setMnemonic('h');
        searchJMenuItem.setText("Search");
        searchJMenuItem.setToolTipText("Search for a particular parcel.");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(searchJMenuItem);

        printJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("Prints Parcel and Recipient Information into a separate text file as well as displays the Parcel info in a separate window..");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);

        exitJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exitJMenuItem.setMnemonic('x');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Exits the application.");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        JMenuBar.add(fileJMenu);

        actionJMenu.setMnemonic('A');
        actionJMenu.setText("Action");

        scanNewJMenuItem.setMnemonic('S');
        scanNewJMenuItem.setText("Scan New");
        scanNewJMenuItem.setToolTipText("Enter new parcel information.");
        scanNewJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanNewJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(scanNewJMenuItem);

        addJMenuItem.setMnemonic('d');
        addJMenuItem.setText("Add");
        addJMenuItem.setToolTipText("Add current parcel to list of sent parcels.");
        addJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(addJMenuItem);

        removeJMenuItem.setMnemonic('R');
        removeJMenuItem.setText("Remove");
        removeJMenuItem.setToolTipText("Remove selected parcel from list of sent parcels.");
        removeJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(removeJMenuItem);

        editJMenuItem.setMnemonic('E');
        editJMenuItem.setText("Edit");
        editJMenuItem.setToolTipText("Edit currently selected parcel's information.");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(editJMenuItem);

        updateJMenuItem.setMnemonic('U');
        updateJMenuItem.setText("Update");
        updateJMenuItem.setToolTipText("Saves selected edited parcel in list of sent parcels.");
        updateJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(updateJMenuItem);

        backJMenuItem.setMnemonic('B');
        backJMenuItem.setText("Back");
        backJMenuItem.setToolTipText("Scroll backwards through all sent parcels.");
        backJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(backJMenuItem);

        nextJMenuItem.setMnemonic('N');
        nextJMenuItem.setText("Next");
        nextJMenuItem.setToolTipText("Scrolls forward through all sent parcels.");
        nextJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(nextJMenuItem);

        JMenuBar.add(actionJMenu);

        helpJMenu.setMnemonic('H');
        helpJMenu.setText("Help");

        aboutJMenuItem.setMnemonic('A');
        aboutJMenuItem.setText("About");
        aboutJMenuItem.setToolTipText("Displays an about page describing application intricasies.");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        JMenuBar.add(helpJMenu);

        setJMenuBar(JMenuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(actionJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(parcelInfoJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(stateInfoJPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(arrivalJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(arrivalJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(parcelInfoJPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 213, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(stateInfoJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(actionJPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Invokes the 'Scan New' function.
     * @param evt scan new jButton clicked
     * @see javax.swing.JButton
     * @see #scanNewAction
     */
    private void scanNewJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanNewJButtonActionPerformed
        scanNewAction();
    }//GEN-LAST:event_scanNewJButtonActionPerformed

    /**
     * Invokes the 'Add' function.
     * @param evt addJButton clicked
     * @see javax.swing.JButton
     * @see Parcel
     * @see #makeParcel
     * @see #addAction() 
     */
    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed
        addAction();
    }//GEN-LAST:event_addJButtonActionPerformed

    /**
     * Invokes the 'Scan New' function.
     * @param evt scanNewJMenuItem clicked
     * @see javax.swing.JMenuItem
     * @see #scanNewAction
     */
    private void scanNewJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanNewJMenuItemActionPerformed
        scanNewAction();
    }//GEN-LAST:event_scanNewJMenuItemActionPerformed
    /**
     * Invokes the 'Add' function.
     * @param evt AddJMenuItem clicked
     * @see javax.swing.JMenuItem
     * @see #addAction
     */
    private void addJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJMenuItemActionPerformed
        addAction();
    }//GEN-LAST:event_addJMenuItemActionPerformed
    /**
     * Sets the JList to display the appropriate parcel ID's for the selected
     * state.
     * @param evt stateJComboBox item changed
     * @see javax.swing.JComboBox
     * @see java.util.Iterator
     */
    private void stateJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateJComboBoxActionPerformed
        setList();
    }//GEN-LAST:event_stateJComboBoxActionPerformed
    /**
     * Invokes the remove parcel function.
     * @param evt removeJButton clicked
     * @see #removeButtonMenu
     * @see javax.swing.JMenuItem
     * @see Parcel
     * @see java.util.ArrayList
     */
    private void removeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeJButtonActionPerformed
        removeButtonMenu();
    }//GEN-LAST:event_removeJButtonActionPerformed
    /**
     * Invokes the edit parcel function.
     * @param evt editJButton clicked
     * @see javax.swing.JButton
     * @see Parcel
     * @see #editParcelActionButtonMenus() 
     */
    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJButtonActionPerformed
        editParcelActionButtonMenus();
    }//GEN-LAST:event_editJButtonActionPerformed
    /**
     * Invokes the update parcel function.
     * @param evt updateJButton clicked
     * @see javax.swing.JButton
     * @see #updateParcel
     * @see Parcel
     */
    private void updateJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateJButtonActionPerformed
        updateParcel();
    }//GEN-LAST:event_updateJButtonActionPerformed
    /**
     * Invokes the exit function.
     * @param evt exitJMenuItem clicked
     * @see javax.swing.JMenuItem
     * @see System
     */
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitJMenuItemActionPerformed
    /**
     * Invokes the clear function, setting all text fields blank.
     * @param evt clearJMenuItem clicked
     * @see javax.swing.JMenuItem
     * @see #setFieldsBlank
     * @see javax.swing.event.MenuEvent
     */
    private void clearJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJMenuItemActionPerformed
        setFieldsBlank();
    }//GEN-LAST:event_clearJMenuItemActionPerformed
    /**
     * Invokes the next function, scrolling to the next parcel in the list.
     * @param evt nextJButton clicked
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see #nextButton
     */
    private void nextJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextJButtonActionPerformed
        nextButton();
    }//GEN-LAST:event_nextJButtonActionPerformed

    /**
     * Invokes the back function, scrolling to the previous parcel in the list.
     * @param evt backJButton clicked
     * @see java.util.ArrayList
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see #backButton
     */
    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backJButtonActionPerformed
        backButton();
    }//GEN-LAST:event_backJButtonActionPerformed

    /**
     * Invokes the edit parcel function.
     * @param evt editJButton clicked
     * @see javax.swing.JButton
     * @see Parcel
     * @see #editParcelActionButtonMenus() 
     */
    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
        editParcelActionButtonMenus();
    }//GEN-LAST:event_editJMenuItemActionPerformed

    /**
     * Invokes the remove parcel function.
     * @param evt removeJButton clicked
     * @see #removeButtonMenu
     * @see javax.swing.JMenuItem
     * @see Parcel
     * @see java.util.ArrayList
     */
    private void removeJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeJMenuItemActionPerformed
        removeButtonMenu();
    }//GEN-LAST:event_removeJMenuItemActionPerformed

    /**
     * Invokes the update parcel function.
     * @param evt updateJButton clicked
     * @see javax.swing.JButton
     * @see #updateParcel
     * @see Parcel
     */
    private void updateJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateJMenuItemActionPerformed
        updateParcel();
    }//GEN-LAST:event_updateJMenuItemActionPerformed

    /**
     * Invokes the back function, scrolling to the previous parcel in the list.
     * @param evt backJMenuItem clicked
     * @see java.util.ArrayList
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see #backButton
     */
    private void backJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backJMenuItemActionPerformed
        backButton();
    }//GEN-LAST:event_backJMenuItemActionPerformed

    /**
     * Invokes the next function, scrolling to the next parcel in the list.
     * @param evt nextJMenuItem clicked
     * @see javax.swing.JButton
     * @see javax.swing.JMenuItem
     * @see #nextButton
     */
    private void nextJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextJMenuItemActionPerformed
        nextButton();
    }//GEN-LAST:event_nextJMenuItemActionPerformed

    /**
     * Invokes the print function, printing parcel contents to both a panel
     * and a text file.
     * @param evt printJMenuItem clicked
     * @see UtilPane
     * @see Parcel
     * @see javax.swing.JMenuItem
     * @see #printButtonMenu
     */
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        printButtonMenu();
    }//GEN-LAST:event_printJMenuItemActionPerformed

    /**
     * Invokes the about function, displaying a window with information on the
     * functionality of the application.
     * @param evt aboutJMenuItem clicked
     * @see UtilPane
     * @see Parcel
     * @see PrintStream
     * @see javax.swing.JMenuItem
     */
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        try {
            aboutMenu();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShippingHubGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    /**
     * Allows the user to search in a separate window for a specific parcel,
     * either by name or state abbreviation.
     * @param evt searchJMenuItem clicked
     * @see javax.swing.JMenuItem
     * @see Search
     * @see Parcel
     * @see UtilPane
     */
    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
        Search newSearch = new Search(parcelArrayList);
        newSearch.setVisible(true);
    }//GEN-LAST:event_searchJMenuItemActionPerformed

    /**
     * Sets the text fields to display the correct parcel information based on
     * which parcel ID the user selects in the list.
     * @param evt stateJList item clicked
     * @see javax.swing.JList
     * @see DefaultListModel
     * @see java.util.ArrayList
     */
    private void stateJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stateJListMouseClicked
        jListClicked();
    }//GEN-LAST:event_stateJListMouseClicked

    private void zipJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zipJTextFieldActionPerformed
        addJButton.requestFocus();
    }//GEN-LAST:event_zipJTextFieldActionPerformed

    /**
     * Calls the GUI creator for ShippingHubGUI
     * @param args the command line arguments
     * @see ShippingHubGUI
     * @see Parcel
     * @see Search
     * @see UtilPane
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
            java.util.logging.Logger.getLogger(ShippingHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShippingHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShippingHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShippingHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShippingHubGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar JMenuBar;
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JMenu actionJMenu;
    private javax.swing.JPanel actionJPanel;
    private javax.swing.JButton addJButton;
    private javax.swing.JMenuItem addJMenuItem;
    private javax.swing.JLabel addressJLabel;
    private javax.swing.JTextField addressJTextField;
    private javax.swing.JPanel arrivalJPanel;
    private javax.swing.JLabel arrivedAtJLabel;
    private javax.swing.JTextField arrivedAtJTextField;
    private javax.swing.JButton backJButton;
    private javax.swing.JMenuItem backJMenuItem;
    private javax.swing.JLabel cityJLabel;
    private javax.swing.JTextField cityJTextField;
    private javax.swing.JMenuItem clearJMenuItem;
    private javax.swing.JButton editJButton;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameJLabel;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JButton nextJButton;
    private javax.swing.JMenuItem nextJMenuItem;
    private javax.swing.JLabel parcelIDJLable;
    private javax.swing.JTextField parcelIDJTextField;
    private javax.swing.JPanel parcelInfoJPanel;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JButton removeJButton;
    private javax.swing.JMenuItem removeJMenuItem;
    private javax.swing.JButton scanNewJButton;
    private javax.swing.JMenuItem scanNewJMenuItem;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.JPanel stateInfoJPanel;
    private javax.swing.JComboBox stateJComboBox;
    private javax.swing.JLabel stateJLabel;
    private javax.swing.JList stateJList;
    private javax.swing.JComboBox stateSelectJComboBox;
    private javax.swing.JButton updateJButton;
    private javax.swing.JMenuItem updateJMenuItem;
    private javax.swing.JLabel zipJLabel;
    private javax.swing.JFormattedTextField zipJTextField;
    // End of variables declaration//GEN-END:variables
}
