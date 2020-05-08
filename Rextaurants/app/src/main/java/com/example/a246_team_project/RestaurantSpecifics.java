package com.example.a246_team_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * An activity to display the restaurant details. We chose to display the details in this activity
 * because it requires an extra API call to get hours and phone number, whereas the data displayed
 * in the expandable recycler view can be obtained for many restaurants on one API call. This class
 * will get the restaurant object, make an API call to get details, then display them.
 * @author Rachel Jones, Tanner Larson
 */
public class RestaurantSpecifics extends AppCompatActivity implements Serializable {

    private Restaurant restaurant;

    /**
     * When this activity is created it will get the intent and get the serializable extra from
     * main. This is how we get the restaurant object needed for the API data call, getSpecifics.
     * @param savedInstanceState Required for the onCreate function
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_specifics);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Restaurant r = (Restaurant)intent.getSerializableExtra("Restaurant");
        restaurant = r;

        getSpecifics();
    }

    /**
     * Starts a background thread to get the specifics of a defined restaurant
     */
    protected void getSpecifics() {
        Network n = new Network(this, true, restaurant);
        n.start();
    }

    /**
     * This function is called from the Network class.  It will start when the Network thread is
     * finished, and uses the list of restaurants created in the Network thread. It will then
     * set the proper phone number and hours of operation
     * @param r Restaurant with specifics received from Network thread
     */
    protected void gotSpecifics(Restaurant r) {
        this.runOnUiThread(()-> {
         //   Toast.makeText(this,
           //         "Got Specifics", Toast.LENGTH_SHORT).show();
            // Capture the layout's TextView and set the proper restaurant
            TextView pView = findViewById(R.id.phone_number);
            TextView hView = findViewById(R.id.hours);
            pView.setText("Phone number for " + r.getName() + ": " + r.getFormatted_phone_number());
            hView.setText("Hours:\n" + r.getHours().getFormatted_hours().get(0) + "\n" //mond
                    + r.getHours().getFormatted_hours().get(1) + "\n" //tues
                    + r.getHours().getFormatted_hours().get(2) + "\n" //wed
                    + r.getHours().getFormatted_hours().get(3) + "\n" //thurs
                    + r.getHours().getFormatted_hours().get(4) + "\n" //fri
                    + r.getHours().getFormatted_hours().get(5) + "\n" //sat
                    + r.getHours().getFormatted_hours().get(6)); //sun
        });
    }
}
