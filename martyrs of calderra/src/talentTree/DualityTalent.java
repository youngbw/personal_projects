package talentTree;

import model.AbstractHero;

public class DualityTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public DualityTalent(AbstractHero hero) {
		super(hero, 5, .02, "Duality", "duality", SRC);
		this.enabled = true;
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase attack damage, but lose armor equal to " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when dual weidling weapons";
		} else {
			return "Increase attack damage, but lose armor equal to " + this.currentPoints * this.amountPerPoint * 100 + "% when dual weidling weapons";
		}
	}
	
	
}
