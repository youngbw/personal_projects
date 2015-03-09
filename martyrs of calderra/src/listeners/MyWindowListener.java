package listeners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import layout.BattleGUI;
import model.AbstractHero;

public class MyWindowListener implements WindowListener, ActionListener {

	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	private static final Dimension SIZE_OFFSET = new Dimension(25, 50);
	
//	Timer myTimer;
	BattleGUI comp;
	AbstractHero hero;
	
	public MyWindowListener(AbstractHero hero, BattleGUI comp) {
		this.hero = hero;
		this.comp = comp;
//		myTimer = new Timer(0, this);
//		myTimer.setDelay(1);
	}


	private void step() {
		while (comp.getWidth() != comp.getPreferredSize().width) {
			comp.repaint();
			comp.setLocationRelativeTo(null);
			boolean addWidth = false;
			if (comp.getWidth() <= comp.getPreferredSize().width - SIZE_OFFSET.width) {
				addWidth = true;
			}
			if (addWidth) {
				comp.setSize(new Dimension(comp.getWidth() + SIZE_OFFSET.width, comp.getHeight()));
			} else {
				break;
			}
			
			
		} 
		while (comp.getHeight() != comp.getPreferredSize().height) {
			comp.repaint();
			comp.setLocationRelativeTo(null);
			boolean addHeight = false;
			if (comp.getHeight() <= comp.getPreferredSize().height - SIZE_OFFSET.height) {
				addHeight = true;
			}
			if (addHeight) {
				comp.setSize(new Dimension(comp.getWidth(), comp.getHeight() + SIZE_OFFSET.height));
			} else {
				break;
			}
		}
		comp.setLocationRelativeTo(null);
		comp.repaint();
	}
	
	
	@Override
	public void windowOpened(WindowEvent e) {
//		comp.setIgnoreRepaint(true);
		comp.setSize(new Dimension(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 5));
		step();
//		myTimer.start();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.hero.exitBattle();
//		this.hero.changed("Battle Show");
//		this.hero.changed();
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		step();
		
	}
	
	
	

}
