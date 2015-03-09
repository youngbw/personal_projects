package model;

import layout.CalderraGUI;

public class Mage extends AbstractHero {

	private static final String SOURCE = "./src/resources/Mage.jpg";
	private static final String CLASS = "Mage";
	private static final String[] INITIAL_ATTS = {"intel", "maxMagicPower", "currentMagicPower", "accuracy"};
	
	
	public Mage(CalderraGUI controller) {
		this("Silith", controller);
	}
	
	public Mage(String name, CalderraGUI controller) {
		super(name, SOURCE, CLASS, INITIAL_ATTS, controller);
	}
	
	
	
	
}
