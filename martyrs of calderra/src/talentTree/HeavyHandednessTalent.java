package talentTree;

import model.AbstractHero;

public class HeavyHandednessTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public HeavyHandednessTalent(AbstractHero hero) {
		super(hero, 5, .03, "Heavy Handedness", "2Hand", SRC);
		this.enabled = true;
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase attack damage, but lose armor equal to " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when weilding one weapon and no shield";
		} else {
			return "Increase attack damage, but lose armor equal to " + this.currentPoints * this.amountPerPoint * 100 + "% when weilding one weapon and no shield";
		}
	}
}
