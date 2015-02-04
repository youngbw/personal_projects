package model;

import interfaces.Physical;

@SuppressWarnings("serial")
public class QuickStrike extends AbstractCard implements Physical {

	private static final String[] ATTACK_GIF = {"src/resources/mage.jpg", "", ""};
	private static final int CRUX_COST = 5;
	
	public QuickStrike(AbstractHero hero) {
		super(hero, "src/resources/Rage.jpg", "Quick Strike");
		this.cost = 100;
	}
	
	
	@Override
	public void use() {
		this.hero.setAdditionalStatPercent("speed", .5, INCREMENT);
		super.use();
		this.hero.setAdditionalStatPercent("speed", .5, DECREMENT);
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
		return "A swift attack that amplifies the users speed to unbeforeseen levels";
	}
	
	
	
}
