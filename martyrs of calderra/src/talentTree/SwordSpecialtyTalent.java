package talentTree;

import model.AbstractHero;

public class SwordSpecialtyTalent extends AbstractTalent {

private static final String SRC = "";
	
	
	public SwordSpecialtyTalent(AbstractHero hero) {
		super(hero, 1, .05, "Weapon Specialization: Blades", "specialtySwordAxe", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase damage by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when weilding a sword or an axe";
		} else {
			return "Increase damage by " + this.currentPoints * this.amountPerPoint * 100 + "% when weilding a sword or an axe";
		}
	}
	
}
