package talentTree;

import model.AbstractHero;

public class SuppressiveSpeedTalent extends AbstractTalent {

	
private static final String SRC = "";
	
	public SuppressiveSpeedTalent(AbstractHero hero) {
		super(hero, 1, .2, "Ultimate: Supressive Speed", "speed", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Speed increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when specialty weapon is equipped";
		} else {
			return "Speed increased by " + this.currentPoints * this.amountPerPoint * 100 + "% when specialty weapon is equipped";
		}
	}
}
