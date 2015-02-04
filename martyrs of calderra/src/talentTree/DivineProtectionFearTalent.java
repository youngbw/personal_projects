package talentTree;

import model.AbstractHero;

public class DivineProtectionFearTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public DivineProtectionFearTalent(AbstractHero hero) {
		super(hero, 1, .05, "Divine Protection: Fear", "fearReduction", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Incoming damage from Fear based enemies reduced by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Incoming damage from Fear based enemies reduced by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	} 
	
}
