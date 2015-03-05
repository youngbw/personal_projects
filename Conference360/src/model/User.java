package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JOptionPane;

import model.Job.Title;

/**
 * This class represents one User object.
 * @author BrentYoung
 * @version 1.0
 */
public class User implements Serializable {
	/**
	 * Reference to number of papers assigned to the Subprogram Chair.
	 */
	private int subPCCount;
	/**
	 * Reference to the name of the user.
	 */
	private String name;
	/**
	 * Reference to the email address of this user.
	 */
	private String email;
	/**
	 * Reference to the password for this user.
	 */
	private String password;
	/**
	 * Reference to the Title the User is currently set to.
	 */
	private Title currentlyViewedAsTitle;
	/**
	 * Reference to a list of papers for this User.
	 */
	private List<Manuscript> myPapers;
	/**
	 * Reference to a list of jobs for this User.
	 */
	private List<Job> myJobs;
	/**
	 * Reference to a Map of papers assigned to a specific job for this user.
	 */
	private Map<Title, Map<Integer, Set<Manuscript>>> jobPapers;
	/**
	 * Reference to a Map of papers submitted to a Conference.
	 */
	private Map<Integer, Set<Manuscript>> confPaper;
	
	/**
	 * Default constructor for the User class.
	 */
	public User() {
		this("none", "none", "none");
	}
	
	/**
	 * Parameterized constructor for the User class, loads name, email, and password.
	 * 
	 * @param name the name
	 * @param email the email
	 * @param password the password
	 */
	public User(String name, String email, String password) {
		this.setName(name);
		this.setEmail(email);
		this.setPassword(password);
		currentlyViewedAsTitle = null;
		myPapers = new ArrayList<Manuscript>();
		myJobs = new ArrayList<Job>();
		jobPapers = new HashMap<Title, Map<Integer, Set<Manuscript>>>();		
		confPaper = new HashMap<Integer, Set<Manuscript>>();
		subPCCount = 0;
	}
	
	/**
	 * Sets the name and email for the user, without storing a password.
	 * 
	 * @param name the name
	 * @param email the email address
	 */
	public User(String name, String email) {
		this(name, email, "none");
	}
	
	/**
	 * Returns the name of the user.
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Returns the email address of the user.
	 * @return the email address
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Returns the password for this user.
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Returns the current Title of the user.
	 * @return
	 */
	public Title getCurrentlyViewedAs() {
		return this.currentlyViewedAsTitle;
	}
	
	/**
	 * Returns a list of papers for this User.
	 * @return the list of papers
	 */
	public List<Manuscript> getMyPapers() {
		return this.myPapers;
	}
	
	/**
	 * Returns a list of Jobs for this User.
	 * @return the list of jobs
	 */
	public List<Job> getMyJobs() {
		return this.myJobs;
	}
	
	/**
	 * Sets the name of the user.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the email address of the user.
	 * 
	 * @param email the email address
	 */
	public void setEmail(String email) {
		if (email.contains("@") && (email.contains(".com") || email.contains(".net"))) {
			this.email = email;
		}
	}
	
