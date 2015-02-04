package talentTree;

import model.AbstractHero;

public class RighteousRetaliationTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public RighteousRetaliationTalent(AbstractHero hero) {
		super(hero, 5, .02, "Righteous Retaliation", "turnDependentDamage", SRC);
		this.enabled = true;
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "After a heal, your next attacks damage is increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "After a heal, your next attacks damage is increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
}
