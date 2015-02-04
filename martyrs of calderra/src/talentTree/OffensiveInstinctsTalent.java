package talentTree;

import model.AbstractHero;

public class OffensiveInstinctsTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public OffensiveInstinctsTalent(AbstractHero hero) {
		super(hero, 3, .05, "Offensive Instincts", "under30damageIncrease", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Damage increased by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when under 30% health";
		} else {
			return "Damage increased by " + this.currentPoints * this.amountPerPoint * 100 + "% when under 30% health";
		}
	} 
	
}
