package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class Scale implements Serializable {
	
	//private TreeMap<Integer, ArrayList<Scale>> scales;	
	//private ArrayList<Scale> aScale;	
	/**
	 * Reference to the number of scales.
	 */
	public static final int NUM_SCALES = 3;
	/**
	 * A TreeMap of possible scales.
	 */
	private TreeMap<Integer, ArrayList<String>> scales;
	
	/**
	 * Default constructor for the Scale class.
	 */
	public Scale() {
		scales = new TreeMap<Integer, ArrayList<String>>();
		loadScales();		
	}
	
	/**
	 * Returns an arraylist representation of a Scale.
	 * @param scaleID
	 * @return
	 */
	public ArrayList<String> chooseScale(int scaleID){
		return scales.get(scaleID);
	}
	
	
	/**
	 * This method is called by the constructor to load all the 
	 * possible scales that can be used in a conference.
	 */
	private void loadScales() {
		//get from input file???
		
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> list3 = new ArrayList<String>();
		
		int count = 1;
		//scale 1-5
		while(count <= 5){
			list.add("" + count);
			count++;
		}
		
		scales.put(0, list);
				
		count = 1;
		
		//scale 1-10
		while(count <= 10){
			list2.add("" + count);
			count++;
		}
		
		scales.put(1, list2);
		
		
		list3.add("strong accept");
		list3.add("accept");
		list3.add("neutral");
		list3.add("reject");
		list3.add("strong reject");
		
		scales.put(2, list3);	
	}
}
