package listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.AbstractHero;

public class MyKeyListener extends KeyAdapter {

	
	
	private AbstractHero hero;
	
	public MyKeyListener(AbstractHero hero) {
		this.hero = hero;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_I) {
			hero.showInventory();
		} else if (e.getKeyCode() == KeyEvent.VK_T) {
			hero.showTalentTree();
		}
		
	}
	
}
