package talentTree;

import model.AbstractHero;

@SuppressWarnings("serial")
public class RageTalent extends AbstractTalent {

	private static final String SRC = "./src/resources/Rage.jpg";
	
	
	RageTalent(AbstractHero hero) {
		super(hero, 3, .02, "Rage", "strength", SRC);
		this.enabled = true;
	}
	
	@Override
	public boolean increment() {
		boolean able = super.increment();
		if (this.currentPoints <= maxPoints && able) {
			this.hero.setAdditionalStatPercent(this.category, this.amountPerPoint, INCREMENT_VALUE);
			this.hero.changed();
		}
		
		return able;
	}
	
	@Override
	public boolean decrement() {
		boolean able = super.decrement();
		if (able) {
			this.hero.setAdditionalStatPercent(this.category, this.amountPerPoint, DECREMENT_VALUE);
			this.hero.changed();
		}
		
		return able;
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase strength by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Increase strength by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
	
}
