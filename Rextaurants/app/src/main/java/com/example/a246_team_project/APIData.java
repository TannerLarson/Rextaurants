package com.example.a246_team_project;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is only used to deserialize API data from the Google API text search.  It has the data
 * to get more results from the text search, but that isn't used in this application
 * @author Tanner Larson
 */
public class APIData {
    private String next_page_token;
    @SerializedName("results")
    private List<Restaurant> restaurants = new ArrayList<>();

    /**
     * Gets next page token
     * If used with the correct URL, it will get the next page of results from the Google API text
     * search
     * @return String of next_page_token
     */
    public String getNext_page_token() { return next_page_token; }

    /**
     * Sets next_page_token
     * Used for deserialization
     * @param next_page_token set next_page_token equal to this
     */
    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    /**
     * Gets restaurants
     * @return Current data in restaurants
     */
    public List<Restaurant> getRestaurants() { return restaurants; }

    /**
     * Sets restaurants
     * @param restaurants Set restaurant equal to this
     */
    public void setList(List<Restaurant> restaurants) { this.restaurants = restaurants; }
}
