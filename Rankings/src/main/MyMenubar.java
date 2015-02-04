package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MyMenubar extends JMenuBar {

	private RankGui gui;
	
	public MyMenubar(RankGui gui) {
		this.gui = gui;
		setup();
	}
	
	private void setup() {
		JMenu file = createFile();
		JMenu positions = createPositions();
		JMenu options = createOptions();
		
		this.add(file);
		this.add(positions);
		this.add(options);
	}
	
	private JMenu createFile() {

		JMenu fileMenu = new JMenu("FILE");

		JMenuItem save = new JMenuItem("SAVE");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.save(JOptionPane.YES_OPTION);
			}
		});
		fileMenu.add(save);

		JMenuItem exit = new JMenuItem("EXIT");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		fileMenu.add(exit);

		return fileMenu;

		
	}
	
	
	private JMenu createPositions() {
		JMenu positions = new JMenu("POSITIONS");
		
		JMenuItem rbs = new JMenuItem(new MyAction(gui, "Running Backs"));
		JMenuItem wrs = new JMenuItem(new MyAction(gui, "Wide Receivers"));
		JMenuItem qbs = new JMenuItem(new MyAction(gui, "QuarterBacks"));
		JMenuItem tes = new JMenuItem(new MyAction(gui, "Tight Ends"));
		
		positions.add(qbs);
		positions.add(rbs);
		positions.add(wrs);
		positions.add(tes);
		
		return positions;
	}
	
	
	private JMenu createOptions() {
		JMenu options = new JMenu("OPTIONS");
		JMenuItem addPlayer = new JMenuItem("ADD PLAYER");
		addPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.addPlayer();
				
			}
		});
		options.add(addPlayer);
		
		JMenuItem reset = new JMenuItem("RESET SESSION");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.resetTest();
				
			}
		});
		options.add(reset);
		return options;
	}
	
	
	
}
