package talentTree;

import model.AbstractHero;

public class LethalityTalent extends AbstractTalent {

private static final String SRC = "";
	
	public LethalityTalent(AbstractHero hero) {
		super(hero, 1, .1, "Ultimate: Lethality", "lethality", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Poison damage increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Poison damage increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
	
}
