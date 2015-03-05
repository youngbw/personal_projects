package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;

/**
 *	Contains a Review score based on some Scale<T>, review comments, and a reviewer use id 
 * @author Josh Moore
 */
public class Review implements Serializable {
	/**
	 * Reference to the score for this Review.
	 */
	private String score; 
	/**
	 * Reference to the comment for this Review.
	 */
	private String comment;
	/**
	 * Reference to the reviewer ID.
	 */
	private String reviewerId;
	/**
	 * Reference to the name of the paper.
	 */
	private String paperName;
	/**
	 * Reference to the Review form.
	 */
	private File reviewForm;
	
	/**
	 * Initializes a new review 
	 * @param score currently an int, but likely to change to type Scale
	 * @param comment String text of review comments
	 * @param reviewer_id Id of the reviewer
	 */
	public Review(String paper, final String score, File review, final String reviewer_id) {
		this.score = score;
		this.reviewerId = reviewer_id;
		reviewForm = new File("Test.txt");
		paperName = paper;
		
		
		try {
			uploadFile(review);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//reviewForm = review; //figured can initially submit in constructor and than use set method if revising review
	}
	
	/**
	 * no argument constructor placeholder
	 */
	public Review(){
		
	}
	
	/**
	 * Stores the file in the programs file system and sets myPaper to the upload 
	 * @pre paper is a valid file location and not null
	 * @post paper saved to file/id.file and myPaper = File(file/id.file)
	 * @param paper the file to be uploaded
	 * @throws IOException 
	 */
	public void uploadFile(final File paper) throws IOException {
		
		File upload = new File("files/" + paper.getName());
		FileChannel src;
		FileChannel dest;

		try {
			src = new FileInputStream(paper).getChannel();
			dest = new FileOutputStream(upload).getChannel();
			try{
			dest.transferFrom(src, 0, src.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			src.close();
			dest.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		reviewForm = upload;
	}
	public void setReviewForm(File form) {
		this.reviewForm = form;
	}
	
	public File getReviewForm() {
		return reviewForm;
	}
	
	public String getReviewerID() {
		return this.reviewerId;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public String getScore() {
		return this.score;
	}
}
