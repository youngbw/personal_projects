package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import listeners.SubPCListener;
import model.ConferenceClient;
import model.Job;
import model.Manuscript;
import model.Conference;
import model.Manuscript.Decision;


/**
 * This class extends ViewPanel to display the papers the user was assigned to 
 * have reviewed and make a recommendation to the program chair in the conference
 * they selected.  In this view they are able to access the recommendation form
 * and submit their recommendation for the paper.
 * 
 *@author mmills
 *@author Brent Young and Keith Foreman //based GUI off what they did in other classes.
 */
@SuppressWarnings("serial")
public class SubprogramView extends ViewPanel {
	
	private static final int NUM_BUTTONS = 5;
	
	private JPanel center;
	private JPanel centerPanel2;
	private JLabel southLabel;
	private GridBagLayout center2Layout;
	private GridBagConstraints constraints;
	
	private Conference myConference;
	private ConferenceClient theClient;
	private ClientGui theFrame;
	private int confID;

	/**
	 * The constructor accepts a Client and GUI objects so that the
	 * papers needed in the view can be accessed.
	 * 
	 * @param client the Conference system
	 * @param frame the GUI main frame
	 */
	public SubprogramView(ConferenceClient theClient, ClientGui frame, String title) {
		
		super(theClient, frame, title);
		
		center2Layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		constraints.weightx = .5f;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridheight = 10;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		this.theClient = theClient;
		theFrame = frame;
		confID = theClient.getCurrentConference().getMyID();

		
		setup();
	}
	
	/**
	 * This method set up the GUI in this view.
	 */
	private void setup() {
		
		//For viewPanel center
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(this.getWidth() - (this.getWidth() / 15 * 2),
				this.getHeight() - (this.getHeight() / 15 + this.getHeight() / 20)));
		
		//Center panel for the Center panel created above with BorderLayout
		centerPanel2 = new JPanel();
		centerPanel2.setBackground(Color.WHITE);
		centerPanel2.setLayout(center2Layout);
		
		JScrollPane pane = new JScrollPane(centerPanel2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.getVerticalScrollBar().setUnitIncrement(16);
		
		//gets papers based on this conference only and as a reviewer
		Set<Manuscript> getPapers = client.getCurrentUser().getJobPapers(confID, Job.Title.SUBPROGRAMCHAIR);
		
		if(getPapers != null) {

			for (Manuscript m: getPapers) {				
				this.addPaperToList(m);
			}
		}
		
		//For centerPanel SOUTH
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width, centerPanel.getPreferredSize().height / 20));
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		southLabel = new JLabel("", SwingConstants.CENTER);
		infoPanel.add(southLabel, BorderLayout.CENTER);
		centerPanel.add(infoPanel, BorderLayout.SOUTH);
		
		//For viewPanel WEST -- CAN ADD MORE COMPONENTS HERE IF NEEDED
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		GridLayout grid = new GridLayout(NUM_BUTTONS, 1);
		buttonPanel.setLayout(grid);
		buttonPanel.setPreferredSize(new Dimension(this.getWidth() / 6, this.getHeight() - (this.getHeight() / 20 + this.getHeight() / 15)));
		JButton getForm = new JButton("Get Form");
		setButtonProperties(getForm); 
		getForm.addActionListener(new SubPCListener(theClient, theFrame));
		buttonPanel.add(getForm);		
		
		this.add(buttonPanel, BorderLayout.WEST);
		centerPanel.add(pane, BorderLayout.CENTER);
		this.add(centerPanel, BorderLayout.CENTER);		
		
		this.revalidate();
	}
	
	/**
	 * This method sets the text in the south label of the frame
	 * based on the current user and their roles.
	 * 
	 * @param text text for south label
	 */
	public void setLabelText(String text) {
		southLabel.setText(text);
	}
	
	/**
	 * This method adds the paper the sub PC is assigned in the 
	 * selected conference.
	 * 
	 * @author mmills
	 * @author Brent/Keith //used their GUI
	 * @param thePaper an assigned paper
	 */
	public void addPaperToList(Manuscript thePaper) {
		
		JPanel paperPanel = new JPanel();
		paperPanel.setBackground(Color.WHITE);
		paperPanel.setBorder(new LineBorder(Color.BLACK));
		paperPanel.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight() / 20));
		paperPanel.setLayout(new GridLayout(1, 10));
		
		JLabel manuLabel = new JLabel(thePaper.getName() + " ... ");
		manuLabel.setHorizontalTextPosition(JLabel.LEFT);
		manuLabel.setPreferredSize(new Dimension(paperPanel.getWidth() / 2, paperPanel.getHeight()));
		paperPanel.add(manuLabel);
		
		
		String [] recommend =  {"Undecided", "Accept", "Reject", "Neutral"};
		
		JComboBox decisionBox = new JComboBox(recommend);
		decisionBox.addActionListener(new SubPCListener(theClient, theFrame, thePaper.getName(), theClient.getCurrentUser().getName()));
		decisionBox.setSelectedItem("Undecided");
		decisionBox.setEnabled(true);
		decisionBox.setEditable(false);
				
		String scaleSelected = (String) decisionBox.getSelectedItem();
		paperPanel.add(decisionBox);
		
		JButton submitRec = new JButton();
		submitRec.setText("Submit Recommendation");
		submitRec.setPreferredSize(new Dimension(paperPanel.getWidth() / 2, paperPanel.getHeight()));
		submitRec.addActionListener(new SubPCListener(theClient, theFrame));
		paperPanel.add(submitRec);
		
		centerPanel2.add(paperPanel, constraints);
	}	
}