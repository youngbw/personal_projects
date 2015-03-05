package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Conference;
import model.ConferenceClient;
import model.Job;
import model.Manuscript;
import model.User;
import model.Job.Title;



/**
 * This acts as the background frame for the program.
 * @author BrentYoung
 * @edited Keith Foreman
 */
@SuppressWarnings("serial")
public class ClientGui extends JFrame {
	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	
	private Stack<JPanel> backLog;
	private JPanel currentComp;
	private ConferenceClient client;
	
	public ClientGui() {
//		//deserialize - Keith
		try {
			FileInputStream fileIn = new FileInputStream("data/clientState.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			client = (ConferenceClient) objectIn.readObject();
			objectIn.close();
			fileIn.close();
		} catch (ClassNotFoundException e) {
			e.getStackTrace();
		} catch (IOException f) {
			System.out.println("clientState.ser not found!");
			
			f.getStackTrace();
		}
		//End Deserialize Code
		
		/////Uncomment code below (as well as the Debug Conferences in ConferenceClient) when initializing for JAR creation.
		/////NOTE: After running once and saving a new .ser file, comment everything back out and uncomment deserialize code above.
		/////Will create a Program Chair for the 2nd Conference.
		
//		Conference conf2 = new Conference("2nd Mediterranean Conference on Embedded Computing", new Date(), 1);
//		client = new ConferenceClient(null, conf2);
//		User test1 = new User("test", "test@gmail.com", "test");
//		test1.addJob(2, Title.PROGRAMCHAIR);;
//		client.addUser(test1);
		
		//END DEBUG
		//
	}
	
	/**
	 * Initializes first screen of program.
	 */
	public void start() {
		//Screen size and location behavior
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2);
		this.setTitle("Conference Manager");
		this.setLayout(new BorderLayout());
		backLog = new Stack<JPanel>();

		LoginView view = new LoginView(client, this);
		
		
		this.add(view);
		this.currentComp = view;
		this.pack();
		this.setLocation(SCREEN_SIZE.width / 2 - this.getWidth() / 2, SCREEN_SIZE.height / 2 - this.getHeight() / 2);
		this.setVisible(true);
		
		//Keith's close
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	client.resetUser();
		    	client.save();
		        System.exit(0);
		    }
		});
	}
	
	/**
	 * Resets the backlog.
	 */
	public void resetBackLog() {
		backLog = new Stack<JPanel>();
	}
	
	/**
	 * Moves selected component to the frame.
	 * 
	 * @param comp the component
	 */
	public void moveToComp(JPanel comp) {
		this.remove(currentComp);
		this.currentComp = comp;
		((ViewPanel)comp).setRoleSelected(Title.GENERAL);
		this.add(currentComp, BorderLayout.CENTER);
		revalidate();
		repaint();
		this.pack();
		this.setLocation(SCREEN_SIZE.width / 2 - this.getWidth() / 2, SCREEN_SIZE.height / 2 - this.getHeight() / 2);
		setProperUserState(comp);
		this.backLog.push(comp);
	}
	
	/**
	 * Moves to previous component.
	 */
	public void moveToPreviousComp() {
		if (this.backLog.size() > 1) {
			JPanel currentPanel = this.backLog.pop();
			JPanel nextPanel = this.backLog.pop();
			while (nextPanel.getClass().getSimpleName().equals(currentPanel.getClass().getSimpleName()) &&
					this.backLog.size() > 0) {
				nextPanel = this.backLog.pop();
			}
			if (!(nextPanel.getClass().getSimpleName().equals(currentPanel.getClass().getSimpleName()))) {
				setProperUserState(nextPanel);
				moveToComp(nextPanel);
			}
			
		}
	}
	
	/**
	 * This method ensures proper user state when moving backwards through view history, assuming that the user
	 * has already been checked for permission to view the panel passed into this method.
	 * @param panel the panel that is being backtracked to in backLog
	 * @see #moveToPreviousComp()
	 * @see #backLog
	 */
	private void setProperUserState(JPanel panel) {
		if (client.getCurrentUser() != null) {
			if (client.getCurrentConference() != null && client.getCurrentUser().conferenceMatchJob(client.getCurrentConference().getMyID()) != null) {
//				System.out.println("User Jobs " + client.getCurrentUser().conferenceMatchJob(client.getCurrentConference().getMyID()).getTitles().toString());
//				System.out.println("Conference ID for above jobs" + client.getCurrentConference().getMyID());
			}
			if (client.getCurrentUser().conferenceMatchJob(client.getCurrentConference().getMyID()) != null) {
				if ((panel instanceof AuthorConferenceView || panel instanceof AuthorManuscriptView) &&
						(client.getCurrentUser().conferenceMatchJob(client.getCurrentConference().getMyID()).getTitles().contains(Title.AUTHOR))) {
					
					client.getCurrentUser().setBeingViewAs(Title.AUTHOR);
					
				} else if ((panel instanceof PCConferenceView || panel instanceof PCManuscriptView || panel instanceof PCSubPCView) &&
						(client.getCurrentUser().conferenceMatchJob(client.getCurrentConference().getMyID()).getTitles().contains(Title.PROGRAMCHAIR))) {
					
					client.getCurrentUser().setBeingViewAs(Title.PROGRAMCHAIR);
					
				} else if (panel instanceof SubprogramView &&
						(client.getCurrentUser().conferenceMatchJob(client.getCurrentConference().getMyID()).getTitles().contains(Title.SUBPROGRAMCHAIR))) {
					
					client.getCurrentUser().setBeingViewAs(Title.SUBPROGRAMCHAIR);
					
				} else if (panel instanceof ReviewerView &&
						(client.getCurrentUser().conferenceMatchJob(client.getCurrentConference().getMyID()).getTitles().contains(Title.REVIEWER))) {
					
					client.getCurrentUser().setBeingViewAs(Title.REVIEWER);
					
				} else {
					client.getCurrentUser().setBeingViewAs(Title.GENERAL);
				}
				((ViewPanel)currentComp).setRoleSelected(client.getCurrentUser().getCurrentlyViewedAs());
			}
		}
	}
	
	/**
	 * Returns the current component.
	 * 
	 * @return the current component
	 */
	public JPanel getCurrentComp() {
		if (!backLog.isEmpty()) return backLog.peek();
		else return null;
	}
	
	/**
	 * Returns a Stack of previous JPanels.
	 * @return
	 */
	public Stack<JPanel> getBackLog() {
		return this.backLog;
	}
}
