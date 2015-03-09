package model;

import layout.CalderraGUI;
import interfaces.Stacker;

public class HealthPotion extends Consumable implements Stacker {


//	private static final long serialVersionUID = 1L;
	//	protected static int ID;
	private static final String SRC = "/src/resources/HealthPotion.jpg";
	private static final int COST = 10;
	
	public HealthPotion(CalderraGUI controller) {
		super(SRC, "Health Potion", controller);
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
		super.consume();
		int amount = this.controller.getHero().consume(this, CONSUME_TYPE_POTION, 20);
		this.controller.getHero().getAttributes().put("currentHealth", this.controller.getHero().getAttributes().get("currentHealth") + amount >
		this.controller.getHero().getmaxHealth() ? this.controller.getHero().getmaxHealth() : this.controller.getHero().getcurrentHealth() + amount);
		
		System.out.println("Current Health: " + this.controller.getHero().getcurrentHealth());
		System.out.println("Quantity: " + this.getQuantity());
		this.controller.getHero().changed("stats");
	}
	
	
	
	
	
}
