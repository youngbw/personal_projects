package shipping.hub;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;

/**
*  Class:	<b>Search</b>
*  File:	Search.java
* <pre>
*  Description:	This class searches a given ArrayList for a specific parcel,
*  either by name or by state abbreviation.
*  @author	<i>Brent Young<i/>
*  Environment:	Mac OSx, jdk1.7_17, NetBeans 7.3
*  Date:	4/17/2013
*  @version	%1% %0%
*  @see         java.io.FileNotFoundException
*  @see         java.util.ArrayList
*  @see         java.util.Iterator
*  @see         javax.swing.DefaultListModel
*  @see         Parcel
*  @see         UtilPane
*  @see         ShippingHubGUI
* </pre>
*  History Log:	Created on April 1, 2013, 1:12:19 AM
*/

public class Search extends javax.swing.JFrame {
    
    /**
     * Creates a reference parcel for use in iterating through ArrayLists of
     * Parcel type.
     * @see Parcel
     * @see ShippingHubGUI.#parcelArrayList
     * @see #searchArrayList
     */
    private Parcel myParcel = new Parcel();
    
    /**
     * ArrayList containing parcels to be searched through.
     * @see Parcel
     * @see ShippingHubGUI.#parcelArrayList
     */
    private ArrayList<Parcel> searchArrayList = new ArrayList<>();
    
    /**
     * Listmodel used for displaying of recipient names of parcels.
     * @see DefaultListModel
     * @see Parcel
     * @see Parcel.#getName() 
     */
    DefaultListModel searchListmodel = new DefaultListModel();
    
    /**
     * Creates new form of search panel and sets the focus of the application
     * to the search text field.
     * @see javax.swing.JTextField
     * @see #Search(java.util.ArrayList) 
     */
    public Search() {
        initComponents();
        setLocationRelativeTo(null);
        searchJTextField.requestFocus();
    }
    
    /**
     * Creates new form of search panel and reads a passed ArrayList's contents
     * into searchArrayList.
     * @param searchList ArrayList to be searched
     * @see #searchArrayList
     * @see ShippingHubGUI.#parcelArrayList
     * @see java.util.ArrayList
     */
    public Search(ArrayList searchList) {
        initComponents();
        searchArrayList.addAll(searchList);
        setLocationRelativeTo(null);
        initializeList();
        searchJTextField.requestFocus();
    }
    
    /**
     * Adds the contents of searchArrayList to a JList in order to display
     * search criteria.
     * @see #searchArrayList
     * @see Parcel
     * @see #myParcel
     * @see #Search(java.util.ArrayList) 
     */
    private void initializeList() {
        Iterator<Parcel> parcelIt = searchArrayList.iterator();
        while (parcelIt.hasNext()) {
            myParcel = parcelIt.next();
            searchListmodel.addElement(myParcel.getName());
        }
        searchJList.setModel(searchListmodel);
    }
    
    /**
     * Displays the selected Parcel's toString information in an adjacent panel.
     * @see #Search(java.util.ArrayList) 
     * @see Parcel
     * @see ShippingHubGUI.#printToPanel(shipping.hub.UtilPane) 
     * @see ShippingHubGUI.#printButtonMenu() 
     */
    private void showParcel() {
        String index = (String) searchJList.getSelectedValue();
        Iterator<Parcel> parcelIt = searchArrayList.iterator();
        while (parcelIt.hasNext()) {
            myParcel = parcelIt.next();
            if (myParcel.getName().equals(index)) {
                parcelJTextArea.setText(myParcel.toString());
                break;
            }
        }
    }
    
    /**
     * Determines whether a search will be conducted using a name parameter or
     * a state abbreviation parameter.
     * @param input text that has been entered thus far
     * @see #searchJTextField
     * @see #nameSearch(java.lang.String) 
     * @see #stateSearch(java.lang.String) 
     */
    private void decideSearch(String input) {
        searchListmodel.clear();
        searchJList.setModel(searchListmodel);
        if (nameJRadioButton.isSelected()) {
           nameSearch(input);
        } else if (stateJRadioButton.isSelected()) {
           stateSearch(input);
        } else {
           initializeList(); // brings list back to original state
        }
    }
    
