package model;

import interfaces.AttackCard;
import interfaces.Magical;
import interfaces.Physical;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JDialog;

import layout.CalderraGUI;
import layout.MyPanel;
import layout.MyPopupMenu;


@SuppressWarnings("serial")
public class AbstractCard extends JPanel implements MouseListener, Observer, java.io.Serializable {

	
	public static final int DELAY = 300;
	
	public static final int INCREMENT = 1;
	public static final int DECREMENT = -1;
	
	public static String[] gif1;
	public static String[] gif2;
	

	JDialog dia;
	MyPanel panel;
	AbstractCard card;
	Timer myTimer;
	int k;
//	static int numRunning;
	boolean heroAttackUsed;
	boolean enemyAttackUsed;
	
	
	private static int classId = 0;
	private int id;
	public AbstractHero hero;
	private String name;
	private String src;
	protected int cost;
	protected int quantity;
	//private int tier??
	
	//possible boolean values to help know where this card is in the game
	public boolean inShop; //may not need
	public boolean isEquipped;
	public boolean isInBag;
	public boolean isInBattle;
	public static MyPopupMenu pop;
	public boolean enabled;
	
	String healType;
	int periodicIncrease;
	int trueDamage;
	
//	private boolean drawEquip;
	public boolean mouseOn;
	
	public AbstractCard() {
		this(null, "", "");
		
	}
	
	public AbstractCard(AbstractHero hero, String src, String name) {
//		super(src);
		this.src = src;
		this.name = name;
		
		this.hero = hero;
		
		this.inShop = false;
		this.isInBag = false;
		this.isEquipped = false;
		this.isInBattle = false;
		this.enabled = true;
		this.mouseOn = false;
		healType = Heal.HEAL_TYPE_NONE;
		periodicIncrease = 0;
		
		
		//FOR FIGHT GIF
		dia = new JDialog();
		dia.setAlwaysOnTop(true);
		dia.setSize(CalderraGUI.SCREEN_WIDTH / 2, CalderraGUI.SCREEN_HEIGHT / 2);
		dia.setLayout(new BorderLayout());
		dia.setLocationRelativeTo(this);
		dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		panel = new MyPanel();
//		this.hero.addObserver(panel);
		dia.add(panel);
		myTimer = new Timer(0, new MyTimerListener());
		myTimer.setDelay(DELAY);
		myTimer.setRepeats(true);
		k = 0;
		//END FIGHT GIF
		
		this.id = classId++;
		classId++;
		quantity = 0;
		pop = new MyPopupMenu(this.hero, this);
		this.hero.addObserver(this);
		this.addMouseListener(this);
	}
	
