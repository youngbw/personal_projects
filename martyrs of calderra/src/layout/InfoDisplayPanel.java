package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InfoDisplayPanel extends JDialog implements MouseListener {

	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	boolean mouseOn;
	Object object;
	
	public InfoDisplayPanel(Object object) {
		this.setLayout(new BorderLayout());
		this.object = object;
		mouseOn = false;
		setup(object);
	}
	
	
	private void setup(Object object) {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(SCREEN_WIDTH / 6, SCREEN_WIDTH / 6));
		
		this.setResizable(false);
		this.setFocusable(false);
		this.setAlwaysOnTop(true);
		this.setFocusableWindowState(false);
		
		MyPanel panel = new MyPanel();
		panel.setLayout(new BorderLayout());
		
		Color color = new Color(20, 20, 20, 0);
		
		JScrollPane pane = new JScrollPane();
		
//		pane.getViewport().setBackground(new Color(20, 20, 20, 0));
//		pane.setBackground(new Color(0, 0, 0, 0));
		
		TextArea area = new TextArea("", 20, 20, TextArea.SCROLLBARS_NONE);
		area.setForeground(Color.white);
		area.setBackground(color);
		area.setEditable(false);
		area.setFont(new Font("Times Roman", Font.BOLD, 14));
		area.setText(object.toString());
		
		pane.add(area);
		pane.setViewportView(area);
//		pane.setOpaque(true);
		panel.add(pane, BorderLayout.CENTER);
		area.addMouseListener(this);
//		this.addMouseListener(this);
		this.add(panel);		
	}	
	
	public void attemptDispose() {
		if (!mouseOn) {
			this.dispose();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOn = true;
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		mouseOn = false;
		this.attemptDispose();
	}
	
}
