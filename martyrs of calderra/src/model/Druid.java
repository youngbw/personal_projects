package model;

import layout.CalderraGUI;

public class Druid extends AbstractHero {

	private static final String SOURCE = "./src/resources/Druid.jpg";
	private static final String CLASS = "Druid";
	private static final String[] INITIAL_ATTS = {"strength", "maxMagicPower", "currentMagicPower", "accuracy", "speed"};
	
	
	public Druid(CalderraGUI controller) {
		this("Silith", controller);
	}
	
	public Druid(String name, CalderraGUI controller) {
		super(name, SOURCE, CLASS, INITIAL_ATTS, controller);
	}
	
}
