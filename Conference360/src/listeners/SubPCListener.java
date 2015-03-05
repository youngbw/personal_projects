package listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.ConferenceClient;
import model.Manuscript.Recommendation;
import view.ClientGui;

/**
 * This class represents the action of acquiring papers to review as well as
 * submitting a recommendation for that paper.
 * 
 * @author MariaMills
 */
public class SubPCListener implements ActionListener {

	private ConferenceClient theClient;
	private ClientGui theFrame;
	private String paper;
	private String subPC;
	private Recommendation rec;

	/**
	 * This constructor accepts a ConferenceClient object and it's GUI
	 * frame. 
	 * 
	 * @author mmills
	 * @param client the conference system
	 * @param frame the GUI main frame
	 */
	public SubPCListener(ConferenceClient client, ClientGui frame) {
		
		theClient = client;
		theFrame = frame;
		rec = Recommendation.NEUTRAL;
	}
	
	/**
	 * This second constructor accepts in addition two strings, one for the title of 
	 * the paper and the other for the name of the sub PC.
	 * 
	 * @param paperName  the paper's name
	 * @param subPC person's name
	 * @param client system
	 * @param frame GUI frame
	 */
	public SubPCListener(ConferenceClient client, ClientGui frame, String paperName, String subPC) {
		
		theClient = client;
		theFrame = frame;
		paper = paperName;
		this.subPC = subPC;
	}

	/**
	 * This method handles the action events passed in from the sub PC view
	 * class.  
	 * 
	 * @author mmills
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals("Get Form")) {
			
			Desktop desk = Desktop.getDesktop();
	        File reviewForm = new File("files/RecommendationForm.txt");
	        
	        try {
				desk.open(reviewForm);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(theFrame, "Could not open the file", "File Error", JOptionPane.ERROR_MESSAGE);
			}        
	        
		} else if(arg0.getActionCommand().equals("Submit Recommendation")) {			

			JFileChooser chooser = new JFileChooser();			
			
			int option = chooser.showOpenDialog(theFrame);
			
			if (option == JFileChooser.APPROVE_OPTION) {
				
				File recForm = chooser.getSelectedFile();				
				theClient.submitRecommendation(paper, recForm, rec);
			}
		
		} else {
			
			JComboBox obj = (JComboBox) arg0.getSource();
			String choice = obj.getSelectedItem().toString();
			rec = null;
			
			if(choice.startsWith("N")) {
				
				rec = Recommendation.NEUTRAL;
			} else if(choice.startsWith("A")) {

				rec = Recommendation.ACCEPT;
			} else if(choice.startsWith("R")) {
			
				rec = Recommendation.REJECT;
			}
		}		
	}
}
