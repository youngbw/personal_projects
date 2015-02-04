package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


public class RankGui extends javax.swing.JFrame implements Observer {
	
	private String fileName;
	
	private List<Info> players;
	
	private JTextArea area;
	private JPanel player1Panel;
	private JPanel player2Panel;
	private InnerPanel innerPanel1;
	private InnerPanel innerPanel2;
	
	
	
	
	protected static final Toolkit KIT = Toolkit.getDefaultToolkit();
	protected static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	protected static final int SCREEN_WIDTH = SCREEN_SIZE.width;
	protected static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	public RankGui() {
		players = new ArrayList<Info>();
		this.setSize(new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));
		this.setLocation(SCREEN_WIDTH / 2 - this.getWidth() /  2, SCREEN_HEIGHT / 2 - this.getHeight() / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		this.setJMenuBar(new MyMenubar(this));
		
	}
	
	public void start() {
		player1Panel = new JPanel();
		player2Panel = new JPanel();
		area = new JTextArea();
		
		area.setBorder(new LineBorder(Color.BLACK));
		
		player1Panel.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 2));
		player2Panel.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 2));
		
		player1Panel.setLayout(new GridLayout(1, 1));
		player2Panel.setLayout(new GridLayout(1, 1));
		
		player1Panel.setBorder(new LineBorder(Color.BLACK));
		player2Panel.setBorder(new LineBorder(Color.BLACK));
		
		innerPanel1 = new InnerPanel();
		innerPanel2 = new InnerPanel();
		
		player1Panel.add(innerPanel1);
		player2Panel.add(innerPanel2);
		
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(2, 1));
		
		westPanel.add(player1Panel);
		westPanel.add(player2Panel);
		
		area.setLineWrap(true);
		area.setFont(new Font("Times Roman", Font.BOLD, 20));
		area.setEditable(false);
		
		 JScrollPane pane = new JScrollPane();
		 pane.add(area);
		 pane.setViewportView(area);
		 
		
		this.add(westPanel, BorderLayout.WEST);
		this.add(pane, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	private void loadText(String fileName) {
		try {
			
			File file = new File(fileName);
			Scanner fileScan = new Scanner(file);
			fileScan.useDelimiter(", ");
			
			if (fileScan.hasNext()) {
				String title = fileScan.next();
				this.setTitle(title);
			}
			int count = 1;
			while (fileScan.hasNext()) {
				String name = fileScan.next();
				Info myInfo = new Info(name, count++, "src/resources/" + name + ".jpg");
				myInfo.addObserver(this);
				players.add(myInfo);
			}
			fileScan.close();
			
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(this, "File could not be found", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void fixRanks(Info target) {
		Info info1 = innerPanel1.getInfo();
		Info info2 = innerPanel2.getInfo();
		if (info1.equals(target)) {
			if (info1.getRank() > info2.getRank()) {
				moveDown(players.indexOf(info2), info1);
			}
		} else if (info2.equals(target)) {
			if (info2.getRank() > info1.getRank()) {
				moveDown(players.indexOf(info1), info2);
			}
		}
		setView();
	}
	
	private void moveDown(int index, Info info) {
		Info temp;
		int endIndex = players.indexOf(info);
		for (; index < endIndex; index++) {
			temp = players.get(index);
			players.set(index, info);
			info = temp;
		}
		players.set(endIndex, info);
		
		int count = 1;
		for (Info i: players) {
			i.setRank(count++);
		}
	}
	
	private void setView() {
		area.setText(this.toString());
		Random rand = new Random();
		int index1 = rand.nextInt(players.size());
		int index2 = rand.nextInt(players.size());
		int checks = 0;
		boolean matchFound = false;
		while (!matchFound) {
			if (players.get(index1).getChecked().size() < players.size() - 1) {
				while (index1 == index2 || players.get(index1).getChecked().contains(players.get(index2).getInfo())) {
					index2 = (index2 + 1) % players.size();
				}
				players.get(index1).addEntry(players.get(index2).getInfo());
				players.get(index2).addEntry(players.get(index1).getInfo());
				matchFound = true;
			} else if (checks < players.size()) {
				index1 = (index1 + 1) % players.size();
				checks++;
			} else {
				break;
			}
			area.setCaretPosition(0);
		}
		if (!matchFound && checks >= players.size() - 1) {
			int result = JOptionPane.showConfirmDialog(this, "You have completed your rankings, would you like to save?", "Save", JOptionPane.YES_NO_OPTION);
			save(result);
			
		} else {
			innerPanel1.setView(players.get(index1));
			innerPanel2.setView(players.get(index2));
		}
		
	}
	
	public void addPlayer() {
		String name = JOptionPane.showInputDialog("Player Name?", "Adrian Peterson");
		if (!nameCheck(name)) {
			JOptionPane.showMessageDialog(this, "Invalid Name: Already Exists");
			return;
		}
		
		String rank = JOptionPane.showInputDialog("Player Rank", players.size() + 1);
		int actRank = players.size() + 1;
		if (rankCheck(rank)) {
			actRank = Integer.parseInt(rank);
		} else {
			JOptionPane.showMessageDialog(this, "Invalid Rank");
			return;
		}
		String src = JOptionPane.showInputDialog("Source Image (src/resources/NAME.jpg)", "src/resources/" + name + ".jpg");
		Info info = new Info(name, actRank, src);
		info.addObserver(this);
		players.add(info);
		moveDown(actRank - 1, info);
		setView();
	}
	
	public void resetTest() {
		for(Info info: players) {
			info.resetList();
		}
	}
	
	private boolean nameCheck(String name) {
		for (Info info: players) {
			if (name.equals(info.getInfo())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean rankCheck(String rank) {
		try {
			int actRank = Integer.parseInt(rank);
			if (actRank > 0) return true;
			else return false;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	public void save(int result) {
		if (result == JOptionPane.YES_OPTION) {
			try {
				PrintStream out = new PrintStream(new File(fileName));
				out.print(this.getTitle() + ", ");
				for (int i = 0; i < players.size(); i++) {
					out.print(players.get(i).getInfo() + ", ");
				}
				out.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	
	public void setFile(String file) {
		this.fileName = file;
		players = new ArrayList<Info>();
		loadText(fileName);
		
		if (!players.isEmpty()) setView();
	}

	@Override
	public void update(Observable o, Object arg) {
		fixRanks((Info)o);
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < players.size(); i++) {
			sb.append(players.get(i).toString());
		}
		
		return sb.toString();
	}
}
