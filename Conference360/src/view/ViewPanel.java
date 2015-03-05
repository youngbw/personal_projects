package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import listeners.BackButtonActionListener;
import listeners.MyButtonMouseListener;
import listeners.RoleListener;
import model.ConferenceClient;
import model.Job;
import model.Job.Title;
import model.User;

/**
 * This class is the Template panel for all of the Views in this program. Represents
 * the exterior portion of all views and facilitates the layout of all views in this program.
 * @author BrentYoung
 *
 */
@SuppressWarnings("serial")
public class ViewPanel extends JPanel {

	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SIZE = KIT.getScreenSize();
	
	private ArrayList<RoleChooserRadioButton> radioButtons;
	protected ConferenceClient client;
	protected ClientGui theFrame;
	protected String currrentSelectedTitle;
	
	public ViewPanel(ConferenceClient theClient, ClientGui theFrame, String title) {
		this.client = theClient;
		this.theFrame = theFrame;
		radioButtons = new ArrayList<RoleChooserRadioButton>();
		setup(title);
	}
	
	/**
	 * This method is to initialize and maintain the layout of the View, setting up a 
	 * template for the program overall look.
	 * @param title the String to be displayed as the title of the screen currently being viewed.
	 */
	private void setup(String title) {
		this.setSize(SIZE.width / 2, SIZE.height / 2);
		this.setPreferredSize(new Dimension(SIZE.width / 2, SIZE.height / 2));
		this.setLayout(new BorderLayout());
		
		//NORTH PANEL
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setLayout(new BorderLayout());
		northPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 10));
		
		//TITLE
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		JPanel titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 10));
		titlePanel.setBackground(Color.WHITE);
		GridBagLayout myGridLayout = new GridBagLayout();
		myGridLayout.setConstraints(this, new GridBagConstraints());
		titlePanel.setLayout(myGridLayout);
		titlePanel.add(titleLabel);
		
		//BACK BUTTON
		if (!(this instanceof LoginView)) {
			JButton backButton = new JButton("BACK");
			setButtonProperties(backButton);
			backButton.setPreferredSize(new Dimension(this.getWidth() / 10, this.getHeight() / 10));
			backButton.addActionListener(new BackButtonActionListener(this.theFrame));
			northPanel.add(backButton, BorderLayout.WEST);
		} else {
			JPanel fillerPanel = new JPanel();
			fillerPanel.setBackground(Color.WHITE);
			fillerPanel.setPreferredSize(new Dimension(this.getWidth() / 10, this.getHeight() / 10));
			northPanel.add(fillerPanel, BorderLayout.WEST);
		}
		
		
		//CONFERENCE ADDITION
		if (this instanceof ClientGeneralView) {
			
			JButton addConferenceButton = new JButton("ADD");
			setButtonProperties(addConferenceButton);
			addConferenceButton.setPreferredSize(new Dimension(this.getWidth() / 10, this.getHeight() / 10));
			addConferenceButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ConferenceDialog dialog = new ConferenceDialog(theFrame, client);
					
				}
			});
			northPanel.add(addConferenceButton, BorderLayout.EAST);
			
		} else {
			JPanel fillerPanel = new JPanel();
			fillerPanel.setBackground(Color.WHITE);
			fillerPanel.setPreferredSize(new Dimension(this.getWidth() / 10, this.getHeight() / 10));
			northPanel.add(fillerPanel, BorderLayout.EAST);
		}
		
		
		northPanel.add(titlePanel, BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
		
		
		//SOUTH PANEL
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 10));
		southPanel.setBackground(Color.WHITE);
		southPanel.setLayout(new BorderLayout());
		southPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		User currentUser = client.getCurrentUser();
		JLabel roleLabel;
		if (currentUser != null) {
			roleLabel = new JLabel(currentUser.getName() + " currently logged in as: ");
		} else {
			roleLabel = new JLabel(" You haven't logged in yet.");
		}
		roleLabel.setFont(new Font("Times Roman", Font.PLAIN, 12));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension((int)(this.getWidth() * .75), southPanel.getPreferredSize().height));
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.add(roleLabel, BorderLayout.WEST);
		ButtonGroup roleGroup = new ButtonGroup();
		
		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setBackground(Color.WHITE);
		
		if (currentUser != null && !(this instanceof ClientGeneralView)) { // && !(theFrame.getCurrentComp() instanceof LoginView) && !(theFrame.getCurrentComp() instanceof ClientGeneralView)) {
			for (Job j: currentUser.getMyJobs()) {
				if (j.getConferenceID() == client.getCurrentConference().getMyID()) {
					
					for (Title t: j.getTitles()) {
						
						RoleChooserRadioButton button = new RoleChooserRadioButton(theFrame, client, t);
						button.setFont(new Font("Times Roman", Font.PLAIN, 10));
						button.addActionListener(new RoleListener(theFrame, client, t));
						roleGroup.add(button);
						radioButtons.add(button);
						centerButtonPanel.add(button, BorderLayout.CENTER);
					}
					
				}
				
			}
			//PSUEDO button so that there is something to select
			RoleChooserRadioButton button = new RoleChooserRadioButton(theFrame, client, Title.GENERAL);
			button.setFont(new Font("Times Roman", Font.PLAIN, 10));
			button.addActionListener(new RoleListener(theFrame, client, Title.GENERAL));
			roleGroup.add(button);
			radioButtons.add(button);
		}
		
		buttonPanel.add(centerButtonPanel, BorderLayout.CENTER);
		southPanel.add(buttonPanel, BorderLayout.WEST);
		
		JPanel logoutPanel = new JPanel();
		logoutPanel.setBackground(Color.WHITE);
		logoutPanel.setLayout(new GridLayout(2, 1));
		logoutPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width / 5, southPanel.getPreferredSize().height));
		
		JButton logoutButton = new JButton("logout");
		setButtonProperties(logoutButton);
		logoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				client.resetUser();
				theFrame.resetBackLog();
				theFrame.moveToComp(new LoginView(client, theFrame));
				
			}
		});
		
		logoutPanel.add(logoutButton);
		
		//A button to navigate back to the ClientGeneralView
		JButton homeButton = new JButton("Home");
		setButtonProperties(homeButton);
		homeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (client.getCurrentUser() != null && !(theFrame.getCurrentComp() instanceof LoginView)) {
					theFrame.resetBackLog();
					theFrame.moveToComp(new ClientGeneralView(client, theFrame));
				}
			}
		});
		logoutPanel.add(homeButton);
		
		southPanel.add(logoutPanel, BorderLayout.EAST);
		
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * This method selects the correct RoleChooserRadioButton in correspondence with 
	 * the user current role.
	 * @param theTitle
	 */
	public void setRoleSelected(Title theTitle) {
		for (int i = 0; i < radioButtons.size(); i++) {
			if (radioButtons.get(i).getMyTitle() == theTitle) {
				radioButtons.get(i).setSelected(true);
			} 
		}
	}
	
	/**
	 * This method serves as a stylistic guideline for all buttons added to the 
	 * WEST side of the panel.
	 * @param button the button to be modified
	 */
	protected void setButtonProperties(JButton button) {
		button.setBackground(Color.WHITE);
		button.setBorder(new EmptyBorder(0, 0, 0, 0));
		button.setFont(new Font("Times Roman", Font.PLAIN, 12));
		button.addMouseListener(new MyButtonMouseListener());
	}

	public void setSelectedPaperTitle(String title) {
		this.currrentSelectedTitle = title;
	}
	
	public String getCurrentlySelectedTitle() {
		return this.currrentSelectedTitle;
	}
}
