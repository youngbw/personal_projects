package model;

import java.util.ArrayList;

import interfaces.HealingCard;
import layout.CalderraGUI;


@SuppressWarnings("serial")
public class HealingTouch extends Heal {

	private static final String[] ATTACK_GIF = {"src/resources/TSstart.jpg", "src/resources/TSmid1.jpg", "src/resources/TSfinal.jpg"};
	private static final int CRUX_COST = 5;
	
//	AbstractHero hero;
	
	public HealingTouch(CalderraGUI controller) {
		super("src/resources/HealingTouch.jpg", "Healing Touch", controller);
		this.cost = 200;
		this.healingAmount = 4;
//		this.hero.addObserver(this);
		healType = HEAL_TYPE_BURST;
	}
	
	
	public int getHealAmount() {
		return this.healingAmount;
	}
	
	@Override
	public String[] getGifSrc() {
		return ATTACK_GIF;
	}
	
	@Override
	public int getCruxCost() {
		return CRUX_COST;
	}
	
	public String toString() {
		return "Heals the user for 25% of their max Health";
	}
	
	@Override
	public ArrayList<String> getAttackGif() {
		attackGif = new ArrayList<String>();
		for (String s: ATTACK_GIF) {
			attackGif.add(s);
		}
		return attackGif;
	}
}
