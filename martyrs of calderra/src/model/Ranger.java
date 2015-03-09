package model;

import layout.CalderraGUI;

public class Ranger extends AbstractHero {
	
	private static final String SOURCE = "./src/resources/Ranger.jpg";
	private static final String CLASS = "Ranger";
	private static final String[] INITIAL_ATTS = {"accuracy", "speed", "speed", "strength"};
	
	
	public Ranger(CalderraGUI controller) {
		this("Silith", controller);
	}
	
	public Ranger(String name, CalderraGUI controller) {
		super(name, SOURCE, CLASS, INITIAL_ATTS, controller);
	}

	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
