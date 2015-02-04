package main;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class MyAction extends AbstractAction {

	private static final String EXTENSION = "src/resources/";
	
	private RankGui gui;
	private String file;
	
	public MyAction(RankGui gui, String file) {
		this.gui = gui;
		this.file = file;
		
		this.putValue(Action.NAME, this.file);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gui.setFile(EXTENSION + file + ".txt");
		
	}

}
