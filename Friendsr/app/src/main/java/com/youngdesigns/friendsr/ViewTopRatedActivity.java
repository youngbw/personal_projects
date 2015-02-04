package com.youngdesigns.friendsr;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.youngdesigns.friendsr.userinfo.UserProfile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ViewTopRatedActivity extends ActionBarActivity {


    private List<UserProfile> profileList;
    private UserProfile currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_top_rated);

        profileList = new ArrayList<UserProfile>();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_top_rated, menu);
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

        //setup method

    }

    public void setupView() {




    }

}
