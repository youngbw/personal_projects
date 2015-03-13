package model;

import interfaces.AttackCard;
import interfaces.Axe;
import interfaces.Bow;
import interfaces.Fear;
import interfaces.Hammer;
import interfaces.Hate;
import interfaces.Magical;
import interfaces.Physical;
import interfaces.Poisoner;
import interfaces.Sorrow;
import interfaces.Staff;
import interfaces.Sword;
import interfaces.Wand;
import interfaces.Weapon;
import inventory.InventoryGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;

import talentTree.TalentTreeGUI;
import layout.CalderraGUI;
import layout.MyPanel;
import listeners.MyKeyListener;

public abstract class AbstractHero extends Observable implements Hero, java.io.Serializable {
	
	
	public static final int LEVEL_CAP = 60;
	public static final int MAX_EQUIP = 6;
	public static final int MAX_ATTACK = 4;
	public static final String[] STATS = {"strength", "armor", "speed", "accuracy", "intel", "maxHealth"};
	
	private static final long serialVersionUID = 1L;
	private static final String[] HIT_GIF = {"", "", "", ""};
	private static final String[] MISS_GIF = {"", "", "", ""};
	
	//Map of base stats
	private HashMap<String, Integer> attributeMap = new HashMap<String, Integer>();
	
	//Map of additional stats
	private HashMap<String, Integer> additionalStats = new HashMap<String, Integer>();
	
	//special case talents that involve multiple stats
	private HashMap<String, Double> specialCases = new HashMap<String, Double>();
	
	//talent map
	private HashMap<String, Double> powerupMap = new HashMap<String, Double>();
	
	private ArrayList<AbstractCard> equipped;
	private ArrayList<AbstractCard> attack;
		
	private InventoryGUI inventory;
	private TalentTreeGUI talentTree;
	public AbstractHero enemy;
	
	private String name;
	private String title;
	private String heroClass;
	private String imageSource;
	
//	private int level;
//	private int experience;
//	private int experienceNeeded;
//	private int currentHealth;
//	private int maxHealth;
	
	//for fight
	protected ArrayList<String> enemyMissGif;
	protected ArrayList<String> enemyHitGif;
	public double tempHealthPercent;
	public double tempArmorPercent;
	public double tempStrengthPercent;
	public double tempAccuracyPercent;
	public double tempSpeedPercent;
	public double tempIntelPercent;
	public AbstractCard attackToBeUsed;
	
	//for fight
	public int strengthInc;
	public int armorInc;
	public int accuracyInc;
	public int speedInc;
	public int intelInc;
	public int healthInc;
	
	public boolean healedLastTurn;
	public int periodicHealAmount;
	public int healTime;
	public int healAmount;
	private boolean isPoisoned;
	private int periodicDamageTime;
	public boolean oppMiss;
	
	//End fight stats
	
//	private int strength;
//	private int accuracy;
//	private int armor;
//	private int intel;
//	private int speed;
//	private int currentMagicPower;
//	private int maxMagicPower;
//	private int bagCapacity;
//	private int numInBag;
//	
//	private int gold;
	private int points;
	private boolean isInBattle;
		
	public AbstractHero(CalderraGUI controller) {
		this("Silith", "", "", null, controller);
	}
	
