package listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;

import layout.CalderraGUI;

public class MyFrameFocusListener implements FocusListener {

	private CalderraGUI myComp;
	
	public MyFrameFocusListener(CalderraGUI gui) {
		myComp = gui;
	}
	
	
	@Override
	public void focusGained(FocusEvent e) {
		if (!e.getComponent().equals(myComp) && !myComp.characterSelectOn) {
			myComp.requestFocus();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getComponent().equals(myComp) && !myComp.characterSelectOn) {
			myComp.requestFocus();
		}
		
	}

}
