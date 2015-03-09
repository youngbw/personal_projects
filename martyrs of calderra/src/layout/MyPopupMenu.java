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

	CalderraGUI controller;
	private AbstractCard card;
	
	public MyPopupMenu(CalderraGUI controller, AbstractCard card) {
		this.controller = controller;
		this.card = card;
//		System.out.println(this.controller + " " + this.card);
		setup();
		
	}
	
	
	private void setup() {
		if (!(this.controller.getHero() instanceof AbstractVillain) && !this.controller.getHero().getIsInBattle()) {
			if (card.isInBag && !(this.card instanceof Consumable)) {
				JMenuItem equip = new JMenuItem("Equip");
				equip.setMnemonic('E');
				equip.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.getHero().equipCard(card);
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
						controller.getHero().unequipCard(card);
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
					controller.getHero().deleteCard(card);
				}
			});
			this.add(delete);
			
			
		}
		
	}
		
	
	
}
