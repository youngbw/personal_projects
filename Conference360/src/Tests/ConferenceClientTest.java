package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import model.Conference;
import model.ConferenceClient;
import model.Job;
import model.Manuscript;
import model.Review;
import model.User;

import org.junit.Before;
import org.junit.Test;

public class ConferenceClientTest {
	private ConferenceClient client;
	private Conference conf;
	private List<Review> reviews;
	
	private String paperTitle;
	private File reviewForm;
	private String score;
	private String reviewerID;
	private User u;
	
	@Before
	public void setUp() throws Exception {
		paperTitle = "files/fFull.txt";
		score = "0"; 
		reviewerID = "tempID";
		
		client = new ConferenceClient();
		conf = new Conference("ASCII", new Date(), 1);
		reviewForm = new File("files/ReviewForm.txt");
		client.setCurrentConference(conf);
		u = new User("user", "emailtest", "pass");
		client.setUser(u);
	}

	/**
	 * Test for submitReview
	 */
	@Test
	public void testSubmitReview() {
		
		conf.submitPaper("Title", u.getEmail(), new File(paperTitle));
		//incorect title
		client.submitReview("Incorrect Title", reviewForm, score, reviewerID);
		//correct title
		client.submitReview("Title", reviewForm, score, reviewerID);

		reviews = client.getCurrentConference().getPaper("Title").getReviews();
		boolean hasReview = false;
		//check if the review is stored
		for(Review r : reviews) {
			 if (r.getReviewerID().equals(reviewerID) && r.getScore().equals(score)){
				 hasReview = true;
			 }

		}
		//assertTrue(reviews.contains(reviewForm));
		assertTrue("No review found", hasReview);
		assertTrue(client.getCurrentConference().getPaper("Incorrect Title") == null);
	}

	
	
	/**
	 * tests verify User and Register
	 */
	@Test
	public void testVerifyUserAndRegister() {
		client.registerUser("user", "email", "pass");
		
		assertTrue("Correct user verify failed",client.verifyUser("email", "pass"));
		assertFalse("Wrong password verify failed",client.verifyUser("email", "passWrong"));
		assertFalse("Wrong email verify failed",client.verifyUser("wrongmail", "pass"));

	}
	
	/**
	 * Test  for Conference submitPaper and deletePaper
	 */
	@Test
	public void testSubmitAndDeletePaper() {
		String t = "New Title";
		Manuscript m = new Manuscript(u.getEmail(), t, new File("files/fFull.txt"));
		conf.submitPaper(m.getTitle(), u.getEmail(), m.getFile());
		assertFalse("Failed submission",conf.getPaper(m.getTitle()) == null);
		conf.deletePaper("Wrong Title");
		assertFalse("Failed wrong deletion test",conf.getPaper(m.getTitle()) == null);
		
		conf.setDeadline(new GregorianCalendar(2000, 12, 12).getTime());
		conf.deletePaper(t);
		assertFalse("Failed deletion sfter deadline",client.getCurrentConference().getPaper(m.getTitle()) == null);
		conf.setDeadline(new GregorianCalendar(2015, 12, 12).getTime());
		conf.deletePaper(t);
		assertTrue("Failed deletion before deadline", client.getCurrentConference().getPaper(m.getTitle()) == null);
	}

	/**
	 * Test for Conference save()
	 */
	@Test
	public void testSave() {
		//Test data
		User brent = new User("Brent", "athero23@gmail.com", "cardiofreak23");
    	client.registerUser(brent.getName(), brent.getEmail(), brent.getPassword());
		client.getCurrentUser().addJob(1, Job.Title.AUTHOR);
    	client.getCurrentUser().addJob(1, Job.Title.PROGRAMCHAIR);
    	client.getCurrentUser().addJob(1, Job.Title.REVIEWER);
		client.getCurrentUser().addJob(2, Job.Title.REVIEWER);
		client.getCurrentUser().addJob(3, Job.Title.REVIEWER);
		client.getCurrentUser().addJob(4, Job.Title.REVIEWER);
		
		client.getCurrentUser().addPaper(new Manuscript("Brent Young", "With the Willows"), 1);
		client.getCurrentUser().addPaper(new Manuscript("Brent Young", "Another paper...."), 2);
		client.getCurrentUser().addPaper(new Manuscript("Brent Young", "Third paper...."), 3);
		
		//Manuscript testPaper = new Manuscript("Brent Young", "Third paper....");
		client.getCurrentUser().assignPaper(1, Job.Title.REVIEWER, new Manuscript("jane doe", "1st paper...."));
		//client.getCurrentUser().assignPaper(1, Job.Title.REVIEWER, testPaper);
		client.getCurrentUser().assignPaper(1, Job.Title.REVIEWER, new Manuscript("john doe", "then another paper...."));
		client.getCurrentUser().assignPaper(2, Job.Title.REVIEWER, new Manuscript("jim carrey", "try this...."));
		client.getCurrentUser().assignPaper(2, Job.Title.REVIEWER, new Manuscript("jeff daniels", "try this AGAIN...."));
		
		User maria = new User("Maria", "mmills13@gmail.com", "pswd13");
    	client.registerUser(maria.getName(), maria.getEmail(), maria.getPassword());
		client.getCurrentUser().addJob(1, Job.Title.SUBPROGRAMCHAIR);
		client.getCurrentUser().assignPaper(1, Job.Title.SUBPROGRAMCHAIR, new Manuscript("Brent Young", "another paper...."));
		client.getCurrentUser().assignPaper(1, Job.Title.SUBPROGRAMCHAIR, new Manuscript("Brent Young", "a second paper...."));
		client.getCurrentUser().addJob(2, Job.Title.REVIEWER);
		client.getCurrentUser().assignPaper(2, Job.Title.REVIEWER, new Manuscript("Brent Young", "Another paper...."));
		
		client.addConference (new Conference("IADIS MULTI Conference Computer Science and Information System", new Date(System.currentTimeMillis() + 1000000000), 1));
		client.addConference (new Conference("Tucson ANNUAL Conference", new Date(System.currentTimeMillis() + 2000000000), 2));
		client.addConference (new Conference("International NAME Conference", new Date(), 1));
		client.addConference (new Conference("7th ACM Recommender System conference", new Date(), 2));
		//end test data
		client.save();
		ConferenceClient c2 = new ConferenceClient();
		//read serialized data
		try{
			FileInputStream fileIn = new FileInputStream("data/clientState.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			c2 = (ConferenceClient) objectIn.readObject();
			objectIn.close();
			fileIn.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		
		//Current Client is the same for both
		assertTrue(client.getCurrentConference().getMyID() == c2.getCurrentConference().getMyID());
		//Users are the same
		for(User u : client.getAllUsers()) {
			boolean hasMatch = false;
			if(u != null){
				for(User u2: c2.getAllUsers()){
					if(u2 !=null && u.getEmail().equals(u2.getEmail())) hasMatch = true;
				}
				assertTrue("Fail on conference check " + u.getEmail(), hasMatch);
			}
		}
		//Conference are the same
		for(Conference c : client.getMyConferences()) {
			boolean hasMatch = false;
			if(c != null){
				for(Conference conf2: c2.getMyConferences()){
					if(conf2 !=null && c.getName().equals(conf2.getName())) hasMatch = true;
				}
				assertTrue("Fail on conference check " + c.getName(), hasMatch);
			}
		}

	}


	
	
}
