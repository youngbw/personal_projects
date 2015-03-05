package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents one set of responsibilities for one Conference.
 * @author Brent
 *
 */
public class Job implements Serializable {

	
	/**
	 * This seemed like a good implementation of this. If we need to take it out we can do that later.
	 * @author BrentYoung
	 *
	 */
	public static enum Title {
		GENERAL, PROGRAMCHAIR, SUBPROGRAMCHAIR, REVIEWER, AUTHOR;
	}
	
	/**
	 * The conference that this Job is associated with.
	 */
	private int conferenceID;
	
	/**
	 * The specific titles that a user is for this specific conference related job.
	 */
	private List<Title> myTitles;
	
	public Job() {
		this.conferenceID = Integer.MIN_VALUE;
		myTitles = new ArrayList<Title>();
	}
	
	public Job(int conferenceID) {
		this.setConferenceID(conferenceID);
		myTitles = new ArrayList<Title>();
	}
	
	/**
	 * Returns all the Titles for this Job in a List.
	 * @return List of titles
	 * @author BrentYoung
	 */
	public List<Title> getTitles() {
		return this.myTitles;
	}
	
	/**
	 * Returns the conference ID of this Job.
	 * @return int conf. ID
	 * @author BrentYoung
	 */
	public int getConferenceID() {
		return this.conferenceID;
	}
	
	/**
	 * Adds a new title to this Job only if the user is not already assuming that Title.
	 * @param theTitle title to be added.
	 * @author BrentYoung
	 */
	public void addTitle(Title theTitle) {
		if (myTitles != null && !this.myTitles.contains(theTitle)) {
			this.myTitles.add(theTitle);
		}
	}
	
	/**
	 * Sets the conferenceID for this Job. Cannot be changed.
	 * @param theID
	 * @author BrentYoung
	 */
	public void setConferenceID(int theID) {
//		if (this.conferenceID == Integer.MIN_VALUE) {
//			this.conferenceID = theID;
//		}
		this.conferenceID = theID;
	}
	
	/**
	 * Removes the specified title from the user for this conference relasted Job.
	 * @param theTitle the title to be removed.
	 * @author BrentYoung
	 */
	public void removeTitle(Title theTitle) {
		if (myTitles.contains(theTitle)) myTitles.remove(theTitle);
	}

	
	
}
