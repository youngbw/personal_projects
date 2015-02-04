package talentTree;

import model.AbstractHero;

public class ElusivenessTalent extends AbstractTalent {

	
private static final String SRC = "";
	
	public ElusivenessTalent(AbstractHero hero) {
		super(hero, 3, .03, "Elusiveness", null, SRC);
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
			return "Increase armor by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% of speed";
		} else {
			return "Increase armor by " + this.currentPoints * this.amountPerPoint * 100 + "% of speed";
		}
	}
}
