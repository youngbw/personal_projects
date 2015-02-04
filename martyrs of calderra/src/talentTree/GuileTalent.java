package talentTree;

import model.AbstractHero;

public class GuileTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public GuileTalent(AbstractHero hero) {
		super(hero, 3, .02, "Guile", "intel", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Intelligence increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Intelligence increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
}
