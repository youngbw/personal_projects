
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import listeners.MyReviewListener;

/**
 * Contains author, review, and other relevant information about a submitted paper
 * @author Josh Moore
 * @author Keith Foreman
 */
public class Manuscript implements Serializable {
	/**
	 * References to possible Decisions.
	 */
	public enum Decision {
		ACCEPTED, REJECTED, UNDECIDED;
	}
	
	/**
	 * References to possible Recommendations.
	 */
	public enum Recommendation {
		ACCEPT, REJECT, NEUTRAL;
	}
 
	/**
	 * User specified Title of the paper.
	 */
	private String title;
	/**
	 * The name of the file.
	 */
	private String name;
	/**
	 * Reference to the AuthorID.
	 */
	private String author_id;
	/**
	 * Reference to the paper file.
	 */
	private File myPaper;

	/**
	 * Stack is useful because in cases where we need to retrieve a specific ID rather than merely
	 * check that it is anywhere in the stack it will be useful to just peek at the top element.
	 * @see #getMostRecentConferenceID()
	 * @author BrentYoung
	 */
	private Stack<Integer> conferenceIDs;
	/**
	 * A list of reviews for this manuscript.
	 */
	private List<Review> reviews;
	/**
	 * A list of reviewers for this manuscript.
	 */
	private List<String> reviewers;
	/**
	 * A boolean value representing whether or not the manuscript may be reviewed.
	 */
	private boolean upForReview;
	/**
	 * Reference to the Decision status of the manuscript.
	 */
	private Decision status;
	/**
	 * Reference to the recommendation for this manuscript.
	 */
	private Recommendation recommendation;
	/**
	 * The date this manuscript was submitted.
	 */
	private Date submit_date;
	/**
	 * Unique ID for this manuscript.
	 */
	private String id;
	/**
	 * The Subprogram Chair assigned to this manuscript.
	 */
	private String subPC;

	/**
	 * Creates a new instance of Manuscript, storing passed data
	 * @param author_id unique identifier for the paper's author
	 * @param name paper title
	 */
	public Manuscript(final String author_id, final String name) {
		this.name = name;
		this.author_id = author_id;
		title = "";
		myPaper = null;
		subPC = null;
		conferenceIDs = new Stack<>(); // see field javadoc for comments
		reviews = new ArrayList<Review>();
		reviewers = new ArrayList<String>();
		
		status = Decision.UNDECIDED; // acceptance status is assumed false until changed
		recommendation = Recommendation.NEUTRAL;
		upForReview = false; //false until it has been assigned. 

		//debug
		Review rev = new Review(this.getTitle(), "strong accept", new File("Test.txt"), "Rev1");
		
		addConferenceID(1);
		addReview(rev);
		this.status = Decision.UNDECIDED;
		//end debug
		
		this.submit_date = new Date();
		this.id = generateId();
	}
	
