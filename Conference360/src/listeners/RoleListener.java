package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ConferenceClient;
import model.Job.Title;
import view.AuthorConferenceView;
import view.ClientGeneralView;
import view.ClientGui;
import view.PCConferenceView;
import view.ReviewerView;
import view.SubprogramView;
import view.ViewPanel;

/**
 * This class represents the action of selecting a different role within a specific Conferece.
 * @author BrentYoung
 *
 */
public class RoleListener implements ActionListener {

	
	private ClientGui theFrame;
	private ConferenceClient client;
	private Title myTitle;
	
	public RoleListener(ClientGui theFrame, ConferenceClient theClient, Title theTitle) {
		this.theFrame = theFrame;
		this.client = theClient;
		myTitle = theTitle;
	}
	
	/**
	 * This method uses the Title of this listener to determine which view to show.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ViewPanel theView;
		if (myTitle == Title.AUTHOR) {
			theView = new AuthorConferenceView(client, theFrame); 
		} else if (myTitle == Title.REVIEWER) {
			theView = new ReviewerView(client, theFrame);
		} else if (myTitle == Title.PROGRAMCHAIR) {
			theView = new PCConferenceView(client, theFrame, client.getCurrentConference().getName());
		} else if (myTitle == Title.SUBPROGRAMCHAIR) {
			theView = new SubprogramView(client, theFrame, client.getCurrentConference().getName());
		} else {
			theView = new ClientGeneralView(client, theFrame);
		}
		theFrame.moveToComp(theView);
	}

}
