package layout;

import inventory.InventoryPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.AbstractCard;

@SuppressWarnings("serial")
public class DropPanel extends InventoryPanel implements MouseListener {

	public static final int MAX_PICKUP = 2;
	public static int currentPickup = 0;
	
	public DropPanel(AbstractCard card, CalderraGUI controller) {
		super(card, controller);
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
//		int num = 1;
		if (this.card != null && currentPickup < MAX_PICKUP) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				controller.getHero().addCard(this.card);
				this.removeCard();
//				num--;
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				controller.getHero().equipCard(card);
				this.removeCard();
//				num--;
			}
			currentPickup++;
		}
	}
	
	
	
}