	/**
	 * Creates a new instance of Manuscript, storing passed data
	 * @param author_id unique identifier for the paper's author
	 * @param name paper title
	 * @param
	 */
	public Manuscript(final String author_id, final String name, final File paper) {
		this(author_id, name);
		try {
			uploadFile(paper);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * @return the unique id generated for the manuscript, id algorithm subject to change
	 */
	private String generateId() {
		return author_id + name;
	}

	/**
	 * Changes the acceptance status of the Manuscript
	 * @param status true if accepted false if not
	 */
	public void changeStatus(final Decision status) {
		this.status = status;
	}
	
	/**
	 * 
	 * @param reviewer_id
	 */
	public void assignReviewer(String reviewer_id){
		reviewers.add(reviewer_id);
	}
	
	/**
	 * Attaches a new review to the Manuscript
	 * @param r a review for this Manuscript
	 */
	public void addReview(Review r) {
		reviews.add(r);
	}

	/**
	 * @return the unique id of this manuscript
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Sets the ID number for this paper.
	 * @author BrentYoung
	 */
	public void setPaperID(String theID) {
		if (theID != null) {
			this.id = theID;
		}
	}
	
	/**
	 * Sets the title of this manuscript.
	 * 
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns the title of the manuscript.
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Assigns a User as a Subprogram Chair.
	 * 
	 * @param id the user id of the SubPC
	 */
	public void assignSubPC(User user, int conferenceID) {
		user.addJob(conferenceID, Job.Title.SUBPROGRAMCHAIR);
		
	}
	/**
	 * Adds a new conference to this paper if paper already exists and is merely being submitted
	 * to a new conference.
	 * @param theConferenceID the conference the paper is being submitted to.
	 * @author BrentYoung
	 */
	public void addConferenceID(int theConferenceID) {
		if (!this.conferenceIDs.contains(theConferenceID)) this.conferenceIDs.push(theConferenceID);
	}
	
	/**
	 * Returns the ID number for this paper.
	 * @return int paper ID
	 * @author BrentYoung
	 */
	public String getPaperID() {
		return this.id; 
	}
	
	/**
	 * Stores the file in the programs file system and sets myPaper to the upload 
	 * @pre paper is a valid file location and not null
	 * @post paper saved to file/id.file and myPaper = File(file/id.file)
	 * @param paper the file to be uploaded
	 * @throws IOException 
	 */
	public void uploadFile(final File paper) throws IOException {
		
		File upload = new File("files/" + id); // + ".file");
		FileChannel src;
		FileChannel dest;

		try {
			src = new FileInputStream(paper).getChannel();
			dest = new FileOutputStream(upload).getChannel();
			try{
			dest.transferFrom(src, 0, src.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			src.close();
			dest.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		myPaper = upload;
	}
	
	/**
	 * This method allows the user to see what the most recent conference was that this paper was
	 * added to so the AUTHOR title can be added to that user for that specific conference.
	 * @return int ID of the most recent conference this paper was submitted to.
	 * @author BrentYoung
	 */
	public int getMostRecentConferenceID() {
		if (!conferenceIDs.isEmpty()) {
			return (Integer) ((Stack<Integer>) conferenceIDs).peek();
		} else {
			return Integer.MIN_VALUE;
		}
	}

	/**
	 * Return true if paper is submitted to the Conference.
	 * 
	 * @param matchID the conference ID
	 * @return true if paper is submitted to the conference
	 */
	public boolean isSubmittedtoConferece(int matchID) {
		return this.conferenceIDs.contains(matchID);
	}
	
	/**
	 * Removes the conference ID from this manuscript.
	 * 
	 * @param conferenceID the conference ID
	 */
	public void removeConferenceID(Integer conferenceID) {
		this.conferenceIDs.remove(conferenceID);
	}
	
	/**
	 * Returns the paper file for this manuscript.
	 * 
	 * @return the paper file
	 */
	public File getFile(){
		return myPaper;
	}
	
	/**
	 * Returns the name of the manuscript.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the decision status of this manuscript.
	 * 
	 * @param status the status
	 */
	public void setStatus(Decision status) {
		this.status = status;
	}
	
	/**
	 * Returns a list of reviews for this manuscript.
	 * 
	 * @return a list of reviews
	 */
	public List<Review> getReviews() {
		return reviews;
	}
	
	/**
	 * Returns the decision status of this manuscript.
	 * 
	 * @return the decision status
	 */
	public Decision getStatus() {
		return status;
	}
	
	/**
	 * Returns the recommendation for this manuscript.
	 * 
	 * @return the recommendation
	 */
	public Recommendation getRecommendation() {
		return this.recommendation;
	}
	
	/**
	 * Sets the recommendation for this manuscript.
	 * 
	 * @param rec the recommendation
	 */
	public void setRecommendation(Recommendation rec) {
		this.recommendation = rec;
	}
	
	/**
	 * Returns a Stack of Conferences that this manuscript has been submitted to.
	 * @return
	 */
	public Stack<Integer> getSubmittedConferences() {
		return this.conferenceIDs;
	}
	
	/**
	 * Sets the Subprogram Chair for this manuscript.
	 * @param id the subPC ID
	 */
	public void setSubPC(String id) {
		subPC = id;
	}
	
	/**
	 * Returns the Subprogram chair for this Conference.
	 * @return
	 */
	public String getSubPC() {
		return subPC;
	}
	
	/**
	 * Returns the author ID for this manuscript.
	 * @return the author id
	 */
	public String getAuthorID () {
		return author_id;
	}
}