	public AbstractHero(String name, String src, String className, String[] initialStats, CalderraGUI controller) { 
		initializePowerUps();
		this.name = name;
		title = "the Meek";
		heroClass = className;
		imageSource = src;
		isInBattle = false;
		enemy = null;
		oppMiss = false;
		inventory = new InventoryGUI(controller);
		talentTree = new TalentTreeGUI(this);
		
		enemyMissGif = new ArrayList<String>();
		enemyHitGif =  new ArrayList<String>();
		tempHealthPercent = 0.;
		tempArmorPercent = 0.;
		tempStrengthPercent = 0.;
		tempAccuracyPercent = 0.;
		tempSpeedPercent = 0.;
		tempIntelPercent = 0.;
		
		
		strengthInc = 0;
		armorInc = 0;
		accuracyInc = 0;
		speedInc = 0;
		intelInc = 0;
		healthInc = 0;
		
//		level = 1;
//		experience = 0;
//		experienceNeeded = 100;
//		currentHealth = 100;
//		maxHealth = 100;
//		strength = 20;
//		accuracy = 20;
//		armor = 20;
//		intel = 20;
//		speed = 20;
//		currentMagicPower = 100;
//		maxMagicPower = 100;
//		gold = 1000;
//		points = 0;
//		bagCapacity = 6; //may not need
//		numInBag = 0;
		
		healTime = 0;
		healAmount = 0;
		isPoisoned = false;
		healedLastTurn = false;
		periodicDamageTime = 0;
		periodicHealAmount = 0;
		
		attributeMap.put("level", 1);
		attributeMap.put("maxHealth", 100);
		attributeMap.put("currentHealth", 100);
		attributeMap.put("strength", 20);
		attributeMap.put("accuracy", 20);
		attributeMap.put("armor", 20);
		attributeMap.put("intel", 20);
		attributeMap.put("speed", 20);
		attributeMap.put("gold", 1000);
		attributeMap.put("experience", 0);
		attributeMap.put("Experience To Next Level", 100);
		attributeMap.put("points", 0);
		attributeMap.put("maxMagicPower", 100);
		attributeMap.put("currentMagicPower", 100);
		attributeMap.put("capacity", 6);
		attributeMap.put("numInBag", 0);
		attributeMap.put("bossesDefeated", 0);
		
		additionalStats.put("strength", 0);
		additionalStats.put("speed", 0);
		additionalStats.put("accuracy", 0);
		additionalStats.put("armor", 0);
		additionalStats.put("intel", 0);
		additionalStats.put("maxHealth", 0);
		additionalStats.put("maxMagicPower", 0);
		
		initializeHeroAdditionalStats(initialStats);
		
		equipped = new ArrayList<AbstractCard>();
		attack = new ArrayList<AbstractCard>();
		
		specialCases.put("Elusiveness", 0.); // May not need this due to adding it in the talent specific class
		specialCases.put("Endurance", 0.); //same
		specialCases.put("Bartering", 0.);
		specialCases.put("Ultimate: Alchemist", 0.);
//		talentTree.addTalentPoints(5);
//		inventory.addCard(new HealthPotion(this));
//		inventory.addCard(new ThunderingSmash(this));
	}
	
	public void setEnemy(AbstractHero enemy) {
		this.enemy = enemy;
	}
	
	public void setPoison(boolean poison) {
		this.isPoisoned = poison;
	}
	
	public void incrementPeriodicDamageTime(int time) {
		this.periodicDamageTime += time;
	}
	
	
	public void incrementPeriodicDamagetoValue(int value) {
		if (this.periodicDamageTime < value) this.periodicDamageTime = value;
	}
	
	public void changed() {
		setChanged();
		notifyObservers(this);
	}
	
	public void changed(AbstractCard card) {
		setChanged();
		notifyObservers(card);
	}
	
	public void changed(String str) {
		setChanged();
		notifyObservers(str);
	}
	
	public String getFullName() {
		return name + " " + title;
	}
	
	public String getHeroClass() {
		return heroClass;
	}
	
	public String getImageSource() {
		return imageSource;
	}
	
	public boolean getIsInBattle() {
		return this.isInBattle;
	}
	
	public int getBossesDefeated() {
		return attributeMap.get("bossesDefeated");
	}
	
	public int getStrength() {
		return attributeMap.get("strength") + additionalStats.get("strength");
	}
	
	public int getmaxHealth() {
		return attributeMap.get("maxHealth") + additionalStats.get("maxHealth");
	}
	
	public int getcurrentHealth() {
		return attributeMap.get("currentHealth");
	}
	
	public int getAccuracy() {
		return attributeMap.get("accuracy") + additionalStats.get("accuracy");
	}
	
	public int getArmor() {
		return attributeMap.get("armor") + additionalStats.get("armor");
	}
	
	public int getIntel() {
		return attributeMap.get("intel") + additionalStats.get("intel");
	}
	
	public int getSpeed() {
		return attributeMap.get("speed") + additionalStats.get("speed");
	}
	
