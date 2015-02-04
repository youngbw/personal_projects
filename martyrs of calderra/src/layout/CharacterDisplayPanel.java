package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

import model.AbstractHero;

@SuppressWarnings("serial")
public class CharacterDisplayPanel extends JPanel implements Observer {

	private static final int FIELD_HEIGHT = 25;
//	private HashMap<String, JProgressBar> bars;
	private ArrayList<JProgressBar> bars;
//	private ArrayList<Double> barPercents;
//	private ArrayList<Integer> barAdditions;
	private ArrayList<AttributeButton> buttons;
	private JTextField goldField;
	private JTextField levelField;
	private HeroDisplayPanel heroPanel;
	
	private JProgressBar healthBar;
	private JTextField progressBar;
	private AbstractHero hero;
	
	public CharacterDisplayPanel(HeroDisplayPanel heroPanel, AbstractHero hero) {
		super();
		this.heroPanel = heroPanel;
		this.hero = hero;
//		bars = new HashMap<String, JProgressBar>();
		bars = new ArrayList<JProgressBar>();
//		barAdditions = new ArrayList<Integer>();
		buttons = new ArrayList<AttributeButton>();
//		barPercents = new ArrayList<Double>();
//		setTempValues();
		this.hero.calculateAdditionalStats();
		goldField = new JTextField("" + hero.getAttributes().get("gold"));
		goldField.setEditable(false);
//		goldField.setBackground(Color.WHITE);
		goldField.setHorizontalAlignment((int)JTextField.CENTER_ALIGNMENT);
		goldField.setPreferredSize(new Dimension(FIELD_HEIGHT * 4, FIELD_HEIGHT));
		goldField.setFocusable(false);
		goldField.setBackground(new Color(10, 10, 10, 250));
		goldField.setBorder(new LineBorder(Color.CYAN));
		goldField.setForeground(Color.CYAN);
		
		levelField = new JTextField("" + hero.getAttributes().get("level"));
		levelField.setEditable(false);
		levelField.setBackground(Color.WHITE);
		levelField.setHorizontalAlignment((int)JTextField.CENTER_ALIGNMENT);
		levelField.setPreferredSize(new Dimension(FIELD_HEIGHT * 4, FIELD_HEIGHT));
		levelField.setFocusable(false);
		levelField.setBackground(new Color(10, 10, 10, 250));
		levelField.setBorder(new LineBorder(Color.CYAN));
		levelField.setForeground(Color.CYAN);
		
//		pb = new JProgressBar(0, hero.getAttributes().get("Experience To Next Level"));
		progressBar = new JTextField(0 + "/" + hero.getAttributes().get("Experience To Next Level"));
		progressBar.setEditable(false);
		progressBar.setHorizontalAlignment((int)JTextField.CENTER_ALIGNMENT);
		progressBar.setPreferredSize(new Dimension(FIELD_HEIGHT * 4, FIELD_HEIGHT));
		progressBar.setFocusable(false);
		hero.addObserver(this);
		setup(heroPanel, hero);
		
	}
	
	
	private void setup(HeroDisplayPanel heroPanel, AbstractHero hero) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 2,MyPanel. SCREEN_HEIGHT / 2));
		
//		//Left Panel container for name and picture of hero
//		JPanel leftPane = new JPanel();
//		leftPane.setBorder(new LineBorder(Color.BLACK));
//		leftPane.setLayout(new BorderLayout());
//		leftPane.setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 2 / 3, MyPanel.SCREEN_HEIGHT / 2));
//		add(leftPane, BorderLayout.WEST);
		
		
		
//		leftPane.add(heroPanel, BorderLayout.CENTER);
//		heroPanel.setSize(heroPanel.getParent().getWidth(), heroPanel.getParent().getHeight() - FIELD_HEIGHT);
		heroPanel.setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 2 / 3, MyPanel.SCREEN_HEIGHT / 2));
		add(heroPanel, BorderLayout.WEST);
		
		JPanel statPanel = new JPanel();
		statPanel.setLayout(new BorderLayout());
		statPanel.setBackground(new Color(10, 10, 10, 200));
		
		
		//For the labels of the battle ready attributes
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(new Color(20, 20, 20, 100));
				
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(20, 20, 20, 100));
		String[] labels = {"Health", "Strength", "Accuracy", "Armor", "Intel", "Speed"};
		
		centerPanel.setLayout(new GridLayout(labels.length, 1, 100, 0));
		labelPanel.setLayout(new GridLayout(labels.length, 1, 5, 0));
		
		JLabel health = new JLabel("\t" + labels[0]);
		health.setForeground(Color.CYAN);
		healthBar = new JProgressBar(0, (int) hero.getAttributes().get("max" + labels[0]));
		healthBar.setFont(new Font("Times Roman", Font.BOLD, 12));
		healthBar.setOpaque(true);
		healthBar.setBackground(new Color(20, 20, 20, 255));
		healthBar.setStringPainted(true);
		healthBar.setValue(hero.getAttributes().get("currentHealth"));
		healthBar.setString(healthBar.getValue() + "/" + healthBar.getMaximum());
		labelPanel.add(health);
		centerPanel.add(healthBar);
		
		for (int i = 1; i < labels.length; i++) {
			JLabel myLabel = new JLabel("\t" + labels[i]);
			myLabel.setForeground(Color.CYAN);
			labelPanel.add(myLabel);
			JProgressBar pb = new JProgressBar(0, 100);
			pb.setFocusable(false);
			pb.setBackground(new Color(20, 20, 20, 255));
			pb.setOpaque(true);
			pb.setForeground(Color.cyan);
			pb.setFont(new Font("Times Roman", Font.BOLD, 12));
			pb.setValue(hero.getAttributes().get(labels[i].toLowerCase()));
			pb.setString(pb.getValue() + "/" + pb.getMaximum());
			pb.setStringPainted(true);
//			bars.put(labels[i], pb);
			bars.add(pb);
			centerPanel.add(pb);
		}
		
		//For the attributes that are not directly usable in battle or freely incrementable
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(20, 20, 20, 100));
		southPanel.setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 2 - labelPanel.getWidth(), MyPanel.SCREEN_HEIGHT / 20));
		
		//Updater needed for new level acquisition
		JLabel myLabel = new JLabel("\tLevel");
		myLabel.setForeground(Color.CYAN);
		southPanel.add(myLabel);
		southPanel.add(levelField);
		
		
		//Updater needed for new gold acquisition
		myLabel = new JLabel("\tGold");
		myLabel.setForeground(Color.CYAN);
		southPanel.add(myLabel);
		southPanel.add(goldField);
		
		myLabel = new JLabel("\tExp to Level");
		myLabel.setForeground(Color.CYAN);
