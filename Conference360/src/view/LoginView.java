package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.ConferenceClient;

/**
 * Displays the Login screen for our program.
 * 
 * @author Keith Foreman
 */
@SuppressWarnings("serial")
public class LoginView extends ViewPanel {

	private GridBagConstraints c = new GridBagConstraints();
	private JPanel loginPanel;
	private JButton loginButton;
	private JButton regButton;
	private JTextField userTF;
	private JPasswordField passF;
	
	private static String title = "View Perspectives";
	
	public LoginView(ConferenceClient theClient, ClientGui theFrame) {
		super(theClient, theFrame, title);
		setup();
	}
	
	//Not done, just filling it in as I have time
	private void setup() {
		JLabel emailLabel = new JLabel("Email address:");
		JLabel passLabel = new JLabel("Password:");
		c = new GridBagConstraints();
		loginPanel = new JPanel(new GridBagLayout());
		loginPanel.setBackground(Color.WHITE);
		loginButton = new JButton("Login");
		regButton = new JButton("Register");
		userTF = new JTextField(25);
		
		
		passF = new JPasswordField(25);
		passF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginButton.doClick();
				
			}
		});
		loginButton.addActionListener(new LoginListener());
		regButton.addActionListener(new RegListener());
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		loginPanel.add(emailLabel, c);
		c.gridy++;
		loginPanel.add(passLabel, c);
		c.gridx++;
		c.gridy--;
		loginPanel.add(userTF, c);
		c.gridy++;
		loginPanel.add(passF, c);
		c.gridx++;
		c.gridy--;
		loginPanel.add(loginButton, c);
		c.gridy++;
		loginPanel.add(regButton, c);
		theFrame.requestFocus();
		this.add(loginPanel);
		userTF.requestFocus();
	}
	
	private class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent a) {
			String email = userTF.getText().trim();
			String pass = new String(passF.getPassword()).trim();
			
			if (client.verifyUser(email, pass)) {
				theFrame.moveToComp(new ClientGeneralView(client, theFrame));
			} else {
				JOptionPane.showMessageDialog(theFrame, "Invalid User, please register or try again.",
						"Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class RegListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			JTextField nameTF = new JTextField(25);
			JTextField emailTF = new JTextField(25);
			JPasswordField passTF = new JPasswordField(25);
			
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
			
			myPanel.add(new JLabel("Name: "));
			myPanel.add(nameTF);
			myPanel.add(Box.createHorizontalStrut(50));
			myPanel.add(new JLabel("Email address: "));
			myPanel.add(emailTF);
			myPanel.add(Box.createHorizontalStrut(50));
			myPanel.add(new JLabel("Password: "));
			myPanel.add(passTF);
			
			int result = JOptionPane.showConfirmDialog(null, myPanel, "Please enter info:", JOptionPane.OK_CANCEL_OPTION);
			String name = nameTF.getText().trim();
			String email = emailTF.getText().trim();
			String pass = new String(passTF.getPassword()).trim();
			if (result == JOptionPane.OK_OPTION && !name.equals("") && !email.equals("") && !pass.equals("") 
					&& !client.verifyUser(email, pass)) {
				client.registerUser(name, email, pass);
				theFrame.moveToComp(new ClientGeneralView(client, theFrame));
				//save state
				client.save();
			} else if (result == JOptionPane.OK_OPTION && client.isUser(email)){
				JOptionPane.showMessageDialog(theFrame, "Already a registered user.",
						"Error!", JOptionPane.ERROR_MESSAGE);
			} else if (result == JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(theFrame, "Please fill out all fields.",
						"Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
}
