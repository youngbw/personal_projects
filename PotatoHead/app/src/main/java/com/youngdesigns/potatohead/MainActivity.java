package com.youngdesigns.potatohead;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {


    public void boxChecked(View view) {
        ImageView image;
        switch (view.getId()) {

            case R.id.checkarms:
                image = (ImageView) findViewById(R.id.arm_image);
                break;

            case R.id.checkears:
                image = (ImageView) findViewById(R.id.ears_image);
                break;

            case R.id.checkeyebrows:
                image = (ImageView) findViewById(R.id.eyebrows_image);
                break;

            case R.id.checkeyes:
                image = (ImageView) findViewById(R.id.eyes_image);
                break;

            case R.id.checkglasses:
                image = (ImageView) findViewById(R.id.glasses_image);
                break;

            case R.id.checknose:
                image = (ImageView) findViewById(R.id.nose_image);
                break;

            case R.id.checkhat:
                image = (ImageView) findViewById(R.id.hat_image);
                break;

            case R.id.checkmustache:
                image = (ImageView) findViewById(R.id.mustache_image);
                break;

            case R.id.checkshoes:
                image = (ImageView) findViewById(R.id.shoe_image);
                break;

            case R.id.checkmouth:
                image = (ImageView) findViewById(R.id.mouth_image);
                break;

            default:
                return;

        }
        CheckBox box = (CheckBox) findViewById(view.getId());
        if (box.isChecked()) {
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.INVISIBLE);
        }
    }



    // **********************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
