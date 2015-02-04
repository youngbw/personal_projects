package model;

import java.util.ArrayList;
import java.util.List;

import model.Job.Title;

public class User implements java.io.Serializable {

	private String name;
	private String email;
	private String password;
	
	//Seems like this would be useful, but can be deleted along with getter if we don't use.
	private Title currentlyViewedAsTitle;
	
	private List<Manuscript> myPapers;
	private List<Job> myJobs;
	
	public User() {
		name = "none";
		email = "none";
		password = "none";
		
		myPapers = new ArrayList<Manuscript>();
		myJobs = new ArrayList<Job>();
		
	}
	
	public User(String name, String email, String password) {
		this.setName(name);
		this.setEmail(email);
		this.setPassword(password);
	}
	
	public User(String name, String email) {
		this.setName(name);
		this.setEmail(email);
	}
	
	//BEGIN GETTERS
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Title getCurrentlyBeingViewedAs() {
		return this.currentlyViewedAsTitle;
	}
	
	public List<Manuscript> getMyPapers() {
		return this.myPapers;
	}
	
	public List<Job> getMyJobs() {
		return this.myJobs;
	}
	
	//END GETTERS
	
	//BEGIN SETTERS
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		if (email.contains("@") && (email.contains(".com") || email.contains(".net"))) {
			this.email = email;
		}
	}
	
	public void setPassword(String password) {
		this.password = "" + password;
	}
	
	//END SETTERS
	
	/**
	 * UTILITY METHOD -- can be revised or deleted if not used in project.
	 * This method returns a manuscript located within the List "myPapers" that matches the name passed into the method,
	 * or null if no paper is found.
	 * @param paperName The name of the paper to be searched for in the List "myPapers."
	 * @return the Manuscript object whose name equals the String passed to this method.
	 * @param paperName String representing the name of the paper to be searched for.
	 * 
	 * @author BrentYoung
	 */
	public Manuscript getSpecificPaper(String paperName) {
		for (int i = 0; i < myPapers.size(); i++) {
			if (myPapers.get(i).getTitle().equals(paperName)) {
				return myPapers.get(i);
			}
		}
		return null;
	}
	
	/**
	 * UTILITY METHOD -- can be deleted if not used in project.
	 * This method will return the manuscript object in this users List "myPapers" if a manuscript object is found
	 * with a matching ID, or NULL otherwise.
	 * @param paperID int the ID of the paper to be searched for in "myPapers"
	 * @return Manuscript the manuscript object with the matching ID to paperID
	 * 
	 * @author BrentYoung
	 */
	public Manuscript getSpecificPaper(int paperID) {
		for (int i = 0; i < myPapers.size(); i++) {
			if (myPapers.get(i).getPaperID() == paperID) {
				return myPapers.get(i);
			}
		}
		return null;
	}
	
	public void addPaper(Manuscript thePaper) {
		//This may need to change based on Manuscript -- if this conferenceID identifier type (Stack currently) changes
		addJob(thePaper.getMostRecentConferenceID(), Title.AUTHOR);
		
		myPapers.add(thePaper);
	}
	
	public void addJob(int conferenceID, Title theTitle) {
		Job theJob = conferenceMatchJob(conferenceID);
		theJob.addTitle(theTitle);
	}

	public List<Title> getTitles(int conferenceID) {
		return conferenceMatchJob(conferenceID).getTitles();
		
	}
	
	
	//BEGIN PRIVATE METHODS
	
	private Job conferenceMatchJob(int conferenceID) {
		for (int i = 0; i < myJobs.size(); i++) {
			if (myJobs.get(i).getConferenceID() == conferenceID) {
				return myJobs.get(i);
			}
		}
		return null;
	}
	
	//END PRIVATE METHODS
}

	





