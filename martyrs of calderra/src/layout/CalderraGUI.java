package layout;

import inventory.InventoryGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import FileReaders.TextReader;
import listeners.MyFrameFocusListener;
import listeners.MyKeyListener;
import model.AbstractCard;
import model.AbstractHero;
import model.AbstractVillain;
import model.Ranger;

@SuppressWarnings("serial")
public class CalderraGUI extends JFrame implements Observer, FocusListener {
	
	public static final Toolkit KIT = Toolkit.getDefaultToolkit();
	public static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	public static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	public static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	public boolean characterSelectOn;

	
	private StoryPanel storyPanel;
	private CharacterDisplayPanel characterPanel;
	private HeroDisplayPanel heroPanel;
	private HeroDisplayPanel heroPanel2;
	private EquipmentPanel equipmentPanel;
	private ShopPanel shopPanel;
	private MenuBar menu;
	private TextReader tr;
	
	private MyKeyListener kListen;
	
	public AbstractHero hero;
	
	public CalderraGUI() {
//		this.setResizable(false);
		this.setLayout(new BorderLayout());
//		this.setBackground(new Color(0, 0, 0, 0));
		this.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		this.setLocation(SCREEN_WIDTH / 2 / 2, SCREEN_HEIGHT / 2 / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		characterSelectOn = false;
		tr = new TextReader();
		hero = new Ranger();
		
	}
	
	public void start() {
		Color transparent = new Color(255, 255, 255, 25);
		MyPanel backGroundPanel = new MyPanel("src/resources/background.jpg");
		backGroundPanel.setForeground(new Color(200, 200, 200, 50));
		backGroundPanel.setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new LineBorder(Color.BLACK));
		
		heroPanel = new HeroDisplayPanel(hero);
		heroPanel.setBackground(transparent);
		
		heroPanel2 = new HeroDisplayPanel(hero);
		heroPanel2.setBackground(transparent);
		
		storyPanel = new StoryPanel("", 1, 3, this.hero);
		storyPanel.setBackground(transparent);
		
		characterPanel = new CharacterDisplayPanel(heroPanel, hero);
		characterPanel.setBackground(transparent);
		
		equipmentPanel = new EquipmentPanel(hero, AbstractHero.MAX_EQUIP, heroPanel2);
		equipmentPanel.setBackground(transparent);
		
		shopPanel = new ShopPanel(hero, tr.readShop("src/resources/shopText.txt", hero));
		shopPanel.setBackground(transparent);
		
		menu = new MenuBar(hero, this);
		menu.setBackground(Color.BLACK);
		menu.setForeground(Color.BLACK);
		
		addObservers();
		addListeners();
		
		tabbedPane.setBackground(transparent);
		tabbedPane.add("Story", storyPanel);
		tabbedPane.add("Hero", characterPanel);
		tabbedPane.add("Equipment", equipmentPanel);
		tabbedPane.add("Shop", shopPanel);
//		repaint();
		
		tabbedPane.setBackgroundAt(0, Color.BLACK);
		tabbedPane.setBackgroundAt(1, Color.BLACK);
		tabbedPane.setBackgroundAt(2, Color.BLACK);
		tabbedPane.setBackgroundAt(3, Color.BLACK);
		
		
		
		
		setJMenuBar(menu);
		
//		this.add(tabbedPane);
		backGroundPanel.add(tabbedPane, BorderLayout.CENTER);
		this.add(backGroundPanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.requestFocus(); // need this until i make a focus listener
	}
	
	public void addObservers() {
		hero.addObserver(this);
		hero.addObserver(equipmentPanel);
	}
	
	private void addListeners() {
		
		//add focus listeners
		this.addFocusListener(new MyFrameFocusListener(this));
		//add key listeners
		kListen = new MyKeyListener(hero);
		this.addKeyListener(kListen);
				
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (!e.getComponent().equals(this)) {
			this.requestFocus();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		e.getComponent().requestFocus();
	}
	
	public void setHero (AbstractHero hero) {
		this.hero.resetTalentTree();
		this.hero.resetInventory();
		this.hero = hero;
		this.hero.addObserver(this);
		this.hero.changed();
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof AbstractHero && !(arg instanceof AbstractVillain)) {
			System.out.println("in calderraGUI update");
			
			this.removeKeyListener(kListen);
			kListen = new MyKeyListener(hero);
			this.addKeyListener(kListen);
			AbstractHero hero = (AbstractHero) arg;
			
//			storyPanel.setHero(hero);
			storyPanel.update(o, hero);
			heroPanel.setHero(hero); 
			heroPanel.update(o, hero);
			heroPanel2.setHero(hero); 
			heroPanel2.update(o, hero);
//			characterPanel.setHero(hero); 
			characterPanel.update(o, hero);
//			equipmentPanel.setHero(hero); 
			equipmentPanel.update(o, hero);
//			shopPanel.setHero(hero);
			shopPanel.update(o, hero);
			menu.setHero(hero);
		} else if (arg instanceof AbstractCard) {
//			this.setVisible(false);
		} else if (arg instanceof String) {
			if (((String)arg).equals("Battle Over")) {
				this.setVisible(true);
			}
		}
		
		
//		revalidate();
		repaint();
	}
		
		
	}
