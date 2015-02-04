package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InnerPanel extends JPanel implements MouseListener {

	private Info info;
//	JLabel label;
//	JLabel rankLabel;
//	JButton button = new JButton("Select");
	
	public InnerPanel() {
		this(null);
	}
	
	public InnerPanel(Info info) {
		this.info = info;
		this.addMouseListener(this);
//		JPanel panel = new JPanel();
		
//		label  = new JLabel();
//		rankLabel = new JLabel();
//		label.setFont(new Font("Times Roman", Font.BOLD, 16));
//		rankLabel.setFont(new Font("Times Roman", Font.BOLD, 16));
		
//		panel.setLayout(new GridLayout(1, 2));
//		this.setLayout(new GridLayout(2, 1));
//		panel.add(label);
//		panel.add(rankLabel);
//		
//		
//		this.add(label);
//		this.add(rankLabel);
		
		
		
	}
	
	public void setView(Info info) {
		this.info = info;
		repaint();
		
	}
	
	public Info getInfo() {
		return this.info;
	}
	
	
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
		final Graphics2D g2D = (Graphics2D) theGraphics;
		if (info != null) {
			if (!info.getSrc().equals("")) {
				g2D.drawImage(new ImageIcon(this.info.getSrc()).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
				g2D.setColor(new Color(200, 200, 200, 150));
				g2D.fillRect(0,  0, this.getWidth(), this.getHeight() / 4);
			}
			g2D.setColor(Color.BLACK);
			g2D.setFont(new Font("Times Roman", Font.BOLD, 20));
			g2D.drawString("Player Name: " + info.getInfo(), 5, this.getHeight() / 10);
			g2D.drawString("Player Rank: " + info.getRank(), 5, this.getHeight() / 5);
		}
		
		
        
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (info != null) this.info.changed();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
