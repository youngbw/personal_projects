package layout;

import inventory.InventoryPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import listeners.MyWindowListener;
import model.AbstractCard;
import model.AbstractHero;
import model.AbstractVillain;
import model.Heal;
import model.Warrior;

public class BattleGUI extends JDialog implements Observer {

	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	HeroDisplayPanel displayHero;
	HeroDisplayPanel displayVillain;
	AbstractHero hero;
	AbstractVillain villain;
	
	AbstractCard heroAttack;
	AbstractCard enemyAttack;
	
	public boolean heroAttackUsed;
	public boolean villainAttackUsed; 
	
	
	MyWindowListener myListener;
	ArrayList<InventoryPanel> panels;
	ArrayList<InventoryPanel> theVillainPanels;
	
	public BattleGUI(AbstractHero hero) {
		heroAttack = null;
		enemyAttack = null;
		heroAttackUsed = false;
		villainAttackUsed = false;
		
		
		this.hero = hero;
		this.hero.addObserver(this);
		this.hero.enterBattle();
		panels = new ArrayList<>();
		theVillainPanels = new ArrayList<>();
		
		this.villain = new AbstractVillain(new Warrior("Harkin"));
		this.hero.enemy = this.villain;
		this.villain.enemy = this.hero;
		this.hero.enemy.addObserver(this);

		
		this.displayHero = new HeroDisplayPanel(this.hero);
		this.displayVillain = new HeroDisplayPanel(this.villain);
		
		myListener = new MyWindowListener(this.hero, this);
		this.addWindowListener(myListener);
		
		setup();
	}
	
	
	private void setup() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setAlwaysOnTop(true);
		
//		this.setLocation(SCREEN_WIDTH / 2 - this.getWidth() / 2, SCREEN_HEIGHT / 2 - this.getHeight() / 2);
		
		JPanel heroPanel = new JPanel();
		JPanel attackCardPanel = new JPanel();
		
		//one column for hero, one for villain
		heroPanel.setLayout(new GridLayout(1, 2));
		attackCardPanel.setLayout(new GridLayout(1, 2));
		heroPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		attackCardPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		attackCardPanel.setPreferredSize(new Dimension(SCREEN_WIDTH / 2,SCREEN_HEIGHT / 8));
		
