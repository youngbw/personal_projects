package model;

import interfaces.Weapon;

@SuppressWarnings("serial")
public class ShieldOfTheRighteous extends AbstractCard implements Weapon {

	private static final String SRC = "./src/resources/shield of the righteous.jpeg";
	private static final String CLASSNAME = "Shield of the Righteous";
	private static final int COST = 40;
	
	public ShieldOfTheRighteous() {
		super();
	}
	
	public ShieldOfTheRighteous(AbstractHero hero) {
		super(hero, SRC, CLASSNAME);
		this.cost = COST;
	}
	
}
