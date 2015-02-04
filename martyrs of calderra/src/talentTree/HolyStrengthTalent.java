package talentTree;

import model.AbstractHero;

public class HolyStrengthTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public HolyStrengthTalent(AbstractHero hero) {
		super(hero, 1, .2, "Ultimate: Holy Strength", "holyStrength", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Strength increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% of last heal for one attack";
		} else {
			return "Strength increased by " + this.currentPoints * this.amountPerPoint * 100 + "% of last heal for one attack";
		}
	} 
	
}
