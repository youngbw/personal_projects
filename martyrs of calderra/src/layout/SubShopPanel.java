package layout;

import inventory.InventoryGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.AbstractHero;
import model.AbstractCard;
import model.Consumable;

@SuppressWarnings("serial")
public class SubShopPanel extends MyPanel implements MouseListener, Observer {

	AbstractHero hero;
	AbstractCard card;
	InfoDisplayPanel infoPanel;
	
	public SubShopPanel(AbstractHero hero, AbstractCard card) {
		this.card = card;
		this.hero = hero;
		infoPanel = new InfoDisplayPanel(this.card);
		setup();
	}
	
	private void setup() {
		setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.CYAN));
		this.setBackground(new Color(20, 20, 20, 200));
		MyPanel picture = new MyPanel(card.getSrc());
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setBackground(new Color(20, 20, 20, 200));
		lowerPanel.setBorder(new LineBorder(Color.CYAN));
		lowerPanel.setSize(new Dimension(this.getWidth(), this.getHeight() / 4));
		
		JLabel costLabel = new JLabel("Cost: " + card.getCost());
		costLabel.setForeground(Color.CYAN);
		lowerPanel.add(costLabel);
		
		add(lowerPanel, BorderLayout.SOUTH);
		add(picture, BorderLayout.CENTER);
		this.addMouseListener(this);
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try {	
				Class<?> another = Class.forName(card.getClass().getName());
				Constructor<?> con = another.getConstructor(AbstractHero.class);
				Object real = con.newInstance(hero);
				hero.buyCard((AbstractCard) real);
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof AbstractHero) {
			this.hero = (AbstractHero) arg;
		}
		
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
		if (infoPanel != null && !infoPanel.isVisible()) {
			infoPanel.setLocation(this.getLocationOnScreen().x - infoPanel.getWidth() > 0 ? this.getLocationOnScreen().x - infoPanel.getWidth() : this.getLocationOnScreen().x + this.getWidth(),
					this.getLocationOnScreen().y - infoPanel.getHeight() > 0 ? this.getLocationOnScreen().y - infoPanel.getHeight() : this.getLocationOnScreen().y + this.getHeight());
			infoPanel.setVisible(true);
		}
				
	}

	@Override
	public void mouseExited(MouseEvent e) {
		infoPanel.dispose();
		
	}
	
	
}
