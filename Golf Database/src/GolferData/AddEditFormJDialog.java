package GolferData;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * AddEditFormJDialog.java
 * A class representing the GUI used in a maintaining a cities database.
 * <pre>
 *    Project: GolfersGUI Database
 *    Platform: jdk 1.6.0_14; NetBeans IDE 6.8; Mac OSx
 *    Course: CS 143
 *    Hours: 1 hour
 *    Created on May 12, 2013, 10:00:00 PM
 * </pre>
 *
 * @author	brent.young@go.shoreline.edu
 * @version 	%1% %3%
 * @see     	javax.swing.JTextField
 * @see         java.util.ArrayList
 * @see         javax.swing.JOptionPane
 * @see         GolfersGUI
 * @see         Golfer
 */
public class AddEditFormJDialog extends javax.swing.JDialog {

    /**
     * Temporary Golfer used as a place holder.
     * @see Golfer
     * @see #myGolfer
     */
    Golfer tempNewGolfer;
    
    /**
     * Boolean value used to set this as an edit form as opposed to an add
     * form. Loads a golfer's values into the text fields.
     * @see GolfersGUI
     * @see GolfersGUI.#addNewMember()  
     * @see GolfersGUI.#addNewGolferComplete
     * 
     */
    protected boolean isEditForm;
    
    /**
     * ArrayList to hold GolfersGUI passed to this form.
     * @see Golfer
     * @see GolfersGUI.#myGolfers
     */
    private ArrayList<Golfer> moreGolfers = new ArrayList<>();
    
    /**
     * PlaceHolder for rank validation.
     * @see Golfer
     * @see #tempNewGolfer
     */
    Golfer myGolfer;
    
