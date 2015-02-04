package model;

import java.util.ArrayList;
import java.util.List;

public class Job implements java.io.Serializable {

	static enum Title {
		PROGRAMCHAIR, SUBPROGRAMCHAIR, REVIEWER, AUTHOR;
	}
	
	private int conferenceID;
	private List<Title> myTitles;
	
	public Job() {
		conferenceID = Integer.MIN_VALUE;
		myTitles = new ArrayList<Title>();
	}
	
	public List<Title> getTitles() {
		return this.myTitles;
	}
	
	public int getConferenceID() {
		return this.conferenceID;
	}
	
	public void addTitle(Title theTitle) {
		if (!this.myTitles.contains(theTitle)) {
			this.myTitles.add(theTitle);
		}
	}
	
	public void setConferenceID(int theID) {
		if (this.conferenceID == Integer.MIN_VALUE) {
			this.conferenceID = theID;
		}
	}
}
