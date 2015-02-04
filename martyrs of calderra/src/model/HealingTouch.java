package model;


public class HealingTouch extends Heal {

	private static final String[] ATTACK_GIF = {"src/resources/TSstart.jpg", "src/resources/TSmid1.jpg", "src/resources/TSfinal.jpg"};
	private static final int CRUX_COST = 5;
	AbstractHero hero;
	
	public HealingTouch(AbstractHero hero) {
		super(hero, "src/resources/HealingTouch.jpg", "Healing Touch");
		this.cost = 200;
		this.hero = hero;
//		this.hero.addObserver(this);
		healType = HEAL_TYPE_BURST;
	}
	
	
	@Override
	public void use() {
		this.hero.healAmount = this.hero.getmaxHealth() / 4;
		System.out.println("Hero quarter health: " + this.hero.getClass().getSimpleName() + ", " + this.hero.healAmount);
		super.use();
		System.out.println(this.hero.getmaxHealth() + " MAX HEALTH");
		System.out.println(this.hero.healAmount + "Hero healAmount in HealingTouch");
//		this.hero.heal(this, this.getHealType());
		
		
		//ONLY NEED THIS WAY IF YOU WANT TO USE OUTSIDE OF BATTLE
//		if (this.hero.enemy != null) {
//			super.use();
//		} else if (this.hero.getCurrentMagicPower() - this.getCruxCost() >= 0) {
//			this.hero.getAttributes().put("currentHealth", this.hero.getcurrentHealth() + this.hero.healAmount <= this.hero.getmaxHealth() ?
//					this.hero.getcurrentHealth() + this.hero.healAmount : this.hero.getmaxHealth());
//			this.hero.getAttributes().put("currentMagicPower", this.hero.getCurrentMagicPower() - this.getCruxCost());
//		}
				
//		this.hero.changed();
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
	
	
}
