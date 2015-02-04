package talentTree;

import model.AbstractHero;

public class DeftnessTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public DeftnessTalent(AbstractHero hero) {
		super(hero, 3, .02, "Deftness", "accuracy", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Accuracy increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Accuracy increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
}
