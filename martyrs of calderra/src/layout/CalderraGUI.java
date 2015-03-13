package layout;

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

	public InfoDisplayPanel infoDisplay;
	private StoryPanel storyPanel;
	private CharacterDisplayPanel characterPanel;
	private HeroDisplayPanel heroPanel;
	private HeroDisplayPanel heroPanel2;
	private EquipmentPanel equipmentPanel;
	private ShopPanel shopPanel;
	private MenuBar menu;
	private TextReader tr;
	
	private MyKeyListener kListen;
	
	private AbstractHero hero;
	private AbstractVillain enemy;
	
	public CalderraGUI() {
		this.setLayout(new BorderLayout());
		this.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		this.setLocation(SCREEN_WIDTH / 2 / 2, SCREEN_HEIGHT / 2 / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		characterSelectOn = false;
		hero = new Ranger(this);
		enemy = new AbstractVillain(this);
		tr = new TextReader();
		infoDisplay = new InfoDisplayPanel(this, this.infoDisplay.toShow);
		
	}
	
	public void start() {
		Color transparent = new Color(255, 255, 255, 25);
		MyPanel backGroundPanel = new MyPanel("src/resources/background.jpg");
		backGroundPanel.setForeground(new Color(200, 200, 200, 50));
		backGroundPanel.setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new LineBorder(Color.BLACK));
		
		heroPanel = new HeroDisplayPanel(this, this.hero);
		heroPanel.setBackground(transparent);
		
		heroPanel2 = new HeroDisplayPanel(this, this.hero);
		heroPanel2.setBackground(transparent);
		
		storyPanel = new StoryPanel("", 1, 3, this);
		storyPanel.setBackground(transparent);
		
		characterPanel = new CharacterDisplayPanel(heroPanel, this);
		characterPanel.setBackground(transparent);
		
		equipmentPanel = new EquipmentPanel(this, AbstractHero.MAX_EQUIP, heroPanel2);
		equipmentPanel.setBackground(transparent);
		
		shopPanel = new ShopPanel(this, tr.readShop("src/resources/shopText.txt", this));
		shopPanel.setBackground(transparent);
		
		menu = new MenuBar(this);
		menu.setBackground(Color.BLACK);
		menu.setForeground(Color.BLACK);
		
		hero.addObserver(this);
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
	
	//Call when a new character is chosen from the character select screen
	public void setHero (AbstractHero hero) {
		this.hero.resetTalentTree(); //may want to revisit this order
		this.hero.resetInventory();
		this.hero = hero;
		this.hero.addObserver(this);
		this.hero.changed();
	}
	
	//THIS NEEDS TO BE DONE RIGHT
	public void createNewEnemy() {
		this.enemy = new AbstractVillain(this);
		
	}
	
	public AbstractVillain getEnemy() {
		return this.enemy;
	}
	
	public AbstractHero getHero() {
//		System.out.println(this.hero);
		return this.hero;
		
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("in calderraGUI update");
		storyPanel.update(o, hero);
		heroPanel.update(hero);
		heroPanel2.update(hero);
		characterPanel.update(hero);
		equipmentPanel.update(o, arg);
		shopPanel.update(o, hero);
		
//		if (arg instanceof String) {
//			if (((String)arg).equals("Battle Over")) {
//				this.setVisible(true);
//			}
//		}
		
//		if (arg instanceof AbstractHero && !(arg instanceof AbstractVillain)) {
//			
//			
////			this.removeKeyListener(kListen);
////			kListen = new MyKeyListener(hero);
////			this.addKeyListener(kListen);
////			AbstractHero hero = (AbstractHero) arg;
//			
//			
//			
//		} else if (arg instanceof AbstractCard) {
////			this.setVisible(false);
//		} else 
		
		
//		revalidate();
		repaint();
	}
		
		
	}
