package model;

import layout.CalderraGUI;
import interfaces.Stacker;

public class MagicPotion extends Consumable implements Stacker {


//	private static final long serialVersionUID = 1L;
	//	protected static int ID;
	private static final String SRC = "/src/resources/MagicPotion.jpg";
	private static final int COST = 10;
	
	public MagicPotion(CalderraGUI controller) {
		super(SRC, "Magic Potion", controller);
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
		this.controller.getHero().getAttributes().put("currentMagicPower", this.controller.getHero().getCurrentMagicPower() + amount >
		this.controller.getHero().getMaxMagicPower() ? this.controller.getHero().getMaxMagicPower() : this.controller.getHero().getCurrentMagicPower() + amount);
		
		System.out.println("Current Magic Power: " + this.controller.getHero().getCurrentMagicPower());
		System.out.println("Quantity: " + this.getQuantity());
		this.controller.getHero().changed("stats");
	}
	
}
