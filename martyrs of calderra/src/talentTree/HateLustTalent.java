package talentTree;

import model.AbstractHero;

public class HateLustTalent extends AbstractTalent {

private static final String SRC = "";
	
	
	public HateLustTalent(AbstractHero hero) {
		super(hero, 1, .1, "Lust Talent: Hate", "damageHate", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase damage by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when attacking Hate based enemies";
		} else {
			return "Increase damage by " + this.currentPoints * this.amountPerPoint * 10 + "% when attacking Hate based enemies";
		}
	}
}
