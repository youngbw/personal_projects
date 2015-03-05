package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import model.ConferenceClient;
import view.AuthorConferenceView;
import view.ClientGui;

/**
 * This class represents the action of Removing a Paper from a specific Conference.
 * @author BrentYoung
 *
 */
public class MyRemovePaperListener implements ActionListener {
	/**
	 * Reference to the client GUI frame.
	 */
	private ClientGui theFrame;
	/**
	 * Reference to the conference client.
	 */
	private ConferenceClient theClient;
	/**
	 * Reference to a author conference view.
	 */
	private AuthorConferenceView theView;
	
	/**
	 * Stores references to the client gui frame, the client, and a AuthorConferenceView
	 * @param theFrame
	 * @param theClient
	 * @param theView
	 */
	public MyRemovePaperListener(ClientGui theFrame, ConferenceClient theClient, AuthorConferenceView theView) {
		this.theFrame = theFrame;
		this.theClient = theClient;
		this.theView = theView;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (theView.getCurrentlySelectedTitle() != null) {
			
			int result = JOptionPane.showConfirmDialog(theFrame, "Are you sure you want to delete this paper"
					+ "\nfrom this conference?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.YES_OPTION) {
				theClient.deletePaper(theView.getCurrentlySelectedTitle());
				theFrame.moveToComp(new AuthorConferenceView(theClient, theFrame));
			}
			
		}
		
	}

}
