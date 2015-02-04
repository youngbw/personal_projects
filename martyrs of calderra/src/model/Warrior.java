package model;

public class Warrior extends AbstractHero {

	private static final String SOURCE = "./src/resources/Warrior.jpg";
	private static final String CLASS = "Warrior";
	private static final String[] INITIAL_ATTS = {"strength", "maxHealth", "currentHealth", "armor"};
	
	
	public Warrior() {
		this("Silith");
	}
	
	public Warrior(String name) {
		super(name, SOURCE, CLASS, INITIAL_ATTS);
	}
	
	
}
