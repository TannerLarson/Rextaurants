package com.example.a246_team_project;

import java.util.List;

/**
 * A threaded class to fill the Restaurant array.  Simply starts a background thread, then lets
 * MainActivity know when the background thread is done.
 * @author Tanner Larson
 */
public class Network extends Thread {

    private MainActivity activity;
    private RestaurantFunctions rf = new RestaurantFunctions();
    private List<Restaurant> list;
    private List<String> cuisines;
    private Boolean needDetails;
    private Restaurant restaurant;
    private RestaurantSpecifics specificsActivity;


    /**
     * Default constructor for Network, designed for fillRestaurantArray
     * @param activity Used to sendthread results to MainActivity
     * @param listRestaurants Restaurant list to be added to
     * @param cuisines Cuisines to be used to make a new list of restaurants
     */
    public Network(MainActivity activity, List<Restaurant> listRestaurants, List<String> cuisines){
        this.activity = activity;
        this.list = listRestaurants;
        this.cuisines = cuisines;
        this.needDetails = false; // Make sure needDetails is false to make correct call
    }

    /**
     * Default constructor for Network, designed for getSpecifics
     * @param activity Used to send thread results to RestaurantSpecifics
     * @param needDetails True: calls getSpecifics, False: calls fillRestaurantArray (DON"T DO THIS
     * WITH THIS CONSTRUCTOR)
     * @param r Restaurant to get the specifics for
     */
    public Network(RestaurantSpecifics activity, Boolean needDetails, Restaurant r) {
        this.specificsActivity = activity;
        this.needDetails = needDetails;
        this.restaurant = r;
    }

    /**
     * If needDetails is false:
     * Simply fills the restaurant array with usable data, then calls MainActivity.gotRestaurants
     * to let MainActivity know that the restaurant array was filled
     *
     * If needDetails is true:
     * Gives restaurant more information, being the phone number and hours of operation.  This
     * should only be called from the RestaurantSpecifics class.  After the restaurant has the extra
     * data it will send the information to RestaurantSpecifics.gotSpecifics
     */
    @Override
    public void run() {
        if(!needDetails) {
            // Just fill the array
            list = rf.fillRestaurantArray(list, cuisines);
            activity.gotRestaurants(list);
        }
        else {
            // Need the details of a specific restaurant
            restaurant = rf.getSpecifics(restaurant);
            specificsActivity.gotSpecifics(restaurant);
        }
    }
}
