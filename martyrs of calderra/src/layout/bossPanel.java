package layout;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import model.AbstractHero;
import model.AbstractVillain;

@SuppressWarnings("serial")
public class bossPanel extends MyPanel implements MouseListener, UserInteractive {

	private boolean enabled;
	private CalderraGUI controller;
	BattleGUI battle;
//	private int numBossesDefeated;
	
	public bossPanel(String src, CalderraGUI controller) {
		super(src);
		this.controller = controller;
//		this.numBossesDefeated = 0;
		this.addMouseListener(this);
		this.controller.getHero().addObserver(this);
		this.enabled = false;
		repaint();
	}
	
	
	public void mouseClicked(MouseEvent e) {
		battle = new BattleGUI(this.controller);
		battle.setVisible(true);
	}

	public void mousePressed(MouseEvent e) {}


	
	public void mouseReleased(MouseEvent e) {}


	
	@Override
	public void mouseEntered(MouseEvent e){
		if (this.enabled) this.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY));
	}


	@Override
	public void mouseExited(MouseEvent e) {
		if (this.enabled) this.setBorder(new LineBorder(Color.BLACK));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			if (((String)arg).equals("Enable Boss")) {
				this.setEnabled();
			}
		}
		
//		if (arg instanceof AbstractHero) {
//			this.hero = (AbstractHero) arg;
////			this.hero.addObserver(this);
//			
////			if (battle != null) {
////				battle.update(o, arg);
//////				this.hero.addObserver(battle);
////			}
//			
//			if (this.hero.getLevel() % 15 == 0 && this.hero.getBossesDefeated() < this.hero.getLevel() / 15) {
//				this.setEnabled();
//			}
//		}
		
	}
	
	public String toString() {
		return "BOSS";
	}

}