	/**
	 * Sets the password for the user.
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = "" + password;
	}
	
	/**
	 * Sets the current Title of the user.
	 * @param theTitle
	 */
	public void setBeingViewAs(Title theTitle) {
		this.currentlyViewedAsTitle = theTitle;
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
	public Manuscript getSpecificPaper(String paperTitle) {
		for (int i = 0; i < myPapers.size(); i++) {
			if (myPapers.get(i).getTitle().equals(paperTitle)) {
				return myPapers.get(i);
			}
		}
		return new Manuscript("none", "none");
	}
	
	/**
	 * Adds a paper to the List of papers this user has submitted.
	 * @param thePaper
	 * @author BrentYoung
	 */
	public void addPaper(Manuscript thePaper, int conferenceID) {
		boolean exists = false;
		for (int i = 0; i < myPapers.size(); i++) {
			if (thePaper.getTitle().equals(myPapers.get(i).getTitle()) && myPapers.get(i).getSubmittedConferences().contains(conferenceID)) {
				exists = true;
				break;
			}
		}
		
		if (!exists) {
			addJob(conferenceID, Title.AUTHOR);
			
			if (thePaper != null) {
				thePaper.addConferenceID(conferenceID);
				myPapers.add(thePaper);
			} else {
				throw new NullPointerException("Null Paper passed");
			}
		} else {
			JOptionPane.showMessageDialog(null, "You cannot submit multiple papers with the same title"
					+ "\nto the same Conference", "Submission Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	/**
	 * Deletes the paper with the given title (not file name) from the myPapers field.
	 * @param theTitle
	 */
	public void deletePaper(String theTitle, int conferenceID) {
		Manuscript manu = null;
		boolean author = false;
		for (Manuscript m: myPapers) {
			if (m.getTitle().equals(theTitle) && m.getSubmittedConferences().contains(conferenceID)) {
				manu = m;
			} else if (m.isSubmittedtoConferece(conferenceID)) {
				author = true;
			}
		}
		if (manu != null) {
			myPapers.remove(manu);
			manu.removeConferenceID(conferenceID);
		}
		if (!author) {
			removeJob(conferenceID, Title.AUTHOR);
		}
		
	}
	
	/**
	 * This method adds a Job, which is linked to this User and
	 * one Conference, and represents all of the Titles that this
	 * User has for that Conference.
	 * @param conferenceID the ID of the Conference this Job is for, can only be set once.
	 * @param theTitle the Title that it trying to be added to the Job.
	 * 
	 * @author BrentYoung
	 */
	public void addJob(int conferenceID, Title theTitle) {
		Job theJob = conferenceMatchJob(conferenceID);
		if (theJob != null) {
			theJob.addTitle(theTitle);
		} else {
			Job theNewJob = new Job(conferenceID);
			theNewJob.addTitle(theTitle);
			myJobs.add(theNewJob);
		}
	}
	
	public void removeJob(int conferenceID, Title theTitle) {
		Job theJob = conferenceMatchJob(conferenceID);
		Job newJob = new Job(conferenceID);
		for (Title t: theJob.getTitles()) {
			if (theTitle != t) {
				newJob.addTitle(t);
			}
		}
		myJobs.remove(theJob);
		myJobs.add(newJob);
//		}
	}
	
	/**
	 * This method assigns a paper to a user with their role in that conf
	 * 
	 * @param confID
	 * @param theTitle
	 * @param thePaper
	 */
	public void assignPaper(int confID, Title theTitle, Manuscript thePaper) {
		if(theTitle == Job.Title.REVIEWER || theTitle == Job.Title.SUBPROGRAMCHAIR) {
			for(Title t : jobPapers.keySet()) {
				if(t == Title.AUTHOR) {
					for (Manuscript m : jobPapers.get(t).get(confID) ) {
						if(t == Title.AUTHOR && thePaper.getId().equals(m.getId())) {
							System.err.println("Can't assign User to be Reviewer or SubProgramChair of their own paper");
							return;
						}
					}
				}
			}
		}
		
		if(!confPaper.isEmpty() && confPaper.containsKey(confID)) {
			
			confPaper.get(confID).add(thePaper);
		} else {
			
			confPaper.put(confID, new HashSet<Manuscript>());
			confPaper.get(confID).add(thePaper);
		}
		
		if(!jobPapers.isEmpty() && jobPapers.containsKey(theTitle)) {
			
			jobPapers.get(theTitle).put(confID, confPaper.get(confID));
		} else {
			
			jobPapers.put(theTitle, new HashMap<Integer, Set<Manuscript>>());
			jobPapers.get(theTitle).put(confID, confPaper.get(confID));
		}

		this.addJob(confID, theTitle);
		if(theTitle == Job.Title.SUBPROGRAMCHAIR) subPCCount++;
	}
	
	
	/**
	 * This method returns the users papers for that conference and title only
	 * 
	 * @author mmills
	 * @param confID
	 * @param theTitle
	 * @return
	 */
	public Set<Manuscript> getJobPapers(int confID, Title theTitle) {		

		if(jobPapers.isEmpty() || !jobPapers.containsKey(theTitle) || !jobPapers.get(theTitle).containsKey(confID)) {
			return null;
		}
		return jobPapers.get(theTitle).get(confID);
	}
	

	public List<Title> getTitles(int conferenceID) {
		if (conferenceMatchJob(conferenceID) != null) {
			return conferenceMatchJob(conferenceID).getTitles();
		} else {
			ArrayList<Title> list = new ArrayList<Title>();
			list.add(Title.GENERAL);
			return list;
		}
	}
	
	public int getSubPCCount() {
		return subPCCount;
	}
	
	/**
	 * This methods returns a Job if there is one for this User
	 * that matches the conference ID that is passed.
	 * @param conferenceID the ID to be checked for the conference object
	 * @return Job the Job that matches the conference ID, or null otherwise.
	 * 
	 * @author BrentYoung
	 */
	public Job conferenceMatchJob(int conferenceID) {
		if (myJobs != null) {
			for (int i = 0; i < myJobs.size(); i++) {
				if (myJobs.get(i).getConferenceID() == conferenceID) {
					return myJobs.get(i);
				}
			}
		}
		return null;
	}
}
