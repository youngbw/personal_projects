package layout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.AbstractCard;
import model.AbstractHero;
import model.AbstractVillain;
import model.Consumable;

public class MyPopupMenu extends JPopupMenu {

	AbstractHero hero;
	AbstractCard card;
	
	public MyPopupMenu(AbstractHero hero, AbstractCard card) {
		this.hero = hero;
		this.card = card;
		setup();
		
	}
	
	
	private void setup() {
		if (!(this.hero instanceof AbstractVillain) && !this.hero.getIsInBattle()) {
			if (card.isInBag && !(this.card instanceof Consumable)) {
				JMenuItem equip = new JMenuItem("Equip");
				equip.setMnemonic('E');
				equip.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
					hero.equipCard(card);
					setVisible(false);
					
					}
				});
				this.add(equip);
			}
			
			if (card.isEquipped) {
				JMenuItem unequip = new JMenuItem("Unequip");
				unequip.setMnemonic('U');
				unequip.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						hero.unequipCard(card);
						setVisible(false);
					}
				});
				this.add(unequip);
			}
			
			
			JMenuItem delete = new JMenuItem("Delete");
			delete.setMnemonic('D');
			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					hero.deleteCard(card);
				}
			});
			this.add(delete);
			
			
		}
		
	}
		
	
	
}
