package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import view.AuthorConferenceView;
import view.ViewPanel;

/**
 * This class represents the selection of a table cell, and the allocation of the value as the
 * value to be acted upon.
 * @author BrentYoung
 *
 */
public class MyTableMouseListener implements java.awt.event.MouseListener {

	private JTable myTable;
	private ViewPanel theView;
	
	public MyTableMouseListener(JTable table, ViewPanel theView) {
		myTable = table;
		this.theView = theView;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		theView.setSelectedPaperTitle((String)(myTable.getValueAt(myTable.getSelectedRow(), 0)));

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
