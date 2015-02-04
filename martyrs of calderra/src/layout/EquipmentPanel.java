package layout;

import inventory.InventoryPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.AbstractHero;

@SuppressWarnings("serial")
public class EquipmentPanel extends JPanel implements Observer {
	
	private static final int ATTACK_LIM = 4;
	private static final int MAX_EQUIP = 6;
	
	ArrayList<InventoryPanel> equipPanels;
	ArrayList<InventoryPanel> attackPanels;
	
	CardPanel equipPanel;
	CardPanel attackPanel;
	
	HeroDisplayPanel heroPanel;
	private JProgressBar healthBar;
	private JProgressBar magicBar;
	private int num;
	private AbstractHero hero;
	
	public EquipmentPanel(AbstractHero hero, int num, HeroDisplayPanel heroPanel) {
		this.num = num;
		this.hero = hero;
		this.heroPanel = heroPanel;
		this.hero.addObserver(this.heroPanel);
		equipPanels = new ArrayList<InventoryPanel>();
		attackPanels = new ArrayList<InventoryPanel>();
		healthBar = new JProgressBar(0, hero.getAttributes().get("maxHealth"));
		healthBar.setString("HP: " + (hero.getAttributes().get("currentHealth") + "/" + healthBar.getMaximum()));
		healthBar.setStringPainted(true);
		healthBar.setValue(hero.getAttributes().get("currentHealth"));
//		healthBar.setBackground(new Color(0, 255, 255));
		magicBar = new JProgressBar(0, hero.getAttributes().get("maxMagicPower"));
		magicBar.setString("MP: " + (this.hero.getAttributes().get("currentMagicPower") + "/" + magicBar.getMaximum()));
		magicBar.setStringPainted(true);
		magicBar.setValue(hero.getAttributes().get("currentMagicPower"));
//		magicBar.setBackground(new Color(255, 0, 0));
		
		equipPanel = new CardPanel(num / 2 - 1, num / 2 + 1);
		attackPanel = new CardPanel(1, 4);
		setup(hero, heroPanel);
		
//		hero.addObserver(this);
	}
	
	
	private void setup(AbstractHero hero, HeroDisplayPanel heroPanel) {
		for (int i = 0; i < MAX_EQUIP; i++) {
			InventoryPanel panel = new InventoryPanel();
			equipPanels.add(panel);
			equipPanel.add(panel);
		}
		for (int i = 0; i < ATTACK_LIM; i++) {
			InventoryPanel panel = new InventoryPanel();
			attackPanels.add(panel);
			attackPanel.add(panel);
		}
		
		//Left Side of the equipmentPanel
//		JPanel westPanel = new JPanel();
//		westPanel.setLayout(new BorderLayout());
		
//		HeroDisplayPanel heroPanel = new HeroDisplayPanel(hero);
//		JPanel progressPanel = new JPanel();
//		progressPanel.setPreferredSize(new Dimension(SCREEN_WIDTH / 7, SCREEN_HEIGHT / 2 / 8));
//		progressPanel.setLayout(new GridLayout(2, 1));
//		progressPanel.add(healthBar);
//		progressPanel.add(magicBar);
		
		
//		westPanel.add(progressPanel, BorderLayout.SOUTH);
//		westPanel.add(heroPanel, BorderLayout.CENTER);
		
		//Center part of the Equipment panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(MyPanel.TRANSPARENT);
		
		setEquipCards();
		setAttackCards();
		
		equipPanel.setBackground(MyPanel.TRANSPARENT);
		equipPanel.setBorder(new TitledBorder(getBorder(), "EQUIPMENT", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("Times Roman", Font.PLAIN, 13), Color.CYAN));
		attackPanel.setBackground(MyPanel.TRANSPARENT);
		attackPanel.setBorder(new TitledBorder(getBorder(), "ABILITIES", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("Times Roman", Font.PLAIN, 13), Color.CYAN));
		attackPanel.setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 2 - heroPanel.getWidth(), MyPanel.SCREEN_HEIGHT / 2 / 5));
		centerPanel.add(attackPanel, BorderLayout.SOUTH);
		centerPanel.add(equipPanel, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		heroPanel.setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 2 / 3, MyPanel.SCREEN_HEIGHT / 2));
		heroPanel.setBorder(new LineBorder(Color.BLACK));
//		add(westPanel, BorderLayout.WEST);
		add(heroPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
	}

	private void setEquipCards() {
		for (int i = 0; i < MAX_EQUIP; i++) {
			if (hero.getEquipped().size() > i) {
				equipPanels.get(i).removeCard();
				equipPanels.get(i).addCard(hero.getEquipped().get(i));
				equipPanels.get(i).getCard().isEquipped = true;
				equipPanels.get(i).getCard().isInBag = false;
			} else {
				equipPanels.get(i).removeCard();
			}
		}
		
	}
	
	private void setAttackCards() {
		for (int i = 0; i < ATTACK_LIM; i++) {
			if (hero.getAttack().size() > i) {
				attackPanels.get(i).removeCard();
				attackPanels.get(i).addCard(hero.getAttack().get(i));
				attackPanels.get(i).getCard().isEquipped = true;
				attackPanels.get(i).getCard().isInBag = false;
			} else {
				attackPanels.get(i).removeCard();
			}
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {		
		if (arg instanceof AbstractHero) {
			this.hero = (AbstractHero) arg;
			heroPanel.update(o, this.hero);
			this.hero.addObserver(this);
			setEquipCards();
			setAttackCards();
		}
		
		healthBar.setMaximum((int) (this.hero.getmaxHealth()));
		healthBar.setValue(this.hero.getcurrentHealth());
		healthBar.setString("HP: " + healthBar.getValue() + "/" + healthBar.getMaximum());
		magicBar.setMaximum(this.hero.getMaxMagicPower());
		magicBar.setValue(this.hero.getCurrentMagicPower());
		magicBar.setString("MP: " + magicBar.getValue() + "/" + magicBar.getMaximum());
		
		
		if (arg instanceof String) {
			if ("equip".equals((String) arg)) {
				setEquipCards();
				setAttackCards();
			}
		}
	}
}
