package model;

import java.util.ArrayList;

import layout.CalderraGUI;
import interfaces.Physical;

@SuppressWarnings("serial")
public class QuickStrike extends AbstractCard implements Physical {

	private static final String[] ATTACK_GIF = {"src/resources/Mage.jpeg", "./src/resources/Mage.jpg", "/src/resources/Mage.jpg", "src/resources/Mage.jpg"};
	private static final int CRUX_COST = 5;
	
	public QuickStrike(CalderraGUI controller) {
		super("src/resources/Rage.jpg", "Quick Strike", controller, ATTACK_GIF);
		this.cost = 100;
		
	}
	
	
	@Override
	public String[] getGifSrc() {
		return ATTACK_GIF;
	}
	
	@Override
	public int getCruxCost() {
		return CRUX_COST;
	}
	

	@Override
	public void getUpgrade() {
		this.controller.getHero().setAdditionalStatPercent("speed", .5, INCREMENT);
		
	}

	@Override
	public void getDowngrade() {
		this.controller.getHero().setAdditionalStatPercent("speed", .5, DECREMENT);
		
	}
	
	public String toString() {
		return "A swift attack that amplifies the users speed to unbeforeseen levels";
	}


	@Override
	public ArrayList<String> getAttackGif() {
//		attackGif = new ArrayList<String>();
//		for (String s: ATTACK_GIF) {
//			attackGif.add(s);
//		}
		System.out.println("Attack Gif List " + attackGif.toString());
		return super.getattackGif();
	}

	
}
