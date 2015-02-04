package layout;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class smallStoryPanel extends MyPanel implements MouseListener, UserInteractive {

	public smallStoryPanel(String src) {
		super(src);
		repaint();
	}
	
	public String toString() {
		return "CINEMATIC";
	}

	public void mouseClicked(final MouseEvent e) {
		//small story event
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e){
		if (this.enabled) this.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY));
	}


	@Override
	public void mouseExited(MouseEvent e) {
		if (this.enabled) this.setBorder(new LineBorder(Color.BLACK));
	}
}
