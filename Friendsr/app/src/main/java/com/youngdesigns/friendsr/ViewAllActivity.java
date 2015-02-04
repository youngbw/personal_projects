package com.youngdesigns.friendsr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.youngdesigns.friendsr.userinfo.UserProfile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ViewAllActivity extends ActionBarActivity {

    private static final int REQ_CODE = 123;

    private UserProfile currentProfile;
    private List<UserProfile> profileList;


    public void profileClicked(View view) {

        Intent myIntent = new Intent(this, ProfileViewActivity.class);
        switch(view.getId()) {

            case R.id.chandler:
                myIntent.putExtra("profile name", "Chandler");
                break;

            case R.id.monica:
                myIntent.putExtra("profile name", "Monica");
                break;

            case R.id.ross:
                myIntent.putExtra("profile name", "Ross");
                break;


            case R.id.rachel:
                myIntent.putExtra("profile name", "Rachel");
                break;


            case R.id.phoebe:
                myIntent.putExtra("profile name", "Phoebe");
                break;


            case R.id.joey:
                myIntent.putExtra("profile name", "Joey");
                break;

            default:
                myIntent = null;
                break;

        }

        if (myIntent != null) startActivity(myIntent);


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        if (requestCode == REQ_CODE) {
//            profileList = (ArrayList<UserProfile>) intent.getSerializableExtra("profiles");
//        }
//        setupProfileViews();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        profileList = new ArrayList<>();

//        Intent myIntent = getIntent();
//        String[] array = getResources().getStringArray(R.array.friend_names);

        try {

            ObjectInputStream ois = new ObjectInputStream(openFileInput("profiles"));
            profileList = (ArrayList<UserProfile>) ois.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        if (currentProfile == null || currentProfile.getUserID() == 0) {
//            //Create new profile intent
//            currentProfile = new UserProfile("Brent", "AThero23@gmail.com", 24, 'M', 100);
//        }

//        setupProfileViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_all, menu);
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

        setupProfileViews();
    }

    public void setupProfileViews() {
        for (UserProfile profile: profileList) {
//            UserProfile profile = (UserProfile) myIntent.getSerializableExtra(array[i]);
//            profileList.add(profile);

            RatingBar bar;
            TextView tv;
            switch (profile.getName()) {

                case "Chandler":
                    bar = (RatingBar) findViewById(R.id.chandler_rating);
                    bar.setRating(currentProfile.getRating(profile.getUserID()));
                    tv = (TextView) findViewById(R.id.name_field_1);
                    tv.setText(tv.getText() + profile.getName());
                    tv = (TextView) findViewById(R.id.age_field_1);
                    tv.setText(tv.getText() + "" + profile.getAge());
                    tv = (TextView) findViewById(R.id.sex_field_1);
                    tv.setText(tv.getText() + "" + profile.getSex());
                    break;

                case "Ross":
                    bar = (RatingBar) findViewById(R.id.ross_rating);
                    bar.setRating(currentProfile.getRating(profile.getUserID()));
                    tv = (TextView) findViewById(R.id.name_field_5);
                    tv.setText(tv.getText() + profile.getName());
                    tv = (TextView) findViewById(R.id.age_field_5);
                    tv.setText(tv.getText() + "" + profile.getAge());
                    tv = (TextView) findViewById(R.id.sex_field_5);
                    tv.setText(tv.getText() + "" + profile.getSex());
                    break;


                case "Joey":
                    bar = (RatingBar) findViewById(R.id.joey_rating);
                    bar.setRating(currentProfile.getRating(profile.getUserID()));
                    tv = (TextView) findViewById(R.id.name_field_3);
                    tv.setText(tv.getText() + profile.getName());
                    tv = (TextView) findViewById(R.id.age_field_3);
                    tv.setText(tv.getText() + "" + profile.getAge());
                    tv = (TextView) findViewById(R.id.sex_field_3);
                    tv.setText(tv.getText() + "" + profile.getSex());
                    break;


                case "Monica":
                    bar = (RatingBar) findViewById(R.id.monica_rating);
                    bar.setRating(currentProfile.getRating(profile.getUserID()));
                    tv = (TextView) findViewById(R.id.name_field_2);
                    tv.setText(tv.getText() + profile.getName());
                    tv = (TextView) findViewById(R.id.age_field_2);
                    tv.setText(tv.getText() + "" + profile.getAge());
                    tv = (TextView) findViewById(R.id.sex_field_2);
                    tv.setText(tv.getText() + "" + profile.getSex());
                    break;


                case "Rachel":
                    bar = (RatingBar) findViewById(R.id.rachel_rating);
                    bar.setRating(currentProfile.getRating(profile.getUserID()));
                    tv = (TextView) findViewById(R.id.name_field_4);
                    tv.setText(tv.getText() + profile.getName());
                    tv = (TextView) findViewById(R.id.age_field_4);
                    tv.setText(tv.getText() + "" + profile.getAge());
                    tv = (TextView) findViewById(R.id.sex_field_4);
                    tv.setText(tv.getText() + "" + profile.getSex());
                    break;


                case "Phoebe":
                    bar = (RatingBar) findViewById(R.id.phoebe_rating);
                    bar.setRating(currentProfile.getRating(profile.getUserID()));
                    tv = (TextView) findViewById(R.id.name_field_6);
                    tv.setText(tv.getText() + profile.getName());
                    tv = (TextView) findViewById(R.id.age_field_6);
                    tv.setText(tv.getText() + "" + profile.getAge());
                    tv = (TextView) findViewById(R.id.sex_field_6);
                    tv.setText(tv.getText() + "" + profile.getSex());
                    break;

                default:

            }

        }
    }
}
