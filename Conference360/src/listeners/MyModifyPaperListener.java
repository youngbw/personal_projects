package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.Conference;
import model.ConferenceClient;
import model.Manuscript;
import view.AuthorConferenceView;
import view.ClientGui;

/**
 * This class represents the modification of an already submitted paper, either by replacing it or
 * ultimately removing it.
 * @author BrentYoung
 *
 */
public class MyModifyPaperListener implements ActionListener {
	/**
	 * Reference to the client GUI frame.
	 */
	private ClientGui theFrame;
	/**
	 * Reference to the conference client.
	 */
	private ConferenceClient theClient;
	/**
	 * Reference to a AuthorConferenceView.
	 */
	private AuthorConferenceView theView;
	
	/**
	 * Stores references to the frame, the client, and a AuthorConferenceView.
	 */
	public MyModifyPaperListener(ClientGui theFrame, ConferenceClient theClient,
			AuthorConferenceView theView) {
		this.theFrame = theFrame;
		this.theClient = theClient;
		this.theView = theView;
	}
	
	/**
	 * This method allows the user to choose a paper to add, and removes the selected paper. If the user cancels
	 * it asks if they would still like to remove the paper.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//add
		if (this.theClient.getCurrentConference().getSubmissionDeadline().after(new Date())) {
			
			//Choose file
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showOpenDialog(theFrame);
			
			//Once chosen create manuscript and send it to client to get added to the conference and user lists
			if (option == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				Manuscript m = new Manuscript(theClient.getCurrentUser().getEmail(), f.getName(), f);
				m.setTitle(JOptionPane.showInputDialog(theFrame, "Please enter the title of your manuscript", "Manuscript Submission", JOptionPane.DEFAULT_OPTION));
				theClient.submitPaper(f, m);
				AuthorConferenceView next = new AuthorConferenceView(theClient, theFrame);
				next.setLabelText("Successfully uploaded " + m.getTitle());
				theFrame.moveToComp(next);
				remove();
				
			} else {
				int result = JOptionPane.showConfirmDialog(theFrame, "Would you still like to remove the selected paper?", "Remove Paper", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					remove();
				}
			}
			theClient.save();
		} else {
			JOptionPane.showMessageDialog(theFrame, "Cannot submit a paper after the submission deadline has passed.", "Paper Submission Denied", JOptionPane.ERROR_MESSAGE);
		}
		

	}
	
	/**
	 * Deletes a paper.
	 */
	private void remove() {
		//remove
		if (theView.getCurrentlySelectedTitle() != null) {
			
			int result = JOptionPane.showConfirmDialog(theFrame, "Are you sure you want to delete" 
					+ theView.getCurrentlySelectedTitle()
					+ "\nfrom this conference?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.YES_OPTION) {
				theClient.deletePaper(theView.getCurrentlySelectedTitle());
				theFrame.moveToComp(new AuthorConferenceView(theClient, theFrame));
			}
			
		}
	}

}
