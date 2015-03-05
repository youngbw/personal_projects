/**
 * 
 */
package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import junit.framework.Assert;
import model.Manuscript;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua Moore
 *
 */
public class ManuScriptTest {
	
	//blank file with nothing in it
	private File fEmpty;
	//File with text 
	private File fFull;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		fEmpty = new File("files/fEmpty.txt");
		fFull = new File("files/fFull.txt");

	}

	/**
	 * Test method for UploadFile
	 */
	@Test
	public void testUploadFile() {
		Manuscript m = new Manuscript("author1", "title1");
		Manuscript m2 = new Manuscript("author2", "title2");
		//upload an empty file
		try {
			m.uploadFile(fEmpty);
			try{
			File f = new File("files/"+m.getId() + ".file");
			File source = new File("files/fEmpty.txt");
			
			Scanner s = new Scanner(f);
			Scanner s2 = new Scanner(source);

			while(s.hasNext() && s2.hasNext()) {
				String l = s.nextLine();
				String l2 = s2.nextLine();
				
				assertEquals(l, l2);
			}
			assertTrue(!s.hasNext() && !s2.hasNext());
			s.close();
			s2.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//upload a file with text
		try {
			m2.uploadFile(fFull);
			try{
			File f = new File("files/"+m2.getId() + ".file");
			File source = new File("files/fFull.txt");
			
			Scanner s = new Scanner(f);
			Scanner s2 = new Scanner(source);

			while(s.hasNext() && s2.hasNext()) {
				String l = s.nextLine();
				String l2 = s2.nextLine();
				
				assertEquals(l, l2);
			}
			assertTrue(!s.hasNext() && !s2.hasNext());
			s.close();
			s2.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
