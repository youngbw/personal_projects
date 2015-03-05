package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import listeners.PCDetailListener;
import model.Conference;
import model.ConferenceClient;
import model.Job;
import model.Manuscript;
import model.User;
import model.Manuscript.Decision;
/**
 * GUI for Program Chair View once a conference is selected.
 * @author Keith Foreman
 */
@SuppressWarnings("serial")
public class PCConferenceView extends ViewPanel {
	/**
	 * Reference to the selected User.
	 */
	private User selectedUser;
	/**
	 * Reference to the selected manuscript.
	 */
	private Manuscript selectedMan;
	/**
	 * Reference to the selected email address.
	 */
	private String selected;
	/**
	 * JTable to use in PCConferenceView.
	 */
	private JTable subPCTable;

	private static final int NUM_BUTTONS = 5;

	/**
	 * JTable to use in PCConferenceView.
	 */
	private JTable myTable;
	/**
	 * The current conference.
	 */
	private Conference myConference; 
	/**
	 * Default constructor for PCConferenceView.
	 * 
	 * @param theClient the conference client
	 * @param theFrame the clientGUI frame
	 * @param title the title of the conference
	 */
	public PCConferenceView(ConferenceClient theClient, ClientGui theFrame,
			String title) {
		super(theClient, theFrame, title);
		myConference = theClient.getCurrentConference();
		setup();
	}

	/**
	 * Creates the general look of the page.
	 */
	private void setup() {
		
		String[] col = {"Title", "Number of Reviews", "Decision Status"};
		myTable = new JTable(new DefaultTableModel(col, 50)) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		JScrollPane scroller = new JScrollPane(myTable);
		myTable.addMouseListener(new JTableMouseListener());
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		myTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		myTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		
		int i = 0;
		Collection<User> users = client.getAllUsers();
		for (User u : users) {
			for (Manuscript m: u.getMyPapers()) {
				if (m.isSubmittedtoConferece(client.getCurrentConference().getMyID())) {
					this.addPaperToList(m, i);
					i++;
				}
			}
		}
		
		//west panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		GridLayout grid = new GridLayout(NUM_BUTTONS, 1);
		buttonPanel.setLayout(grid);
		buttonPanel.setPreferredSize(new Dimension(this.getWidth() / 5, this.getHeight()
				- (this.getHeight() / 20 + this.getHeight() / 12)));
		
		JButton viewSubPCButton = new JButton("View Subprogram Chairs");
		setButtonProperties(viewSubPCButton);
		viewSubPCButton.addActionListener(new ViewSubPCListener());
		buttonPanel.add(viewSubPCButton);
		//
		JButton acceptDecButton = new JButton("<Accept> this paper.");
		acceptDecButton.addActionListener(new AcceptanceActionListener());
		JButton rejDecButton = new JButton("<Reject> this paper.");
		rejDecButton.addActionListener(new RejectionActionListener());
		JButton setSubPCButton = new JButton("Set Subprogram Chair");
		setSubPCButton.addActionListener(new SetSubPCActionListener());
		setButtonProperties(acceptDecButton);
		setButtonProperties(rejDecButton);
		setButtonProperties(setSubPCButton);
		buttonPanel.add(acceptDecButton);
		buttonPanel.add(setSubPCButton);
		buttonPanel.add(rejDecButton);
		
		JButton detailButton = new JButton("View\nReviews");
		setButtonProperties(detailButton);
		detailButton.addActionListener(new PCDetailListener(theFrame, client, this));
		buttonPanel.add(detailButton);
		
		this.add(buttonPanel, BorderLayout.WEST);
		this.add(scroller, BorderLayout.CENTER);
		myTable.clearSelection();
		this.revalidate();
	}
	
	/**
	 * Adds a paper to center panel's JTable.
	 * 
	 * @param paper the Manuscript
	 * @param rowIndex the row index
	 */
	private void addPaperToList(final Manuscript paper, int rowIndex) {
		myTable.setValueAt(paper.getTitle(), rowIndex, 0);
		myTable.setValueAt(paper.getReviews().size(), rowIndex, 1);
		myTable.setValueAt(paper.getStatus().toString(), rowIndex, 2);
	}
	
	/**
	 * Adds a User to the subPC JTable for use in multiple places.
	 * 
	 * @param user the user
	 * @param rowIndex the row index
	 */
	private void addSubPCtoList(final User user, final int rowIndex) {
		subPCTable.setValueAt(user.getName(),rowIndex, 0);
		subPCTable.setValueAt(user.getEmail(), rowIndex, 1);
	}
	
	public Manuscript getSelectedManuscript() {
		return this.selectedMan;
	}
	
