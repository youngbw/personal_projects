package talentTree;

import model.AbstractHero;

public class HammerSpecialtyTalent extends AbstractTalent {

	
private static final String SRC = "";
	
	public HammerSpecialtyTalent(AbstractHero hero) {
		super(hero, 1, .05, "Weapon Specialization: Hammers/Ranged", "specialtyHammerBow", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase damage by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when weilding a Hammer or Bow";
		} else {
			return "Increase damage by " + this.currentPoints * this.amountPerPoint * 100 + "% when weilding a Hammer or Bow";
		}
	}
}
