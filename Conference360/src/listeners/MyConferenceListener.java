package listeners;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JLabel;

import model.Conference;
import model.ConferenceClient;
import model.Job;
import model.Job.Title;
import view.AuthorConferenceView;
import view.ClientGui;
import view.ReviewerView;
import view.ViewPanel;

/**
 *
 * @author Brent Young
 *
 */
public class MyConferenceListener implements MouseListener {

	protected ClientGui theFrame;
	protected ConferenceClient client;
	private ViewPanel theView;
	
	public MyConferenceListener(ClientGui theFrame, ConferenceClient theClient, ViewPanel theView) {
		this.client = theClient;
		this.theView = theView;
		this.theFrame = theFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Conference c = new Conference("None", new Date(), 0);
		for (int i = 0; i < client.getMyConferences().size(); i++) {
			if (theView.getCurrentlySelectedTitle().equals(client.getMyConferences().get(i).getName())) {
				c = client.getMyConferences().get(i);
			}
		}
		client.setCurrentConference(c);
		theFrame.moveToComp(new AuthorConferenceView(client, theFrame));
			
	}



	@Override
	public void mouseEntered(MouseEvent e) {
//		((JLabel)e.getSource()).setFont(new Font("Times Roman", Font.BOLD, 20));
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
//		((JLabel)e.getSource()).setFont(new Font("Times Roman", Font.PLAIN, 20));
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
