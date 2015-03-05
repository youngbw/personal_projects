package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import listeners.MyConferenceListener;
import listeners.MyTableMouseListener;
import model.ConferenceClient;
import model.Conference;

/**
 * This class represents the view of the list of Conference in this program.
 * @author BrentYoung
 *
 */
@SuppressWarnings("serial")
public class ClientGeneralView extends ViewPanel {
	/**
	 * JTable to use in ClientGeneralView.
	 */
	private JTable myTable;
	/**
	 * Default constructor, sets the client, frame, and title.
	 * 
	 * @param theClient the client
	 * @param theFrame the frame
	 */
	public ClientGeneralView(ConferenceClient theClient, ClientGui theFrame) {
		super(theClient, theFrame, "MSEE");
		setup();
	}
	
	/**
	 * Sets up general layout of this view.
	 */
	private void setup() {
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(this.getWidth() - (this.getWidth() / 15 * 2),
				this.getHeight() - (this.getHeight() / 15 + this.getHeight() / 20)));
		
		String[] col = {"Conference", "Submission Deadline"};
		myTable = new JTable(new DefaultTableModel(col, 50)) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		myTable.addMouseListener(new MyTableMouseListener(myTable, this));
		myTable.addMouseListener(new MyConferenceListener(theFrame, client, this));
		JScrollPane scroller = new JScrollPane(myTable);
		
		int i = 0;
		for (Conference c: client.getMyConferences()) {
			addConferenceToList(c, i);
			i++;
		}
		
		centerPanel.add(scroller, BorderLayout.CENTER);
		this.add(centerPanel, BorderLayout.CENTER);
		this.revalidate();
	}

	/**
	 * Adds a Conference to the list.
	 * 
	 * @param conf the conference
	 * @param rowIndex the row index
	 */
	private void addConferenceToList(Conference conf, int rowIndex) {
		if (rowIndex == 0) this.currrentSelectedTitle = conf.getName();
		myTable.setValueAt(conf.getName(), rowIndex, 0);
		Date confDate = conf.getSubmissionDeadline();
		DateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
		formatter.setLenient(false);
		myTable.setValueAt(formatter.format(confDate) + "  -- 12:00 AM PST", rowIndex, 1);
	}
}