    /**
     * Searches searchArrayList for a parcel with the same recipient name as
     * the passed String.
     * @param input text entered thus far
     * @see #decideSearch(java.lang.String) 
     * @see #stateSearch(java.lang.String) 
     * @see #searchArrayList
     */
    private void nameSearch(String input) {
        
        Iterator<Parcel> nameIt = searchArrayList.iterator();
        while (nameIt.hasNext()) {
            myParcel = nameIt.next();
            if (myParcel.getName().toLowerCase().startsWith(input.toLowerCase())) {
                searchListmodel.addElement(myParcel.getName());
            }
        }
        searchJList.setModel(searchListmodel);
    }
    
    /**
     * Searches searchArrayList for a parcel with a matching recipient state.
     * @param input state abbreviation
     * @see #nameSearch(java.lang.String) 
     * @see #decideSearch(java.lang.String) 
     * @see Parcel
     * @see #searchArrayList
     */
    private void stateSearch(String input) {
        
        Iterator<Parcel> nameIt = searchArrayList.iterator();
        while (nameIt.hasNext()) {
            myParcel = nameIt.next();
            if (myParcel.getState().toLowerCase().startsWith(input.toLowerCase())) {
                searchListmodel.addElement(myParcel.getName());
            }
        }
        searchJList.setModel(searchListmodel);
    }
    
    /**
     * An adapted print method from ShippingHubGUI, allows search to print the
     * selected Parcel to a separate window.
     * @see UtilPane
     * @see ShippingHubGUI
     * @see Parcel
     * @see ShippingHubGUI.#printButtonMenu() 
     * @see ShippingHubGUI.#printToPanel(shipping.hub.UtilPane) 
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
     * An adapted method from ShippingHubGUI that allows the search window to
     * print the selected Parcel to a separate window.
     * @param pane pane to print text to
     * @throws FileNotFoundException if parcel is not found
     * @see UtilPane
     * @see Parcel
     * @see ShippingHubGUI
     * @see UtilPane.#printTextToPane(java.lang.String) 
     * @see ShippingHubGUI.#printToPanel(shipping.hub.UtilPane) 
     */
    private void printToPanel (UtilPane pane) throws FileNotFoundException {
        Iterator<Parcel> printIt = searchArrayList.iterator();
        String index = (String) searchJList.getSelectedValue();
        while (printIt.hasNext()) {
            myParcel = printIt.next();
            if (myParcel.getName() == index) {
                pane.printTextToPane(myParcel.toString());
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        titleJPanel = new javax.swing.JPanel();
        titleJLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        nameJRadioButton = new javax.swing.JRadioButton();
        stateJRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        closeJButton = new javax.swing.JButton();
        searchJButton = new javax.swing.JButton();
        searchJTextField = new javax.swing.JTextField();
        displayJPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchJList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        parcelJTextArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        printJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        titleJLabel.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText("Brent Young Shipping Service\n");

        org.jdesktop.layout.GroupLayout titleJPanelLayout = new org.jdesktop.layout.GroupLayout(titleJPanel);
        titleJPanel.setLayout(titleJPanelLayout);
        titleJPanelLayout.setHorizontalGroup(
            titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(titleJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(titleJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                .addContainerGap())
        );
        titleJPanelLayout.setVerticalGroup(
            titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(titleJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(titleJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(titleJPanel, java.awt.BorderLayout.PAGE_START);

        buttonGroup1.add(nameJRadioButton);
        nameJRadioButton.setSelected(true);
        nameJRadioButton.setText("Name");
        nameJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameJRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(stateJRadioButton);
        stateJRadioButton.setText("State");
        stateJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateJRadioButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Search By:");

        closeJButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        closeJButton.setMnemonic('C');
        closeJButton.setText("Close");
        closeJButton.setToolTipText("Closes the Search window");
        closeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeJButtonActionPerformed(evt);
            }
        });

        searchJButton.setMnemonic('S');
        searchJButton.setText("Search");
        searchJButton.setToolTipText("Click to Search for a Parcel.");
        searchJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJButtonActionPerformed(evt);
            }
        });

        searchJTextField.setToolTipText("Enter search criteria.");
        searchJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJTextFieldActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(searchJButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 428, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(closeJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(158, 158, 158)))
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(stateJRadioButton)
                    .add(nameJRadioButton)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(nameJRadioButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(stateJRadioButton)
                .add(32, 32, 32))
            .add(jPanel4Layout.createSequentialGroup()
                .add(33, 33, 33)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(searchJButton)
                    .add(searchJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(closeJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(12, 12, 12))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_END);

        searchJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                searchJListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(searchJList);

        parcelJTextArea.setEditable(false);
        parcelJTextArea.setColumns(20);
        parcelJTextArea.setRows(5);
        jScrollPane3.setViewportView(parcelJTextArea);

        org.jdesktop.layout.GroupLayout displayJPanelLayout = new org.jdesktop.layout.GroupLayout(displayJPanel);
        displayJPanel.setLayout(displayJPanelLayout);
        displayJPanelLayout.setHorizontalGroup(
            displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(displayJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 268, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 446, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        displayJPanelLayout.setVerticalGroup(
            displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(displayJPanelLayout.createSequentialGroup()
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .add(jScrollPane3))
                .addContainerGap())
        );

        getContentPane().add(displayJPanel, java.awt.BorderLayout.LINE_START);

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");

        printJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("Prints the selected parcel");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);

        exitJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exitJMenuItem.setMnemonic('E');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Closes Window");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        jMenuBar1.add(fileJMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Selects the stateJRadioButton and allows Parcel's to be searched via
     * state abbreviation.
     * @param evt statsJRadioButton activated
     * @see #stateSearch(java.lang.String) 
     * @see #nameSearch(java.lang.String) 
     * @see #decideSearch(java.lang.String) 
     */
    private void stateJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateJRadioButtonActionPerformed
        nameJRadioButton.setSelected(false);
    }//GEN-LAST:event_stateJRadioButtonActionPerformed

    /**
     * Selects the nameJRadioButton and allows Parcel's to be searched via
     * parcel.getName.
     * @param evt nameJRadioButton activated
     * @see #nameSearch(java.lang.String) 
     * @see #stateSearch(java.lang.String) 
     * @see #decideSearch(java.lang.String) 
     */
    private void nameJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameJRadioButtonActionPerformed
        stateJRadioButton.setSelected(false);
    }//GEN-LAST:event_nameJRadioButtonActionPerformed