//		pb.setValue(hero.getAttributes().get("experience"));
//		pb.setString(pb.getValue() + "/" + pb.getMaximum());
//		pb.setStringPainted(true);
		progressBar.setText(this.hero.getAttributes().get("experience") + "/" + this.hero.getAttributes().get("Experience To Next Level"));
		progressBar.setBorder(new LineBorder(Color.CYAN));
		progressBar.setBackground(Color.black);
		progressBar.setForeground(Color.CYAN);
//		bars.add(pb);
		southPanel.add(myLabel);
		southPanel.add(progressBar);
		
		
		//For incrementing and decrementing attributes
		JPanel changePanel = new JPanel();
		changePanel.setBackground(new Color(20, 20, 20, 100));
		changePanel.setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 10, MyPanel.SCREEN_HEIGHT / 2));
		changePanel.setLayout(new GridLayout(labels.length, 2));
		AttributeButton healthButton = new AttributeButton(hero, "maxHealth", true);
		AttributeButton anotherButton = new AttributeButton(hero, "maxHealth", false);
		this.hero.addObserver(healthButton);
		this.hero.addObserver(anotherButton);
		healthButton.setEnabled(false);
		anotherButton.setEnabled(false);
		healthButton.setFocusable(false);
		anotherButton.setFocusable(false);
		buttons.add(healthButton);
		buttons.add(anotherButton);
		healthButton.setText("+");
		anotherButton.setText("-");
		changePanel.add(healthButton);
		changePanel.add(anotherButton);
		for (int i = 1; i < labels.length; i++) {
			AttributeButton button = new AttributeButton(hero, labels[i].toLowerCase(), true);
			AttributeButton another = new AttributeButton(hero, labels[i].toLowerCase(), false);
			button.setText("+");
			another.setText("-");
			button.setEnabled(false);
			another.setEnabled(false);
			button.setFocusable(false);
			another.setFocusable(false);
			buttons.add(button);
			buttons.add(another);
			this.hero.addObserver(button);
			this.hero.addObserver(another);
			changePanel.add(button);
			changePanel.add(another);
		}
		
		
		
		statPanel.add(changePanel, BorderLayout.EAST);
		statPanel.add(labelPanel, BorderLayout.WEST);
		statPanel.add(southPanel, BorderLayout.SOUTH);
		statPanel.add(centerPanel, BorderLayout.CENTER);
		add(statPanel, BorderLayout.CENTER);
	}


	@Override
	public void update(Observable o, Object arg) {
//		System.out.println("in updater for chracter display");
		this.heroPanel.update(o, arg);
		
		
		if(arg instanceof AbstractHero) {
			this.hero = (AbstractHero) arg;
			this.hero.addObserver(this);
			for (AttributeButton b: buttons) {
				b.setHero((AbstractHero) arg);
				this.hero.addObserver(b);
				

			}
		}
		
				
		this.hero.calculateAdditionalStats();
		String[] atts = {"strength", "accuracy", "armor", "intel", "speed", "maxHealth"};
		for (int i = 0; i < bars.size() && i < atts.length - 1; i++) {
			bars.get(i).setValue(this.hero.getAttributes().get(atts[i]) + this.hero.getAdditionalStats().get(atts[i]));
			bars.get(i).setString(bars.get(i).getValue() + "/" + bars.get(i).getMaximum());
//			bars.get(i).setStringPainted(true);
		}

		healthBar.setMaximum((int)(this.hero.getAttributes().get("maxHealth") + this.hero.getAdditionalStats().get("maxHealth")));
		healthBar.setValue(heroPanel.getHero().getAttributes().get("currentHealth"));
		healthBar.setString(healthBar.getValue() + "/" + healthBar.getMaximum());

		goldField.setText("" + this.hero.getAttributes().get("gold"));
		levelField.setText("" + this.hero.getAttributes().get("level"));

		//				pb.setMaximum(this.hero.getAttributes().get("Experience To Next Level"));
		//				pb.setValue(this.hero.getAttributes().get("experience"));
		//				pb.setString(pb.getValue() + "/" + pb.getMaximum());
		progressBar.setText(this.hero.getAttributes().get("experience") + "/" + this.hero.getAttributes().get("Experience To Next Level"));		
		
		
	}
}
