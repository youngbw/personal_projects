package inventory;

import interfaces.AttackCard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import layout.CalderraGUI;
import layout.InfoDisplayPanel;
import layout.MyPanel;
import layout.MyPopupMenu;
import model.AbstractCard;
import model.AbstractHero;
import model.Consumable;
import model.Heal;

@SuppressWarnings("serial")
public class InventoryPanel extends MyPanel implements MouseListener {

		protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
		protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
		protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
		protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
		
		public static final String DEFAULT_BACKGROUND = "./src/resources/inventoryPanelBackground.jpg";
		
		protected CalderraGUI controller;
		protected String src;
		protected AbstractCard card;
		
		private InfoDisplayPanel infoPanel;
		
		public InventoryPanel(CalderraGUI controller) {
			this("", controller);
		}
		
		public InventoryPanel(String src, CalderraGUI controller) {
			this.src = src;
			this.controller = controller;
			this.setBorder(new LineBorder(Color.CYAN));
			this.setBackground(new Color(0, 0, 0, 0));
			this.addMouseListener(this);
		}
		
		public InventoryPanel(AbstractCard card, CalderraGUI controller) {
			this.card = card;
			this.controller = controller;
//			this.card.isInBag = true;
//			this.card.isEquipped = false;
			if (card == null) this.src = DEFAULT_BACKGROUND; else this.src = card.getSrc();
			infoPanel = new InfoDisplayPanel(this.card, controller.infoDisplay.toShow);
			this.setBackground(new Color(0, 0, 0, 0));
			this.addMouseListener(this);
		}
		
		
		/**
		 * Repaint the desired image on the panel.
		 */
		@Override
	    public void paintComponent(final Graphics theGraphics) {
	        super.paintComponent(theGraphics);
			final Graphics2D g2D = (Graphics2D) theGraphics;
	       
	        if (this.card != null && !src.equals("")) {
	        	g2D.drawImage(new ImageIcon(src).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	        	if (this.card.getQuantity() > 1) {
	           	 int width = this.getWidth() / 3;
//	                g2D.setColor(Color.BLACK);
//	                g2D.fillRect(0, 0, this.getWidth() / 3, this.getHeight() / 9);  
	                g2D.setColor(Color.BLACK);
	                g2D.setFont(new Font("Times New Roman", Font.BOLD, 16));
	                g2D.drawString("" + this.card.getQuantity(), this.getWidth() / 30, this.getHeight() - 5);
	            }
	        	
	        	if (this.card.mouseOn) {
	        		g2D.setColor(new Color(150, 150, 150, 100));
	        		g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
	        	}

	        } else {
	        	g2D.drawImage(new ImageIcon(DEFAULT_BACKGROUND).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
//	        	g2D.setColor(MyPanel.TRANSPARENT);
//	        	g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
	        	
	        }
		}

//		public void setHero(AbstractHero hero) {
//			this.hero = hero;
//		}
		
		public AbstractCard getCard() {
			return this.card;
		}
		
		public void addCard(AbstractCard card) {
			this.card = card;
//			this.card.isInBag = true;
			this.src = card.getSrc();
			infoPanel = new InfoDisplayPanel(this.card, controller.infoDisplay.toShow);
			repaint();
		}
		
		public void removeCard() {
//			if (this.card != null) this.card.isInBag = false;
			this.src = "";
			if (infoPanel != null) {
				infoPanel.dispose();
			}
//			infoPanel = null;
			this.card = null;
			repaint();

		}

		@Override
		public void mouseClicked(MouseEvent e) {
//			if (this.card != null) {
//				this.card.fireClick(e);
//				repaint();
//			}
			
			if (this.card != null && this.card.enabled) {
//				System.out.println("Card clicked");
//				System.out.println("Card in battle? " + this.card.isInBattle);
				
				if (card.pop != null && card.pop.isVisible()) card.pop.setVisible(false);
				if (e.getButton() == MouseEvent.BUTTON3) {
					card.pop.setVisible(false);
					card.pop = new MyPopupMenu(this.controller, card);
					card.pop.setLocation(e.getXOnScreen(), e.getYOnScreen());
					card.pop.setVisible(true);
					card.mouseOn = false;
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					if (this.card.inShop) {
						this.controller.getHero().buyCard(this.card);
					} else {
						if ((this.card instanceof AttackCard && this.card.isInBattle)) {
							this.controller.getHero().attackToBeUsed = this.card;
							this.controller.getEnemy().setAttackToBeUsed();
							this.controller.getHero().changed("attacks chosen");
						} else if (this.card instanceof Consumable) {
							((Consumable)this.card).consume();
						}
					}
				}
				repaint();
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
			if (this.card != null) {
				this.card.mouseEntered(e);
				infoPanel.tryToSetVisible(this.getLocationOnScreen().x - infoPanel.getWidth() > 0 ? this.getLocationOnScreen().x - infoPanel.getWidth() : this.getLocationOnScreen().x + this.getWidth(),
						this.getLocationOnScreen().y - infoPanel.getHeight() > 0 ? this.getLocationOnScreen().y - infoPanel.getHeight() : this.getLocationOnScreen().y + this.getHeight());
				repaint();
			}
			if (infoPanel != null && !infoPanel.isVisible()) {
//				infoPanel.setLocation();
				
//				infoPanel.setVisible(true);
			}
			
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (this.card != null) {
				this.card.mouseExited(e);
				repaint();
			}
			if (infoPanel != null && infoPanel.isVisible()) {
				infoPanel.dispose();
			}
			
		}

}
