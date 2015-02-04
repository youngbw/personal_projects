package talentTree;

import model.AbstractHero;

public class DivineProtectionSorrowTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public DivineProtectionSorrowTalent(AbstractHero hero) {
		super(hero, 1, .05, "Divine Protection: Sorrow", "sorrowReduction", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Incoming damage from Sorrow based enemies reduced by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Incoming damage from Sorrow based enemies reduced by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	} 
}