	/**
	 * Stores the selected manuscript from JTable.
	 * @author Keith Foreman
	 */
	private class JTableMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			String name = (String) myTable.getValueAt(myTable.getSelectedRow(), 0);
			Collection<User> users = client.getAllUsers();
			if (name != null) {
				for (User u : users) {
					for (Manuscript m: u.getMyPapers()) {
						if (m.isSubmittedtoConferece(client.getCurrentConference().getMyID()) && m.getTitle().equals(name)) {
							selectedMan = m;
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Displays list of subPC's.
	 * 
	 * @author Keith Foreman
	 */
	private class ViewSubPCListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] col = {"Name", "Email Address"};
			subPCTable = new JTable(new DefaultTableModel(col, 50)) {
				@Override
				public boolean isCellEditable(int arg0, int arg1) {
					return false;
				}
			};
			JScrollPane scroller = new JScrollPane(subPCTable);
			subPCTable.addMouseListener(new ViewJTableMouseListener());
			
			Collection<User> users = client.getAllUsers();
			int i = 0;
			for (User u : users) {
				if(u.conferenceMatchJob(client.getCurrentConference().getMyID()) != null) {
					if(u.conferenceMatchJob(client.getCurrentConference().getMyID())
						.getTitles().contains(Job.Title.SUBPROGRAMCHAIR)) {
					addSubPCtoList(u, i);
					i++;
					}
				}
			}
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			subPCTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			subPCTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
			JOptionPane.showMessageDialog(null, scroller, "Click SubPC to view assigned papers.",
					JOptionPane.OK_CANCEL_OPTION);
		}
		
	}
	
	/**
	 * Stores the selected Subprogram Chair and changes the frame to the SubPC view.
	 * 
	 * @author Keith Foreman
	 */
	private class ViewJTableMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (subPCTable.getValueAt(subPCTable.getSelectedRow(), 1) != null) {
				selected = (String) subPCTable.getValueAt(subPCTable.getSelectedRow(), 1);
			}
			if (selected != null) {
				theFrame.moveToComp(new PCSubPCView(client, theFrame, client.getUser(selected), selected));
			} else {
				JOptionPane.showMessageDialog(theFrame, "No SubPC selected!",
						"Error!", JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Makes an acceptance decision on a selected Manuscript.
	 * 
	 * @author Keith Foreman
	 */
	private class AcceptanceActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(selectedMan != null) {
				JPanel acceptPanel = new JPanel();
				JLabel label = new JLabel("Are you sure you want to Accept this paper?");
				acceptPanel.add(label);
				int result = JOptionPane.showConfirmDialog(null, acceptPanel, "Accept",
						JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION) {
					selectedMan.setStatus(Decision.ACCEPTED);
					theFrame.moveToComp(new PCConferenceView(client, theFrame, client.getCurrentConference().getName()));
				}
			}
		}
		
	}
	
	/**
	 * Makes a rejection decision on a selected manuscript.
	 * 
	 * @author Keith Foreman
	 *
	 */
	private class RejectionActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(selectedMan != null) {
				JPanel rejectPanel = new JPanel();
				JLabel label = new JLabel("Are you sure you want to Reject this paper?");
				rejectPanel.add(label);
				int result = JOptionPane.showConfirmDialog(null, rejectPanel, "Reject",
						JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION) {
					selectedMan.setStatus(Decision.REJECTED);
					theFrame.moveToComp(new PCConferenceView(client, theFrame, client.getCurrentConference().getName()));
				}
			}
		}
		
	}
	
	/**
	 * Sets a selected Subprogram Chair for a selected manuscript.
	 * 
	 * @author Keith Foreman
	 */
	private class SetSubPCActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(selectedMan != null) {
				String[] col = {"Name", "Email Address"};
				subPCTable = new JTable(new DefaultTableModel(col, 50)) {
					@Override
					public boolean isCellEditable(int arg0, int arg1) {
						return false;
					}
				};
				JScrollPane scroller = new JScrollPane(subPCTable);
				subPCTable.addMouseListener(new SetSubPCMouseListener());
				
				Collection<User> users = client.getAllUsers();
				int i = 0;
				for (User u : users) {
					if(!u.getMyJobs().contains(Job.Title.PROGRAMCHAIR)) {
						addSubPCtoList(u, i);
						i++;
					}
				}
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment( JLabel.CENTER );
				subPCTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
				subPCTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
				int result = JOptionPane.showConfirmDialog(null, scroller, "Choose a User:",
						JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.OK_OPTION && !selected.equals("") && client.isUser(selected)) {
					if (client.getUser(selected).getSpecificPaper(selectedMan.getTitle()).equals(selectedMan)) {
						JOptionPane.showMessageDialog(theFrame, "User is the author of this paper.",
								"Error!", JOptionPane.ERROR_MESSAGE);
					} else if (client.getUser(selected).getJobPapers(client.getCurrentConference().getMyID(), Job.Title.SUBPROGRAMCHAIR) != null &&
							client.getUser(selected).getJobPapers(client.getCurrentConference().getMyID(), Job.Title.SUBPROGRAMCHAIR).contains(selectedMan)) {
						JOptionPane.showMessageDialog(theFrame, "User is already SubPC for this paper.",
								"Error!", JOptionPane.ERROR_MESSAGE);
					} else if (client.getUser(selected).getSubPCCount() < 4) {
						client.getUser(selected).addJob(client.getCurrentConference().getMyID(), Job.Title.SUBPROGRAMCHAIR);
						client.getUser(selected).assignPaper(client.getCurrentConference().getMyID(), Job.Title.SUBPROGRAMCHAIR, selectedMan);
						theFrame.moveToComp(new PCConferenceView(client, theFrame, client.getCurrentConference().getName()));
						client.save();
						
					}else {
						JOptionPane.showMessageDialog(theFrame, "SubPC already assigned maximum number of papers.",
								"Error!", JOptionPane.ERROR_MESSAGE);
					}
				} else if (result == JOptionPane.OK_OPTION && !client.isUser(selected)){
					JOptionPane.showMessageDialog(theFrame, "Not a User.",
							"Error!", JOptionPane.ERROR_MESSAGE);
				} else if (result == JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(theFrame, "Please fill out all fields.",
							"Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	/**
	 * Selects a user to be set as Subprogram Chair.
	 * @author Keith Foreman
	 */
	private class SetSubPCMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (subPCTable.getValueAt(subPCTable.getSelectedRow(), 1) != null) {
				selected = (String) subPCTable.getValueAt(subPCTable.getSelectedRow(), 1);
			}
			if(selected == null) {
				JOptionPane.showMessageDialog(theFrame, "No SubPC selected!",
						"Error!", JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
