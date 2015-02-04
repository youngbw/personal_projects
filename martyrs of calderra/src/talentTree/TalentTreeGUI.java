package talentTree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;

import model.AbstractCard;
import model.AbstractHero;

@SuppressWarnings("serial")
public class TalentTreeGUI extends JDialog {

	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	private Tree strengthTree;
	private Tree healingTree;
	private Tree utilityTree;
		
	public TalentTreeGUI(AbstractHero hero) {
		strengthTree = new Tree("Strength", hero, 5);
		healingTree = new Tree("Healing", hero, 4);
		utilityTree = new Tree("Utility", hero, 4);
		setup(hero);
		this.setLocationRelativeTo(null);
	}
	
	
	private void setup(AbstractHero hero) {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));
		this.setLayout(new GridLayout(1, 3));
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		
		setupStrengthTree(hero);
		setupHealingTree(hero);
		setupUtilityTree(hero);
		
		this.add(strengthTree);
		this.add(healingTree);
		this.add(utilityTree);
	}
	
	private void setupStrengthTree(AbstractHero hero) {
		//Root talents of tree
		FortitudeTalent fortitude = new FortitudeTalent(hero);
		RageTalent rage = new RageTalent(hero);
		EverlastingWillTalent will = new EverlastingWillTalent(hero);
		BloodScentTalent scent = new BloodScentTalent(hero);
		
		//initialize other talents
		//style based - level 2
		FearLustTalent fear = new FearLustTalent(hero);
		HateLustTalent hate = new HateLustTalent(hero);
		SorrowLustTalent sorrow = new SorrowLustTalent(hero);
		
		//level 3
		PoisonTippedBladeTalent poison = new PoisonTippedBladeTalent(hero);
		ElusivenessTalent elusiveness = new ElusivenessTalent(hero);
		EnduranceTalent endurance = new EnduranceTalent(hero);
		
		//specialties - level 4
		SwordSpecialtyTalent sword = new SwordSpecialtyTalent(hero);
		StaffSpecialtyTalent staff = new StaffSpecialtyTalent(hero);
		HammerSpecialtyTalent hammer = new HammerSpecialtyTalent(hero);
		
		//ultimate - level 5
		UndyingTremorsTalent tremors = new UndyingTremorsTalent(hero);
		LethalityTalent lethal = new LethalityTalent(hero);
		SuppressiveSpeedTalent speed = new SuppressiveSpeedTalent(hero);
		
		//setup dependencies 1-->2
		fortitude.addTalentChild(fear);
		fortitude.addTalentChild(hate);
		fortitude.addTalentChild(sorrow);
		
		rage.addTalentChild(fear);
		rage.addTalentChild(hate);
		rage.addTalentChild(sorrow);
		
		will.addTalentChild(fear);
		will.addTalentChild(hate);
		will.addTalentChild(sorrow);
		
		//2-->3
		fear.addTalentChild(poison);
		fear.addTalentChild(elusiveness);
		fear.addTalentChild(endurance);
		
		hate.addTalentChild(poison);
		hate.addTalentChild(elusiveness);
		hate.addTalentChild(endurance);
		
		sorrow.addTalentChild(poison);
		sorrow.addTalentChild(elusiveness);
		sorrow.addTalentChild(endurance);
		
		//3-->4
		poison.addTalentChild(sword);
		poison.addTalentChild(staff);
		poison.addTalentChild(hammer);
		
		elusiveness.addTalentChild(sword);
		elusiveness.addTalentChild(staff);
		elusiveness.addTalentChild(hammer);
		
		endurance.addTalentChild(sword);
		endurance.addTalentChild(staff);
		endurance.addTalentChild(hammer);
		
		//4-->5
		sword.addTalentChild(tremors);
		sword.addTalentChild(lethal);
		sword.addTalentChild(speed);
		
		staff.addTalentChild(tremors);
		staff.addTalentChild(lethal);
		staff.addTalentChild(speed);
		
		hammer.addTalentChild(tremors);
		hammer.addTalentChild(lethal);
		hammer.addTalentChild(speed);
		//end dependencies
				
		strengthTree.addRoots(hero, fortitude);
		strengthTree.addRoots(hero, rage);
		strengthTree.addRoots(hero, will);
		strengthTree.addRoots(hero, scent);
		strengthTree.setup();
	}
	
	private void setupHealingTree(AbstractHero hero) {
		//Root Talents - Level 1
		RadienceTalent radience = new RadienceTalent(hero);
		InnerPeaceTalent inner = new InnerPeaceTalent(hero);
		RighteousRetaliationTalent righteous = new RighteousRetaliationTalent(hero);
		
		//Level 2
		DivineProtectionFearTalent fear = new DivineProtectionFearTalent(hero);
		DivineProtectionHateTalent hate = new DivineProtectionHateTalent(hero);
		DivineProtectionSorrowTalent sorrow = new DivineProtectionSorrowTalent(hero);
		
		//Level 3
		DefensiveInstinctsTalent defensive = new DefensiveInstinctsTalent(hero);
		OffensiveInstinctsTalent offensive = new OffensiveInstinctsTalent(hero);
		NurturingTalent nurture = new NurturingTalent(hero);
		
		//Level 4
		ProlongedRejuvinationTalent rejuv = new ProlongedRejuvinationTalent(hero);
		BlessedMiracleTalent blessed  = new BlessedMiracleTalent(hero);
		HolyStrengthTalent holy = new HolyStrengthTalent(hero);
		
		//Setup dependencies 1-->2
		radience.addTalentChild(fear);
		radience.addTalentChild(hate);
		radience.addTalentChild(sorrow);
		
		inner.addTalentChild(fear);
		inner.addTalentChild(hate);
		inner.addTalentChild(sorrow);
		
		righteous.addTalentChild(fear);
		righteous.addTalentChild(hate);
		righteous.addTalentChild(sorrow);
		
		//2-->3
		fear.addTalentChild(defensive);
		fear.addTalentChild(offensive);
		fear.addTalentChild(nurture);
		
		hate.addTalentChild(defensive);
		hate.addTalentChild(offensive);
		hate.addTalentChild(nurture);
		
		sorrow.addTalentChild(defensive);
		sorrow.addTalentChild(offensive);
		sorrow.addTalentChild(nurture);
		
		//3-->4
		offensive.addTalentChild(holy);
		defensive.addTalentChild(blessed);
		nurture.addTalentChild(rejuv);
		
		healingTree.addRoots(hero, radience);
		healingTree.addRoots(hero, inner);
		healingTree.addRoots(hero, righteous);
		healingTree.setup();
	}
	
	private void setupUtilityTree(AbstractHero hero) {
		//Root talents - Level 1
		DualityTalent duality = new DualityTalent(hero);
		HeavyHandednessTalent heavy = new HeavyHandednessTalent(hero);
		
		//Level 2
		GuileTalent guile = new GuileTalent(hero);
		TactTalent tact = new TactTalent(hero);
		DeftnessTalent deft = new DeftnessTalent(hero);
		QuicknessTalent quick = new QuicknessTalent(hero);
		
		//Level 3
		BarteringTalent barter = new BarteringTalent(hero);
		SuperiorityTalent superior = new SuperiorityTalent(hero);
		DeadlyAimTalent deadly = new DeadlyAimTalent(hero);
		
		//Level 4
		MasterTradesmanTalent trade = new MasterTradesmanTalent(hero);
		DeadEyeTalent eye = new DeadEyeTalent(hero);
		AlchemistTalent alchemist = new AlchemistTalent(hero);
		
		//set up dependencies 1-->2
		duality.addTalentChild(guile);
		duality.addTalentChild(tact);
		duality.addTalentChild(deft);
		duality.addTalentChild(quick);
		
		heavy.addTalentChild(guile);
		heavy.addTalentChild(tact);
		heavy.addTalentChild(deft);
		heavy.addTalentChild(quick);
		
		//2-->3
		guile.addTalentChild(superior);
		tact.addTalentChild(barter);
		deft.addTalentChild(deadly);
		
		//3-->4
		barter.addTalentChild(trade);
		deadly.addTalentChild(eye);
		superior.addTalentChild(alchemist);
		
		utilityTree.addRoots(hero, duality);
		utilityTree.addRoots(hero, heavy);
		utilityTree.setup();
	}
	
	public void addTalentPoints(int points) {
		strengthTree.addTalentPoints(points);
	}
	
}
