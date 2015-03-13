package model;

import layout.CalderraGUI;
import interfaces.Weapon;

@SuppressWarnings("serial")
public class DestroyerOfWorlds extends AbstractCard implements Weapon {

	private static final String SRC = "./src/resources/Destroyer of Worlds.jpg";
	private static final String CLASSNAME = "Destroyer of Worlds";
	private static final int COST = 50;
	
	
	
	public DestroyerOfWorlds(CalderraGUI controller) {
		super(SRC, CLASSNAME, controller, AbstractCard.NULL_GIF);
		this.cost = COST;
	}
	
}
