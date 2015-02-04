package model;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import FileReaders.TextReader;

public class AbstractVillain extends AbstractHero {

	protected Random rand;
	protected AbstractHero hero;
	protected ArrayList<AbstractCard> genAttackList;
	protected TextReader tr = new TextReader();
	public int healAmount;
	
	public AbstractVillain(AbstractHero hero) {
		super("Villain", hero.getImageSource(), "Fear", null);
		rand = new Random();
		genAttackList = new ArrayList<AbstractCard>();
		this.hero = hero;
		this.enemy = hero;
		statModification();
		loadList();
		loadAttacks();
	}
	
	
	
	private void statModification() {
		
		this.getAttributes().put("strength", this.hero.getAttributes().get("strength"));
		this.getAttributes().put("intel", this.hero.getAttributes().get("intel"));
		this.getAttributes().put("armor", this.hero.getAttributes().get("armor"));
		this.getAttributes().put("speed", this.hero.getAttributes().get("speed"));
		this.getAttributes().put("accuracy", this.hero.getAttributes().get("accuracy"));
		
		//randomly assigns a level to this villain, use for health and magic power stats
		this.getAttributes().put("level", rand.nextInt(4) + this.hero.getAttributes().get("level"));
	}
	
	
	public AbstractCard chooseAttack() {
		AbstractCard card = this.getAttack().get(rand.nextInt(this.getAttack().size()));
//		this.addObserver(card);
		card.hero = this;
		this.addObserver(card);
		return card;
//		choice.mouseClicked(new MouseEvent(null, 1, System.currentTimeMillis(), 0, 0, 0, 0, false));
	}
	
	private void loadList() {
		genAttackList = tr.readAttacks(this);
		genAttackList.add(new HealingTouch(this));
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
				attacks.add(card);
				genAttackList.remove(card);
				values++;
				
			}
		}
	}
	
	
	
	
}
