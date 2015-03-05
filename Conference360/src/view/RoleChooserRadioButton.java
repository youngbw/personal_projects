package view;

import javax.management.remote.SubjectDelegationPermission;
import javax.swing.JRadioButton;

import listeners.RoleListener;
import model.ConferenceClient;
import model.Job.Title;

public class RoleChooserRadioButton extends JRadioButton {

	private Title myTitle;
	
	public RoleChooserRadioButton(ClientGui theFrame, ConferenceClient theClient, Title theTitle) {
		super(theTitle.toString());
		myTitle = theTitle;

//		if (this.myTitle == theClient.getUser().getCurrentlyViewedAs()) this.setSelected(true);

	}
	
	public Title getMyTitle() {
		return this.myTitle;
	}
	
	
	
}
