package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ConferenceClient;
import model.Manuscript;
import model.Job.Title;
import view.ClientGui;
import view.PCConferenceView;
import view.PCManuscriptView;

public class PCDetailListener implements ActionListener {
	/**
	 * Reference to a PCConferenceView
	 */
	private PCConferenceView theView;
	/**
	 * Reference to the conference client.
	 */
	private ConferenceClient theClient;
	/**
	 * Reference to the client GUI frame.
	 */
	private ClientGui theFrame;
	
	/**
	 * Stores references to the frame, the client, and the view.
	 * @param theFrame
	 * @param theClient
	 * @param theView
	 */
	public PCDetailListener(ClientGui theFrame, ConferenceClient theClient, PCConferenceView theView) {
		this.theFrame = theFrame;
		this.theClient = theClient;
		this.theView = theView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		Manuscript m = theView.getSelectedManuscript();
		if (theClient.getCurrentUser().getCurrentlyViewedAs() == Title.PROGRAMCHAIR) {
			if (theFrame.getCurrentComp() instanceof PCConferenceView) theFrame.moveToComp(new PCManuscriptView(theClient, theFrame, m));
		}
	}
}
