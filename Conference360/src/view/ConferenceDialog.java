package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.Conference;
import model.ConferenceClient;
import model.Scale;
import model.User;
import model.Job.Title;

public class ConferenceDialog extends JDialog implements ActionListener {

	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	
	private ClientGui theFrame;
	private ConferenceClient theClient;
	private Conference conference;
	
	private JTextField titleField;
	private JTextField monthField;
	private JTextField dayField;
	private JTextField yearField;
	private JTextField scaleField;
	private JComboBox<ArrayList<String>> scaleBox;
	
	
	public ConferenceDialog (ClientGui theFrame, ConferenceClient client) {
		super(theFrame, "Conference Creation", false);
		this.theClient = client;
		this.theFrame = theFrame;
		
		titleField = new JTextField();
		titleField.addActionListener(this);
		
		
		monthField = new JTextField();
		monthField.addActionListener(this);
		
		dayField = new JTextField();
		dayField.addActionListener(this);
		
		
		yearField = new JTextField();
		yearField.addActionListener(this);
		
		
		scaleField = new JTextField();
		scaleField.addActionListener(this);
		
		setup();
	}
	
	private void setup() {
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(SCREEN_SIZE.width / 4, SCREEN_SIZE.height / 2));
		this.setLayout(new BorderLayout());
		
		//NorthPanel
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 10));
		northPanel.setBorder(new TitledBorder("Title"));
		northPanel.setLayout(new BorderLayout());
		
		northPanel.add(titleField);
		this.add(northPanel, BorderLayout.NORTH);
		
		//SouthPanel;
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 5));
		southPanel.setBorder(new TitledBorder("Review Scale"));
		
		JPanel subSouthPanel = new JPanel();
		subSouthPanel.setLayout(new GridLayout(2, 1));
		subSouthPanel.setPreferredSize(southPanel.getPreferredSize());
		
		Scale scaleloader = new Scale();
		scaleBox = new JComboBox<ArrayList<String>>();
//		scaleBox.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, southPanel.getPreferredSize().height / 2));
		for (int i = 0; i < Scale.NUM_SCALES; i++) {
			scaleBox.addItem(scaleloader.chooseScale(i));
		}
		subSouthPanel.add(scaleBox);
		
		JButton enterButton = new JButton("Submit");
//		enterButton.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, southPanel.getPreferredSize().height / 2));
		enterButton.addActionListener(this);
		subSouthPanel.add(enterButton);
		
		southPanel.add(subSouthPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		
		
		//CenterPanel
		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(new TitledBorder("Submission Deadline"));
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setSize(new Dimension(SCREEN_SIZE.width / 4, yearField.getHeight() * 3));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1));
		leftPanel.setPreferredSize(new Dimension(centerPanel.getWidth() / 4, centerPanel.getHeight()));
		leftPanel.add(new JLabel("Year:"));
		leftPanel.add(new JLabel("Month:"));
		leftPanel.add(new JLabel("Day:"));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.add(yearField);
		rightPanel.add(monthField);
		rightPanel.add(dayField);
		centerPanel.add(leftPanel, BorderLayout.WEST);
		centerPanel.add(rightPanel, BorderLayout.CENTER);
		this.add(centerPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setLocation(SCREEN_SIZE.width / 2 - this.getWidth() / 2, SCREEN_SIZE.height / 2 - this.getHeight() / 2);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String name = titleField.getText();
			Integer year = Integer.parseInt(yearField.getText());
			Integer month = Integer.parseInt(monthField.getText());
			Integer day = Integer.parseInt(dayField.getText());
			Integer scaleID = scaleBox.getSelectedIndex();
			
//			System.out.println("NAME:" + name);
//			System.out.println("DATE:" + year + ", " + month + ", " + day);
			
			
			GregorianCalendar calendar = new GregorianCalendar(year, month, day);
			
			
			if (!name.equals("") && calendar.getTime().after(new Date())) {
				conference = new Conference(name, calendar.getTime(), scaleID);
				theClient.getCurrentUser().addJob(conference.getMyID(), Title.PROGRAMCHAIR);
				theClient.addConference(conference);
				theFrame.moveToComp(new ClientGeneralView(theClient, theFrame));
				dispose();
			}
		} catch (NumberFormatException ex) {
			
		}
	}
}
