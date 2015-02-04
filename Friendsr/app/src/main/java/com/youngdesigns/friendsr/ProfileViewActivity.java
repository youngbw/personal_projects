package com.youngdesigns.friendsr;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

import com.youngdesigns.friendsr.userinfo.UserProfile;


public class ProfileViewActivity extends ActionBarActivity {

    private UserProfile currentProfile;
    private List<UserProfile> profileList;
    private UserProfile viewingProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
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

        viewingProfile = findProfile();

        final RatingBar rb = (RatingBar) findViewById(R.id.rb_1);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                currentProfile.addRating(viewingProfile.getUserID(), (int)rb.getRating());

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_view, menu);
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

//        viewingProfile = findProfile();
        setupProfileView();
    }


    public void setupProfileView() {


        //Rating bar setup
        RatingBar rb = (RatingBar) findViewById(R.id.rb_1);
        rb.setRating(currentProfile.getRating(viewingProfile.getUserID()));

        //text views setup
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_1);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;


        String[] bios = getResources().getStringArray(R.array.friend_details);
        String[] fullNames = getResources().getStringArray(R.array.friend_full_names);

        TextView nameView = new TextView(this);
        nameView.setText("Name: " + fullNames[findFriendIndex(fullNames)]);
        nameView.setTextSize(1, 20f);
        ll.addView(nameView, params);

        TextView ageView = new TextView(this);
        ageView.setText("Age: " + viewingProfile.getAge());
        ageView.setTextSize(1, 20f);
        ll.addView(ageView, params);

        TextView sexView = new TextView(this);
        sexView.setText("Sex: " + viewingProfile.getSex());
        sexView.setTextSize(1, 20f);
        ll.addView(sexView, params);


        TextView bioView = new TextView(this);
        bioView.setText("Biography: \n" + bios[findFriendIndex(bios)]);
        bioView.setTextSize(1, 18f);
        ll.addView(bioView, params);

        //ImageView Setup
        setupProfilePicture();



    }

    private void setupProfilePicture() {
        //ImageView Setup
        ImageView image = (ImageView) findViewById(R.id.iv_1);

        switch (viewingProfile.getName()) {

            case "Chandler":
                image.setImageResource(R.drawable.chandler);
                break;

            case "Ross":
                image.setImageResource(R.drawable.ross);
                break;

            case "Joey":
                image.setImageResource(R.drawable.joey);
                break;

            case "Rachel":
                image.setImageResource(R.drawable.rachel);
                break;

            case "Monica":
                image.setImageResource(R.drawable.monica);
                break;

            case "Phoebe":
                image.setImageResource(R.drawable.phoebe);
                break;

            default:
                return;
        }


    }

    private int findFriendIndex(String[] array) {
        int index = -1;
        String name = getIntent().getStringExtra("profile name");

        switch (name) {

            case "Chandler":
                index = 0;
                break;

            case "Ross":
                index = 5;
                break;

            case "Joey":
                index = 1;
                break;

            case "Rachel":
                index = 4;
                break;

            case "Monica":
                index = 2;
                break;

            case "Phoebe":
                index = 3;
                break;

            default:
                return index;
        }
        return index;
    }

    private UserProfile findProfile() {
        Intent callingIntent = getIntent();
        String extra = callingIntent.getStringExtra("profile name");
        for (UserProfile p: profileList) {
            if (p.getName().equals(extra)) {
                return p;
            }
        }
        return new UserProfile();
    }


}
