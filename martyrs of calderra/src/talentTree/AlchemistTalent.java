package talentTree;

import model.AbstractHero;

public class AlchemistTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public AlchemistTalent(AbstractHero hero) {
		super(hero, 1, .3, "Ultimate: Alchemist", null, SRC);
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
			return "Potions are " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% more effective";
		} else {
			return "Potions are " + this.currentPoints * this.amountPerPoint * 100 + "% more effective";
		}
	}
	
}
