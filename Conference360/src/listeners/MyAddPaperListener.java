package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import view.AuthorConferenceView;
import view.ClientGui;
import model.Conference;
import model.ConferenceClient;
import model.Manuscript;
import model.Job.Title;

/**
 * Listener used to submit a paper to a Conference.
 * @author BrentYoung
 *
 */
public class MyAddPaperListener implements ActionListener {
	/**
	 * Reference to a Conference.
	 */
	private Conference myConference;
	/**
	 * Reference to the frame.
	 */
	private ClientGui theFrame;
	/**
	 * Reference to the conference client.
	 */
	private ConferenceClient client;
	
	/**
	 * Stores references to the client, the frame, and the conference.
	 * @param client the client
	 * @param theFrame the frame
	 * @param theConference the conference
	 */
	public MyAddPaperListener(ConferenceClient client, ClientGui theFrame, Conference theConference) {
		this.myConference = theConference;
		this.client = client;
		this.theFrame = theFrame;
	}
	
	/**
	 * @edited Keith Foreman
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (myConference.getSubmissionDeadline().after(new Date())) {
			
			//Choose file
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showOpenDialog(theFrame);
			
			//Once chosen create manuscript and send it to client to get added to the conference and user lists
			if (option == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				Manuscript m = new Manuscript(client.getCurrentUser().getEmail(), f.getName(), f);
				String title = JOptionPane.showInputDialog(theFrame, "Please enter the title of your manuscript", "Manuscript Submission", JOptionPane.DEFAULT_OPTION);
				m.setTitle(title);
				client.submitPaper(f, m);
				AuthorConferenceView next = new AuthorConferenceView(client, theFrame);
				next.setLabelText("Successfully uploaded " + m.getTitle());
				theFrame.moveToComp(next);
				
				client.save();
			}
		} else {
			JOptionPane.showMessageDialog(theFrame, "Cannot submit a paper after the submission deadline has passed.", "Paper Submission Denied", JOptionPane.ERROR_MESSAGE);
		}
	}

}
