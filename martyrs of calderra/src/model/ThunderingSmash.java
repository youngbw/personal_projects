package model;

import interfaces.Physical;

@SuppressWarnings("serial")
public class ThunderingSmash extends AbstractCard implements Physical {

	private static final String[] ATTACK_GIF = {"src/resources/TSstart.jpg", "src/resources/TSmid1.jpg", "src/resources/TSfinal.jpg"};
	private static final int CRUX_COST = 5;
	
	public ThunderingSmash(AbstractHero hero) {
		super(hero, "src/resources/UndyingTremors.jpg", "Thundering Smash");
		this.cost = 100;
	}

	
	@Override
	public void use() {
		this.hero.setAdditionalStatInteger("strength", 10, INCREMENT);
		super.use();
		this.hero.setAdditionalStatInteger("strength", 10, DECREMENT);
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
		return "A powerful physical attack that sunders the ground beneath the feet of your foe with terrifying force";
	}
	
}
