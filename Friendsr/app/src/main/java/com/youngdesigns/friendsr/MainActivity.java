package com.youngdesigns.friendsr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.youngdesigns.friendsr.userinfo.UserProfile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private UserProfile currentProfile;
    private List<UserProfile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentProfile = new UserProfile(); //This will have to propogate, use file system to keep a user profile in tact, perhaps using create new profile
        //to make sure there is an actual user profile to use.
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput("profiles"));
            profileList = (ArrayList<UserProfile>)ois.readObject();

            //load current user
            ois = new ObjectInputStream(openFileInput("userProfile"));
            currentProfile = (UserProfile) ois.readObject();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (profileList == null || profileList.isEmpty()) {
            String[] array = getResources().getStringArray(R.array.friend_names);
            profileList = new ArrayList<>();

            UserProfile chandlerProfile = new UserProfile(array[0], "ChanandalerBong@gmail.com", 24, 'M', R.id.chandler);
            UserProfile rossProfile = new UserProfile(array[5], "OnaBreak@we.com", 26, 'M', R.id.ross);
            UserProfile monicaProfile = new UserProfile(array[2], "breezy_girl@gmail.com", 25, 'F', R.id.monica);
            UserProfile joeyProfile = new UserProfile(array[1], "joseph_stalin_4life.com", 27, 'M', R.id.joey);
            UserProfile phoebeProfile = new UserProfile(array[3], "Notursula@yahoo.com", 25, 'F', R.id.phoebe);
            UserProfile rachaelProfile = new UserProfile(array[4], "Daddys_Little_Girl@gmail.com", 24, 'F', R.id.rachel);


            profileList.add(chandlerProfile);
            profileList.add(rossProfile);
            profileList.add(monicaProfile);
            profileList.add(joeyProfile);
            profileList.add(phoebeProfile);
            profileList.add(rachaelProfile);
        }

        if (currentProfile == null || currentProfile.getUserID() == 0) {
            //Create new profile intent
            currentProfile = new UserProfile("Brent", "AThero23@gmail.com", 24, 'M', 100);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle inState) {
//        super.onRestoreInstanceState(inState);
//
//    }

    //START EVENT HANDLERS

    public void buttonClick(View view) {
        Intent myIntent;
        if (view.getId() == R.id.view_all_button) {

            myIntent = new Intent(this, ViewAllActivity.class);

        } else if (view.getId() == R.id.create_button) {

            myIntent = new Intent(this, CreateNewProfileActivity.class);

        } else {

            myIntent = new Intent(this, ViewTopRatedActivity.class);

        }
        startActivity(myIntent);



    }

    @Override
    public void onPause() {
        super.onPause();

        try {

            ObjectOutputStream os = new ObjectOutputStream(openFileOutput("profiles", MODE_PRIVATE));
            os.writeObject(profileList);

            os = new ObjectOutputStream(openFileOutput("userProfile", MODE_PRIVATE));
            os.writeObject(currentProfile);
            os.close();
        } catch (IOException e) {

            e.printStackTrace();

        }


    }


    @Override
    public void onResume() {
        super.onResume();
        try {

            ObjectInputStream ois = new ObjectInputStream(openFileInput("profiles"));
            profileList = (ArrayList<UserProfile>) ois.readObject();
            ois = new ObjectInputStream(openFileInput("userProfile"));
            currentProfile = (UserProfile) ois.readObject();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
