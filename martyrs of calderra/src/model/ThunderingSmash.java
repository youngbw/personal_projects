package model;

import java.util.ArrayList;

import layout.CalderraGUI;
import interfaces.Physical;

@SuppressWarnings("serial")
public class ThunderingSmash extends AbstractCard implements Physical {

	private static final String[] ATTACK_GIF = {"src/resources/TSstart.jpg", "src/resources/TSstart.jpg", "src/resources/TSmid1.jpg", "src/resources/TSfinal.jpg"};
	private static final int CRUX_COST = 5;
	
	public ThunderingSmash(CalderraGUI controller) {
		super("src/resources/UndyingTremors.jpg", "Thundering Smash", controller, ATTACK_GIF);
		this.cost = 100;
		
	}
	
	
	@Override
	public int getCruxCost() {
		return CRUX_COST;
	}
	
	public String toString() {
		return "A powerful physical attack that sunders the ground beneath the feet of your foe with terrifying force";
	}

	@Override
	public void getUpgrade() {
		this.controller.getHero().setAdditionalStatInteger("strength", 10, INCREMENT);
	}

	@Override
	public void getDowngrade() {
		this.controller.getHero().setAdditionalStatInteger("strength", 10, DECREMENT);
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
