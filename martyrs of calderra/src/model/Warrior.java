package model;

import layout.CalderraGUI;

public class Warrior extends AbstractHero {

	private static final String SOURCE = "./src/resources/Warrior.jpg";
	private static final String CLASS = "Warrior";
	private static final String[] INITIAL_ATTS = {"strength", "maxHealth", "currentHealth", "armor"};
	
	
	public Warrior(CalderraGUI controller) {
		this("Silith", controller);
	}
	
	public Warrior(String name, CalderraGUI controller) {
		super(name, SOURCE, CLASS, INITIAL_ATTS, controller);
	}
	
	
}
