package layout;

import interfaces.AttackCard;
import interfaces.Magical;
import interfaces.Physical;
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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.DelayQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import listeners.MyWindowListener;
import model.AbstractCard;
import model.AbstractHero;
import model.AbstractVillain;
import model.Heal;
import model.Warrior;

@SuppressWarnings("serial")
public class BattleGUI extends JDialog implements Observer {

	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	private HeroDisplayPanel displayHero;
	private HeroDisplayPanel displayVillain;
	private CalderraGUI controller;
	
	private javax.swing.Timer myTimer;
	private MyTimerListener myTimerListener;
	private MyWindowListener myListener;
	private ArrayList<InventoryPanel> panels;
	private ArrayList<InventoryPanel> theVillainPanels;
	
	private Queue<String> heroQueue;
	private Queue<String> enemyQueue;
	private int heroNum;
	private int enemyNum;
	private boolean heroFirst;
	private int attacksCompleted;
	
	public BattleGUI(CalderraGUI controller) {
		
		
		heroQueue = new PriorityQueue<String>();
		enemyQueue = new PriorityQueue<String>();
		heroNum = 0;
		enemyNum = 0;
		attacksCompleted = 0;
		heroFirst = false;
		
		this.controller = controller;
		this.controller.createNewEnemy();
		this.controller.getHero().setEnemy(this.controller.getEnemy());
		this.controller.getEnemy().setEnemy(this.controller.getHero());
		this.controller.getHero().addObserver(this);
		this.controller.getHero().enterBattle();
		this.controller.getEnemy().enterBattle();
		
		panels = new ArrayList<>();
		theVillainPanels = new ArrayList<>();
		
		
		this.displayHero = new HeroDisplayPanel(this.controller, this.controller.getHero());
		this.displayVillain = new HeroDisplayPanel(this.controller, this.controller.getEnemy());
		
		myListener = new MyWindowListener(this.controller.getHero(), this);
		this.addWindowListener(myListener);
		
		myTimerListener = new MyTimerListener();
		myTimer = new javax.swing.Timer(300, myTimerListener);
		
		
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
			InventoryPanel iPanel = new InventoryPanel(this.controller);
			InventoryPanel uPanel = new InventoryPanel(this.controller);
			heroAttackPanels.add(uPanel); //list
			panel.add(uPanel); //panel
			villainAttackPanels.add(iPanel);//list
			villainPanel.add(iPanel); //panel
			panels.add(iPanel); //list
			panels.add(uPanel); //list
			theVillainPanels.add(iPanel); //list
		}
		int i;
		int j;
		for (i = 0, j = 0; i < this.controller.getHero().getAttack().size() || j < this.controller.getEnemy().getAttack().size();) {
			if (i < this.controller.getHero().getAttack().size()) {
				heroAttackPanels.get(i).addCard(this.controller.getHero().getAttack().get(i));
				heroAttackPanels.get(i).getCard().isInBattle = true;
				i++;
			}
			if (j < this.controller.getEnemy().getAttack().size()) {
				villainAttackPanels.get(j).addCard(this.controller.getEnemy().getAttack().get(j));
				villainAttackPanels.get(j).getCard().enabled = false;
				theVillainPanels.get(j).addCard(this.controller.getEnemy().getAttack().get(j));
				theVillainPanels.get(j).getCard().enabled = false;
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
		
		
	}
	
	
	private void chooseOrder() {
		Random rand = new Random();
		int heroSpeed = rand.nextInt(20) + this.controller.getHero().getSpeed();
		int enemySpeed = rand.nextInt(20) + this.controller.getEnemy().getSpeed();
		if (heroSpeed > enemySpeed) {
			heroFirst = true;
		}
	}
	
	public int progressAttackHero() {
		
		System.out.println("hero " + this.controller.getHero() + " attack " + this.controller.getHero().attackToBeUsed);
		
		if (this.controller.getHero().getCurrentMagicPower() >= ((AttackCard)this.controller.getHero().attackToBeUsed).getCruxCost() && !this.controller.getHero().attackToBeUsed.isInBag) {			
			if ((this.controller.getHero().attackToBeUsed instanceof Physical || this.controller.getHero().attackToBeUsed instanceof Magical)
					&& this.controller.getEnemy() != null) {
				int damage = this.controller.getHero().getHit(this.controller.getHero().attackToBeUsed);
				return damage;
				
			} else if (this.controller.getHero().attackToBeUsed instanceof Heal) {
				//ACTUAL HEAL TAKES PLACE IN HEAL METHOD OF HERO
				int heal = this.controller.getHero().heal(this.controller.getHero().attackToBeUsed, this.controller.getHero().attackToBeUsed.getHealType());
				return heal;
			}
			
		}
		return 0;
	}
	
	public int progressAttackEnemy() {
		if (this.controller.getEnemy().getCurrentMagicPower() >= ((AttackCard)this.controller.getEnemy().attackToBeUsed).getCruxCost()) {			
			if ((this.controller.getEnemy().attackToBeUsed instanceof Physical || this.controller.getEnemy().attackToBeUsed instanceof Magical)
					&& this.controller.getEnemy() != null) {
				int damage = this.controller.getEnemy().getHit(this.controller.getEnemy().attackToBeUsed);
				return damage;
				
			} else if (this.controller.getEnemy().attackToBeUsed instanceof Heal) {
				//ACTUAL HEAL TAKES PLACE IN HEAL METHOD OF HERO
				int heal = this.controller.getEnemy().heal(this.controller.getEnemy().attackToBeUsed, this.controller.getEnemy().attackToBeUsed.getHealType());
				return heal;
			}
			
		}
		return 0;
	}
	
	private void hitOrMissHero() {
		
		if (!(this.controller.getHero().attackToBeUsed instanceof Heal)) {
			heroNum = this.controller.getEnemy().takeHit(heroNum);
			this.controller.getEnemy().getAttributes().put("currentHealth", Math.max(0, (this.controller.getEnemy().getAttributes().get("currentHealth") - heroNum)));
		} else {
			this.controller.getHero().getAttributes().put("currentHealth", 
					this.controller.getHero().getcurrentHealth() + heroNum <= this.controller.getHero().getmaxHealth() ? 
							this.controller.getHero().getcurrentHealth() + heroNum : this.controller.getHero().getmaxHealth());
		}
		attacksCompleted++;
		
		//LOAD GIFS
		for (String s: this.controller.getHero().attackToBeUsed.getattackGif()) {
			heroQueue.offer(s);
		}
		if (heroNum == 0 && !(this.controller.getHero().attackToBeUsed instanceof Heal)) {
			
			for (String s: this.controller.getEnemy().getEnemyMissGif()) {
				enemyQueue.offer(s);
			}
			
		} else if (this.controller.getHero().attackToBeUsed instanceof Heal) {
			//no change in image for enemy heal
			for (int i = 0; i < this.controller.getHero().attackToBeUsed.getattackGif().size(); i++) {
				enemyQueue.offer(this.controller.getEnemy().getImageSource());
			}
		
		} else {
			
			for (String s: this.controller.getEnemy().getEnemyHitGif()) {
				enemyQueue.offer(s);
			}
			
		}
		
		if (this.controller.getEnemy().getcurrentHealth() > 0 && attacksCompleted < 2) hitOrMissEnemy();
	}
	
	private void hitOrMissEnemy() {
		
		if (!(this.controller.getEnemy().attackToBeUsed instanceof Heal)) {
			enemyNum = this.controller.getHero().takeHit(enemyNum);
			this.controller.getHero().getAttributes().put("currentHealth", Math.max(0, (this.controller.getHero().getAttributes().get("currentHealth") - enemyNum)));
		} else {
			this.controller.getEnemy().getAttributes().put("currentHealth", 
					this.controller.getEnemy().getcurrentHealth() + enemyNum <= this.controller.getEnemy().getmaxHealth() ? 
							this.controller.getEnemy().getcurrentHealth() + enemyNum : this.controller.getEnemy().getmaxHealth());
		}
		attacksCompleted++;
		
		//LOAD GIFS
		for (String s: this.controller.getEnemy().attackToBeUsed.getattackGif()) {
			enemyQueue.offer(s);
		}
		if (enemyNum == 0 && !(this.controller.getHero().attackToBeUsed instanceof Heal)) {
			//if miss show miss image
			for (String s: this.controller.getHero().getEnemyMissGif()) {
				heroQueue.offer(s);
			}
			
		} else if (this.controller.getEnemy().attackToBeUsed instanceof Heal) {
			//no change in image for enemy heal
			for (int i = 0; i < this.controller.getEnemy().attackToBeUsed.getattackGif().size(); i++) {
				heroQueue.offer(this.controller.getHero().getImageSource());
			}
		
		} else {
			//if hit show hit image
			for (String s: this.controller.getHero().getEnemyHitGif()) {
				heroQueue.offer(s);
			}
			
		}
		
		if (this.controller.getHero().getcurrentHealth() > 0 && attacksCompleted < 2) hitOrMissHero();
	}
	
	
	private void toggleButtons(boolean toggle) {
		for(InventoryPanel p: panels) {
			if (p.getCard() != null) p.getCard().setEnabled(toggle);
		}
	}
	
	private void checkState() {
		displayHero.update(this.controller.getHero());
		displayVillain.update(this.controller.getEnemy());
		if (this.controller.getHero().getcurrentHealth() < 1 || (this.controller.getEnemy() != null && this.controller.getEnemy().getcurrentHealth() < 1)) {
			this.dispose();//maybe move this to after the visual of the experience gain is finished.
			this.controller.getHero().exitBattle();

			for (int i = 0; i < panels.size(); i++) {
				if (panels.get(i).getCard() != null) {
					panels.get(i).getCard().isInBattle = false;
					panels.get(i).mouseExited(new MouseEvent(this, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));
				}
			}
			displayHero.mouseExited(new MouseEvent(this, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));
			displayVillain.mouseExited(new MouseEvent(this, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));

		
		}
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
		
		if (arg instanceof String) {

			if (arg.equals("attacks chosen")) {
					toggleButtons(false);
					chooseOrder();
					heroNum = progressAttackHero();
					enemyNum = progressAttackEnemy();
					if (heroFirst) {
						hitOrMissHero();
					}
					else {
						hitOrMissEnemy();
					}
					
					myTimer.start();
					
					checkState();
					
					
					
					//reset for next round
					heroFirst = false; 
					attacksCompleted = 0;
					heroNum = 0;
					enemyNum = 0;
			}
			
		} 
		
	}
	
	protected void step() {
		if (!heroQueue.isEmpty() && !enemyQueue.isEmpty()) {
			displayHero.setSource(heroQueue.poll());
			displayVillain.setSource(enemyQueue.poll());
		} else {
			myTimer.stop();
			toggleButtons(true);
			displayHero.setSource(this.controller.getHero().getImageSource());
			displayVillain.setSource(this.controller.getEnemy().getImageSource());
		}
	}
	
	@SuppressWarnings("unused")
	private class MyTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			step();
			 
		}
	}
}


