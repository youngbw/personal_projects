package layout;

import model.AbstractCard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import model.AbstractHero;

@SuppressWarnings("serial")
public class ShopPanel extends JPanel {

	private static final int GRID_WIDTH = 5;
	private static final int GRID_HEIGHT = 5;
	private static final String[] TAB_LABELS = {"Offensive Items", "Defensive Items", "Consumables", "Attacks", "Other"};
	private static final String SHOP_SOURCE = "./src/resources/ezra.jpeg";
	
	
	private CalderraGUI controller;
	private ArrayList<SubShopPanel> panels;
	
	public ShopPanel(CalderraGUI controller, ArrayList<ArrayList<AbstractCard>> shopLists) {
		this.setLayout(new BorderLayout());
		panels = new ArrayList<SubShopPanel>();
		this.controller = controller;
		setup(shopLists);
	}
	
	private void setup(ArrayList<ArrayList<AbstractCard>> shopLists) {
		//Shop keeper portrait
		MyPanel keeperPanel = new MyPanel(SHOP_SOURCE);
		keeperPanel.setPreferredSize(new Dimension(MyPanel.SCREEN_WIDTH / 2 / 3, MyPanel.SCREEN_HEIGHT / 2));
		
		add(keeperPanel, BorderLayout.EAST);
		
		//shop listings
		MyPanel centerPanel = new MyPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.BLACK);
		JTabbedPane myTabbedPanel = new JTabbedPane();
		
		//Wont work until there is something in shopLists
//		if (!shopLists.isEmpty()) {
			for (int i = 0; i < shopLists.size(); i++) {
				MyPanel tabPanel = new MyPanel();
				tabPanel.setLayout(new GridLayout(GRID_WIDTH, GRID_HEIGHT));
				ArrayList<AbstractCard> nextList= (ArrayList<AbstractCard>) shopLists.get(i);
				for (int j = 0; j < nextList.size() || j < GRID_HEIGHT * GRID_WIDTH; j++) {
					if (j < nextList.size() && nextList.get(j) != null) {
						SubShopPanel panel = new SubShopPanel(this.controller, nextList.get(j));
						panels.add(panel);
						tabPanel.add(panel);
					} else {
						JPanel thePanel = new JPanel();
						thePanel.setBorder(new LineBorder(Color.CYAN));
						thePanel.setBackground(new Color(20, 20, 20, 200));
						tabPanel.add(thePanel);
					}
				}
				myTabbedPanel.add(TAB_LABELS[i], tabPanel);
			}
//		}
		
		centerPanel.add(myTabbedPanel, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);
		
		
		
		
	}

	
	public void update(Observable o, Object arg) {
//		if (arg instanceof AbstractHero) {
////			System.out.println("In shop hero update: " + arg.getClass().getSimpleName());
//			this.hero = (AbstractHero) arg;
//			this.hero.addObserver(this);
//			
//			for (SubShopPanel p: panels) {
//				p.update(o, arg);
//			}
//		}
		
	}
	
}
