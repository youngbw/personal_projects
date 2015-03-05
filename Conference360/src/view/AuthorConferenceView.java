package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import listeners.DetailListener;
import listeners.MyAddPaperListener;
import listeners.MyModifyPaperListener;
import listeners.MyRemovePaperListener;
import listeners.MyTableMouseListener;
import model.ConferenceClient;
import model.Manuscript;

/**
 * This class represent the Authors view of a specific Conference and all papers they
 * have submitted to that Conference.
 * @author BrentYoung
 *
 */
@SuppressWarnings("serial")
public class AuthorConferenceView extends ViewPanel {
	/**
	 * Number of buttons available.
	 */
	private static final int NUM_BUTTONS = 4;
	/**
	 * Reference to southern JLabel.
	 */
	private JLabel southLabel;
	/**
	 * Reference to the JTable used in AuthorConferenceView.
	 */
	private JTable myTable;
	
	/**
	 * Default constructor.
	 * 
	 * @param client the client
	 * @param theFrame the frame
	 */
	public AuthorConferenceView(ConferenceClient client, ClientGui theFrame) {
		super(client, theFrame, client.getCurrentConference().getName());
		setup();
	}
	
	/**
	 * This method sets up the layout of this view.
	 */
	private void setup() {
		//For viewPanel center
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(this.getWidth() - (this.getWidth() / 15 * 2),
				this.getHeight() - (this.getHeight() / 15 + this.getHeight() / 20)));
		

		//JTABLE ATTEMPT
		String[] col = {"Title", "Number of Reviews", "Decision Status"};
		myTable = new JTable(new DefaultTableModel(col, 50)) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		myTable.addMouseListener(new MyTableMouseListener(myTable, this));
		JScrollPane scroller = new JScrollPane(myTable);
		
		//ADD existing papers
		int i = 0;
		for (Manuscript m: (ArrayList<Manuscript>)client.getCurrentUser().getMyPapers()) {
			if (m.isSubmittedtoConferece(client.getCurrentConference().getMyID())) {
				this.addPaperToList(m, i);
				i++;
			}
		}
		
		//For centerPanel SOUTH
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width, centerPanel.getPreferredSize().height / 20));
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		southLabel = new JLabel("Click an option below to switch your role within this conference.", SwingConstants.CENTER);
		infoPanel.add(southLabel, BorderLayout.CENTER);
		centerPanel.add(infoPanel, BorderLayout.SOUTH);
		
		
		//For viewPanel WEST -- CAN ADD MORE COMPONENTS HERE IF NEEDED
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		GridLayout grid = new GridLayout(NUM_BUTTONS, 1);
		buttonPanel.setLayout(grid);
		buttonPanel.setPreferredSize(new Dimension(this.getWidth() / 7, this.getHeight() - (this.getHeight() / 20 + this.getHeight() / 15)));
		
		JButton addButton = new JButton("ADD");
		setButtonProperties(addButton);
		addButton.addActionListener(new MyAddPaperListener(client, theFrame, this.client.getCurrentConference()));
		buttonPanel.add(addButton);
		
		
		JButton removeButton = new JButton("REMOVE");
		setButtonProperties(removeButton);
		removeButton.addActionListener(new MyRemovePaperListener(theFrame, client, this));
		buttonPanel.add(removeButton);
		
		JButton replaceButton = new JButton("MOD");
		setButtonProperties(replaceButton);
		replaceButton.addActionListener(new MyModifyPaperListener(theFrame, client, this));
		buttonPanel.add(replaceButton);
		
		JButton detailButton = new JButton("View\nReviews");
		setButtonProperties(detailButton);
		detailButton.addActionListener(new DetailListener(theFrame, client, this));
		buttonPanel.add(detailButton);
		
		
		//dummy panels
		while (buttonPanel.getComponentCount() < NUM_BUTTONS) {
			JPanel dummy = new JPanel();
			dummy.setBackground(Color.WHITE);
			buttonPanel.add(dummy);
		}
		
		this.add(buttonPanel, BorderLayout.WEST);
		centerPanel.add(scroller, BorderLayout.CENTER); //pane
		this.add(centerPanel, BorderLayout.CENTER);
		this.revalidate();
	}
	
	public void setLabelText(String text) {
		southLabel.setText(text);
	}
	
	/**
	 * This method adds a paper to the table of papers.
	 * @param paper the submitted paper to be added
	 * @param rowIndex the inex of the table to add the paper to
	 */
	private void addPaperToList(Manuscript paper, int rowIndex) {
		if (rowIndex == 0) this.currrentSelectedTitle = paper.getTitle();
		myTable.setValueAt(paper.getTitle(), rowIndex, 0);
		myTable.setValueAt(paper.getReviews().size(), rowIndex, 1);
		myTable.setValueAt(paper.getStatus().toString(), rowIndex, 2);
	}
}
