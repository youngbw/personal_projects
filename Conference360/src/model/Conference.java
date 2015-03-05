/**
 * 
 */
package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import model.Manuscript.Decision;

/**
 * This class holds a conference which consists of a list of users working in the conference, 
 * a list of manuscripts submitted for review and a scale by which the papers will be scored.
 * 
 * @author Maria Mills
 * @version 1.0
 */
public class Conference implements Serializable {
	/**
	 * Default conference ID.
	 */
	private static int conferenceID;
	/**
	 * This conferences ID.
	 */
	private int myConferenceID;
	/**
	 * Name of Conference.
	 */
	private String name;
	/**
	 * Collection of papers in this Conference.
	 */
	private TreeMap<String, Manuscript> papers;
	/**
	 * Collection of Users in this Conference.
	 */
	private ArrayList<User> users;
	/**
	 * A reference to the Scale for this Conference.
	 */
	private Scale aScale;
	/**
	 * Collection of Scales.
	 */
	private ArrayList<String> scaleList;
	/**
	 * The submission deadline for this Conference.
	 */
	private Date submissionDeadline;
	
	/**
	 * This constructor accepts an int for the scale option that
	 * is selected for this conference. The scale is stored in a 
	 * string list.
	 * 
	 * @param scaleID the ID of scale chose.
	 */
	public Conference(String name, Date deadline, int scaleID){
		myConferenceID = ++conferenceID;
		this.name = name;
		papers = new TreeMap<String, Manuscript>();
		users = new ArrayList<User>();
		aScale = new Scale();	
		scaleList = aScale.chooseScale(scaleID);
		submissionDeadline = deadline;
	}
	
	/**
	 * This method creates a manuscript object that includes the authors name, id
	 * and the file name of his paper.  It is then added to the list of submitted 
	 * manuscripts in the conference.
	 * @edited Keith Foreman
	 */
	public void submitPaper(String title, String author_id, File thePaper) {
		Manuscript myPaper = new Manuscript(author_id, title, thePaper);
		papers.put(title, myPaper);	
	}
	
	/**
	 * Used to delete a paper from a conference. See ConferenceClient for information
	 * on submission deadlines.
	 * @param theTitle
	 * @edited Brent Young
	 * @see ConferenceClient
	 */
	public void deletePaper(String theTitle) {
		papers.remove(theTitle);
	}
	
	/**
	 * Changes approval status of the specified paper.
	 * @edited Keith Foreman
	 */
	public void changeStatus(Decision status, String nameOfPaper) {
		papers.get(nameOfPaper).changeStatus(status);
	}
	
	/**
	 * Returns this Conference's ID.
	 * @return the conference ID
	 */
	public int getMyID() {
		return this.myConferenceID;
	}

	/**
	 * Returns the submission deadline for this Conference.
	 * @return the submission deadline
	 */
	public Date getSubmissionDeadline() {
		return this.submissionDeadline;
	}
	
	/**
	 * This method gets all the manuscripts stored in the map
	 * and returns them as a collection.
	 * 
	 * @return all papers in the conference
	 */
	public Collection<Manuscript> getPapers() {
		return papers.values();
	}

	/**
	 * This method accepts a string for the name of the paper requested
	 * and returns the manuscript with that paper name.
	 * 
	 * @param name this is the name of the paper
	 * @return manuscript object
	 */
	public Manuscript getPaper(String name) {
		if(papers.containsKey(name)) {
			return papers.get(name);
		} else {
			return null;
		}
	}
	
	/**
	 * This method returns the name of this conference.
	 * @return String the name of this conference.
	 * 
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the list of scales.
	 * @return the list of scales
	 */
	public ArrayList<String> getScale() {			
	
		return scaleList;	

	}
	
	/**
	 * Adds a User to this Conference.
	 * 
	 * @author Keith Foreman
	 * @param user the User to add
	 */
	public void addUser(User user) {
		users.add(user);
	}
	
	/**
	 * Sets the deadline for this conference.
	 * @param d the deadline
	 */
	public void setDeadline(Date d) {
		submissionDeadline = d;
	}
}