    /**
     * Creates new form AddEditFormJDialog for adding and editing members of
     * the passed ArrayList.
     * @see GolfersGUI
     * @see Golfer
     * @see #moreGolfers
     */
    public AddEditFormJDialog(java.awt.Frame parent, boolean modal, ArrayList user, Golfer joe) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        enableJTextFields(true);
        moreGolfers.addAll(user);
        myGolfer = joe;
    }
    
    /**
     * Default constructor in the case that the overloaded constructor's values
     * aren't properly passed.
     * @param parent parent frame
     * @param modal
     * @see #AddEditFormJDialog(java.awt.Frame, boolean, java.util.ArrayList, GolferData.Golfer)  
     */
    public AddEditFormJDialog(java.awt.Frame parent, boolean modal) {}
    
    /**
     * Enables/Disables textFields.
     * @param status boolean to turn fields on or off
     * @see GolfersGUI.#enableJButtons(boolean)  
     */
      private void enableJTextFields(boolean status) {
        firstNameJTextField.setEditable(status);
        lastNameJTextField.setEditable(status);
        emailJTextField.setEditable(status);
        phoneJTextField.setEditable(status);
        levelJTextField.setEditable(status);
    }
    
    /**
     * Validates all fields and catches all exceptions that may be thrown by
     * improper user input.
     * @throws NumberFormatException
     * @see Validation
     * @see #editValid(GolferData.Golfer, boolean) 
     * @see #editForm(GolferData.Golfer) 
     */
    private void confirmChanges() {
        int result = JOptionPane.showConfirmDialog(null, "Save golfer?", "Add/Edit Golfer", 
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.YES_OPTION && (!firstNameJTextField.getText().equals("")
                && !lastNameJTextField.getText().equals("")
                && !phoneJTextField.getText().equals("")
                && !levelJTextField.getText().equals(""))) {
            try {
                String firstName = Validation.noNum(firstNameJTextField.getText());
                String lastName = Validation.noNum(lastNameJTextField.getText());
                String anEmail = Validation.isEmail(emailJTextField.getText(), false);
                String aPhone = Validation.isPhoneNumber(phoneJTextField.getText());
                
                //unique level for edit form with same rank
                int aLevel = 0;
                if (Validation.isNumber(levelJTextField.getText())) {
                    aLevel = Integer.parseInt(levelJTextField.getText());
                    if (!Validation.isUniqueLevel(aLevel, GolfersGUI.myGolfers, myGolfer, isEditForm)) {
                        shiftRanks(GolfersGUI.myGolfers, myGolfer, aLevel);
                     } 
                } else {
                        throw new NumberFormatException();
                }
                
                //Create new golfer and set valid values
                tempNewGolfer = new Golfer();
                tempNewGolfer.setFirstName(firstName);
                tempNewGolfer.setLastName(lastName);
                tempNewGolfer.setEmail(anEmail);
                tempNewGolfer.setPhone(aPhone);
                tempNewGolfer.setLevel(aLevel);
                tempNewGolfer.setIcon(playerPicJTextField.getText());
                GolfersGUI.addNewGolferComplete = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Golfer not updated", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (result != JOptionPane.YES_OPTION) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Entry", "Add/Edit Golfer", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Called if the form is meant to edit an existing member, loading the text
     * fields with the passed member's information.
     * @param joe Golfer to be edited
     * @see GolfersGUI
     * @see GolfersGUI.#editForm() 
     * @see #isEditForm
     */
    public void editForm(Golfer joe) {
        firstNameJTextField.setText(joe.getFirstName());
        lastNameJTextField.setText(joe.getLastName());
        emailJTextField.setText(joe.getEmail());
        phoneJTextField.setText(joe.getPhone());
        levelJTextField.setText("" + joe.getLevel());
        playerPicJTextField.setText(joe.getIcon());
        
        if (emailJTextField.getText().equals(" ")) {
            emailJTextField.setText("");
        }
        firstNameJTextField.requestFocus();
        firstNameJTextField.selectAll();
        
    }
    
    /**
     * Method: shiftRanks
     * In the case that an edit form is what is being used, returns a unique team
     * rank for the passed Golfer.
     * @param joe Golfer object to have rank validated
     * @param isEdit whether or not an edit form is being used
     * @return integer representing a unique team rank
     * precondition: isEditForm == true
     * @see Validation
     * @see #editForm(GolferData.Golfer) 
     * @see #isEditForm
     */
    private void shiftRanks(ArrayList golf, Golfer selectedGolfer, int fieldValue) {
      GolfersGUI.sort(golf);
      boolean isChanged = false;
      Iterator<Golfer> golfIt = golf.iterator();
      Golfer g = new Golfer();
      while (golfIt.hasNext() && !isChanged) {
          g = golfIt.next();
          if (g.getLevel() >= fieldValue && !g.equals(myGolfer)) {
              g.setLevel(g.getLevel() + 1);
          } else if (g.getLevel() >= fieldValue && g.equals(myGolfer)) {
              g.setLevel(fieldValue);
              isChanged = true;
          }
      }
    }
    
    /**
     * Method: setPic
     * Allows choice of certain image file types to be set as an icon by
     * grabbing the absolute path of the icon.
     * @return String representing file's absolute path
     * @see Golfer.#getIcon() 
     * @see GolfersGUI.#setIcon(java.lang.String) 
     */
    private String setPic() {
        JFileChooser picChooser = new JFileChooser("./src");
        FileNameExtensionFilter picFilter = new FileNameExtensionFilter("JPeg/jpg files only.", "jPEG", "jpg");
        picChooser.setFileFilter(picFilter);
        int returnVal = picChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String pic = picChooser.getSelectedFile().getAbsolutePath();
            return pic;
        }
        return "";
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
        titleJLabel = new javax.swing.JLabel();
        iconJLabel = new javax.swing.JLabel();
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
        playerPicJButton = new javax.swing.JButton();
        playerPicJTextField = new javax.swing.JTextField();
        removePlayerPicJButton = new javax.swing.JButton();
        controlPanel = new javax.swing.JPanel();
        updateJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        clearJMenuItem = new javax.swing.JMenuItem();
        cancelJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        titleJLabel.setFont(new java.awt.Font("Tahoma", 2, 32)); // NOI18N
        titleJLabel.setForeground(new java.awt.Color(51, 0, 0));
        titleJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GolferData/golficon.jpeg"))); // NOI18N
        titleJLabel.setText("Golf Database Data Input");

        iconJLabel.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        iconJLabel.setForeground(new java.awt.Color(51, 0, 0));
        iconJLabel.setToolTipText("Buckingham Fountain");

        org.jdesktop.layout.GroupLayout titleJPanelLayout = new org.jdesktop.layout.GroupLayout(titleJPanel);
        titleJPanel.setLayout(titleJPanelLayout);
        titleJPanelLayout.setHorizontalGroup(
            titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(titleJPanelLayout.createSequentialGroup()
                .add(49, 49, 49)
                .add(iconJLabel)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, titleJPanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .add(titleJLabel)
                .add(24, 24, 24))
        );
        titleJPanelLayout.setVerticalGroup(
            titleJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, titleJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(titleJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 56, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(iconJLabel)
                .addContainerGap())
        );

        getContentPane().add(titleJPanel, java.awt.BorderLayout.NORTH);

        firstNameJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        firstNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        firstNameJLabel.setText("First Name*:");

        firstNameJTextField.setEditable(false);
        firstNameJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        firstNameJTextField.setToolTipText("Press Enter to update");
        firstNameJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameJTextFieldActionPerformed(evt);
            }
        });

        lastNameJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lastNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lastNameJLabel.setText("Last Name*:");

        lastNameJTextField.setEditable(false);
        lastNameJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lastNameJTextField.setToolTipText("Press Enter to update");
        lastNameJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameJTextFieldActionPerformed(evt);
            }
        });
        lastNameJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lastNameJTextFieldFocusGained(evt);
            }
        });

        emailJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emailJLabel.setText("Email Address:");

        emailJTextField.setEditable(false);
        emailJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        emailJTextField.setToolTipText("Press Enter to update");
        emailJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailJTextFieldActionPerformed(evt);
            }
        });
        emailJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                emailJTextFieldFocusGained(evt);
            }
        });

        phoneJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        phoneJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        phoneJLabel.setText("Phone Numer*:");

        phoneJTextField.setEditable(false);
        phoneJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        phoneJTextField.setToolTipText("Enter a valid US phone number and Press Enter to update");
        phoneJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneJTextFieldActionPerformed(evt);
            }
        });
        phoneJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneJTextFieldFocusGained(evt);
            }
        });

        levelJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        levelJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        levelJLabel.setText("Level*:");

        levelJTextField.setEditable(false);
        levelJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        levelJTextField.setToolTipText("Enter a non-negative integer for team rank and Press Enter to update");
        levelJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelJTextFieldActionPerformed(evt);
            }
        });
        levelJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                levelJTextFieldFocusGained(evt);
            }
        });

        playerPicJButton.setMnemonic('A');
        playerPicJButton.setText("Add Picture");
        playerPicJButton.setToolTipText("Add a player portrait");
        playerPicJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerPicJButtonActionPerformed(evt);
            }
        });

        playerPicJTextField.setEditable(false);

        removePlayerPicJButton.setMnemonic('R');
        removePlayerPicJButton.setText("Remove Picture");
        removePlayerPicJButton.setToolTipText("Remove player portrait");
        removePlayerPicJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePlayerPicJButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout displayJPanelLayout = new org.jdesktop.layout.GroupLayout(displayJPanel);
        displayJPanel.setLayout(displayJPanelLayout);
        displayJPanelLayout.setHorizontalGroup(
            displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(displayJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(displayJPanelLayout.createSequentialGroup()
                        .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(firstNameJLabel)
                            .add(lastNameJLabel)
                            .add(emailJLabel)
                            .add(phoneJLabel))
                        .add(18, 18, 18)
                        .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(displayJPanelLayout.createSequentialGroup()
                                .add(phoneJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(levelJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(18, 18, 18)
                                .add(levelJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(lastNameJTextField)
                            .add(firstNameJTextField)
                            .add(emailJTextField))
                        .add(21, 21, 21))
                    .add(displayJPanelLayout.createSequentialGroup()
                        .add(playerPicJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(playerPicJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 207, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removePlayerPicJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 134, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        displayJPanelLayout.setVerticalGroup(
            displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(displayJPanelLayout.createSequentialGroup()
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(displayJPanelLayout.createSequentialGroup()
                        .add(1, 1, 1)
                        .add(firstNameJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(lastNameJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(emailJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(displayJPanelLayout.createSequentialGroup()
                        .add(7, 7, 7)
                        .add(firstNameJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lastNameJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(emailJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(phoneJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(playerPicJButton)
                    .add(playerPicJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(removePlayerPicJButton))
                .add(9, 9, 9))
        );

        getContentPane().add(displayJPanel, java.awt.BorderLayout.CENTER);

        controlPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setMinimumSize(new java.awt.Dimension(299, 45));
        controlPanel.setLayout(new java.awt.GridLayout(1, 5, 5, 5));

        updateJButton.setBackground(new java.awt.Color(204, 255, 204));
        updateJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        updateJButton.setMnemonic('A');
        updateJButton.setText("Add/Update");
        updateJButton.setToolTipText("Add new Golfer/Update Existing Golfer");
        updateJButton.setMinimumSize(new java.awt.Dimension(57, 45));
        updateJButton.setPreferredSize(new java.awt.Dimension(57, 35));
        updateJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(updateJButton);

        cancelJButton.setBackground(new java.awt.Color(204, 255, 204));
        cancelJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelJButton.setMnemonic('x');
        cancelJButton.setText("Cancel");
        cancelJButton.setToolTipText("Close Window");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(cancelJButton);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        fileJMenu.setText("File");

        clearJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        clearJMenuItem.setText("Clear");
        clearJMenuItem.setToolTipText("Clears all fields");
        clearJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(clearJMenuItem);

        cancelJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        cancelJMenuItem.setText("Cancel");
        cancelJMenuItem.setToolTipText("Close Window");
        cancelJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(cancelJMenuItem);

        jMenuBar1.add(fileJMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action Event fired when enter is clicked while field has focus.
     * @param evt event update JButton clicked
     * @see #confirmChanges() 
     */
    private void firstNameJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameJTextFieldActionPerformed
        updateJButtonActionPerformed(evt);
    }//GEN-LAST:event_firstNameJTextFieldActionPerformed

    /**
     * Action Event fired when enter is clicked while field has focus.
     * @param evt event update JButton clicked
     * @see #confirmChanges() 
     */
    private void lastNameJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameJTextFieldActionPerformed
        updateJButtonActionPerformed(evt);
    }//GEN-LAST:event_lastNameJTextFieldActionPerformed

    /**
     * Action Event fired when enter is clicked while field has focus.
     * @param evt event update JButton clicked
     * @see #confirmChanges() 
     */
    private void emailJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailJTextFieldActionPerformed
        updateJButtonActionPerformed(evt);
    }//GEN-LAST:event_emailJTextFieldActionPerformed

    /**
     * Action Event fired when enter is clicked while field has focus.
     * @param evt event update JButton clicked
     * @see #confirmChanges() 
     */
    private void phoneJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneJTextFieldActionPerformed
        updateJButtonActionPerformed(evt);
    }//GEN-LAST:event_phoneJTextFieldActionPerformed

    /**
     * Action Event fired when enter is clicked while field has focus.
     * @param evt event update JButton clicked
     * @see #confirmChanges() 
     */
    private void levelJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelJTextFieldActionPerformed
        updateJButtonActionPerformed(evt);
    }//GEN-LAST:event_levelJTextFieldActionPerformed

    /**
     * Action Event fired when update/add JButton is clicked.
     * @param evt event update/add JButton clicked
     * @see #confirmChanges() 
     */
    private void updateJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateJButtonActionPerformed
        confirmChanges();
    }//GEN-LAST:event_updateJButtonActionPerformed

    /**
     * Action Event fired when cancel JButton is clicked. Exits window.
     * @param evt event cancel JButton clicked
     * @see GolfersGUI.#quitJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelJButtonActionPerformed

    /**
     * Action Event fired when clear JMenuItem is clicked. Clears all text fields.
     * @param evt event clear JMenuItem clicked
     * @see GolfersGUI.#clearAll()  
     */
    private void clearJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJMenuItemActionPerformed
        firstNameJTextField.setText(null);
        lastNameJTextField.setText(null);
        emailJTextField.setText(null);
        phoneJTextField.setText(null);
        levelJTextField.setText(null);
        playerPicJTextField.setText(null);
    }//GEN-LAST:event_clearJMenuItemActionPerformed

    /**
     * Action Event fired when cancel JMenuItem is clicked.
     * @param evt event cancel JMenuItem clicked
     * @see #cancelJButtonActionPerformed(java.awt.event.ActionEvent)  
     */
    private void cancelJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJMenuItemActionPerformed
        dispose();
    }//GEN-LAST:event_cancelJMenuItemActionPerformed

    /**
     * Action Event fired when Add Picture JButton is clicked.
     * @param evt event add picture JButton clicked
     * @see GolfersGUI.#setIcon(java.lang.String)   
     */
    private void playerPicJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerPicJButtonActionPerformed
       playerPicJTextField.setText(setPic());
    }//GEN-LAST:event_playerPicJButtonActionPerformed

    /**
     * Action Event fired when remove picture JButton is clicked.
     * @param evt event cancel JButton clicked
     * @see #playerPicJButtonActionPerformed(java.awt.event.ActionEvent)   
     */
    private void removePlayerPicJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePlayerPicJButtonActionPerformed
        playerPicJTextField.setText(null);
    }//GEN-LAST:event_removePlayerPicJButtonActionPerformed

    /**
     * Action Event fired when focus is gained in field. Highlights text in
     * field.
     * @param evt event field focus gained
     * @see #emailJTextFieldFocusGained(java.awt.event.FocusEvent)  
     */
    private void lastNameJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lastNameJTextFieldFocusGained
        lastNameJTextField.selectAll();
    }//GEN-LAST:event_lastNameJTextFieldFocusGained

    /**
     * Action Event fired when focus is gained in field. Highlights text in
     * field.
     * @param evt event field focus gained
     * @see #phoneJTextFieldFocusGained(java.awt.event.FocusEvent)   
     */
    private void emailJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailJTextFieldFocusGained
        emailJTextField.selectAll();
    }//GEN-LAST:event_emailJTextFieldFocusGained

    /**
     * Action Event fired when focus is gained in field. Highlights text in
     * field.
     * @param evt event field focus gained
     * @see #levelJTextFieldFocusGained(java.awt.event.FocusEvent)  
     */
    private void phoneJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneJTextFieldFocusGained
        phoneJTextField.selectAll();
    }//GEN-LAST:event_phoneJTextFieldFocusGained

    /**
     * Action Event fired when focus is gained in field. Highlights text in
     * field.
     * @param evt event field focus gained
     * @see #lastNameJTextFieldFocusGained(java.awt.event.FocusEvent)  
     */
    private void levelJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_levelJTextFieldFocusGained
        levelJTextField.selectAll();
    }//GEN-LAST:event_levelJTextFieldFocusGained

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
            java.util.logging.Logger.getLogger(AddEditFormJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddEditFormJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddEditFormJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddEditFormJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddEditFormJDialog dialog = new AddEditFormJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelJButton;
    private javax.swing.JMenuItem cancelJMenuItem;
    private javax.swing.JMenuItem clearJMenuItem;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayJPanel;
    private javax.swing.JLabel emailJLabel;
    private javax.swing.JTextField emailJTextField;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JLabel firstNameJLabel;
    private javax.swing.JTextField firstNameJTextField;
    private javax.swing.JLabel iconJLabel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lastNameJLabel;
    private javax.swing.JTextField lastNameJTextField;
    private javax.swing.JLabel levelJLabel;
    private javax.swing.JTextField levelJTextField;
    private javax.swing.JLabel phoneJLabel;
    private javax.swing.JTextField phoneJTextField;
    private javax.swing.JButton playerPicJButton;
    private javax.swing.JTextField playerPicJTextField;
    private javax.swing.JButton removePlayerPicJButton;
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel titleJPanel;
    private javax.swing.JButton updateJButton;
    // End of variables declaration//GEN-END:variables
}
