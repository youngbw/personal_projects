package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.Line;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.AbstractHero;
import model.Warrior;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	private AbstractHero hero;
	private CalderraGUI gui;
	public static boolean chosen;
	public String choice;
	
	public MenuBar(AbstractHero hero, CalderraGUI gui) {
		this.setBackground(Color.BLACK);
		this.setForeground(Color.CYAN);
		this.hero = hero;
		choice = "";
		chosen = false;
		setup(gui);
	}
	
	
	private void setup(CalderraGUI gui) {
		add(createFileMenu(gui));
		this.gui = gui;
	}
	
	
	private JMenu createFileMenu(final CalderraGUI gui) {
		JMenu menu = new JMenu("FILE");
//		menu.setBackground(Color.BLACK);
//		menu.setForeground(Color.CYAN);
		
		JMenuItem newGameItem = new JMenuItem("NEW GAME");
//		newGameItem.setBackground(Color.BLACK);
//		newGameItem.setForeground(Color.CYAN);
		newGameItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CharacterSelectGUI charGUI = new CharacterSelectGUI(gui);
			}
		});
		menu.add(newGameItem);
		menu.addSeparator();
		
		
		JMenuItem loadItem = new JMenuItem("LOAD");
		loadItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadCharacter();
				
			}
		});
		menu.add(loadItem);
		
		JMenuItem saveItem = new JMenuItem("SAVE");
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveCharacter();
				
			}
		});
		menu.add(saveItem);
		menu.addSeparator();
		
		
		
		JMenuItem exitGameItem = new JMenuItem("EXIT");
//		exitGameItem.setBackground(Color.BLACK);
//		exitGameItem.setForeground(Color.CYAN);
		exitGameItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(exitGameItem);
		
		return menu;
	}
	
	private void loadCharacter() {
		
		try {
			//Save Changes
			int result = JOptionPane.showConfirmDialog(gui, "Save Character?", "Save", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				saveCharacter();
			}
			
			
			//Choice for loading here ****
			Scanner fileScan = new Scanner(new File("./src/resources/characters/names.txt"));
			ArrayList<String> myToons = new ArrayList<String>();
			
			final JDialog dialogue = new JDialog();
			dialogue.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			JPanel choicePanel = new JPanel();
			choicePanel.setLayout(new GridLayout(myToons.size(), 1));
			
			//read toons into list
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				myToons.add(line.substring(0, line.length()));
			}
			fileScan.close();
			//make toons into buttons and add buttons to panel
			ArrayList<JButton> buttons = new ArrayList<JButton>();
			for (int i = 0; i < myToons.size(); i++) {
				JButton button = new JButton(myToons.get(i));
				button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						chosen = true;
						makeChoice(((JButton) e.getSource()).getText());
						dialogue.dispose();
					}
				});
				
				buttons.add(button);
				
				
				
				
				choicePanel.add(buttons.get(i));
			}
			
			
			
			
			
			dialogue.add(choicePanel, BorderLayout.CENTER);
			dialogue.pack();
			dialogue.setLocationRelativeTo(null);
			dialogue.setVisible(true);
			
			
			//End choice

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("No file");
		}
		
		
		
	}
	
	public void makeChoice(String theChoice) {
		try {
//			Scanner lineScan = new Scanner(theChoice);
//			lineScan.useDelimiter(", ");
//			String line = lineScan.next();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/resources/characters/" + theChoice + ".ser"));
			gui.setHero((AbstractHero)ois.readObject());
			System.out.println("Loaded instance of " + (this.hero.getClass().getSimpleName()));
//			lineScan.close();
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.print("Class not found");
		}
	}
	
	private void saveCharacter() {
		//May need to separately save the type of hero this is if can't make more specific than AbstractHero, test this
		//update, doesn't seem to need it..continue to check if all stats are preserved, mapped, etc.
		
		try {
			FileOutputStream os = new FileOutputStream(new File("./src/resources/characters/" + this.hero.getFullName() + " - " + 
						this.hero.getHeroClass() + " - " + this.hero.getLevel() + ".ser"));
			
			File myFile = new File("./src/resources/characters/names.txt");
			Scanner fileScan = new Scanner(myFile);
			String writeString = "";
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				if (!line.contains(this.hero.getFullName() + " - " + this.hero.getHeroClass() + " - " + this.hero.getLevel())) {
					writeString += line + "\n";
				}
				
			}
			fileScan.close();
			PrintStream ps = new PrintStream("./src/resources/characters/names.txt");
			
			ps.print(writeString + this.hero.getFullName() + " - " + this.hero.getHeroClass() + " - " + this.hero.getLevel() + "\n");
			
			ps.close();
			
			ObjectOutputStream objOS = new ObjectOutputStream(os);
			objOS.writeObject(this.hero);
			
			os.close();
			objOS.close();
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	
	public void setHero(AbstractHero hero) {
		this.hero = hero;
	}
	
	
}
