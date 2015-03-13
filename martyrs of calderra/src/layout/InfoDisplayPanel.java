package layout;

import java.awt.BorderLayout;
import java.awt.Canvas;
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

@SuppressWarnings("serial")
public class InfoDisplayPanel extends JDialog implements MouseListener {

	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	public static boolean toShow;
	public boolean mouseOn;
	public boolean isPropertyViewer;
	Object object;
	
	private TextArea area;
	
	public InfoDisplayPanel(Object object, boolean isShow) {
		this.setLayout(new BorderLayout());
		this.object = object;
		toShow = isShow;
		mouseOn = false;
		isPropertyViewer = false;
		setup(object);
	}
	
	
	private void setup(Object object) {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(SCREEN_WIDTH / 6, SCREEN_HEIGHT / 6));
		
		this.setResizable(false);
		this.setFocusable(false);
		this.setAlwaysOnTop(true);
		this.setFocusableWindowState(false);
		
		MyPanel panel = new MyPanel();
		panel.setLayout(new BorderLayout());
		
//		Color color = new Color(20, 20, 20, 0);
		Color color = Color.WHITE;
		Color contrastColor = Color.BLACK;
		
		JScrollPane pane = new JScrollPane();
		
//		pane.getViewport().setBackground(new Color(20, 20, 20, 0));
//		pane.setBackground(new Color(0, 0, 0, 0));
		
		area = new TextArea("", 20, 20, TextArea.SCROLLBARS_NONE);
		area.setForeground(contrastColor);
		area.setBackground(color);
		area.setEditable(false);
		area.setFont(new Font("Times Roman", Font.BOLD, 14));
		if (object != null) area.setText(object.toString());
		
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
	
	public void setDisplayText(String text) {
		this.area.setText(text);
	}
	
	public void setToShow(boolean shouldShow) {
		toShow = shouldShow;
	}

	public void tryToSetVisible(int x, int y) {
		if (this.isPropertyViewer) {
			this.setSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 4));
			this.setLocation(SCREEN_WIDTH - this.getWidth() / 2 * 3, SCREEN_HEIGHT - this.getHeight() / 2 * 3);
		} else {
			this.setLocation(x, y);
		}
		this.setVisible(toShow);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOn = true;
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		mouseOn = false;
		if (!this.isPropertyViewer) {
			this.attemptDispose();
		}
	}
	
}
