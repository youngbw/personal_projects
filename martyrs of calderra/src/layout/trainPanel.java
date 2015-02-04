package layout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import model.AbstractHero;
import model.AbstractVillain;

@SuppressWarnings("serial")
public class trainPanel extends MyPanel implements MouseListener, UserInteractive {

	AbstractHero hero;
	BattleGUI battle;
	boolean mouseOn;
	
	public trainPanel(String src, AbstractHero hero) {
		super(src);
		mouseOn = false;
		this.hero = hero;
		this.addMouseListener(this);
		this.hero.addObserver(this);
		this.enabled = true;
		repaint();
	}
	
	
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
		final Graphics2D g2D = (Graphics2D) theGraphics;
		if (mouseOn) {
			g2D.setColor(new Color(255, 255, 255, 75));
			g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
        
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof AbstractHero && !(o instanceof AbstractVillain)) {
			this.hero = (AbstractHero)o;
		}
		if (arg instanceof AbstractHero) {
			this.hero = (AbstractHero) arg;
			this.hero.addObserver(this);
			
			if (battle != null) {
				battle.update(o, arg);
				this.hero.addObserver(battle);
			}
		}
		
	}
	
	public void mouseClicked(MouseEvent e) {
		if (!this.hero.getIsInBattle()) {
			if (battle != null) {
				battle.setHero(this.hero);
				battle.newFight(this.hero);
			} else {
				battle = new BattleGUI(this.hero);
			}
			battle.setLocationRelativeTo(null);
			battle.setVisible(true);
		}
		
	}


	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e){
//		if (this.enabled) this.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY));
		mouseOn = true;
		repaint();
	}


	@Override
	public void mouseExited(MouseEvent e) {
//		if (this.enabled) this.setBorder(new LineBorder(Color.BLACK));
		mouseOn = false;
		repaint();
	}

	public String toString() {
		return "TRAIN";
	}
	
}
