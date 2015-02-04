package model;

import java.util.List;
import java.util.Stack;

public class Manuscript {

	private String title;
	private int paperID;
	
	//Stack is useful because in cases where we need to retrieve a specific ID rather than merely
	//check that it is anywhere in the stack it will be useful to just peek at the top element.
	private Stack<Integer> conferenceID;
	
	public Manuscript() {
		conferenceID = new Stack<>();
		paperID = Integer.MIN_VALUE;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getPaperID() {
		return this.paperID;
	}
	
	public int getMostRecentConferenceID() {
		return (Integer) ((Stack<Integer>) conferenceID).peek();
	}
}
