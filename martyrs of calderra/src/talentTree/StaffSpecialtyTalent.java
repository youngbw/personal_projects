package talentTree;

import model.AbstractHero;

public class StaffSpecialtyTalent extends AbstractTalent {

	
	private static final String SRC = "";
	
	public StaffSpecialtyTalent(AbstractHero hero) {
		super(hero, 1, .05, "Weapon Specialization: Staves", "specialtyStaffWand", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Increase damage by " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% when weilding a staff or wand";
		} else {
			return "Increase damage by " + this.currentPoints * this.amountPerPoint * 100 + "% when weilding a staff or wand";
		}
	}
}