    /**
     * Displays the proper parcel information should a valid parcel be
     * highlighted by the user.
     * @param evt selection made in list
     * @see #printToPanel(shipping.hub.UtilPane) 
     * @see #printButtonMenu() 
     * @see #showParcel() 
     */
    private void searchJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_searchJListValueChanged
        showParcel();
    }//GEN-LAST:event_searchJListValueChanged

    /**
     * Closes the search window.
     * @param evt exit button clicked
     * @see ShippingHubGUI.#exitJMenuItemActionPerformed(java.awt.event.ActionEvent) 
     */
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    /**
     * Closes the search window.
     * @param evt closeJButton clicked
     * @see #exitJMenuItemActionPerformed(java.awt.event.ActionEvent) 
     */
    private void closeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeJButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeJButtonActionPerformed

    /**
     * Prints the selected parcel to a separate window.
     * @param evt printJMenuItem clicked
     * @see UtilPane
     * @see #printToPanel(shipping.hub.UtilPane) 
     */
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        printButtonMenu();
    }//GEN-LAST:event_printJMenuItemActionPerformed

    /**
     * Searches searchArrayList for the parcel matching the search criteria.
     * @param evt searchJButton clicked
     * @see #decideSearch(java.lang.String) 
     * @see #nameSearch(java.lang.String) 
     * @see #stateSearch(java.lang.String) 
     */
    private void searchJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJButtonActionPerformed
        decideSearch((String) searchJTextField.getText());
        searchJList.setSelectedIndex(0);
    }//GEN-LAST:event_searchJButtonActionPerformed

    private void searchJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJTextFieldActionPerformed
        decideSearch((String) searchJTextField.getText());
        searchJList.setSelectedIndex(0);
    }//GEN-LAST:event_searchJTextFieldActionPerformed

    /**
     * Runs the GUI constructor, creating the Search window.
     * @param args the command line arguments
     * @see Search
     * @see Parcel
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
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Search().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton closeJButton;
    private javax.swing.JPanel displayJPanel;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton nameJRadioButton;
    private javax.swing.JTextArea parcelJTextArea;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JButton searchJButton;
    private javax.swing.JList searchJList;
    private javax.swing.JTextField searchJTextField;
    private javax.swing.JRadioButton stateJRadioButton;
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel titleJPanel;
    // End of variables declaration//GEN-END:variables
}
