package talentTree;

import model.AbstractHero;

public class PoisonTippedBladeTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public PoisonTippedBladeTalent(AbstractHero hero) {
		super(hero, 3, .02, "Poison Tipped Blade", "periodicDamage", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Attacks Poison target, dealing " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% damage";
		} else {
			return "IAttacks Poison target, dealing " + this.currentPoints * this.amountPerPoint * 100 + "% damage";
		}
	}
	
	
}
