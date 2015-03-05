package listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import view.ClientGui;
import model.ConferenceClient;

/**
 * This class represents the action of acquiring the review form as well as
 * submitted a review.
 * 
 * @author MariaMills
 * @edited BrentYoung
 */
public class MyReviewListener implements ActionListener, MouseListener {	

	private ConferenceClient theClient;
	private ClientGui theFrame;
	private String paperName;
	private String score;
	private String reviewer;
	
	/**
	 * This constructor accepts a ConferenceClient object and it's GUI
	 * frame. 
	 * 
	 * @author mmills
	 * @param client the conference system
	 * @param frame the GUI main frame
	 */
	public MyReviewListener(ConferenceClient client, ClientGui frame) {
		
		theClient = client;
		theFrame = frame;
		score = "";		
	}
	
	/**
	 * This second constructor accepts in addition two strings, one for the title of 
	 * the paper and the other for the name of the reviewer.
	 * 
	 * @param title paper name
	 * @param reviewer person's name
	 * @param client system
	 * @param frame GUI frame
	 */
	public MyReviewListener(String title, String reviewer, ConferenceClient client, 
			ClientGui frame) {
		
		this(client, frame);
		paperName = title;
		this.reviewer = reviewer;
	}

	@Override
	/**
	 * This method handles the action events passed in from the reviewer view
	 * class.  
	 * 
	 * @author mmills
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals("Review Form")) {
			
			Desktop desk = Desktop.getDesktop();
	        File reviewForm = new File("/files/RecommendationForm.txt");
	        reviewForm.exists();
	        
	        try {
				desk.open(reviewForm);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(theFrame, "Could not open the file", "File Error", JOptionPane.ERROR_MESSAGE);
			}        
	        
		} else if(arg0.getActionCommand().equals("Submit Review")) {
			
			JFileChooser chooser = new JFileChooser();			
			
			int option = chooser.showOpenDialog(theFrame);
			
			if (option == JFileChooser.APPROVE_OPTION) {
				
				File review = chooser.getSelectedFile();				
				theClient.submitReview(paperName, review, score, reviewer);
				
				theClient.save();
			}
			
		} else if(arg0.getActionCommand().equals("Get Review")) { 		

		} else {
			
			JComboBox obj = (JComboBox) arg0.getSource();
			String choice = obj.getSelectedItem().toString();			
			score = choice;		
		}		
	}



	@Override
	/**
	 * This method handles the mouse events that are passed in
	 * from the reviewer view class.
	 * 
	 * @author mmills
	 */
	public void mouseClicked(MouseEvent arg0) {
			
		Desktop desk = Desktop.getDesktop();
		File thePaper = theClient.getCurrentConference().getPaper(paperName).getFile();
        
        try {
			desk.open(thePaper);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(theFrame, "Could not open the file", "File Error", JOptionPane.ERROR_MESSAGE);
		}   		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
