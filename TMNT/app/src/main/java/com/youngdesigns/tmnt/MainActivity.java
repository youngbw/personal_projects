package com.youngdesigns.tmnt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SPINNER SETUP
        Spinner spin = (Spinner) findViewById(R.id.turtle_chooser);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> spin, View view, int i, long id) {
                TextView result = (TextView) findViewById(R.id.result);
                result.setText("You chose: " + spin.getSelectedItem());
                pickTurtle();
            }

            public void onNothingSelected(AdapterView<?> parent) {} //empty

        });

    }


    /*
     * This method is called when the user chooses one of the turtle radio buttons.
     * In this code we set which turtle image is visible on the screen in the ImageView.
     */
    public void pickTurtle() {
        ImageView img = (ImageView) findViewById(R.id.turtle_image);
        Spinner spin = (Spinner) findViewById(R.id.turtle_chooser);
        TextView view = (TextView) findViewById(R.id.info);
        if (spin.getSelectedItem().equals("Leonardo")) {
            img.setImageResource(R.drawable.tmntleo);
            view.setText(R.string.leo_text);
        } else if (spin.getSelectedItem().equals("Michelangelo")) {
            img.setImageResource(R.drawable.tmntmike);
            view.setText(R.string.mike_text);
        } else if (spin.getSelectedItem().equals("Donatello")) {
            img.setImageResource(R.drawable.tmntdon);
            view.setText(R.string.don_text);
        } else if (spin.getSelectedItem().equals("Raphael")) {
            img.setImageResource(R.drawable.tmntraph);
            view.setText(R.string.raph_text);
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
}
