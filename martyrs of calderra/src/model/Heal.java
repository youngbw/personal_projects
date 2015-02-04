package model;

import interfaces.AttackCard;

public class Heal extends AbstractCard implements AttackCard {

	public static final String HEAL_TYPE_PERIODIC = "periodic";
	public static final String HEAL_TYPE_BURST = "burst";
	public static final String HEAL_TYPE_NONE = "none";
	
	private static final int CRUX_COST = 10;
	
	private int healingAmount;
	
	
	
	public Heal(AbstractHero hero, String src, String name) {
		super(hero, src, name);
		this.healType = HEAL_TYPE_NONE;
		healingAmount = 0;
	}




	@Override
	public int getCruxCost() {
		return CRUX_COST;
	}
	
	
}
