package model;

import interfaces.Stacker;

public class MagicPotion extends Consumable implements Stacker {


//	private static final long serialVersionUID = 1L;
	//	protected static int ID;
	private static final String SRC = "/src/resources/MagicPotion.jpg";
	private static final int COST = 10;
	
	public MagicPotion(AbstractHero hero) {
		super(hero, SRC, "Magic Potion");
		this.localID = ID;
		this.cost = COST;
	}
	
//	@Override
//	public void incrementQuantity(int amount) {
//		super.incrementQuantity(amount);
//		if (this.quantity == MAX_AMOUNT) ID++;
//	}
	
	@Override
	public void consume() {
		int amount = this.hero.consume(this, CONSUME_TYPE_POTION, 20);
		this.hero.getAttributes().put("currentMagicPower", this.hero.getCurrentMagicPower() + amount >
				this.hero.getMaxMagicPower() ? this.hero.getMaxMagicPower() : this.hero.getCurrentMagicPower() + amount);
		
		System.out.println("Current Magic Power: " + this.hero.getCurrentMagicPower());
		System.out.println("Quantity: " + this.getQuantity());
		this.hero.changed("stats");
	}
	
}
