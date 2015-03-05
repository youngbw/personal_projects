package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import listeners.MyTableMouseListener;
import model.ConferenceClient;
import model.Manuscript;
import model.Review;
import model.User;
import model.Job.Title;

/**
 * 
 * @author Brent Young
 *
 */
public class PCSubPCView extends ViewPanel{
	
	private User subPC;
	private JTable myTable;
	
	
	public PCSubPCView(ConferenceClient theClient, ClientGui theFrame, User user,
			String email) {
		super(theClient, theFrame, user.getName() + " - Sub Prog. Chair");
		subPC = theClient.getUser(email);
		setup();
	}
	
	public void setup() {
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(this.getWidth() - (this.getWidth() / 15 * 2),
				this.getHeight() - (this.getHeight() / 15 + this.getHeight() / 20)));
		
		
		String[] col = {"Title", "Number of Reviews", "Recommendation"};
		myTable = new JTable(new DefaultTableModel(col, 50)) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		myTable.addMouseListener(new MyTableMouseListener(myTable, this));
		JScrollPane scroller = new JScrollPane(myTable);
		scroller.getVerticalScrollBar().setUnitIncrement(10);
		
		
		//LIST LOAD
		int i = 0;
		Iterator myIt = subPC.getJobPapers(client.getCurrentConference().getMyID(), Title.SUBPROGRAMCHAIR).iterator();
		while (myIt.hasNext()) {
			Manuscript manu = (Manuscript)myIt.next();
			addPaperToList(manu, i);
			i++;
		}
		
		
		centerPanel.add(scroller, BorderLayout.CENTER); //pane
		this.add(centerPanel, BorderLayout.CENTER);
		this.revalidate();
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
		myTable.setValueAt(paper.getRecommendation().toString(), rowIndex, 2);
	}
	
}
