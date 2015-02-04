package model;

public class Druid extends AbstractHero {

	private static final String SOURCE = "./src/resources/Druid.jpg";
	private static final String CLASS = "Druid";
	private static final String[] INITIAL_ATTS = {"strength", "maxMagicPower", "currentMagicPower", "accuracy", "speed"};
	
	
	public Druid() {
		this("Silith");
	}
	
	public Druid(String name) {
		super(name, SOURCE, CLASS, INITIAL_ATTS);
	}
	
}
