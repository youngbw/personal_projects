package model;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import layout.CalderraGUI;
import FileReaders.TextReader;

@SuppressWarnings("serial")
public class AbstractVillain extends AbstractHero {

	protected Random rand;
	protected CalderraGUI controller;
	protected ArrayList<AbstractCard> genAttackList;
	protected TextReader tr = new TextReader();
	public int healAmount;
	
	public AbstractVillain(CalderraGUI controller) {
		super("Villain", controller.getHero().getImageSource(), "Fear", null, controller);
		rand = new Random();
		genAttackList = new ArrayList<AbstractCard>();
		this.controller = controller;
		this.enemy = this.controller.getHero();
		statModification();
		loadList();
		loadAttacks();
	}
	
	
	
	private void statModification() {
		
		this.getAttributes().put("strength", this.controller.getHero().getAttributes().get("strength"));
		this.getAttributes().put("intel", this.controller.getHero().getAttributes().get("intel"));
		this.getAttributes().put("armor", this.controller.getHero().getAttributes().get("armor"));
		this.getAttributes().put("speed", this.controller.getHero().getAttributes().get("speed"));
		this.getAttributes().put("accuracy", this.controller.getHero().getAttributes().get("accuracy"));
		
		//randomly assigns a level to this villain, use for health and magic power stats
		this.getAttributes().put("level", rand.nextInt(4) + this.controller.getHero().getAttributes().get("level"));
	}
	
	
	public AbstractCard chooseAttack() {
		AbstractCard card = this.getAttack().get(rand.nextInt(this.getAttack().size()));
//		this.addObserver(card);
//		card.hero = this;
//		this.addObserver(card); ***
		return card;
//		choice.mouseClicked(new MouseEvent(null, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));
	}
	
	private void loadList() {
		genAttackList = tr.readAttacks(this.controller);
//		genAttackList.add(new HealingTouch(this.controller));
//		for (AbstractCard c: genAttackList) {
//			c.hero = this;
//		}
		System.out.println(genAttackList);
	}
	
	private void loadAttacks() {
		int values = 0;
		ArrayList<AbstractCard> attacks = this.getAttack();
		while (values < 4 && !genAttackList.isEmpty()) {
			AbstractCard card = genAttackList.get(rand.nextInt(genAttackList.size()));
			if (!attacks.contains(card)) {
				card.isInBattle = true;
				card.enabled = false;
				attacks.add(card);
				genAttackList.remove(card);
				values++;
				
			}
		}
	}
	
	public void setAttackToBeUsed() {
		this.attackToBeUsed = this.chooseAttack();
	}
	
	
	
}
