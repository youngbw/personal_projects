package talentTree;

import model.AbstractHero;

public class UndyingTremorsTalent extends AbstractTalent {

private static final String SRC = "./src/resources/UndyingTremors.jpg";
	
	public UndyingTremorsTalent(AbstractHero hero) {
		super(hero, 1, .2, "Ultimate: Undying Tremors", "undyingTremors", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Attack following an opponents miss increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Attack following an opponents miss increased by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
	}
	
}
