package com.example.a246_team_project;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * A class that will adapt the Restaurant class to be used as a child object in the expandable
 * recycler view. It has a reference to the main restaurant but will not be a parent object and
 * avoid casting a parent object to a child object
 * @author Rachel Jones
 */
public class RestaurantAdapterChild implements Serializable{
    @SerializedName("result")
    private Restaurant restaurant;

    /**
     * A non-default constructor that takes a restaurant and sets the restaurant member variable
     * @param restaurant
     */
    public RestaurantAdapterChild(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Will return a reference to the main restaurant
     * @return restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * A setter for the restaurant object
     * @param r
     */
    public void setRestaurant(Restaurant r) { this.restaurant = r; }
}

