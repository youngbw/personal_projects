package talentTree;

import model.AbstractHero;

public class BarteringTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public BarteringTalent(AbstractHero hero) {
		super(hero, 1, .20, "Bartering", null, SRC);
	}
	
	@Override
	public boolean increment() {
		boolean able = super.increment();
		if (this.currentPoints <= maxPoints && able) {
			this.hero.addSpecialCase(this.name, this.amountPerPoint, INCREMENT_VALUE);
			this.hero.changed();
		}
		
		return able;
	}
	
	@Override
	public boolean decrement() {
		boolean able = super.decrement();
		if (able) {
			this.hero.addSpecialCase(this.name, this.amountPerPoint, DECREMENT_VALUE);
			this.hero.changed();		
		}
		
		return able;
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Item value increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% of the previous value when selling back to the shop";
		} else {
			return "Item value increased by " + this.currentPoints * this.amountPerPoint * 100 + "% of the previous value when selling back to the shop";
		}
	}
}
