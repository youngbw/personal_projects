package talentTree;

import model.AbstractHero;

public class FortitudeTalent extends AbstractTalent {
	
	private static final String SRC = "src/resources/Fortitude.jpg";
	
	
	public FortitudeTalent(AbstractHero hero) {
		super(hero, 3, .01, "Fortitude", "armor", SRC);
		this.enabled = true;
	}
		
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase armor by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "%";
		} else {
			return "Increase armor by " + this.currentPoints * this.amountPerPoint * 100 + "%";
		}
		
	}
	
}
