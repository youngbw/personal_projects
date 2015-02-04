package talentTree;

import model.AbstractHero;

public class NurturingTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public NurturingTalent(AbstractHero hero) {
		super(hero, 3, .05, "Nurturing", "periodicHealing", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Heal " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% of damage taken over next 5 attacks";
		} else {
			return "Heal " + this.currentPoints * this.amountPerPoint * 100 + "% of damage taken over next 5 attacks";
		}
	} 
	
	
}
