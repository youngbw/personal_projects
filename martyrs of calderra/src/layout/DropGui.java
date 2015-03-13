package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import listeners.MyWindowListener;
import model.AbstractCard;

@SuppressWarnings("serial")
public class DropGui extends JDialog {

	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	private static final int MAX_ITEM_DROP = 4;
	
	private CalderraGUI controller;
	private ArrayList<DropPanel> panels;
	private JLabel goldLabel;
	private JLabel expLabel;
	
	public DropGui(CalderraGUI controller, int gold, int experience, ArrayList<AbstractCard> list) {
		this.controller = controller;
		goldLabel = new JLabel("" + gold);
		expLabel = new JLabel("" + experience);
		this.panels = new ArrayList<DropPanel>();
		this.setAlwaysOnTop(true);
//		this.addWindowListener(new MyWindowListener(controller, this));
		setup(list);
	}
	
	private void setup(ArrayList<AbstractCard> list) {
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4));
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(controller);
		
		MyPanel centerPanel = new MyPanel(MyPanel.DEFAULT_BACKGROUND);
		centerPanel.setLayout(new GridLayout(3, 1));
		
		MyPanel goldPanel = new MyPanel(MyPanel.DEFAULT_BACKGROUND);
		MyPanel experiencePanel = new MyPanel(MyPanel.DEFAULT_BACKGROUND);
		MyPanel cardPanel = new MyPanel();
		
		goldPanel.setLayout(new GridLayout(1, 2));
		experiencePanel.setLayout(new GridLayout(1, 2));
		cardPanel.setLayout(new GridLayout(1, MAX_ITEM_DROP));
		
		MyPanel goldPicture = new MyPanel(MyPanel.DEFAULT_BACKGROUND); //need gold pic resource
		goldPicture.setBorder(new EmptyBorder(0, 0, 0, 0));
		goldPicture.setLayout(new FlowLayout(FlowLayout.CENTER));
		MyPanel labelPanel = new MyPanel(MyPanel.DEFAULT_BACKGROUND);
		labelPanel.setLayout(new GridLayout(1, 1));
		goldLabel = new JLabel("0");
		labelPanel.add(goldLabel);
		goldPanel.add(goldPicture);
		goldPanel.add(labelPanel);
		
		centerPanel.add(goldPanel);
		
		MyPanel experiencePicture = new MyPanel(MyPanel.DEFAULT_BACKGROUND); //need gold pic resource
		experiencePicture.setBorder(new EmptyBorder(0, 0, 0, 0));
		goldPicture.setLayout(new FlowLayout(FlowLayout.CENTER));
		MyPanel expLabelPanel = new MyPanel(MyPanel.DEFAULT_BACKGROUND);
		expLabelPanel.setLayout(new GridLayout(1, 1));
		expLabel = new JLabel("0");
		expLabelPanel.add(expLabel);
		experiencePanel.add(experiencePicture);
		experiencePanel.add(expLabelPanel);
		
		centerPanel.add(experiencePanel);
		
		Random rand = new Random();
		for (int i = 0; i < MAX_ITEM_DROP; i++) {
			DropPanel dropPanel = new DropPanel(list.size() > 0 ? list.get(rand.nextInt(list.size())) : null, this.controller);
			panels.add(dropPanel);
			cardPanel.add(dropPanel);
		}
		
		centerPanel.add(cardPanel);
		this.add(centerPanel, BorderLayout.CENTER);
		
		MyPanel southPanel = new MyPanel(MyPanel.DEFAULT_BACKGROUND);
		southPanel.setLayout(new BorderLayout());
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panels.get(0).currentPickup = 0;
				dispose();
				
			}
		});
		southPanel.add(button);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	public void setNewDrop(int gold, int experience, ArrayList<AbstractCard> list) {
		Random rand = new Random();
		goldLabel.setText("" + gold);
		expLabel.setText("" + experience);
		goldLabel.setForeground(Color.white);
		expLabel.setForeground(Color.white);
		goldLabel.setFont(new Font("Times Roman", Font.BOLD, 24));
		expLabel.setFont(new Font("Times Roman", Font.BOLD, 24));
		goldLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		expLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		int numDrops = rand.nextInt(MAX_ITEM_DROP) + 1;
		for (int i = 0; i < numDrops; i++) {
			DropPanel p = panels.get(i);
			p.addCard(list.get(rand.nextInt(list.size())));
		}
		for (int j = numDrops; j < MAX_ITEM_DROP; j++) {
			DropPanel p = panels.get(j);
			p.removeCard();
		}
	}
	
}
