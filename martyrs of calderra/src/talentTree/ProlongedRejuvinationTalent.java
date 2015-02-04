package talentTree;

import model.AbstractHero;

public class ProlongedRejuvinationTalent extends AbstractTalent {

	private static final String SRC = "";
	
	public ProlongedRejuvinationTalent(AbstractHero hero) {
		super(hero, 1, .05, "Ultimate: Prolonged Rejuvination", "periodicHealing", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Heal " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% of max health for next three attacks after a heal";
		} else {
			return "Heal " + this.currentPoints * this.amountPerPoint * 100 + "% of max health for next three attacks after a heal";
		}
	} 
	
	
}
