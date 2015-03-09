package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import layout.CalderraGUI;

@SuppressWarnings("serial")
public class Consumable extends AbstractCard implements java.io.Serializable {

	protected static int ID;
	public static final int MAX_AMOUNT = 10;
	public static final String CONSUME_TYPE_POTION = "potion";
	
	public int localID;
	
	public Consumable(CalderraGUI controller) {
		this("", "", controller);
	}
	
	public Consumable(String src, String name, CalderraGUI controller) {
		super(src, name, controller);
		
		this.quantity = 1;
		repaint();
	}
	
//	public Consumable(AbstractCard card) {
//		this(card.hero, card.getSrc(), card.getName());
//		ID++;
//	}
	
	@Override
	public void consume() {
		this.decrementQuantity();
	}
	
	public void incrementQuantity() {
		incrementQuantity(1);
	}
	
	public void incrementQuantity(int amount) {
		
		for (;quantity < MAX_AMOUNT && amount > 0; amount--) {
			this.quantity++;
		}
		if (this.quantity == MAX_AMOUNT) ID++;
		repaint();
	}
	
	public void decrementQuantity() {
		this.quantity--;
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Consumable) {
			Consumable card = (Consumable)o;
			if (this.getName().equals(card.getName()) && this.localID == card.localID) {
				return true;
			}
		}
		return false;
	}
	
//	/**
//	 * Repaint the desired image on the panel.
//	 */
//	@Override
//    public void paintComponent(final Graphics theGraphics) {
//        super.paintComponent(theGraphics);
//		final Graphics2D g2D = (Graphics2D) theGraphics;  
//		g2D.drawImage(new ImageIcon(this.getSrc()).getImage(), this.getWidth() - 5, this.getHeight() - 5, null);
//    	g2D.setColor(Color.BLACK);
//		g2D.setFont(new Font("Times Roman", Font.BOLD, 16));
//        g2D.drawString("" + this.quantity, 0, 0);
//	}
	
	
	@Override
	public String toString() {
		return super.toString();
	}
}
