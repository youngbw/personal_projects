package listeners;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

/**
 * This listener class handles the look and feel of the buttons in this program.  
 * @author BrentYoung
 *
 */
public class MyButtonMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		((JButton)e.getSource()).setFont(new Font("Times Roman", Font.BOLD, 12));

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((JButton)e.getSource()).setFont(new Font("Times Roman", Font.PLAIN, 12));

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		((JButton)e.getSource()).setBorder(new BevelBorder(BevelBorder.LOWERED));

	}

	@Override
	public void mouseExited(MouseEvent e) {
		((JButton)e.getSource()).setBorder(new EmptyBorder(0, 0, 0, 0));

	}

}
