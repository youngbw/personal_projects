package GolferData;

/**
 * Golfer.java
 * A class representing a Golfer object, with first and last name, email address,
 * phone number, and team rank as identifiers.
 * <pre>
 *    Project: Golfers Database
 *    Platform: jdk 1.6.0_14; NetBeans IDE 6.8; Mac OSx
 *    Course: CS 143
 *    Hours: 1 hours
 *    Created on May 12, 2013, 10:00:00 PM
 * </pre>
 *
 * @author	brent.young@go.shoreline.edu
 * @version 	%1% %3%
 * @see         AddEditFormJDialog
 * @see         Golfers
 * @see         Validation
 */
public class Golfer {
    
    /**
     * First name of the golfer.
     * @see #lastName
     */
    private String firstName;
    
    /**
     * Last name of the Golfer.
     * @see #firstName
     */
    private String lastName;
    
    /**
     * Email address of the Golfer.
     * @see #phone
     */
    private String email;
    
    /**
     * Phone number of the Golfer.
     * @see #email
     */
    private String phone;
    
    /**
     * Team rank of the Golfer.
     * @see #email
     * @see #phone
     */
    private int level;
    
    /**
     * Absolute path of image for player portrait.
     * @see #firstName
     * @see #level
     */
    private String icon;
    
    /**
     * Default Constructor for Golfer, passing null values to the overloaded
     * constructor.
     * @see #Golfer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String)  
     */
    public Golfer() {
        this("", "", "", "", 0, "");
    }
    
    /**
     * Overloaded constructor, sets golfer's informational values.
     * @param firstName first name of the Golfer
     * @param lastName last name of the Golfer
     * @param email email address of the Golfer
     * @param phone phone number of the Golfer
     * @param level team rank of the Golfer
     * @param icon String representing file.absolutePath of player portrait
     * @see Golfers.#setIcon(java.lang.String) 
     * @see #firstName
     * @see #Golfer() 
     */
    public Golfer(String firsName, String lastName, String email, String phone,
            int level, String icon) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.level = level;
        this.icon = icon;
    }

    /**
     * Grabs the first name of the Golfer.
     * @return first name of the golfer object.
     * @see #firstName
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Grabs the last name of the Golfer.
     * @return last name of the golfer object.
     * @see #lastName
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Grabs the email address of the Golfer.
     * @return email address of the golfer object.
     * @see #email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Grabs the phone number of the Golfer.
     * @return phone number of the golfer object.
     * @see #phone
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Grabs the team rank of the Golfer.
     * @return team rank of the golfer object.
     * @see #level
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Grabs the absolute path of the icon file in String form.
     * @return String representing file's absolute path
     * @see #setIcon(java.lang.String) 
     */
    public String getIcon() {
        return icon;
    }
    
    /**
     * Sets the first name of the Golfer.
     * @param name set as first name of Golfer.
     * @see #firstName
     */
    public void setFirstName(String name) {
        this.firstName = name;
    }
    
    /**
     * Sets the last name of the Golfer.
     * @param name set as last name of Golfer.
     * @see #lastName
     */
    public void setLastName(String name) {
        this.lastName = name;
    }
    
    /**
     * Sets the email address of the Golfer.
     * @param email set as email address of Golfer.
     * @see #email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Sets the phone number of the Golfer.
     * @param phone set as phone number of Golfer.
     * @see #phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * Sets the team rank of the Golfer.
     * @param level set as team rank of Golfer.
     * @see #level
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
    /**
     * Sets the absolute path of the icon filename.
     * @param icon String representing file's absolute path
     * @see #getIcon() 
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    /**
     * Overridden toString method laying out the Golfer's attributes.
     * @return String containing golfer attributes.
     * @see #firstName
     * @see #level
     */
    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName 
                + "\nEmail Address:" + email 
                + "\nPhone Number: " + phone + "\nTeam Rank: " + level
                + "\nIcon Path: " + icon;
    }
    
    /**
     * Determines if a Golfer is equal in all ways to another Golfer.
     * @param golfer golfer to be compared to
     * @return boolean true if golfers are equal to each other in all aspects
     * @see #firstName
     * @see #lastName
     * @see #email
     * @see #phone
     * @see #level
     */
    public boolean equals(Golfer golfer) {
        if (this.getFirstName().equalsIgnoreCase(golfer.getFirstName()) &&
                this.getLastName().equalsIgnoreCase(golfer.getLastName()) &&
                Validation.numOnly(this.getPhone()) == Validation.numOnly(golfer.getPhone())) {
            return true;
        } else {
            return false;
        }
    }
}
