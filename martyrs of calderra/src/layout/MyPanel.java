package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.AbstractCard;
import model.AbstractHero;
import model.AbstractVillain;


@SuppressWarnings("serial")
public class MyPanel extends JPanel implements Observer {

	public static final Color TRANSPARENT = new Color(20, 20, 20, 150);
	public static final Color FULL_ALPHA = new Color(0, 0, 0, 0);
	public static final String DEFAULT_BACKGROUND = "./src/resources/inventoryPanelBackground.jpg";
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
//	protected AbstractHero hero;
	public String src;
	boolean enabled;
	public boolean paintDamage;
	public static Queue<Integer> damage;
	public static Queue<Boolean> missMarker;
	
	public MyPanel() {
		this("");
	}
	
	public MyPanel(String src) {
		this.src = src;
		damage = new ArrayDeque<Integer>();
		missMarker = new ArrayDeque<>();
		paintDamage = false;
		this.setBorder(new LineBorder(Color.BLACK));
		repaint();
	}
	
	
	/**
	 * Repaint the desired image on the panel.
	 */
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
		final Graphics2D g2D = (Graphics2D) theGraphics;
        g2D.drawImage(new ImageIcon(this.src).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        
        if (this instanceof UserInteractive) {
        	g2D.setColor(new Color(50, 50, 50, 100));
        	
        	if (!this.enabled) g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        	
        	g2D.setColor(Color.CYAN);
        	g2D.setFont(new Font("Times Roman", Font.BOLD, 14));
        	g2D.drawString(this.toString(), this.getWidth() / 10, this.getHeight() - 2);
        }
        
//        if (paintDamage) {
//        	Font font = new Font("Times Roman", Font.BOLD, 60);
//        	g2D.setFont(font);
//        	g2D.setColor(Color.BLACK);
////        	if (!MyPanel.damage.isEmpty()) {
////        		if (missMarker.poll() == false) {
////            		MyPanel.damage.poll();
////            		g2D.drawString("MISS", this.getWidth() - this.getWidth() / 4, this.getHeight() / 5);
////            		g2D.setColor(Color.YELLOW);
////            		g2D.drawString("MISS", this.getWidth() - this.getWidth() / 4 - 5, this.getHeight() / 5 + 2);
////            		System.out.println(damage.size() + " damage size after poll)");
////            	} else {
////            		System.out.println(damage.size() + " damage size in else statement)");
////            		int theDamage = damage.poll();
////    				g2D.drawString("" +theDamage, this.getWidth() - this.getWidth() / 4, this.getHeight() / 5);
////    				g2D.setColor(Color.YELLOW);
////    				g2D.drawString("" + theDamage, this.getWidth() - this.getWidth() / 4 - 5, this.getHeight() / 5 + 2);
////            	}
////        	}
//        	
//        	paintDamage = false;
//        }
	}
	
	public void setEnabled() {
		this.enabled = true;
		repaint();
	}
	
	public void setDisabled() {
		this.enabled = false;
		repaint();
	}
	
	public void setSource(String src) {
		
		this.src = src;
		this.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

//	public void setHero(AbstractHero theHero) {
//		this.hero = theHero;
//		src = this.hero.getImageSource();
////		((AbstractHero) theHero).addObserver(this);
//		repaint();
//	}
//
//	@Override
//	public void update(Observable o, Object arg) {
//		if (arg instanceof AbstractHero && !(o instanceof AbstractVillain)) {
//			this.hero = (AbstractHero)arg;
//		}
//		
//	}


	
	
	
	
	
}
