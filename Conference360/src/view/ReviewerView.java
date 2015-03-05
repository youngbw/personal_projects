package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import listeners.MyReviewListener;
import model.ConferenceClient;
import model.Job;
import model.Manuscript;

@SuppressWarnings("serial")
/**
 * This class extends ViewPanel to display the papers the user was assigned to 
 * review in the conference they selected.  In this view they are able to 
 * access the review form, submit their score and review for the paper.
 * 
 *@author mmills
 *@author Brent Young and Keith Foreman //based GUI off what they did in other classes.
 */
public class ReviewerView extends ViewPanel {

	private static final int NUM_BUTTONS = 5;
	
	private JPanel centerPanel2;
	private JLabel southLabel;
	private GridBagLayout center2Layout;
	private GridBagConstraints constraints;
	
	private Collection<Manuscript> papers;
	private ArrayList<String> theScale;
	private ConferenceClient theClient;
	private ClientGui theFrame;
	private String reviewerID;
	private int confID;
	
	/**
	 * The constructor accepts a Client and GUI objects so that the
	 * papers needed in the view can be accessed.
	 * 
	 * @param client the Conference system
	 * @param frame the GUI main frame
	 */
	public ReviewerView(ConferenceClient client, ClientGui frame) {
		super(client, frame, client.getCurrentConference().getName());
		
		center2Layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		constraints.weightx = .5f;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridheight = 10;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		theClient = client;
		theFrame = frame;
		confID = client.getCurrentConference().getMyID();
		papers = client.getCurrentUser().getMyPapers();
		theScale = client.getCurrentConference().getScale();
		reviewerID = client.getCurrentUser().getName();
		
		setup();
	}
	
	/**
	 * This method set up the GUI in the view.
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
		Set<Manuscript> getPapers = client.getCurrentUser().getJobPapers(confID, Job.Title.REVIEWER);
		
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
		JButton reviewForm = new JButton("Review Form");
		setButtonProperties(reviewForm);
		reviewForm.addActionListener(new MyReviewListener(theClient, theFrame));
		buttonPanel.add(reviewForm);
		
		
		//dummy panels
		while (buttonPanel.getComponentCount() < NUM_BUTTONS) {
			JPanel dummy = new JPanel();
			dummy.setBackground(Color.WHITE);
			buttonPanel.add(dummy);
		}
		
		this.add(buttonPanel, BorderLayout.WEST);
		centerPanel.add(pane, BorderLayout.CENTER);
		this.add(centerPanel, BorderLayout.CENTER);
		this.revalidate();
	}
	
	/**
	 * This method sets the south label in the frame
	 * which is based on the current user and their
	 * roles.	 * 
	 * 
	 * @param text text needed in south label
	 */
	public void setLabelText(String text) {
		southLabel.setText(text);
	}
	
	/**
	 * This method adds the papers to the GUi with buttons needed
	 * for each paper to submit a review.
	 * 
	 * @author mmills
	 * @author Brent and Keith //I used the GUI format from their base in author
	 * @param thePaper a paper to be reviewed
	 */
	public void addPaperToList(Manuscript thePaper) {
		
		JPanel paperPanel = new JPanel();
		paperPanel.setBackground(Color.WHITE);
		paperPanel.setBorder(new LineBorder(Color.BLACK));
		paperPanel.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight() / 20));
		paperPanel.setLayout(new GridLayout(1, 10));
		
		JLabel manuLabel = new JLabel(thePaper.getTitle());
		manuLabel.addMouseListener(new MyReviewListener(thePaper.getTitle(), reviewerID, theClient, theFrame));
		manuLabel.setHorizontalTextPosition(JLabel.LEFT);
		manuLabel.setPreferredSize(new Dimension(paperPanel.getWidth() / 2, paperPanel.getHeight()));
		paperPanel.add(manuLabel);
		

		String [] scales = new String[theScale.size()];		
		scales = theScale.toArray(scales);		
		
		JComboBox scaleBox = new JComboBox(scales);
		scaleBox.addActionListener(new MyReviewListener(theClient, theFrame));
		scaleBox.addItem("Choose Score");
		scaleBox.setSelectedItem("Choose Score");
		scaleBox.setEnabled(true);
		scaleBox.setEditable(false);		
		
		String scaleSelected = (String) scaleBox.getSelectedItem();
		paperPanel.add(scaleBox);		
		
		JButton submitReview = new JButton();
		submitReview.setText("Submit Review");
		submitReview.setPreferredSize(new Dimension(paperPanel.getWidth() / 2, paperPanel.getHeight()));
		submitReview.addActionListener(new MyReviewListener(thePaper.getTitle(), reviewerID, theClient, theFrame));
		paperPanel.add(submitReview);
		
		JButton getReview = new JButton();
		getReview.setText("Get Review");
		getReview.setPreferredSize(new Dimension(paperPanel.getWidth() / 2, paperPanel.getHeight()));
		getReview.addActionListener(new MyReviewListener(thePaper.getTitle(), reviewerID, theClient, theFrame));
		paperPanel.add(getReview);
		
		centerPanel2.add(paperPanel, constraints);
	}
	
}