package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import listeners.MyTableMouseListener;
import model.Conference;
import model.ConferenceClient;
import model.Job;
import model.Manuscript;
import model.Review;
import model.User;
import model.Manuscript.Decision;
/**
 * GUI view of a manuscript from the Program Chair's perspective,
 * view paper specific recommendations and reviews.
 *  see subPC recommendations, Reviewer reviews, the final status, etc
 * @author BrentYoung
 *
 */
public class PCManuscriptView extends ViewPanel {
	
	private final static int NUM_BUTTONS = 4;
	
	private JTable myReviewTable;
	private Manuscript myPaper;
	public PCManuscriptView(ConferenceClient theClient, ClientGui theFrame, Manuscript manuscript) {
		super(theClient, theFrame, manuscript.getTitle());
		this.myPaper = manuscript;
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
		
		
		JButton downloadButton = new JButton("Download");
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
