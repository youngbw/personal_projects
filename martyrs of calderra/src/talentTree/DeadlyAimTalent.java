package talentTree;

import model.AbstractHero;

public class DeadlyAimTalent extends AbstractTalent {

	
private static final String SRC = "";
	
	public DeadlyAimTalent(AbstractHero hero) {
		super(hero, 1, .2, "Deadly Aim", "deadlyAim", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Critical Strike chance increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% of accuracy";
		} else {
			return "Critical Strike chance increased by " + this.currentPoints * this.amountPerPoint * 100 + "% of accuracy";
		}
	}
}
