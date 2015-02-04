package layout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import model.AbstractHero;
import model.Hero;

@SuppressWarnings("serial")
public class AttributeButton extends JButton implements ActionListener, Observer {

	public static int points;
	public static int addedPoints;
	private static boolean pointsAdded;
	private int maxDecrementValue;
	
	
	private boolean increment;
	private String attribute;
	private AbstractHero hero;
	
	public AttributeButton(AbstractHero hero, String attribute, boolean isIncrement) {
		this.hero = hero;
		this.attribute = attribute;
		this.increment = isIncrement;
		AttributeButton.pointsAdded = false;
		AttributeButton.points = 0;
		AttributeButton.addedPoints = 0;
		maxDecrementValue = this.hero.getAttributes().get(attribute);
		this.hero.addObserver(this);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		
		if (increment) {
			if (AttributeButton.points > 0 && (this.hero.getAttributes().get(attribute) < 100) || attribute.equals("maxHealth")) {
				this.hero.getAttributes().put(attribute, this.hero.getAttributes().get(attribute) + 1);
				AttributeButton.addedPoints++;
				AttributeButton.points--;
				AttributeButton.pointsAdded = false;
				this.hero.getAttributes().put("points", this.hero.getAttributes().get("points") - 1);
			}
		} else {
			if (this.hero.getAttributes().get(attribute) > maxDecrementValue && AttributeButton.addedPoints > 0) {
				this.hero.getAttributes().put(attribute, this.hero.getAttributes().get(attribute) - 1);
				AttributeButton.points++;
				AttributeButton.addedPoints--;
				this.hero.getAttributes().put("points", this.hero.getAttributes().get("points") + 1);
			}
		}
		this.hero.changed("stats");
	}

	
	public void setHero(AbstractHero theHero) {
		this.hero = theHero;
//		this.hero.addObserver(this);
		pointsAdded = false;
		points = 0;
		maxDecrementValue = this.hero.getAttributes().get(attribute);
		update(this.hero, this.hero.getAttributes().get("points"));
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Integer) {
			if (!AttributeButton.pointsAdded) {
				AttributeButton.points += (int) arg;
				AttributeButton.pointsAdded = true;
//				System.out.println((int)arg + "points");
			}
			if (increment) {
				this.setEnabled(true);
				maxDecrementValue = this.hero.getAttributes().get(attribute);
				
			} else {
				maxDecrementValue = this.hero.getAttributes().get(attribute);
			}
		}
		if (AttributeButton.points == 0) {
			this.setEnabled(false);
			AttributeButton.addedPoints = 0;
			AttributeButton.points = 0;
			AttributeButton.pointsAdded = false;
		}
		if (AttributeButton.addedPoints == 1 && !increment) this.setEnabled(true);
		if (AttributeButton.addedPoints == 0 && !increment) this.setEnabled(false);
	}
	
}
