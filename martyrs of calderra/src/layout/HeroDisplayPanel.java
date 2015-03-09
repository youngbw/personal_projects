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

//Has controller reference and removed observer pattern, must call update manually.
@SuppressWarnings("serial")
public class HeroDisplayPanel extends MyPanel implements MouseListener {

	private static final int FIELD_HEIGHT = 25;
	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	
	public InfoDisplayPanel infoPanel;
	
	private CalderraGUI controller;
	private AbstractHero theHeroViewed;
	private JProgressBar healthBar;
	private JProgressBar magicBar;
	private MyPanel heroPanel;
	
	private JTextField nameDisplay;
	private String source;
	
	public HeroDisplayPanel(CalderraGUI controller, AbstractHero theHero) {
		this.controller = controller;
		this.setBackground(new Color(0, 0, 0, 0));
		
		this.theHeroViewed =  theHero;
		
		
		healthBar = new JProgressBar(0, this.theHeroViewed.getAttributes().get("maxHealth"));
		healthBar.setOpaque(true);
		healthBar.setString("HP: " + (this.theHeroViewed.getAttributes().get("currentHealth") + "/" + healthBar.getMaximum()));
		healthBar.setStringPainted(true);
		healthBar.setValue(this.theHeroViewed.getAttributes().get("currentHealth"));
		healthBar.setBackground(new Color(255, 0, 0));
		
		
		magicBar = new JProgressBar(0, this.theHeroViewed.getAttributes().get("maxMagicPower"));
		magicBar.setOpaque(true);
		magicBar.setString("MP: " + (this.theHeroViewed.getAttributes().get("currentMagicPower") + "/" + magicBar.getMaximum()));
		magicBar.setStringPainted(true);
		magicBar.setValue(this.theHeroViewed.getAttributes().get("currentMagicPower"));
		magicBar.setBackground(new Color(0, 255, 255));
		
		healthBar.setFocusable(false);
		magicBar.setFocusable(false);
		source = this.theHeroViewed.getImageSource();
		setup();
		this.addMouseListener(this);
//		this.theHeroViewed.addObserver(this);
	}
	
	private void setup() {
		this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK));
		this.heroPanel = new MyPanel(this.theHeroViewed.getImageSource());
		this.heroPanel.setLayout(new BorderLayout());
		this.heroPanel.setBackground(MyPanel.TRANSPARENT);
		infoPanel = new InfoDisplayPanel(this.theHeroViewed);
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
		nameDisplay = new JTextField(this.theHeroViewed.getFullName());
		nameDisplay.setBackground(new Color(20, 20, 20, 100));
		nameDisplay.setForeground(Color.CYAN);
		nameDisplay.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		nameDisplay.setEditable(false);
		this.add(nameDisplay, BorderLayout.NORTH);
		nameDisplay.setSize(nameDisplay.getParent().getWidth(), FIELD_HEIGHT);
		
		this.heroPanel.add(progressPanel, BorderLayout.SOUTH);
//		this.theHeroViewedPanel.add(magicBar, BorderLayout.SOUTH);
		
//		add(progressPanel, BorderLayout.SOUTH);
		add(this.heroPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Repaint the desired image on the panel.
	 */
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
		final Graphics2D g2D = (Graphics2D) theGraphics;
//        g2D.drawImage(new ImageIcon(this.source).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	public void setSource(String src) {
//		this.source = src;
		heroPanel.setSource(src);
//		repaint();
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		//for debugging
//		this.controller.getHero().addPoints(10);
//		this.controller.getHero().getAttributes().put("gold", this.theHeroViewed.getAttributes().get("gold") + 100);
//		infoPanel.dispose();;
//		this.theHeroViewed.changed();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (infoPanel != null && !infoPanel.isVisible()) {
			infoPanel = new InfoDisplayPanel(this.theHeroViewed);
			infoPanel.setLocation(this.getLocationOnScreen().x - infoPanel.getWidth() > 0 ? this.getLocationOnScreen().x - infoPanel.getWidth() : this.getLocationOnScreen().x + this.getWidth(),
					this.getLocationOnScreen().y - infoPanel.getHeight() > 0 ? this.getLocationOnScreen().y - infoPanel.getHeight() : this.getLocationOnScreen().y + this.getHeight());
			infoPanel.setVisible(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		infoPanel.attemptDispose();
		
	}
	
//	public void setHero(AbstractHero hero) {
//		this.theHeroViewed = this.theHeroViewed;
//	}


	public void update(AbstractHero theHero) {
//		if (arg instanceof Abstractthis.theHeroViewed) {
//			this.theHeroViewed = (Abstractthis.theHeroViewed) arg;
//			this.theHeroViewedPanel.setthis.theHeroViewed(this.theHeroViewed);
//			infoPanel = new InfoDisplayPanel(this.theHeroViewed);
//			nameDisplay.setText(this.theHeroViewed.getFullName());
//			this.theHeroViewed.addObserver(this);
//			repaint();
//		}
		
		healthBar.setMaximum((int) (theHero.getAttributes().get("maxHealth") + theHero.getAdditionalStats().get("maxHealth")));
		healthBar.setValue(theHero.getAttributes().get("currentHealth"));
		healthBar.setString("HP: " + healthBar.getValue() + "/" + healthBar.getMaximum());
		magicBar.setMaximum(theHero.getAttributes().get("maxMagicPower"));
		magicBar.setValue(theHero.getAttributes().get("currentMagicPower"));
		magicBar.setString("MP: " + magicBar.getValue() + "/" + magicBar.getMaximum());
		repaint();
	}


}
