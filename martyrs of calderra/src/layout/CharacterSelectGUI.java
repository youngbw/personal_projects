package layout;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.AbstractHero;
import model.Druid;
import model.Mage;
import model.Ranger;
import model.Warrior;

@SuppressWarnings("serial")
public class CharacterSelectGUI extends javax.swing.JDialog implements MouseListener {
	

	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	private CalderraGUI controller;
	ArrayList<HeroDisplayPanel> panels;
	
	public CharacterSelectGUI(CalderraGUI controller) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.controller = controller;
		panels = new ArrayList<>();
		controller.characterSelectOn = true;
		setup();
	}
	
	private void setup() {
		setLayout(new GridLayout());
		this.setSize(new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));
		this.setLocation(SCREEN_WIDTH / 2 - this.getWidth() / 2, SCREEN_HEIGHT / 2 - this.getHeight() / 2);
		
		//Have to do these separate
		HeroDisplayPanel warriorPanel = new HeroDisplayPanel(controller, this.controller.getHero());
		HeroDisplayPanel rangerPanel = new HeroDisplayPanel(controller, this.controller.getHero());
		HeroDisplayPanel magePanel = new HeroDisplayPanel(controller, this.controller.getHero());
		HeroDisplayPanel druidPanel = new HeroDisplayPanel(controller, this.controller.getHero());
		
		warriorPanel.addMouseListener(this);
		rangerPanel.addMouseListener(this);
		magePanel.addMouseListener(this);
		druidPanel.addMouseListener(this);
		
		panels.add(warriorPanel);
		panels.add(druidPanel);
		panels.add(magePanel);
		panels.add(rangerPanel);
		
		add(warriorPanel);
		add(rangerPanel);
		add(magePanel);
		add(druidPanel);
		
		setVisible(true);
	}
	
	
	public void select(AbstractHero theHero) {
		this.setVisible(true);
		boolean finished = false;
		String optionTitle = "Hero Select";
		String name;
		int result = JOptionPane.YES_OPTION;
		
		
		while (!finished && result != JOptionPane.CANCEL_OPTION) {
			result = JOptionPane.showConfirmDialog(this, "You have chosen: " + theHero.getClass().getSimpleName() + "\n\nContinue?", optionTitle, JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				name = JOptionPane.showInputDialog(this, "Please write your name: ", optionTitle, JOptionPane.QUESTION_MESSAGE);
				
				if (!name.equals("") && name != null) {
					result = JOptionPane.showConfirmDialog(this, "Your name is " + name + "?", optionTitle, JOptionPane.YES_NO_OPTION);
					
					if (result == JOptionPane.YES_OPTION) {
						finished = true;
						theHero.resetInventory();
						setNewHero(theHero, name);
						controller.characterSelectOn = false;
						
						//dispose of all info panels that may be sticking around
						for (HeroDisplayPanel h: panels) h.infoPanel.dispose();
						
						
						dispose();
					}
				}
			}			
		}
	}
	
	private void setNewHero(AbstractHero theHero, String name) {
		if (theHero instanceof Warrior) {
			System.out.println("instance of Warrior");
			controller.setHero(new Warrior(name, this.controller));
		} else if (theHero instanceof Ranger) {
			System.out.println("instance of Ranger");
			controller.setHero(new Ranger(name, this.controller));
		} else if (theHero instanceof Mage) {
			System.out.println("instance of Mage");
			controller.setHero(new Mage(name, this.controller));
		} else if (theHero instanceof Druid) {
			System.out.println("instance of Druid");
			controller.setHero(new Druid(name, this.controller));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		HeroDisplayPanel panel = (HeroDisplayPanel) e.getSource();
		select(controller.getHero()); //have to fix this for character select -- panel.getHero() = old version
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}
