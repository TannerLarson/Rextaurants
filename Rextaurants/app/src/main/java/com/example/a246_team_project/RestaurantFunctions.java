package com.example.a246_team_project;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


/**
 * This class is used to manipulate restaurants.  It contains no member data, only functions.
 * Originally these functions were in the restaurant class, but was later separated due to clarity
 * concerns
 * @author Tanner Larson
 */
public class RestaurantFunctions {

    /**
     * Uses an API call to get a list of restaurants based on a  food type
     * @param foodType String that defines the food type, ie: "Mexican", "American", ect.
     * @return List of restaurants that the Google place text search API gives
     */
    public List<Restaurant> get_details(String foodType) {
        // Make the correct URL
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + foodType
                + "+food+in+rexburg+idaho&key=AIzaSyCO_HU4WONxYLCNMWQd8h81JVUlQdwQIcU";

        Gson g = new Gson();

        // Call get_raw_api and deserialize it
        APIData data = g.fromJson(get_raw_api(url), APIData.class);

        // Remove the data list
        ArrayList <Restaurant> restaurants = new ArrayList<>(data.getRestaurants());
        return restaurants;
    }


    /**
     * Uses an API call to get the specifics of a restaurant based on a restaurant parameter
     * @param rIn Restaurant to get specifics for
     * @return Restaurant with specifics
     */
    public Restaurant getSpecifics(Restaurant rIn) {
        String url ="https://maps.googleapis.com/maps/api/place/details/json?placeid=" +
                rIn.getPlace_id() + "&fields=name,formatted_phone_number,opening_hours&" +
                "key=AIzaSyCO_HU4WONxYLCNMWQd8h81JVUlQdwQIcU";

        Gson g = new Gson();
        RestaurantAdapterChild d = g.fromJson(get_raw_api(url), RestaurantAdapterChild.class);
        return d.getRestaurant();
    }

    /**
     * Gets a URL and returns the string of data that the URL makes
     * @param url URL to fetch information from
     * @return String of information returned
     */
    public String get_raw_api(String url) {
        String charset = "UTF-8";
        String param1 = " ";
        String param2 = " ";

        String query = null;
        try {
            query = String.format(" ",
                    URLEncoder.encode(param1, charset),
                    URLEncoder.encode(param2, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URLConnection connection = null;
        try {
            connection = new URL(url + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a list of restaurants based on cuisine, fills the data in each restaurant, adds the
     * created list to given list, removes any duplicates, returns combined list
     * @param list List of restaurants you want to add to
     * @param cuisines List of cuisine strings you want to add
     * @return original list combined with created list of restaurants without duplicates
     */
    public List<Restaurant> fillRestaurantArray(List<Restaurant> list, List<String> cuisines) {
        // add all the restaurants
        for(int i = 0; i < cuisines.size(); i++) {
            list.addAll(get_details(cuisines.get(i)));
        }

        // Get rid of duplicates
        List<Restaurant> temp = new ArrayList<>();
        HashSet<Restaurant> set = new HashSet<>();
        set.addAll(list);
        temp.addAll(set);

        // Remove any places that doesn't have opening hours
        for(int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getHours().getOpen_now() == null) {
                temp.remove(i);
                i--;
            }
        }
        return temp;
    }
}
