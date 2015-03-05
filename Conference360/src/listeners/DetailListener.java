package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.ConferenceClient;
import model.Job.Title;
import model.Manuscript;
import model.Manuscript.Decision;
import view.AuthorConferenceView;
import view.AuthorManuscriptView;
import view.ClientGui;
import view.PCConferenceView;
import view.PCManuscriptView;
import view.ViewPanel;

/**
 * This class represents the listener that facilitates the movement from a conference view 
 * to a manuscript view. Shows more manuscript detail.
 * @author BrentYoung
 *
 */
public class DetailListener implements ActionListener {

	
	private ViewPanel theView;
	private ConferenceClient theClient;
	private ClientGui theFrame;
	
	/**
	 * Parameterized constructor for DetailListener, stores references to the
	 * clientGUI frame, the Conference client, and the appropriate view.
	 * @param theFrame
	 * @param theClient
	 * @param theView
	 */
	public DetailListener(ClientGui theFrame, ConferenceClient theClient, ViewPanel theView) {
		this.theFrame = theFrame;
		this.theClient = theClient;
		this.theView = theView;
	}
	
	/**
	 * This method will only allow the manuscript view to be passed if the acceptance Decision
	 * is anything other than UNDECIDED.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Manuscript m = theClient.getCurrentUser().getSpecificPaper(theView.getCurrentlySelectedTitle());
		if (m != null) {
			if (m.getStatus() != Decision.UNDECIDED) {
				if (theFrame.getCurrentComp() instanceof AuthorConferenceView) theFrame.moveToComp(new AuthorManuscriptView(theFrame, theClient, m));
			} else {
				JOptionPane.showMessageDialog(theFrame, "Cannot view Reviews until a Deicision \nhas been made by the Program Chair for this Conference",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}
