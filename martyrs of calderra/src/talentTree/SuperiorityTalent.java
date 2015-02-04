package talentTree;

import model.AbstractHero;

public class SuperiorityTalent extends AbstractTalent {

	
private static final String SRC = "";
	
	public SuperiorityTalent(AbstractHero hero) {
		super(hero, 1, .1, "Superiority", "superiority", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Magical spell damage increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Magical spell damage increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
}
