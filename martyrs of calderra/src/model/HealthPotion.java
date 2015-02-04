package model;

import interfaces.Stacker;

public class HealthPotion extends Consumable implements Stacker {


//	private static final long serialVersionUID = 1L;
	//	protected static int ID;
	private static final String SRC = "/src/resources/HealthPotion.jpg";
	private static final int COST = 10;
	
	public HealthPotion(AbstractHero hero) {
		super(hero, SRC, "Health Potion");
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
		this.hero.getAttributes().put("currentHealth", this.hero.getAttributes().get("currentHealth") + amount >
				this.hero.getmaxHealth() ? this.hero.getmaxHealth() : this.hero.getcurrentHealth() + amount);
		
		System.out.println("Current Health: " + this.hero.getcurrentHealth());
		System.out.println("Quantity: " + this.getQuantity());
		this.hero.changed("stats");
	}
	
	
	
	
	
}
