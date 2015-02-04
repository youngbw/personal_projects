package talentTree;

import model.AbstractHero;

public class DefensiveInstinctsTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public DefensiveInstinctsTalent(AbstractHero hero) {
		super(hero, 3, .05, "Defensive Instincts", "under30reduction", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Take " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% less damage when under 30% health";
		} else {
			return "Take " + this.currentPoints * this.amountPerPoint * 100 + "% less damage when under 30% health";
		}
	} 
	
}
