package talentTree;

import model.AbstractHero;

public class InnerPeaceTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public InnerPeaceTalent(AbstractHero hero) {
		super(hero, 5, .02, "Inner Peace", "damageReduction", SRC);
		this.enabled = true;
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Damage taken reduced by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Damage taken reduced by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
}
