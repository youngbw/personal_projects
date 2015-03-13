package talentTree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import layout.CalderraGUI;
import layout.InfoDisplayPanel;
import layout.MyPanel;
import model.AbstractHero;
import model.Hero;

public abstract class AbstractTalent extends MyPanel implements Observer, MouseListener {

	protected static final int DECREMENT_VALUE = -1;
	protected static final int INCREMENT_VALUE = 1;
	protected static int totalPointsAvailable;
	
	List<AbstractTalent> talentChildren;
	List<AbstractTalent> nonConcurrentChildren;
	
	InfoDisplayPanel infoPanel;
	
	protected int maxPoints;
	protected int currentPoints;
	protected boolean enabled;
	protected String name;
	protected String category;
	protected double amountPerPoint;
	protected int numFlowingInto;
	
	protected AbstractHero hero;
	
	public AbstractTalent(AbstractHero hero, int points, double amountPerPoint, String theName, String category, String src) {
		super(src);
		numFlowingInto = 0;
		this.enabled = false;
		talentChildren = new ArrayList<AbstractTalent>();
		nonConcurrentChildren = new ArrayList<AbstractTalent>();
		this.amountPerPoint = amountPerPoint;
		this.maxPoints = points;
		this.name = theName;
		this.category = category;
		this.currentPoints = 0;
		totalPointsAvailable = 0;
		this.hero = hero;
		this.addMouseListener(this);
		infoPanel = new InfoDisplayPanel(this, true);
		this.setup();
	}
	
	
	private void setup() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH / 8, SCREEN_HEIGHT / 8));
		
	}
	
	public void addTalentChild(AbstractTalent talent) {
		talentChildren.add(talent);
	}
	
	public void enable() {
		this.enabled = true;
		repaint();
	}
	
	public void disable() {
		this.enabled = false;
		repaint();
	}
	
	public boolean increment() {
		boolean able = false;
		if (currentPoints < maxPoints && enabled && totalPointsAvailable > 0) {
			if (this.category != null) this.hero.getPowerUps().put(this.category, this.hero.getPowerUps().get(this.category) + this.amountPerPoint);
			currentPoints++;
			totalPointsAvailable--;
			able = true;
			//disable the talents that shouldnt occur at the same time
			if (currentPoints == 1) {
				for (int i = 0; i < nonConcurrentChildren.size(); i++) {
					nonConcurrentChildren.get(i).disable();
				}
				for (int i = 0; i < talentChildren.size(); i++) {
					talentChildren.get(i).numFlowingInto++;
				}
			}
			
			if (this.currentPoints == maxPoints) {
				for (int i = 0; i < talentChildren.size(); i ++) {
					talentChildren.get(i).enable();
				}
			}
		}
		System.out.println(this.name + " increased to " + this.currentPoints);
		repaint(); // for painting new point value
		return able;
	}
	
	public boolean decrement() {
		if (currentPoints == 0) return false;
		boolean able = true;
		boolean moreTalentsFeeding = false;
		for (int i = 0; i < talentChildren.size(); i++) {
			if (talentChildren.get(i).numFlowingInto > 1) moreTalentsFeeding = true;
			if (talentChildren.get(i).currentPoints > 0) able = false;
		}
		if (currentPoints > 0 && enabled && (able || moreTalentsFeeding)) {
			currentPoints--;
			totalPointsAvailable++;
			if (currentPoints == maxPoints - 1) {
				
				for (int i = 0; i < talentChildren.size(); i++) {
					if (talentChildren.get(i).numFlowingInto == 1) {
						talentChildren.get(i).numFlowingInto--;
					}
					if (talentChildren.get(i).numFlowingInto == 0) {
						talentChildren.get(i).disable();
					} 
				}
			}
			if (currentPoints == 0) {
				for (int i = 0; i < talentChildren.size(); i++) {
					if (talentChildren.get(i).numFlowingInto > 1) talentChildren.get(i).numFlowingInto--;
					
				}
				for (int i = 0; i < nonConcurrentChildren.size(); i++) {
					nonConcurrentChildren.get(i).enable();
				}
			}
		}
		System.out.println(this.name + " decreased to " + this.currentPoints);
		repaint();
		return able;
	}
	
	public static void incrementTotalPoints(int points) {
		totalPointsAvailable += points;
	}
	
	public int getPoints() {
		return this.currentPoints;
	}
	
	public int getMax() {
		return this.maxPoints;
	}
	
	public void update(Observable o, Object arg) {
		//For new game, need a different one for load
		if (arg instanceof AbstractHero) {
			this.hero = (AbstractHero) hero;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			this.increment();
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			this.decrement();
		}
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e) {
		if (infoPanel != null && !infoPanel.isVisible()) {
			infoPanel.setLocation(this.getLocationOnScreen().x - infoPanel.getWidth() > 0 ? this.getLocationOnScreen().x - infoPanel.getWidth() : this.getLocationOnScreen().x + this.getWidth(),
					this.getLocationOnScreen().y - infoPanel.getHeight() > 0 ? this.getLocationOnScreen().y - infoPanel.getHeight() : this.getLocationOnScreen().y + this.getHeight());
			infoPanel.setVisible(true);
		}
	}


	@Override
	public void mouseExited(MouseEvent e) {
		if (infoPanel != null && infoPanel.isVisible()) {
			infoPanel.dispose();
		}
	}
	
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
		final Graphics2D g2D = (Graphics2D) theGraphics;
        g2D.drawImage(new ImageIcon(src).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        if (!enabled) {
        	g2D.setColor(new Color(250, 250, 250, 200));
        	g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        
        int width = this.getWidth() / 3;
        g2D.setColor(new Color(0, 0, 0, 155));
        g2D.fillRect(0, 0, this.getWidth() / 3, this.getHeight() / 9);
        g2D.setColor(Color.white);
        g2D.setFont(new Font("Times New Roman", Font.BOLD, 12));
        g2D.drawString(currentPoints + " / " + maxPoints, width / 10, this.getHeight() / 10 + 1);
	}
	
}
