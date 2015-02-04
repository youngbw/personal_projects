package talentTree;

import model.AbstractHero;

public class BlessedMiracleTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public BlessedMiracleTalent(AbstractHero hero) {
		super(hero, 1, .1, "Ultimate: Blessed Miracle", "healing", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Healing immediately increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Healing immediately increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	} 
	
}
