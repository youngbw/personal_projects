package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ClientGeneralView;
import view.ClientGui;

/**
 * This class represents the listener for the back button in this program.
 * @author BrentYoung
 *
 */
public class BackButtonActionListener implements ActionListener {
	/**
	 * Reference to the frame.
	 */
	private ClientGui theFrame;
	
	/**
	 * Stores a reference to the frame.
	 * @param theFrame the frame
	 */
	public BackButtonActionListener(ClientGui theFrame) {
		this.theFrame = theFrame;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(theFrame.getCurrentComp() instanceof ClientGeneralView)) {
			theFrame.moveToPreviousComp();
		}
		
	}

}
