package talentTree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import layout.MyPanel;
import model.AbstractHero;

//This represents one tree of the three
@SuppressWarnings("serial")
public class Tree extends MyPanel implements Observer {

	private int tiers;
	List<AbstractTalent> rootList;
	
	
	
	
	public Tree(String title, AbstractHero hero, int tiers) {
		this.tiers = tiers;
		this.setBorder(new TitledBorder(title));
		this.setLayout(new GridLayout(tiers, 1));
		this.rootList = new ArrayList<AbstractTalent>();
	}
	
	public void setup() {
		//First Row
		
		ArrayList<AbstractTalent> set = new ArrayList<>();
		for (int i = 0; i < rootList.size(); i++) {
			set.add(rootList.get(i));
		}
		set = fillRows(set);
		
		int tierCounter = 1;
		while (tierCounter <= this.tiers && set.size() > 0) {
			set = fillRows(set);
			tierCounter++;
		}
		
		
	}
	
	private ArrayList<AbstractTalent> fillRows(ArrayList<AbstractTalent> list) {
		ArrayList<AbstractTalent> set = new ArrayList<>();
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout());
		for (int i = 0; i < list.size(); i++) {
			MyPanel panel = new MyPanel();
			BorderLayout layout = new BorderLayout();
			panel.setLayout(layout);
			panel.add(list.get(i));
			
			//add children to set
			for (int j = 0; j < list.get(i).talentChildren.size(); j++) {
				if (!set.contains(list.get(i).talentChildren.get(j))) {
					set.add(list.get(i).talentChildren.get(j));
				}
			}
			panel1.add(panel);
		}
		
		this.add(panel1);
		
		
		return set;
	}

	public void addRoots(AbstractHero hero, AbstractTalent talent) {
		hero.addObserver(talent);
		rootList.add(talent);
	}
	
	public void addTalentPoints(int points) {
		if (rootList.get(0) != null) rootList.get(0).incrementTotalPoints(points);
	}
	
	
}