		displayHero.setBorder(new BevelBorder(BevelBorder.LOWERED));
		displayVillain.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, AbstractHero.MAX_ATTACK));
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH / 2,SCREEN_HEIGHT / 8));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		JPanel villainPanel = new JPanel();
		villainPanel.setLayout(new GridLayout(1, AbstractHero.MAX_ATTACK));
		villainPanel.setPreferredSize(new Dimension(SCREEN_WIDTH / 2,SCREEN_HEIGHT / 8));
		villainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		ArrayList<InventoryPanel> villainAttackPanels = new ArrayList<InventoryPanel>();
		ArrayList<InventoryPanel> heroAttackPanels = new ArrayList<InventoryPanel>();
		for (int i = 0; i < AbstractHero.MAX_ATTACK; i++) {
			InventoryPanel iPanel = new InventoryPanel();
			InventoryPanel uPanel = new InventoryPanel();
			heroAttackPanels.add(uPanel);
			panel.add(uPanel);
			villainAttackPanels.add(iPanel);
			villainPanel.add(iPanel);
			panels.add(iPanel);
			panels.add(uPanel);
			theVillainPanels.add(iPanel);
		}
		int i;
		int j;
		for (i = 0, j = 0; i < this.hero.getAttack().size() || j < this.villain.getAttack().size();) {
			if (i < this.hero.getAttack().size()) {
				
				heroAttackPanels.get(i).addCard(this.hero.getAttack().get(i));
				heroAttackPanels.get(i).getCard().isInBattle = true;
//				InventoryPanel cardPanel = new InventoryPanel(card);
//				panel.add(cardPanel);
//				panels.add(cardPanel);
				 i++;
			}
			if (j < this.villain.getAttack().size()) {
//				AbstractCard vCard = ;
//				vCard.isInBattle = true;
				villainAttackPanels.get(j).addCard(this.villain.getAttack().get(j));
				villainAttackPanels.get(j).getCard().enabled = false;
				
//				vCardPanel.getCard().isInBattle = true;
//				villainPanel.add(vCardPanel);
//				panels.add(vCardPanel);
				j++;
			}
			
			
		}

		
		//add hero stuff to fight form
		attackCardPanel.add(panel);
		heroPanel.add(displayHero);
		
		//add villain stuff to fight form
		attackCardPanel.add(villainPanel);
		heroPanel.add(displayVillain);
		
		add(attackCardPanel, BorderLayout.SOUTH);
		add(heroPanel, BorderLayout.CENTER);
		
		for (AbstractCard c: this.hero.getAttack()) {
			System.out.println(c.getName() + " " + c.isInBattle);
		}
		
	}
	
	
	private void chooseOrder() {
		System.out.println("In choose order");
		Random rand = new Random();
		int heroSpeed = rand.nextInt(20) + hero.getSpeed();
		int enemySpeed = rand.nextInt(20) + this.villain.getSpeed();
		if (heroSpeed > enemySpeed) {
			heroAttack.progressAttack();
//			heroAttackUsed = true;
		} else {
//			villainAttackUsed = true;
			enemyAttack = villain.chooseAttack();
			for (int i = 0; i < theVillainPanels.size(); i++) {
				if (theVillainPanels.get(i).getCard().equals(enemyAttack)) {
					theVillainPanels.get(i).mouseClicked(new MouseEvent(this, 1, System.currentTimeMillis(), 0, 0, 0, 0, 0, 1, false, MouseEvent.BUTTON1));
				}
			}
			enemyAttack.progressAttack();
		}
	}
	
	public void setHero(AbstractHero hero) {
		this.hero = hero;
		
		this.removeWindowListener(myListener);
		myListener = new MyWindowListener(this.hero, this);
		this.addWindowListener(myListener);
		this.hero.addObserver(this);
//		setCards();
	}

	public void newFight(AbstractHero hero) {
		this.hero = hero;
		heroAttack = null;
		enemyAttack = null;
		heroAttackUsed = false;
		villainAttackUsed = false;
		this.villain = new AbstractVillain(this.hero);
		this.hero.enemy = this.villain;
		this.hero.enemy.addObserver(this);
		this.villain.enemy = this.hero;
		this.hero.enterBattle();
		displayHero.update(this.hero, this.hero);
		displayVillain.update(this.villain, this.villain);
//		setCards();
	}
	
	private void setCards() {
		
		for (InventoryPanel panel: panels) {
			AbstractCard theCard = panel.getCard();
			if (this.hero != null && theCard != null) {
				theCard.hero = this.hero;
				panel.removeCard();
				panel.addCard(theCard);
			}
			
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (o instanceof AbstractVillain) displayVillain.update(o, arg);
		else displayHero.update(o, arg);
		
		if (arg instanceof String) {
			if (arg.equals("hit")) {
				if (this.hero.getcurrentHealth() < 1 || (this.villain != null && this.villain.getcurrentHealth() < 1)) {
					this.hero.exitBattle();

					for (int i = 0; i < panels.size(); i++) {
						if (panels.get(i).getCard() != null) {
							panels.get(i).getCard().isInBattle = false;
							panels.get(i).mouseExited(new MouseEvent(this, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));
						}
					}
					displayHero.mouseExited(new MouseEvent(this, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));
					displayVillain.mouseExited(new MouseEvent(this, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));
					heroAttackUsed = false;
					villainAttackUsed = false;
					dispose(); //maybe move this to after the visual of the experience gain is finished.
					
				}
			} else if (((String)arg).equals("Battle Show") && (this.hero.getcurrentHealth() > 0 && this.hero.enemy != null)) {
				this.setVisible(true);
			} else if (((String)arg).equals("Battle Show")) {
				this.hero.changed("Battle Over");
				
			} else if (this.hero != null && ((String)arg).equals("Attack Completed")) {
				if (this.hero.enemy != null && o.equals(this.villain)) {
					System.out.println("instance of Villain, set to true");
					villainAttackUsed = true;
					if (this.hero.getcurrentHealth() > 0 && !heroAttackUsed) heroAttack.progressAttack();
					else heroAttackUsed = true;
				} else { 
					System.out.println("Instance of Hero, set to true");
					heroAttackUsed = true;
					if (this.hero.enemy != null && this.hero.enemy.getcurrentHealth() > 0 && !villainAttackUsed) {
						enemyAttack = this.villain.chooseAttack();	
						enemyAttack.progressAttack();
					} else villainAttackUsed = true;
				}
				if (heroAttackUsed && villainAttackUsed) {
					System.out.println("Both true, set to false");
					heroAttackUsed = false;
					villainAttackUsed = false;
				}
			}

		} else if (arg instanceof AbstractHero) {
			this.hero = (AbstractHero)arg;
			this.removeWindowListener(myListener);
			myListener = new MyWindowListener(this.hero, this);
			this.addWindowListener(myListener);
			displayHero.setHero(this.hero);
			displayHero.repaint();
			
		} else if (arg instanceof AbstractCard) {
			if ((((AbstractCard)arg).isInBattle || arg instanceof Heal)) {
				if (o instanceof AbstractVillain) {
					 enemyAttack = (AbstractCard)arg;
				} else if (o instanceof AbstractHero) {
					heroAttack = (AbstractCard)arg;
				}
				if (!heroAttackUsed && !villainAttackUsed) chooseOrder();
			}
			this.hero.addObserver((AbstractCard)arg);
			((AbstractCard)arg).hero = this.hero;
		} 
		
	}
	
	
}


