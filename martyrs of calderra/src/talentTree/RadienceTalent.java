package talentTree;

import model.AbstractHero;

public class RadienceTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public RadienceTalent(AbstractHero hero) {
		super(hero, 5, .01, "Radience", "healing", SRC);
		this.enabled = true;
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Healing increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% of intelligence";
		} else {
			return "Healing increased by " + this.currentPoints * this.amountPerPoint * 100 + "% of intelligence";
		}
	}
	
}
