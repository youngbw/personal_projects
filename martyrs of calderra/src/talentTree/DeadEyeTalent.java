package talentTree;

import model.AbstractHero;

public class DeadEyeTalent extends AbstractTalent {

	
private static final String SRC = "";
	
	public DeadEyeTalent(AbstractHero hero) {
		super(hero, 1, 1, "Ultimate: Dead Eye", "deadEye", SRC);
	}
	
	public String toString() {
		return "Every 5th attack is automatically a critical strike";
	}
	
}
