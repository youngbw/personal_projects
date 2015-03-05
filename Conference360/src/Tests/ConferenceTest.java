/**
 * 
 */
package Tests;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;
import model.Conference;
import model.Manuscript;

import org.junit.*;

/**
 * This class tests the submitPaper() method in the conference class.
 * 
 * @author Maria Mills
 * @version 1.0
 */
public class ConferenceTest extends TestCase {
	
	private Manuscript aManuscript;
	private Conference aConference;
	private String paperName;
	private String author;
	private File myFile;	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		paperName = "Conference Paper";
		author = "user123";
		String fileName = "Test.txt";
		File myFile = new File(fileName);
		aConference = new Conference("ASCII", new Date(), 1);		
		aManuscript = new Manuscript(author, paperName, myFile);
		aConference.submitPaper(paperName, author, myFile);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	  /**
     * This method tests that the paper is submitted to 
     * the conference and found in the collection of 
     * manuscripts.
     *      
     * Test method for {@link Conference#submitPaper()}.
     */
	@Test
	public void testSubmitPaper() {		
		
		assertEquals(author, aConference.getPaper(paperName).getAuthorID()); //tests that the paper was added and the author matches.
		
	}

}
