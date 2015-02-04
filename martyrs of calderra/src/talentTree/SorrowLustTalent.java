package talentTree;

import model.AbstractHero;

public class SorrowLustTalent extends AbstractTalent {

	
private static final String SRC = "";
	
	
	public SorrowLustTalent(AbstractHero hero) {
		super(hero, 1, .1, "Lust Talent: Sorrow", "damageSorrow", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase damage by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when attacking Sorrow based enemies";
		} else {
			return "Increase damage by " + this.currentPoints * this.amountPerPoint * 10 + "% when attacking Sorrow based enemies";
		}
	}
}
