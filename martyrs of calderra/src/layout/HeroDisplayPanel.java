package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.AbstractHero;

@SuppressWarnings("serial")
public class HeroDisplayPanel extends JPanel implements MouseListener, Observer {

	private static final int FIELD_HEIGHT = 25;
	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	
	InfoDisplayPanel infoPanel;
	
	private AbstractHero hero;
	private JProgressBar healthBar;
	private JProgressBar magicBar;
	private MyPanel heroPanel;
	
	private JTextField nameDisplay;
	
	public HeroDisplayPanel(AbstractHero hero) {
		this.hero = hero;
		this.setBackground(new Color(0, 0, 0, 0));
		
		
		
		healthBar = new JProgressBar(0, hero.getAttributes().get("maxHealth"));
		healthBar.setOpaque(true);
		healthBar.setString("HP: " + (hero.getAttributes().get("currentHealth") + "/" + healthBar.getMaximum()));
		healthBar.setStringPainted(true);
		healthBar.setValue(hero.getAttributes().get("currentHealth"));
		healthBar.setBackground(new Color(255, 0, 0));
		
		
		magicBar = new JProgressBar(0, hero.getAttributes().get("maxMagicPower"));
		magicBar.setOpaque(true);
		magicBar.setString("MP: " + (this.hero.getAttributes().get("currentMagicPower") + "/" + magicBar.getMaximum()));
		magicBar.setStringPainted(true);
		magicBar.setValue(hero.getAttributes().get("currentMagicPower"));
		magicBar.setBackground(new Color(0, 255, 255));
		
		healthBar.setFocusable(false);
		magicBar.setFocusable(false);
		setup();
		this.addMouseListener(this);
//		hero.addObserver(this);
	}
	
	private void setup() {
		this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK));
		heroPanel = new MyPanel(hero.getImageSource());
		heroPanel.setLayout(new BorderLayout());
		heroPanel.setBackground(MyPanel.TRANSPARENT);
		infoPanel = new InfoDisplayPanel(this.hero);
		JPanel progressPanel = new JPanel();
		progressPanel.setBackground(MyPanel.TRANSPARENT);
		progressPanel.setPreferredSize(new Dimension(SCREEN_WIDTH / 7, SCREEN_HEIGHT / 20));
		progressPanel.setLayout(new FlowLayout());
//		progressPanel.setLayout(new GridLayout(1, 2));
//		healthBar.setOpaque(false);
//		magicBar.setOpaque(false);
		progressPanel.add(healthBar);
		progressPanel.add(magicBar);
		
		//Name Display
		nameDisplay = new JTextField(this.hero.getFullName());
		nameDisplay.setBackground(new Color(20, 20, 20, 100));
		nameDisplay.setForeground(Color.CYAN);
		nameDisplay.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		nameDisplay.setEditable(false);
		this.add(nameDisplay, BorderLayout.NORTH);
		nameDisplay.setSize(nameDisplay.getParent().getWidth(), FIELD_HEIGHT);
		
		heroPanel.add(progressPanel, BorderLayout.SOUTH);
//		heroPanel.add(magicBar, BorderLayout.SOUTH);
		
//		add(progressPanel, BorderLayout.SOUTH);
		add(heroPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Repaint the desired image on the panel.
	 */
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
		final Graphics2D g2D = (Graphics2D) theGraphics;
        g2D.drawImage(new ImageIcon(hero.getImageSource()).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	public void addObserver() {
		hero.addObserver(this);
	}
	
	public AbstractHero getHero() {
		return hero;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//for debugging
		this.hero.addPoints(10);
		this.hero.getAttributes().put("gold", this.hero.getAttributes().get("gold") + 100);
		infoPanel.dispose();;
		this.hero.changed();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (infoPanel != null && !infoPanel.isVisible()) {
			infoPanel = new InfoDisplayPanel(this.hero);
			infoPanel.setLocation(this.getLocationOnScreen().x - infoPanel.getWidth() > 0 ? this.getLocationOnScreen().x - infoPanel.getWidth() : this.getLocationOnScreen().x + this.getWidth(),
					this.getLocationOnScreen().y - infoPanel.getHeight() > 0 ? this.getLocationOnScreen().y - infoPanel.getHeight() : this.getLocationOnScreen().y + this.getHeight());
			infoPanel.setVisible(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		infoPanel.attemptDispose();
		
	}
	
	public void setHero(AbstractHero hero) {
		this.hero = hero;
	}


	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof AbstractHero) {
			this.hero = (AbstractHero) arg;
			heroPanel.setHero(this.hero);
			infoPanel = new InfoDisplayPanel(this.hero);
			nameDisplay.setText(this.hero.getFullName());
			this.hero.addObserver(this);
			repaint();
		}
		
		healthBar.setMaximum((int) (this.hero.getAttributes().get("maxHealth") + this.hero.getAdditionalStats().get("maxHealth")));
		healthBar.setValue(this.hero.getAttributes().get("currentHealth"));
		healthBar.setString("HP: " + healthBar.getValue() + "/" + healthBar.getMaximum());
		magicBar.setMaximum(this.hero.getAttributes().get("maxMagicPower"));
		magicBar.setValue(this.hero.getAttributes().get("currentMagicPower"));
		magicBar.setString("MP: " + magicBar.getValue() + "/" + magicBar.getMaximum());
		repaint();
	}


}
