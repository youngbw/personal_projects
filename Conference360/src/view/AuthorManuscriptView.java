package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import listeners.MyTableMouseListener;
import model.ConferenceClient;
import model.Manuscript;
import model.Review;
import view.ViewPanel;

/**
 * This class represents a view of a single manuscript and a view
 * of the reviews associated with them.
 * @author BrentYoung
 *
 */
@SuppressWarnings("serial")
public class AuthorManuscriptView extends ViewPanel {

	private static final int NUM_BUTTONS = 4;
	
	private Manuscript myPaper;
	private JTable myReviewTable;
	
	public AuthorManuscriptView(ClientGui theFrame, ConferenceClient theClient, Manuscript manuscript) {
		super(theClient, theFrame, manuscript.getName());
		this.myPaper = manuscript;
		this.currrentSelectedTitle = manuscript.getTitle();
		setup();
	}

	/**
	 * This method sets the layout of this view, listing the manuscripts that have been
	 * submitted to this Conference and any action buttons to act upon those manuscripts.
	 */
	private void setup() {
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(this.getWidth() - (this.getWidth() / 15 * 2),
				this.getHeight() - (this.getHeight() / 15 + this.getHeight() / 20)));
		
		
		String[] col = {"Reviewer ID", "Score"};
		myReviewTable = new JTable(new DefaultTableModel(col, 50)) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		myReviewTable.addMouseListener(new MyTableMouseListener(myReviewTable, this));
		JScrollPane scroller = new JScrollPane(myReviewTable);
		scroller.getVerticalScrollBar().setUnitIncrement(10);
		
		
		//LIST LOAD
		if (myPaper.getReviews() != null && !myPaper.getReviews().isEmpty()) {
			for (int i = 0; i < myPaper.getReviews().size(); i++) {
				addReviewToList(myPaper.getReviews().get(i), i);
			}
		}
		
		
		//FOR WEST PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		GridLayout grid = new GridLayout(NUM_BUTTONS, 1);
		buttonPanel.setLayout(grid);
		buttonPanel.setPreferredSize(new Dimension(this.getWidth() / 7, this.getHeight() - (this.getHeight() / 20 + this.getHeight() / 15)));
		
		
		JButton downloadButton = new JButton("View");
		setButtonProperties(downloadButton);
		downloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Review r: myPaper.getReviews()) {
					if (myReviewTable.getValueAt(myReviewTable.getSelectedRow(), 0).equals(r.getReviewerID()) &&
							myReviewTable.getValueAt(myReviewTable.getSelectedRow(), 1).equals(r.getScore())) {
						
						try {
							Desktop.getDesktop().open(r.getReviewForm());
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(theFrame, "Unable to open the desired file.", "Could Not Find Program", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
			}
		});
		buttonPanel.add(downloadButton);
		
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
	
	/**
	 * This method add a review to the list of reviews that have been submitted for this
	 * paper and associated with this Conference.
	 * @param review the Review for this paper
	 * @param rowIndex the index of the table to add the review to
	 */
	private void addReviewToList(Review review, int rowIndex) {
		myReviewTable.setValueAt(review.getReviewerID(), rowIndex, 0);
		myReviewTable.setValueAt(review.getScore(), rowIndex, 1);
	}
}
