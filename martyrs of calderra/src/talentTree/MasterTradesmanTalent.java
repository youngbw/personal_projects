package talentTree;

import model.AbstractHero;

public class MasterTradesmanTalent extends AbstractTalent {

private static final String SRC = "";
	
	public MasterTradesmanTalent(AbstractHero hero) {
		super(hero, 1, .5, "Ultimate: Master Tradesman", "masterTradesman", SRC);
	}
	
	public String toString() {
		if (this.currentPoints < maxPoints) {
			return "Potions come at " + this.currentPoints + 1 * this.amountPerPoint * 100 + "% reduced cost";
		} else {
			return "Potions come at " + this.currentPoints * this.amountPerPoint * 100 + "% reduced cost";
		}
	}
	
}
