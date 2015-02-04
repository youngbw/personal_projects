package model;

public class Mage extends AbstractHero {

	private static final String SOURCE = "./src/resources/Mage.jpg";
	private static final String CLASS = "Mage";
	private static final String[] INITIAL_ATTS = {"intel", "maxMagicPower", "currentMagicPower", "accuracy"};
	
	
	public Mage() {
		this("Silith");
	}
	
	public Mage(String name) {
		super(name, SOURCE, CLASS, INITIAL_ATTS);
	}
	
	
	
	
}
