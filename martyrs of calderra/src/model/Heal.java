package model;

import java.util.ArrayList;

import layout.CalderraGUI;
import interfaces.AttackCard;
import interfaces.HealingCard;

public class Heal extends AbstractCard implements AttackCard, HealingCard {

	public static final String HEAL_TYPE_PERIODIC = "periodic";
	public static final String HEAL_TYPE_BURST = "burst";
	public static final String HEAL_TYPE_NONE = "none";
	
	private static final int CRUX_COST = 10;
	
	protected int healingAmount;
	
	
	
	public Heal(String src, String name, CalderraGUI controller) {
		super(src, name, controller);
		this.healType = HEAL_TYPE_NONE;
		healingAmount = 0;
	}


	@Override
	public int getCruxCost() {
		return CRUX_COST;
	}


	@Override
	public int getHealAmount() {return 0;}


	@Override
	public void getUpgrade() {}


	@Override
	public void getDowngrade() {}


	@Override
	public ArrayList<String> getAttackGif() {
		return null;
	}


	
	
	
}
