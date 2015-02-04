package model;

public class Ranger extends AbstractHero {
	
	private static final String SOURCE = "./src/resources/Ranger.jpg";
	private static final String CLASS = "Ranger";
	private static final String[] INITIAL_ATTS = {"accuracy", "speed", "speed", "strength"};
	
	
	public Ranger() {
		this("Silith");
	}
	
	public Ranger(String name) {
		super(name, SOURCE, CLASS, INITIAL_ATTS);
	}

	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
