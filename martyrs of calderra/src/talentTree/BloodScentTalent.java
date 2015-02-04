package talentTree;

import model.AbstractHero;

@SuppressWarnings("serial")
public class BloodScentTalent extends AbstractTalent {

	private static final String SRC = "./src/resources/BloodScent.jpg";
	
	public BloodScentTalent(AbstractHero hero) {
		super(hero, 1, .1, "Blood Scent", "damageUnder20", SRC);
		this.enabled = true;
	}
	
	@Override
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase damage by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when attacking opponents under 20% health";
		} else {
			return "Increase damage by " + this.currentPoints * this.amountPerPoint * 100 + "% when attacking opponents under 20% health";
		}
	}
	
	
}
