package com.example.a246_team_project;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RextaurantsTests {

    // Global variable because most of the tests use the RestaurantFunctions class
    RestaurantFunctions f = new RestaurantFunctions();


    @Test
    public void get_raw_api_test() {
        String data = f.get_raw_api("https://maps.googleapis.com/maps/api/place/" +
                "findplacefromtext/json?input=applebees%20rexburg%20Idaho&inputtype=textquery" +
                "&fields=name,place_id&key=AIzaSyCO_HU4WONxYLCNMWQd8h81JVUlQdwQIcU");
        assertTrue(data.contains("Applebee's"));
    }

    @Test
    public void fillRestaurantArray_test() {
        List <Restaurant> r = new ArrayList<>();
        List <String> s = new ArrayList<>();
        s.add("Fast");
        s.add("Mexican");
        r = f.fillRestaurantArray(r, s);
        for(int i = 0; i < r.size(); i++) {
            System.out.println(r.get(i).getName());
        }

        HashSet<Restaurant> set = new HashSet<>();
        set.addAll(r);

        assertTrue(r.size() == set.size());
    }


    @Test
    public void get_restaurant_new_test() {
        List <Restaurant> restaurants = f.get_details("fast");
        assertTrue(restaurants.get(0).getName().contains("Arby's"));
    }

    @Test
    public void get_specifics_test() {
        List <Restaurant> r = new ArrayList<>();
        List <String> s = new ArrayList<>();
        s.add("Fast");
        s.add("Mexican");
        r = f.fillRestaurantArray(r, s);
        for(int i = 0; i < r.size(); i++) {
            System.out.println(r.get(i).getName());
        }

        Restaurant x = f.getSpecifics(r.get(0));
        assertTrue(x.getFormatted_phone_number().contains("(208) 497-0480"));
        assertTrue(x.getHours().getFormatted_hours().get(0).contains("Monday"));
    }
}