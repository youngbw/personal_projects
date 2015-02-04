package com.youngdesigns.friendsr.userinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BrentYoung on 1/29/15.
 */
public class UserProfile implements Serializable {

    public static int globalID;

    private String name;
    private String email;
    private int age;
    private char sex;
    private int userID;
    private List<String> srcList;
    private HashMap<Integer, Integer> ratings;

    public UserProfile() {
        this("No Name");
    }


    public UserProfile(String name) {
        this(name, "No Email", 0, '?', 0);
    }

    public UserProfile(String name, String email, int age, char sex, int id) {
        if (validName(name)) this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
        srcList = new ArrayList<>();
        ratings = new HashMap<>();


        if (id == 0) this.userID = ++globalID;
        else userID = id;


    }


    // BEGIN VALIDATION METHODS

    public boolean validName(String theName) {
        for (int i = 0; i < theName.length(); i++) {
            Character c = theName.charAt(i);
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }



    // END VALIDATION METHODS


    public String getName() {
        return this.name;
    }

    public void setName(String name, int requiredCode) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email, int requiredCode) {
        this.email = email;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age, int requiredCode) {
        this.age = age;
    }

    public Character getSex() {
        return this.sex;
    }

    public void setSex(Character sex, int requiredCode) {
        this.sex = sex;
    }

    public void addRating(int ratedUserID, int stars) {

        if (ratings.containsKey(ratedUserID)) ratings.remove(ratedUserID);
        ratings.put(ratedUserID, stars);

    }

    public int getRating(int targetUserID) {
        return (ratings != null && ratings.containsKey(targetUserID)) ? ratings.get(targetUserID) : 0;
    }

    public int getUserID() {
        return this.userID;
    }

}
