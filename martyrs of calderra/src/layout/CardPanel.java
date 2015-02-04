package layout;

import java.awt.GridLayout;

@SuppressWarnings("serial")
public class CardPanel extends MyPanel {

	
	public CardPanel(int rows, int cols) {
		setLayout(new GridLayout(rows, cols));
	}
	
	public void setGrid(int rows, int cols) {
		setLayout(new GridLayout(rows, cols));
	}
	
	
	
}
