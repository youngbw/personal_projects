package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Job.Title;
import model.Manuscript;
import model.User;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

private User theUser;
	
	@Before
	public void setUp() throws Exception {
		
		theUser = new User("Brent Young", "AThero23@gmail.com", "UWstudent1");
	}

	/**
	 * Test for addPaper
	 */
	@Test
	public void testAddPaper() {
		File myFile = new File("Test.txt");
		int conferenceID = 1;
		//adding one paper
		Manuscript thePaper1 = new Manuscript(theUser.getName(), "The many methods of Java Programming", myFile);
		theUser.addPaper(thePaper1, conferenceID);
		Manuscript theScript = theUser.getMyPapers().get(0);
		assertTrue("The wrong or inappropriate conference ID has been added", theScript.getMostRecentConferenceID() == 1);
		//adding a second paper to check mostRecent
		int anotherConfID = 2;
		theUser.addPaper(thePaper1, anotherConfID);
		theScript = theUser.getMyPapers().get(0);
		assertTrue("The wrong conference ID was added to the paper or the paper was added twwice", theScript.getMostRecentConferenceID() == 2);
		//Adding another paper to check size
		int yetAnotherID = 3;
		Manuscript anotherPaper = new Manuscript(theUser.getName(), "A Record of Time", myFile);
		theUser.addPaper(anotherPaper, yetAnotherID);
		List<Manuscript> myScripts = theUser.getMyPapers();
		assertTrue("The second paper was not added properly", myScripts.size() == 3);
		//checking paper conferenceID's 
		Manuscript firstScript = myScripts.get(0);
		Manuscript secondScript = myScripts.get(2);
		assertTrue("Wrong paper", firstScript.getName().equals("The many methods of Java Programming") && firstScript.getMostRecentConferenceID() == 2);
		assertTrue("Second Wrong paper", secondScript.getName().equals("A Record of Time") && secondScript.getMostRecentConferenceID() == 3);
		
		//checking to see if jobs are correct
		ArrayList<Job> theJobs = (ArrayList<Job>) theUser.getMyJobs();
		assertTrue("Wrong Job for conference 1", theJobs.get(0).getConferenceID() == 1 && theJobs.get(0).getTitles().contains(Title.AUTHOR));
		assertTrue("Wrong Job for conference 2", theJobs.get(1).getConferenceID() == 2 && theJobs.get(1).getTitles().contains(Title.AUTHOR));
		assertTrue("Wrong Job for conference 3", theJobs.get(2).getConferenceID() == 3 && theJobs.get(2).getTitles().contains(Title.AUTHOR));
		
		assertFalse("Wrong Job for conference 3", theJobs.get(2).getTitles().contains(Title.REVIEWER));		
	}
	
	/**
	 * Test for addJob
	 */
	@Test
	public void testAddJob () {
		int id = 1;
		theUser.addJob(id, Job.Title.PROGRAMCHAIR);
		assertTrue(hasJob(theUser, Job.Title.PROGRAMCHAIR, id));
	}
	
	/**
	 * Test for User removeJob
	 */
	@Test
	public void testRemoveJob() {
		int id = 1;
		theUser.addJob(id, Job.Title.PROGRAMCHAIR);
		theUser.removeJob(id, Job.Title.PROGRAMCHAIR);
		assertFalse(hasJob(theUser, Job.Title.PROGRAMCHAIR, id));
	}

	/**
	 * Test for User assignPaper
	 */
	@Test
	public void testAssignPaper(){
		int id= 1;
		Manuscript m1 = new Manuscript(theUser.getEmail(), "man1");
		Manuscript m2 = new Manuscript(theUser.getEmail(), "man2");
		Manuscript m3 = new Manuscript("different email", "man3");

		theUser.assignPaper(id, Job.Title.REVIEWER,m1);
		theUser.assignPaper(id, Job.Title.AUTHOR, m2);
		theUser.assignPaper(id, Job.Title.REVIEWER, m3);

		//Testing assignPaper when the conferenceID and JobTitle have not been submitted before
		boolean hasManuscript = false;
		for (Manuscript m : theUser.getJobPapers(id, Job.Title.REVIEWER)){
			if(m.getId() == m1.getId()){
				hasManuscript = true;
			}
		}
		assertTrue("Failed hasManuscript test for when confId was not pressent and JobTitle not present", hasManuscript);
		
		//Testing assignPaper when the conferenceID has been used and JobTitle has not been submitted before
		hasManuscript = false;
		for (Manuscript m : theUser.getJobPapers(id, Job.Title.AUTHOR)){
			if(m.getId() == m2.getId()){
				hasManuscript = true;
			}
		}
		assertTrue("Failed hasManuscript test for when confId was pressent and JobTitle not present", hasManuscript);
		
		//Testing assignPaper when the conferenceID hasn't been used and JobTitle has been submitted before
		hasManuscript = false;
		for (Manuscript m : theUser.getJobPapers(id, Job.Title.REVIEWER)){
			if(m.getId() == m3.getId()){
				hasManuscript = true;
			}
		}
		assertTrue("Failed hasManuscript test ",hasManuscript);

		//test assigning a paper as reviewer when your the author
		theUser.assignPaper(id, Job.Title.REVIEWER, m2);
		hasManuscript = false;
		for (Manuscript m : theUser.getJobPapers(id, Job.Title.REVIEWER)){
			if(m.getId() == m2.getId()){
				hasManuscript = true;
			}
		}
		assertFalse("Failed BR 6 ", hasManuscript);

	}
	
	/**
	 * Test for User conferenceMatchJob
	 */
	@Test
	public void testConferenceMatchJob() {
		int id = 1;
		Title t = Job.Title.AUTHOR;
		//test for when there isn't a job match
		assertTrue("failed job null match", theUser.conferenceMatchJob(id)==null);
		theUser.addJob(id, t);	
		//test for when there is a job match
		assertTrue("failed job match test",theUser.getMyJobs().contains(theUser.conferenceMatchJob(id)));
	}
	
	/**
	 * Test for User deletePapers()
	 */
	@Test
	public void testDeletePapers(){
		int id = 1;
		Manuscript m  = new Manuscript(theUser.getEmail(), "Title");
		theUser.addPaper(m, id);
		theUser.deletePaper("IncorectTitle", id);
		assertTrue("Failed delete wrong paper test",theUser.getMyPapers().contains(m));
		theUser.deletePaper("Title", id);
		assertFalse("Failed delete correct paper test",theUser.getMyPapers().contains(m));
	}

	
	/** 
	 * @param u a user
	 * @param t the Job.Title
	 * @param confId a conference id
	 * @return true if the user has the right job
	 */
	private boolean hasJob(User u, Title title, int confId) {
		boolean hasJob = false;
		for (Job j : theUser.getMyJobs()) {
			//has the right conf
			if(j.getConferenceID() == confId) {
				
				//has right title in right conf
				for(Title t : j.getTitles()) {
					if(t==title) {
						hasJob = true;
						break;
					}
				}
			}
		}
		return hasJob;
	}


}