	public int getMaxMagicPower() {
		return attributeMap.get("maxMagicPower") + additionalStats.get("maxMagicPower");
	}
	
	public int getCurrentMagicPower() {
		return attributeMap.get("currentMagicPower");
	}
	
	public int getGold() {
		return attributeMap.get("gold");
	}
	
	public int getLevel() {
		return attributeMap.get("level");
	}
	
	public void addPoints(int morePoints) {
		points = morePoints;
		attributeMap.put("points", attributeMap.get("points") + morePoints);
		setChanged();
		notifyObservers(new Integer(points));
	}
	
	public HashMap<String, Integer> getAttributes() {
		return attributeMap;
	}
	
	public HashMap<String, Double> getPowerUps() {
		return powerupMap;
	}
	
	public void deleteCard(AbstractCard card) {
		if (!this.isInBattle) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to drop \n" + card.getName(), "Delete", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				if (card.isEquipped) {
					equipped.remove(card);
				} else if (card.isInBag) {
					inventory.deleteCard(card);
				}
				this.changed();
			}
		}
	}
	
	public void equipCard(AbstractCard card) {
		//insert enemy attack graphic here
		if (!this.isInBattle || (this.isInBattle && card.isDrop)) {
			if (card instanceof Weapon && this.getEquipped().size() < MAX_EQUIP && !equipped.contains(card)) {
				equipped.add(inventory.removeCard(card));
				
			} else if (card instanceof AttackCard && this.getAttack().size() < MAX_ATTACK && !attack.contains(card)) {
				attack.add(inventory.removeCard(card));
			}
//			card.isInBag = false;
//			card.isEquipped = true;
			card.setEquipped();
			card.isDrop = false;
			setChanged();
			notifyObservers("equip");
		}
		
	}
	
	public AbstractCard unequipCard(AbstractCard card) {
		if (!this.isInBattle) {
			if (card instanceof Weapon) {
				for (int i = 0; i < equipped.size(); i++) {
					if (equipped.get(i).equals(card)) {
						if (inventory.getBagSize() < InventoryGUI.MAX_BAG_SIZE) {
//							card.isInBag = true;
//							card.isEquipped = false;
							card.setUnEquipped();
							attributeMap.put("numInBag", attributeMap.get("numInBag") + 1);
							inventory.addCard(equipped.get(i));
							equipped.remove(i);
							setChanged();
							notifyObservers("equip");
							return card;
						}

					}
				}
			} else if (card instanceof AttackCard) {
				for (int i = 0; i < getAttack().size(); i++) {
					if (attack.get(i).equals(card)) {
						if (inventory.getBagSize() < InventoryGUI.MAX_BAG_SIZE) {
							card.setUnEquipped();
							attributeMap.put("numInBag", attributeMap.get("numInBag") - 1);
							inventory.addCard(card);
							attack.remove(i);
							setChanged();
							notifyObservers("equip");
							return card;
						}
					}
				}
			}

		}
		
		return null;
	}

	public void buyCard(AbstractCard card) {
		int cost = card.getCost();
		if (specialCases.get("Bartering") > 0) {
			cost -= cost * specialCases.get("Bartering");
		}
		if (attributeMap.get("gold") >= cost && !isInBattle) {
			boolean success = inventory.addCard(card); //add card to inventory
			if (success) {
				card.inShop = false;
				card.isInBag = true;
				card.isEquipped = false;
				card.addMouseListener(card);
				attributeMap.put("gold", attributeMap.get("gold") - cost);
				System.out.println("Buying " + card.getClass().getName() + " for " + cost + " gold.");
				System.out.println("Gold remaining: " + attributeMap.get("gold"));
				setChanged();
				notifyObservers("stats");
			}
			
		}
		
	}
	
	public void addCard(AbstractCard card) {
		if (!this.bagFull()) {
			inventory.addCard(card);
		} else {
			JOptionPane.showMessageDialog(null, "You do not have space in your inventory", "Insufficient Space", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public boolean bagFull() {
		return inventory.isFull();
	}
	
	public ArrayList<AbstractCard> getEquipped() {
		return equipped;
	}
	
	public ArrayList<AbstractCard> getAttack() {
		return attack;
	}
	
	public int getBagCapacity() {
		return InventoryGUI.MAX_BAG_SIZE;
	}
	
	public int getNumInBag() {
		return attributeMap.get("numInBag");
	}
	
	public ArrayList<String> getEnemyMissGif() {
		enemyMissGif = new ArrayList<String>();
		for (String s: MISS_GIF) {
			enemyMissGif.add(s);
		}
		return this.enemyMissGif;
	}
	
	public ArrayList<String> getEnemyHitGif() {
		enemyHitGif = new ArrayList<String>();
		for (String s: HIT_GIF) {
			enemyHitGif.add(s);
		}
		return this.enemyHitGif;
	}
	
	public void showInventory() {
		if (inventory.isVisible()) {
			inventory.setVisible(false);
		} else {
			inventory.setVisible(true);
		}
	}
	
	public void showTalentTree() {
		if (talentTree.isVisible() || isInBattle) {
			talentTree.setVisible(false);
		} else {
			talentTree.setVisible(true);
		}
	}
	
	public void resetTalentTree() {
		this.talentTree.dispose();
	}
	
	public void resetInventory() {
		this.inventory.dispose();
		
	}
	
	private void initializeHeroAdditionalStats(String[] incAtts) {
		if (incAtts != null) {
			for (int i = 0; i < incAtts.length; i++) {
				this.getAttributes().put(incAtts[i], this.getAttributes().get(incAtts[i]) + 10);
			}
		}
	}
	
	//When the hero is entering battle, change state
	//Call exiting battle for stat changes and whatnot, reset values
	public void enterBattle() {
		this.isInBattle = true;
		for (AbstractCard card: attack) {
			card.isInBattle = true;
		}
		for (AbstractCard card: equipped) {
			card.isInBattle = true;
		}
		showTalentTree();
		inventory.enterBattle();
	}
	
	public void setOutOfBattle() {
		this.isInBattle = false;
		for (AbstractCard card: attack) {
			card.isInBattle = false;
		}
		for (AbstractCard card: equipped) {
			card.isInBattle = false;
		}
		inventory.exitBattle();
	}
	
	public void exitBattle(int experienceGained, int goldGained, ArrayList<AbstractCard> cardsFound) {
//		Random rand = new Random();
		setOutOfBattle();
		
		//IF YOU WON
		if (this.enemy != null && this.enemy.getcurrentHealth() < 1) {
			//Add gold only if you won the battle
			attributeMap.put("gold", this.getGold() + goldGained);
			
			//INCREMENT BOSS KILLED IF BOSS
			if (this.enemy instanceof Boss) {
				attributeMap.put("bossesDefeated", this.attributeMap.get("bossesDefeated") + 1);
			}
			
			//HEALTH AND MAGIC VICTORY SPOILS
			attributeMap.put("currentHealth", getcurrentHealth() + (getcurrentHealth() / 3) <= getmaxHealth() ? getcurrentHealth() + getcurrentHealth() / 3 : getmaxHealth());
			attributeMap.put("currentMagicPower", getCurrentMagicPower() + (getCurrentMagicPower() / 3) <= getMaxMagicPower() ? getCurrentMagicPower() + getCurrentMagicPower() / 3 : getMaxMagicPower());
			
			//ADD EXPERIENCE
//			int experienceGained = rand.nextInt(this.enemy.getLevel() * 5) + attributeMap.get("level") * 5;
//			System.out.println("Experienced gained: " + experienceGained);
			
			//IF LEVEL GAINED
			if (attributeMap.get("experience") + experienceGained >= attributeMap.get("Experience To Next Level") && getLevel() < LEVEL_CAP) {
				
				attributeMap.put("level", attributeMap.get("level") + 1);
				int experience = attributeMap.get("experience") + experienceGained - attributeMap.get("Experience To Next Level");
				int toNext = attributeMap.get("level") * 100;
				attributeMap.put("experience", experience);
				attributeMap.put("Experience To Next Level", toNext);
				
				//ATTRIBUTE POINTS / TALENT POINTS
				if (attributeMap.get("level") % 3 == 0) {
					addPoints(2);
				}
				
				if (attributeMap.get("level") % 2 == 0) {
					talentTree.addTalentPoints(2);
				}
				
				if (getLevel() % 10 == 0) {
					this.setChanged();
					this.notifyObservers("Enable Boss");
				}
				
			} else { //IF LEVEL NOT GAINED
				attributeMap.put("experience", attributeMap.get("experience") + experienceGained);
				//<= attributeMap.get("Experience To Next Level") ?
				//		attributeMap.get("experience") + experienceGained : attributeMap.get("Experience To Next Level"));
				
			}
			
			
			
		} else if (getcurrentHealth() < 1) { //IF YOU DIED
			
			int result = JOptionPane.showConfirmDialog(null, "Would you like to continue? (" + 50 * attributeMap.get("level") + " to resurrect)", "Death", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				attributeMap.put("gold", attributeMap.get("gold") - 50 * this.getLevel() > 0 ? attributeMap.get("gold") - 50 * this.getLevel() : 0);
				attributeMap.put("currentHealth", getmaxHealth() / 2);
				attributeMap.put("currentMagicPower", getMaxMagicPower() / 2);
			} else {
				JOptionPane.showMessageDialog(null, "GAME OVER");
				setChanged();
				notifyObservers("GAME OVER");
			}
		}
		
//		this.enemy = null;
//		setChanged();
//		notifyObservers("stats");
//		changed("Battle Show");
	}
	
	private void initializePowerUps() {
		//General
		this.powerupMap.put("strength", 0.); //special handling necessary in fight
		this.powerupMap.put("armor", 0.);
		this.powerupMap.put("accuracy", 0.);
		this.powerupMap.put("speed", 0.);
		this.powerupMap.put("intel", 0.);
		
		//strength specific
		this.powerupMap.put("periodicDamage", 0.);
		this.powerupMap.put("periodicCount", 0.); //may not need
		this.powerupMap.put("maxHealth", 0.);
		this.powerupMap.put("damageUnder20", 0.); 
		this.powerupMap.put("damageFear", 0.);
		this.powerupMap.put("damageHate", 0.);
		this.powerupMap.put("damageSorrow", 0.);
		this.powerupMap.put("undyingTremors", 0.); 
		this.powerupMap.put("lethality", 0.);
		this.powerupMap.put("specialtySwordAxe", 0.); 
		this.powerupMap.put("specialtyHammerBow", 0.);
		this.powerupMap.put("specialtyStaffWand", 0.);
		this.powerupMap.put("elusiveness", 0.); // may not need in this map 
		this.powerupMap.put("endurance", 0.);  // may not need in this map
		
		//Healing Specific
		this.powerupMap.put("healing", 0.); //heal
		this.powerupMap.put("healingOnIntel", 0.); //heal
		this.powerupMap.put("damageReduction", 0.); //defense
		this.powerupMap.put("turnDependentDamage", 0.);  //LEFT OFF HERE
		this.powerupMap.put("fearReduction", 0.); //defense
		this.powerupMap.put("hateReduction", 0.); //defense
		this.powerupMap.put("sorrowReduction", 0.); //defense
		this.powerupMap.put("periodicHealing", 0.); // healing
		this.powerupMap.put("holyStrength", 0.); // damage
		this.powerupMap.put("under30reduction", 0.); //defense
		this.powerupMap.put("under30damageIncrease", 0.); // damage
		
		//Utility specific
		this.powerupMap.put("duality", 0.);
		this.powerupMap.put("2Hand", 0.);
		this.powerupMap.put("tact", 0.);
		this.powerupMap.put("deftness", 0.);
		this.powerupMap.put("bartering", 0.);
		this.powerupMap.put("superiority", 0.);
		this.powerupMap.put("deadlyAim", 0.);
		this.powerupMap.put("masterTradesman", 0.);
		this.powerupMap.put("deadEye", 0.);
		this.powerupMap.put("deadEyeCounter", 0.);
		this.powerupMap.put("alchemist", 0.);
	}
	
	public void setAdditionalStatPercent(String stat, double percent, int inc) {
		switch (stat) {
		
		case "strength":
			tempStrengthPercent += percent * inc;
			break;
			
		case "armor":
			tempArmorPercent += percent * inc;
			break;
			
		case "intel":
			tempIntelPercent += percent * inc;
			break;
			
		case "speed":
			tempSpeedPercent += percent * inc;
			break;
			
		case "accuracy":
			tempAccuracyPercent += percent * inc;
			break;
			
		case "maxHealth":
			tempHealthPercent += percent * inc;
			break;
		
		}
		setChanged();
		notifyObservers("stats");
	}
	
	public void addSpecialCase(String theCase, double amount, int inc) {		
		if (inc == 1) this.specialCases.put(theCase, this.specialCases.get(theCase) + amount);
		else this.specialCases.put(theCase, this.specialCases.get(theCase) - amount);
	}
	
	public void setAdditionalStatInteger(String stat, int amount, int inc) {
		switch (stat) {
		case "strength":
			strengthInc += amount * inc;
			break;
			
		case "armor":
			armorInc += amount * inc;
			break;
			
		case "speed":
			speedInc += amount * inc;
			break;
			
		case "accuracy":
			accuracyInc += amount * inc;
			break;
			
		case "intel":
			intelInc += amount * inc;
			break;
			
		case "maxHealth":
			healthInc += amount * inc;
			break;
		}
		setChanged();
		notifyObservers("stats");
	}
	
	private void calculateSpecialCases() {
		for (String s: specialCases.keySet()) {
			switch (s) {
			case "Elusiveness":
				this.additionalStats.put("armor", (int) (this.additionalStats.get("armor") + this.getAttributes().get("speed") * specialCases.get(s)));
				break;
				
			case "Endurance":
				this.additionalStats.put("maxHealth", (int)(this.additionalStats.get("maxHealth") + this.getAttributes().get("strength") * specialCases.get(s)));
				break;
				
			}
		}
		
	}
	
	private void calculateAdditionalStatPercent() {
		this.additionalStats.put("strength", this.additionalStats.get("strength") + (int)(this.getAttributes().get("strength") * tempStrengthPercent));
		this.additionalStats.put("accuracy", this.additionalStats.get("accuracy") +  (int)(this.getAttributes().get("accuracy") * tempAccuracyPercent));
		this.additionalStats.put("speed", this.additionalStats.get("speed") +  (int)(this.getAttributes().get("speed") * tempSpeedPercent));
		this.additionalStats.put("intel", this.additionalStats.get("intel") +  (int)(this.getAttributes().get("intel") * tempIntelPercent));
		this.additionalStats.put("armor", this.additionalStats.get("armor") +  (int)(this.getAttributes().get("armor") * tempArmorPercent));
		this.additionalStats.put("maxHealth", this.additionalStats.get("maxHealth") +  (int)(this.getAttributes().get("maxHealth") * tempHealthPercent));
	}
	
	private void resetAdditionals() {
		this.additionalStats.put("strength", 0);
		this.additionalStats.put("armor", 0);
		this.additionalStats.put("speed", 0);
		this.additionalStats.put("accuracy", 0);
		this.additionalStats.put("intel", 0);
		this.additionalStats.put("maxHealth", 0);
	}
	
	private void calculateIntegerAdditions() {
		for (String s: additionalStats.keySet()) {
			switch (s) {
			case "strength":
				this.additionalStats.put(s, this.additionalStats.get(s) + strengthInc);
				break;
				
			case "armor":
				this.additionalStats.put(s, this.additionalStats.get(s) + armorInc);
				break;
				
			case "speed":
				this.additionalStats.put(s, this.additionalStats.get(s) + speedInc);
				break;
				
			case "accuracy":
				this.additionalStats.put(s, this.additionalStats.get(s) + accuracyInc);
				break;
				
			case "intel":
				this.additionalStats.put(s, this.additionalStats.get(s) + intelInc);
				break;
				
			case "maxHealth":
				this.additionalStats.put(s, this.additionalStats.get(s) + healthInc);
				break;
			}
		}
	}
	
	public void calculateAdditionalStats() {
		resetAdditionals();
		calculateIntegerAdditions();
		calculateAdditionalStatPercent();
		calculateSpecialCases();
	}
	
	public HashMap<String, Integer> getAdditionalStats() {
		return additionalStats;
	}
	
	//Hero will have to be actually modified after the return of this method.
	public int consume(AbstractCard card, String consumeType, int amount) {
		int use = amount;
		if (card instanceof Consumable) {
			if (consumeType.equals("potion")) {
				if (this.specialCases.get("Ultimate: Alchemist") > 0) {
					use += use * specialCases.get("Ultimate: Alchemist");
					((Consumable) card).decrementQuantity();
				}
			}
			if (((Consumable)card).getQuantity() == 0) {
				inventory.removeCard((Consumable)card);
			}
		}
		return use;
	}
	
	public int heal(AbstractCard card, String healType) {
		if (card.getHealType().equals(Heal.HEAL_TYPE_PERIODIC)) {
			if (healTime < card.getPeriodicIncrease()) {
				healTime = card.getPeriodicIncrease();
			}
		}
//		MyPanel.missMarker.offer(new Boolean(true));
		int heal = this.getHit(card);
//		MyPanel.damage.offer(heal);
//		this.attributeMap.put("currentHealth", getcurrentHealth() + heal <= getmaxHealth() ? getcurrentHealth() + heal : getmaxHealth());
		
		//line above is side effect, take care of in battle GUI third party class
		return heal;
	}
	
	public int getHit(AbstractCard card) {
		if ((this.enemy != null || card instanceof Heal)) {
			Random rand = new Random();
			int heroStr = this.getStrength();
			int heroIntel = this.getIntel();

			if ((card instanceof Physical || card instanceof Magical) && getCurrentMagicPower() >= ((AttackCard) card).getCruxCost()) {
				this.healedLastTurn = false;
				this.attributeMap.put("currentMagicPower", (getCurrentMagicPower() - ((AttackCard)card).getCruxCost()));
				double damagePercentIncrease = 0.;
				int damage = 0;

				for (String s: powerupMap.keySet()) {
					switch (s) {

					case "periodicCount":
						if (powerupMap.get(s) > 1) {
							powerupMap.put(s, powerupMap.get(s) - 1);
							damagePercentIncrease += powerupMap.get("periodcDamage");
						}
						break;

					case "damageUnder20":
						if (powerupMap.get(s) > 0 && this.enemy.getcurrentHealth() < this.enemy.getmaxHealth() * .2) {
							damagePercentIncrease += .1;
						}
						break;

					case "damageFear":
						if (powerupMap.get(s) > 0 && this.enemy instanceof Fear) {
							damagePercentIncrease += powerupMap.get(s);
						}
						break;

					case "damageHate":
						if (powerupMap.get(s) > 0 && this.enemy instanceof Hate) {
							damagePercentIncrease += powerupMap.get(s);
						}
						break;

					case "damageSorrow":
						if (powerupMap.get(s) > 0 && this.enemy instanceof Sorrow) {
							damagePercentIncrease += powerupMap.get(s);
						}
						break;

					case "undyingTremors":
						if (oppMiss) {
							damagePercentIncrease += powerupMap.get(s);
							oppMiss = false;
						}
						break;

					case "lethality":
						if (powerupMap.get(s) > 0 && card instanceof Poisoner) {
							damagePercentIncrease += powerupMap.get(s);
						}
						break;

					case "specialtySwordAxe":
						for (AbstractCard c: equipped) {
							if (c instanceof Sword || c instanceof Axe) {
								damagePercentIncrease += powerupMap.get(s);
								break;
							}
						}
						break;


					case "specialtyHammerBow":
						for (AbstractCard c: equipped) {
							if (c instanceof Hammer || c instanceof Bow) {
								damagePercentIncrease += powerupMap.get(s);
								break;
							}
						}
						break;

					case "specialtyStaffWand":
						for (AbstractCard c: equipped) {
							if (c instanceof Staff || c instanceof Wand) {
								damagePercentIncrease += powerupMap.get(s);
								break;
							}
						}
						break;
						
					case "turnDependentDamage":
						if (this.powerupMap.get(s) > 0) {
							damage+= (damage * this.powerupMap.get(s));
						}
						
						break;
						
					}
				}

				//Can make this more class specific
				if (card instanceof Physical) {
					damage += (Math.max(0, (rand.nextInt(10 + getLevel()) + (heroStr / 2))) * (1 + damagePercentIncrease));
				} else if (card instanceof Magical) {
					damage += (Math.max(0, (rand.nextInt(10 + getLevel()) + (heroIntel / 2))) * (1 + damagePercentIncrease));
				}
//				System.out.println(damage);
				//Calculate crit
				damage *= crit();

//				//hit the enemy, calculate enemy resistances and defenses
//				this.enemy.takeHit(damage);



				return damage;

				//In the case that the card being used is a heal card
			} else if (card instanceof Heal && (getCurrentMagicPower() >= ((AttackCard) card).getCruxCost())) {
				this.healedLastTurn = true;
				int heal = this.getmaxHealth() / ((Heal)card).getHealAmount();
				healAmount = heal;
				double additionalHealAmount = 0;
				double healPercentIncrease = 0.;
				
				for (String s: powerupMap.keySet()) {
					switch(s) {

					case "healing":
						healPercentIncrease += powerupMap.get(s);
						break;

					case "healingOnIntel":
						additionalHealAmount += (this.attributeMap.get("intel") * powerupMap.get(s));
						break;
						
					

					}
				}
				healAmount = 0;
				System.out.println("heal amount before increase percent: " + heal);
				heal *= (1 + healPercentIncrease);
				heal += additionalHealAmount;
				System.out.println("Returning heal amount: " + heal);
				this.attributeMap.put("currentMagicPower", (getCurrentMagicPower() - ((AttackCard)card).getCruxCost()));
				return heal;
			}
		}


		return 0;
	}
	
	public int takeHit(int incomingDamage) {
		Random rand = new Random();
		int heroArmor = getArmor() + additionalStats.get("armor");
		
		double percentDecrease = heroArmor / 100.;
		int damage = (int)(incomingDamage * (1 - percentDecrease / 2));
		damage -= (damage * this.powerupMap.get("damageReduction"));
		int dodge = rand.nextInt(getSpeed());
		int oppAcc = rand.nextInt(enemy.getAccuracy());
		if (dodge - oppAcc > 0) {
			oppMiss = true;
			MyPanel.missMarker.offer(new Boolean(false));
			System.out.println("miss");
			return 0;
		} else {
//			this.attributeMap.put("currentHealth", Math.max(0, (this.attributeMap.get("currentHealth") - damage)));
			System.out.println(damage + " " + this.getClass().getSimpleName() + " current Health: " + getcurrentHealth());
//			this.changed("hit");
			MyPanel.missMarker.offer(new Boolean(true));
			setChanged();
			notifyObservers("stats");
		}
		return damage;
	}
	
	private double crit() {
		Random rand = new Random();
		int heroAccur = getAccuracy() + additionalStats.get("accuracy");
		if (rand.nextInt(30) < heroAccur / 5) {
//			System.out.println("CRIT");
			return 1.20;
		} else {
			return 1;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getFullName() + "\n");
		sb.append("Hero Class: " + this.getHeroClass());
		sb.append("\n\n");
		
		for (int i = 0; i < STATS.length - 1; i++) {
			int stat = 0;
			if (attributeMap.get(STATS[i]) != null) {
				stat += attributeMap.get(STATS[i]);
			}
			if (additionalStats.get(STATS[i]) != null) {
				stat += additionalStats.get(STATS[i]);
			}
			sb.append(STATS[i] + ": \t" + stat + "\n");
		}
		
		sb.append("\nHealth: \t" + getcurrentHealth() + "/" + getmaxHealth());
		sb.append("\nMagic Power: \t" + getCurrentMagicPower() + "/" + getMaxMagicPower());
		
		return sb.toString();
	}

}
