package talentTree;

import model.AbstractHero;

@SuppressWarnings("serial")
public class FearLustTalent extends AbstractTalent {

	private static final String SRC = "./src/resources/FearLust.jpg";
	
	
	public FearLustTalent(AbstractHero hero) {
		super(hero, 1, .1, "Lust Talent: Fear", "damageFear", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase damage by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when attacking Fear based enemies";
		} else {
			return "Increase damage by " + this.currentPoints * this.amountPerPoint * 10 + "% when attacking Fear based enemies";
		}
	}
	
}
