package talentTree;

import model.AbstractHero;

public class QuicknessTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public QuicknessTalent(AbstractHero hero) {
		super(hero, 3, .01, "Quickness", "speed", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Speed increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Speed increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
	
}