	public AbstractCard(AbstractCard card) {
		this(card.hero, card.src, card.getName());
		this.cost = card.cost;
	}
	
	

//	/**
//	 * Repaint the desired image on the panel.
//	 */
//	@Override
//    public void paintComponent(final Graphics theGraphics) {
//		super.paintComponent(theGraphics);
//		final Graphics2D g2D = (Graphics2D) theGraphics;
//        g2D.drawImage(new ImageIcon(src).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
//         if (drawEquip) {
//        	 g2D.setColor(Color.YELLOW);
//        	 g2D.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, 2));
//        	 g2D.drawLine(2, 2, x, this.getHeight());
//    		 g2D.drawLine(this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight() - x);
//    		 
//    		 g2D.drawLine(x, this.getHeight(), this.getWidth(), this.getHeight());
//    		 g2D.drawLine(this.getWidth() - x, 1, 1, 1);
//        	 
//    		 if (x == this.getWidth()) time.stop();
//         }
        
//	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getPeriodicIncrease() {
		return periodicIncrease;
	}
	
	public String[] getGifSrc() {
		return null;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setEquipped() {
//		time.start();
//		drawEquip = true;
		this.isEquipped = true;
		this.isInBag = false;
//		repaint();
	}
	
	public void setUnEquipped() {
		this.isEquipped = false;
		this.isInBag = true;
	}
	
	public void cardBought() {
		this.inShop = false;
		this.isInBag = true;
	}
	
	
	public String getSrc() {
		return this.src;
	}
	
	public void fireClick(MouseEvent e) {
		this.mouseClicked(e);
		repaint();
	}
	
	private void step() {
		if (k == 0) {
			dia.setVisible(true);
//			dia.setLocation(0, SCREEN_HEIGHT / 2);
		} else if (k == getGifSrc().length) {
			k = 0;
			dia.dispose();
//			panel.damage = 0;
			panel.paintDamage = false;
			myTimer.stop();
			this.hero.changed("Attack Completed");
//			numRunning--;
//			if (numRunning == 0) this.hero.changed("Battle Show");
			return;
		} else if (k == getGifSrc().length - 1) {
			panel.paintDamage = true;
//			panel.damage = trueDamage;
		}
		panel.src = getGifSrc()[k];
		panel.repaint();
//		dia.setVisible(true);
		k++;
//		if (k == getGifSrc().length) panel.paintDamage = false;
	}
	
//	private void playGifs() {
//		//combine and leave room for extra shots of 'incoming panels'
//		String[] comb = new String[gif1.length + gif2.length];
//		int i = 0, j = 0;
//		while (i < gif1.length) {
//			comb[i] = gif1[i];
//			i++;
//		}
//		while (j < gif2.length) {
//			comb[i + j] = gif2[j];
//			j++;
//		}
//		
//		
//		
//		if (k == 0) {
//			dia.setVisible(true);
//		} else if (k == comb.length) {
//			k = 0;
//			dia.dispose();
//			myTimer.stop();
//			this.hero.changed("Battle Show");
//			return;
//		} else if (k == comb.length - 1 || k == comb.length - 1 - gif2.length) {
//			panel.paintDamage = true;
//		}
//		panel.src = comb[k];
//		panel.repaint();
//		k++;
//		//extra paint check
//		if (panel.paintDamage) panel.paintDamage = false;
//	}
	
	public void progressAttack() {
		if (this.hero.getCurrentMagicPower() >= ((AttackCard)this).getCruxCost() && !this.isInBag) {
			Random rand = new Random();
			
			if ((this instanceof Physical || this instanceof Magical) && this.hero.enemy != null) {
				int damage = this.hero.getHit(this);
				int trueDamage = this.hero.enemy.takeHit(damage);
				MyPanel.damage.offer(trueDamage);
				
			} else if (this instanceof Heal) {
				//ACTUAL HEAL TAKES PLACE IN HEAL METHOD OF HERO
				int heal = this.hero.heal(this, this.getHealType());
			}
			myTimer.start();
//			this.hero.cardGif(this);
		}
	}
	
	public void use() {
		this.hero.changed(this);
	}
	
	public void consume() {
//		((Consumable)this).decrementQuantity();
	}
	
	public String getHealType() {
		return this.healType;
	}
	
	@Override
	public boolean equals(Object o ) {
		if (o == null) return false;
		if (o instanceof AbstractCard) {
			AbstractCard card = (AbstractCard) o;
			if (!this.isInBattle && card.name.equals(this.name) && this.id == card.id) {
				return true;
			} else if (this.isInBattle && this.name.equals(card.name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		mouseOn = true;
		repaint();
	}
	
	public void mouseExited(MouseEvent e) {
		mouseOn = false;
		repaint();
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	
	@SuppressWarnings("unused")
	private class MyTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			panel.damage = trueDamage;
			step();
//			playGifs();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof AbstractHero) {
			this.hero = (AbstractHero) arg;
			this.hero.addObserver(this);
//			this.hero.addObserver(panel);
			if (this.hero.getIsInBattle()) this.isInBattle = true; else this.isInBattle = false;
		} else if (arg instanceof AbstractCard) {
			if (((AbstractCard)arg).equals(this)) {
				myTimer.setInitialDelay(0);
//				k = 0;
//				if (numRunning > 0) {
//					myTimer.setInitialDelay(2000);
//				}
//				numRunning++;
//				myTimer.start();
			}
				
			
		} else if (arg instanceof String) {
			if (((String)arg).equals("fireGifs")) {
				
				myTimer.start();
			}
		}
		
	}
	
}
