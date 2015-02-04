package talentTree;

import model.AbstractHero;

public class DivineProtectionHateTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public DivineProtectionHateTalent(AbstractHero hero) {
		super(hero, 1, .05, "Divine Protection: Hate", "hateReduction", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Incoming damage from Hate based enemies reduced by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Incoming damage from Hate based enemies reduced by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	} 
	
}
