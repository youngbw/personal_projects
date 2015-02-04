package layout;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.border.LineBorder;

import model.AbstractHero;
import model.AbstractVillain;


@SuppressWarnings("serial")
public class StoryPanel extends MyPanel {

	private final String STORYSOURCE = "src/resources/ranger.jpeg";
	private final String TRAINSOURCE = "src/resources/druid.jpg";
	private final String BOSSSOURCE = "src/resources/warrior.jpg";
	
	MyPanel train;
	MyPanel boss;
	
	public StoryPanel(String src, int gridRow, int gridCol, AbstractHero hero) {
		super(src);
		this.hero = hero;
		setup(gridRow, gridCol);
	}
	
	private void setup(int gridRow, int gridCol) {
		this.setLayout(new GridLayout(gridRow, gridCol));
		
		MyPanel story = new smallStoryPanel(STORYSOURCE);
		story.setBorder(new LineBorder(Color.WHITE));
		
		train = new trainPanel(TRAINSOURCE, this.hero);
		train.setBorder(new LineBorder(Color.WHITE));
		
		boss = new bossPanel(BOSSSOURCE, this.hero);
		boss.setBorder(new LineBorder(Color.WHITE));
		
		this.add(story);
		this.add(train);
		this.add(boss);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof AbstractHero && !(o instanceof AbstractVillain)) {
//			((AbstractHero) arg).addObserver(this);
			this.hero = (AbstractHero) arg;
			train.update(o, arg);
			boss.update(o, arg);
		}
		
	}

}
