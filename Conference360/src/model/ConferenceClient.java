package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.Job.Title;
import model.Manuscript.Decision;
import model.Manuscript.Recommendation;

/**
 * Maintains current conference as well as current user and submits Reviews.
 * @author Keith Foreman
 */
public class ConferenceClient implements Serializable {
	/**
	 * A collection of Conferences.
	 */
	private ArrayList<Conference> myConferences;
	/**
	 * A collection of Users.
	 */
	private HashMap<String, User> allUsers;
	/**
	 * A collection of recommendations.
	 */
	private HashMap<String, File> recommendations;
	/**
	 * A reference to the current user.
	 */
	private User currentUser;
	/**
	 * A reference to the current conference.
	 */
	private Conference currentConference;
	
	/**
	 * Default constructor.
	 */
	public ConferenceClient() {
		this(null, null);	
	}
	/**
	 * Parameterized Client with initial user and conference to use.
	 */
	public ConferenceClient(User currentUser, Conference currentConf) {
		this.myConferences = new ArrayList<Conference>();
		this.allUsers = new HashMap<String, User>();
		this.currentUser = currentUser;
		this.currentConference = currentConf;
		this.addConference(currentConf);
		recommendations = new HashMap<String, File>();

		
		//Initial DEBUG for JAR Creation (Comment out after running once and .ser has been created,
		//go back and uncomment deserialize/comment out debug)
//		Conference conf = new Conference("IADIS MULTI Conference Computer Science and Information System", new Date(System.currentTimeMillis() + 1000000000), 1);
//		myConferences.add(conf);
//		myConferences.add(new Conference("Tucson ANNUAL Conference", new Date(System.currentTimeMillis() + 2000000000), 2));
//		myConferences.add(new Conference("International NAME Conference", new Date(), 1));
//		myConferences.add(new Conference("7th ACM Recommender System conference", new Date(), 2));
		//END DEBUG
	}
	
	/**
	 * Submits a review for a specific paper within the current Conference.
	 *  
	 * @param paperName the name of the paper
	 * @param reviewForm the review form to be submitted
	 */
	public void submitReview(String paperName, File reviewForm, String score, 
			String reviewerID) {
		Review newReview = new Review(paperName, score, reviewForm, reviewerID);
		//newReview.setReviewForm(reviewForm);
		if(currentConference != null && currentConference.getPaper(paperName) !=null){
			currentConference.getPaper(paperName).addReview(newReview);
		}
	}
	
	/**
	 * Submits a paper to be reviewed to the currentConference.
	 */
	public void submitPaper(File thePaper, Manuscript theScript) {
		currentConference.submitPaper(theScript.getTitle(), currentUser.getName(), thePaper);
		currentUser.addPaper(theScript, currentConference.getMyID());
	}
	
	/**
	 * 
	 * Submit a Recommendation for a paper.
	 * 
	 * @param paperName the paper name
	 * @param recForm the recommendation form
	 * @param rec the Recommendation
	 * @author Maria Mills
	 */
	public void submitRecommendation(String paperName, File recForm, Recommendation rec) {		
		if(currentConference != null && currentConference.getPaper(paperName) !=null){
			recommendations.put(paperName, recForm);
			currentConference.getPaper(paperName).setRecommendation(rec);
		}
	}
	
	/**
	 * Deletes a paper from the Conference.
	 * 
	 * @param paperTitle the paper title
	 */
	public void deletePaper(String paperTitle) {
		if (this.currentConference.getSubmissionDeadline().after(new Date())) {
			this.currentUser.deletePaper(paperTitle, currentConference.getMyID());
			currentConference.deletePaper(paperTitle);
		}
		
	}
	
	/**
	 * Changes approval status of manuscript.
	 */
	public void changeStatus(String paperName, Decision status) {
		currentConference.getPaper(paperName).changeStatus(status);
	}
	
	/**
	 * Sets the current user.
	 * 
	 * @param user the new current user
	 */
	public void setUser(User user) {
		this.currentUser = user;
	}
	
	/**
	 * Retrieves the current user.
	 * 
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Retrieves a specified user, by email.
	 * 
	 * @param email the email address
	 * @return the User
	 */
	public User getUser(String email) {
		return allUsers.get(email);
	}
	
	/**
	 * Returns a list of all Users.
	 * 
	 * @return collection of Users.
	 */
	public Collection <User> getAllUsers() {
		return allUsers.values();
	}
	
	/**
	 * Returns the list of conferences for this client
	 * @return the list of conferences for this client
	 * 
	 */
	public List<Conference> getMyConferences() {
		return this.myConferences;
	}
	
	/**
	 * Returns the current conference
	 * @return the current conference
	 */
	public Conference getCurrentConference() {
		return currentConference;
	}
	
	/**
	 * Set the current conference.
	 * 
	 * @param theConference the current Conference being worked on
	 * @edited Brent Young
	 */
	public void setCurrentConference(Conference theConference) {
		this.currentConference = theConference;
	}
	
	/**
	 * Adds a new conference to the client.
	 * @param theConference
	 * @edited Brent Young
	 */
	public void addConference(Conference theConference) {
		myConferences.add(theConference);
	}
	
	/**
	 * Verifies that the entered email/password is valid for a User,
	 * if valid, they become the currentUser.
	 * @param email the User's email
	 * @param pass the User's password
	 * 
	 * @return true if valid
	 */
	public boolean verifyUser(String email, String pass) {
		if(allUsers != null) {
			if(allUsers.get(email) != null && allUsers.get(email).getPassword().equals(pass)) {
				currentUser = allUsers.get(email);
				currentUser.setBeingViewAs(Title.GENERAL);
				return true;
			}
		}
		return false;
	}
	/**
	 * Registers a new User for the program.
	 * 
	 * 
	 * @param userName the username
	 * @param email the User's email address
	 * @param pass the User's password
	 */
	public void registerUser(String userName, String email, String pass) {
		User newUser = new User(userName, email, pass);
		newUser.setBeingViewAs(Job.Title.GENERAL);
		this.currentUser = newUser;
		allUsers.put(email, newUser);
	}
	
	/**
	 * Adds a User.
	 * 
	 * @param theUser the user
	 */
	public void addUser(User theUser) {
		allUsers.put(theUser.getEmail(), theUser);
	}
	
	/**
	 * Returns true if already a user.
	 */
	public boolean isUser(String email) {
		if(allUsers != null && allUsers.get(email) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Added for testing, clears collections
	 * 
	 */
	public void clear() {
		allUsers.clear();
		myConferences.clear();
		this.currentUser = null;
		save();
	}
	
	/**
	 * For use in closing application.
	 */
	public void resetUser() {
		this.currentUser = null;
	}
	
	/**
	 * Saves state of program.
	 */
	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream("data/clientState.ser");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(this);
			objectOut.close();
			fileOut.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
