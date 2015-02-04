package talentTree;

import model.AbstractHero;

public class TactTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public TactTalent(AbstractHero hero) {
		super(hero, 3, .05, "Tact", "tact", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Shop item cost reduced by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Shop item cost reduced by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
}
