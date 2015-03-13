package inventory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;

import layout.CalderraGUI;
import layout.MyPanel;
import model.AbstractCard;
import model.AbstractHero;
import model.Consumable;
import model.HealthPotion;

@SuppressWarnings("serial")
public class InventoryGUI extends JDialog {

	//Best to keep this as a perfect square to maintain integrity of GUI interface.
	//If not change logic below from MAth.sqrt();
	public static final int MAX_BAG_SIZE = 25;
	private int numPanels = 0;
	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	private CalderraGUI controller;
	private ArrayList<AbstractCard> bag;
//	private ArrayList<MyPanel> fillerPanels;
	private ArrayList<InventoryPanel> inventoryPanels;
	
	public InventoryGUI(CalderraGUI controller) {
		bag = new ArrayList<AbstractCard>();
		this.controller = controller;
//		fillerPanels = new ArrayList<MyPanel>();
		inventoryPanels = new ArrayList<InventoryPanel>();
		this.setAlwaysOnTop(true);
		fillPanels();
		setup();
	}
	
	private void fillPanels() {
		for (int i = 0; i < MAX_BAG_SIZE; i++) {
			InventoryPanel panel = new InventoryPanel(controller);
			inventoryPanels.add(panel);
		}
	}
	
//	@Override
//    public void paintComponents(final Graphics theGraphics) {
//        super.paintComponents(theGraphics);
//		final Graphics2D g2D = (Graphics2D) theGraphics;
//        paintBag();
//	}
	
	public boolean addCard(AbstractCard card) {
		if (card instanceof Consumable) {
			for (InventoryPanel panel: inventoryPanels) {
				if (panel.getCard() != null
						&& (panel.getCard() != null && (panel.getCard()).equals(card)
						|| (panel.getCard().getName().equals(card.getName()) && ((Consumable)panel.getCard()).getQuantity() < ((Consumable)panel.getCard()).MAX_AMOUNT))) {
							((Consumable)panel.getCard()).incrementQuantity();
							panel.repaint();
							System.out.println(((Consumable)panel.getCard()).getName() + " quantity = " + ((Consumable)panel.getCard()).getQuantity() + " of ID: " + ((Consumable)panel.getCard()).localID);
							return true;
				}
			}
		}
			
			for (int i = 0; i < MAX_BAG_SIZE; i++) {
				if (inventoryPanels.get(i).getCard() == null) {
					inventoryPanels.get(i).addCard(card);
					inventoryPanels.get(i).getCard().isInBag = true;
					inventoryPanels.get(i).repaint();
					bag.add(card);
					return true;
				}
		}
		return false;
	}
	
	public AbstractCard removeCard(AbstractCard target) {
		if (!isEmpty()) {
			for (AbstractCard c: bag) {
				if (c.equals(target)) {
					this.controller.getHero().getAttributes().put("numInBag", this.controller.getHero().getAttributes().get("numInBag") - 1); //update bag size
					bag.remove(c);
					
					for (int i = 0; i < MAX_BAG_SIZE; i++) {
						if (inventoryPanels.get(i).getCard() != null && target instanceof Consumable && ((Consumable)target).equals(inventoryPanels.get(i).getCard())) {
							inventoryPanels.get(i).getCard().isInBag = false;
							inventoryPanels.get(i).removeCard();
//							inventoryPanels.get(i).repaint();
							break;
						} else if (inventoryPanels.get(i).getCard() != null && inventoryPanels.get(i).getCard().equals(target)) {
							inventoryPanels.get(i).getCard().isInBag = false;
							inventoryPanels.get(i).removeCard();
							break;
						}
					}
//					this.remove(c);
//					paintBag();
					repaint();
					return c;
				}
			}
		}
//		paintBag();
		return null;
	}
	
	public void deleteCard(AbstractCard card) {
		this.removeCard(card);
		if (this.bag.contains(card)) {
			this.bag.remove(card);
		}
	}
	
	public int getBagSize() {
		return bag.size();
	}
	
	private boolean isEmpty() {
		return bag.size() == 0;
	}
	
	public boolean isFull() {
		return bag.size() == MAX_BAG_SIZE;
	}
	
	private void setup() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 3));
//		this.setLayout(new GridLayout((int)Math.sqrt(MAX_BAG_SIZE), (int)Math.sqrt(MAX_BAG_SIZE)));
		this.setLayout(new BorderLayout());
		loadBag();
		this.setLocation(SCREEN_WIDTH / 2 + this.getWidth() / 4, SCREEN_HEIGHT / 2);
		
	}
	
	private void loadBag() {
		MyPanel thePanel = new MyPanel("./src/resources/insidebag.jpg");
		thePanel.setLayout(new GridLayout((int)Math.sqrt(MAX_BAG_SIZE), (int)Math.sqrt(MAX_BAG_SIZE)));
		for (int i = 0; i < MAX_BAG_SIZE; i++) {
			thePanel.add(inventoryPanels.get(i));
		}
		this.add(thePanel, BorderLayout.CENTER);
	}
	
	public void enterBattle() {
		for (AbstractCard card: bag) {
			card.isInBattle = true;
		}
	}
	
	public void exitBattle() {
		for (AbstractCard card: bag) {
			card.isInBattle = false;
		}
	}
	
	
}
