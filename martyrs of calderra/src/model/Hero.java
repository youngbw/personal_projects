package model;

import java.util.HashMap;

public interface Hero extends java.io.Serializable {

	
	String getImageSource();
	String getFullName();
	HashMap<String, Integer> getAttributes();
	
}
