package com.example.a246_team_project;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.ToggleButton;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This application is designed to show the user all the restaurants in Rexburg Idaho based on
 * cuisines chosen. The application creates a list based on Google APIs and on cuisine(s) desired
 * and displays said restaurants. The restaurants are ordered in this manner: All closed
 * restaurants will be displayed last and will be displayed in red text, while open restaurants
 * will be displayed in green text. Both open and closed restaurants will be displayed in order of
 * distance from the phone.
 *
 * When a specific restaurant is selected, a submenu appears under the restaurant containing
 * information concerning the restaurant, including a button that will sent you to Google Maps for
 * directions to said restaurant
 *
 * @author Kaylee Hartzog, Rachel Jones, Tanner Larson
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String TAG = "Main Activity: ";
    private String favorites;
    private List<Restaurant> listRestaurants;
    private ArrayList<Restaurant> listFavRestaurants = new ArrayList<>();
    private List<Restaurant> favoriteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RestaurantExpandableRecyclerAdapter rAdapter;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation = new Location("l");
    private Task locationTask;

    /**
     * Creates the main activity. Will first get the devices current location and set views. Then
     * getRestaurants will be called to fill the listRestaurants which will then populate
     * expandable recycler view.
     * @param savedInstanceState Required for the onCreate function
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gets current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        setContentView(R.layout.activity_main);

        //this is where we set the expandable recycler view
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager rLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listRestaurants = new ArrayList<>();
        List <String> cuisineList = new ArrayList<>(); // Replace with moodFor data
        cuisineList.add("Mexican");
        getRestaurants(cuisineList);
    }

    /**
     * Function to generate child Restaurants for the expandable recycler view
     * @return parentObjects list - Each parent object has a child object restaurant
     */
    private ArrayList<ParentObject> generateRestaurants() {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        //Log.e("Rachel", "Restaurant size" + listRestaurants.size());
        for (int i = 0; i < listRestaurants.size(); i++) {
            List<Object> dataList = new ArrayList<Object>();
            dataList.add(new RestaurantAdapterChild(listRestaurants.get(i)));
            listRestaurants.get(i).setChildObjectList(dataList);
            parentObjects.add(listRestaurants.get(i));
        }
        return parentObjects;
    }

    /**
     * Starts the Network background thread
     * @param cuisines List of cuisine strings that will be used to create a list of restaurants
     */
    protected void getRestaurants(List<String> cuisines) {
        Network n = new Network(this, listRestaurants, cuisines);
        n.start();
    }

    /**
     * This function is called from the Network class.  It will start when the Network thread is
     * finished, and uses the list of restaurants created in the Network thread
     * @param r List of restaurants sent from Network thread that has all the details needed
     */
    protected void gotRestaurants(List<Restaurant> r) {
        this.runOnUiThread(()-> {
           // Toast.makeText(MainActivity.this,
             //       "Restaurant Array Filled", Toast.LENGTH_SHORT).show();
            listRestaurants = sortRestaurants(r);

            if(rAdapter == null) {
                rAdapter = new RestaurantExpandableRecyclerAdapter(this,
                        generateRestaurants());
                recyclerView.setAdapter(rAdapter);
            }
            else {
                rAdapter.notifyDataSetChanged(); // Only works if you use .add
            }
        });

    }

    /**
     * This function is called when the app returns to the main Activity from a different activity, and we
     * don't want the onCreate function to be called again.
     * @param intent
     */
    protected void onNewIntent (Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        Bundle extras = getIntent().getExtras();
        ArrayList<String> moodsArray = extras.getStringArrayList("moodArray");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        setContentView(R.layout.activity_main);

        //this is where we set the expandable recycler view
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager rLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listRestaurants = new ArrayList<>();
        List <String> cuisineList = new ArrayList<>(); // Replace with moodFor data
        for (int i = 0; i < moodsArray.size(); i++){
            cuisineList.add(moodsArray.get(i));
            //Log.d("KAYNAY", moodsArray.get(i));
        }
        rAdapter = null;
        getRestaurants(cuisineList);
    }

    /**
     * This function is called when the user clicks on the What Are You In The Mood For? button.
     * It take the user to the MoodFor activity.
     * @param view - What was used for the view but it is not used here
     */
    public void goToMoodFor (View view){
        Intent intent = new Intent(this, MoodFor.class);
        startActivity(intent);
    }

    /**
     * Go to specifics connects the the "get details" button. It will take the user to a new
     * activity that shows the restaurant details
     * @param r We must know which restaurant to fetch its details
     */
    public void goToSpecifics (Restaurant r){
        Intent intent = new Intent(this, RestaurantSpecifics.class);
        intent.putExtra("Restaurant", r);
        startActivity(intent);
    }

    /**
     * This function is called when the user clicks on the Favorites button. It replaces the
     * recyler view with a list of all the restaurants that were marked as favorites.
     * @param view - What was used for the view but it is not used here
     */
    //update the main list to favorite restaurants
    public void getFavorites (View view){
        Log.d("in getFavs", "in get favs");
        saveData();

//        SharedPreferences sharedPreferences = getSharedPreferences("preferences ", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("Restaurant List", null);
//        Type type = new TypeToken<ArrayList<Restaurant>>() {}.getType();
//        listFavRestaurants = gson.fromJson(json, type);
//
//        if (listFavRestaurants == null){
//            listFavRestaurants = new ArrayList<>();
//        }
//        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        String restoredText = prefs.getString("savedFavorites", null);
//        favorites = null;
//        if (restoredText != null) {
//            favorites = prefs.getString("savedFavorites", "No name defined");
//        }
//        Log.d(TAG, "Received intent with " + favorites);
        //this is where we'll choose to display only the favorite restaurants
    }

    /**
     * A button to search the list of restaurants for whatever is entered into the search bar.
     * If the searched restaurant is in the list, the list will populate to only show that
     * restaurant. If the searched restaurant is not in the list, then a helpful toast will appear.
     * @param view - What was used for the view but it is not used here
     */
    public void searchRestaurants (View view){
        Log.d(TAG, "In searchview button"); //current search
        SearchView searching = findViewById(R.id.search_view);
        String search = searching.getQuery().toString();
        List<Restaurant> tempList = new ArrayList<>();
        Log.d(TAG, "size: " + listRestaurants.size());
        for (int i = 0; i < listRestaurants.size(); i++) {
            if (new String(search).equals(listRestaurants.get(i).getName()) ||
                    new String(search).equals(listRestaurants.get(i).getName().toLowerCase())) {
                tempList.add(listRestaurants.get(i));
                Log.d(TAG, "listRestaurant: " + tempList.get(0).getName());
                break;
            }
        }
        if (tempList.size() != 0) {
            listRestaurants.clear();
            listRestaurants.add(tempList.get(0));
            tempList.clear();
            Log.d(TAG, "List Restaurant" + listRestaurants.get(0).getName());
            rAdapter = new RestaurantExpandableRecyclerAdapter(this, generateRestaurants());
            recyclerView.setAdapter(rAdapter);
        } else {
           // Toast.makeText(MainActivity.this, "Error: Restaurant does not exist in this list. " +
                         //   "Check spelling and search again", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "After the new list has been updated");
    }

    /**
     * Will clear the search bar and repopulate the list to the default "American" cuisine
     * @param view What was used for the view but it is not used here
     */
    public void clearSearch(View view) {
        rAdapter = null; //must set to null so the data set will be updated
        listRestaurants = new ArrayList<>();
        List <String> cuisineList = new ArrayList<>(); // Replace with moodFor data
        cuisineList.add("American");
        getRestaurants(cuisineList);
    }

    /**
     * When the app is in a paused state it will save the specified favorite restaurants
     */
    @Override
    public void onPause() {
        super.onPause();
        // Get the Intent that started this activity and extract the string
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
       // EditText saved = findViewById(R.id.editText);
       // String message = saved.getText().toString();
       // SharedPreferences.Editor prefEditor = getSharedPreferences(MY_PREFS_NAME,
       //         MODE_PRIVATE).edit();
       // prefEditor.putString("savedFavorites", message);
        //prefEditor.putString("savedFavorites", favorites);
       // prefEditor.apply();
    }


    /**
     * Calculates the distance between current location and location defined in parameters (We will
     * call it point A)
     * @param lat Latitude of point A
     * @param lon Longitude of point A
     * @return Distance between current location and point A as the crow flies
     */
    public double getDistance ( double lat, double lon){
        double latDistance = currentLocation.getLatitude() - lat;
        double lonDistance = currentLocation.getLongitude() - lon;
        return Math.sqrt(Math.pow(latDistance, 2) + Math.pow(lonDistance, 2));
    }

    /**
     * Updates the location variable in MainActivity.  Uses Google Play services to get the last
     * known location.  If permission is not yet granted, asks for permission and tries again
     */
    public void getLocation() {
        // Check if I have permission to access phone location, if not request permission
        if ( ContextCompat.checkSelfPermission( this,
                android.Manifest.permission.ACCESS_FINE_LOCATION ) !=
                PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},PackageManager.PERMISSION_GRANTED);
        }
        // I have permission to access phone location
        else {
            System.out.println("THIS DID A THING");
            locationTask = fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if(location == null) {
                            Log.d("LOCATION", "Location is null");
                        }
                        currentLocation = location;
                    });
        }
    }

    /**
     * Sends the user to either the Google Maps app or the internet to get the directions to the
     * restaurant passed using www.google.com/maps
     * @param r Restaurant to get directions to
     */
    public void getDirections(Restaurant r) {

        System.out.println("Return object = " + locationTask.getResult().getClass());

        String link = "https://www.google.com/maps/dir/?api=1&origin=" +
                currentLocation.getLatitude() + "," + currentLocation.getLongitude() +
                "&destination=" + r.getName() + "&destination_place+id=" + r.getPlace_id() +
                "&travelmode=driving";

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    /**
     * Sorts restaurants by if it's closed first, then by distance from user
     * @param list List to sort
     * @return List of sorted restaurants
     */
    public List<Restaurant> sortRestaurants (List <Restaurant> list) {
        List<Restaurant> listSorted = new ArrayList<>();
        List<Restaurant> closed = new ArrayList<>();
        listSorted.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getHours() == null) {
                closed.add(list.get(i));
            } else
             if (list.get(i).getHours().getOpen_now()) {
                // The restaurant is open
                listSorted.add(list.get(i));
            } else {
                // The restaurant is closed
                closed.add(list.get(i));
            }
        }

        // Sort the open restaurants
        listSorted.sort((o1, o2) -> {
            if(getDistance(o1.getGeometry().getLocation().getLat()
                    , o1.getGeometry().getLocation().getLon()) <=
                    getDistance(o2.getGeometry().getLocation().getLat()
                            , o2.getGeometry().getLocation().getLon())) {
                return -1;
            }
            else {
                return 1;
            }
        });

        // Sort the closed restaurants
        closed.sort((o1, o2) -> {
            if(getDistance(o1.getGeometry().getLocation().getLat()
                    , o1.getGeometry().getLocation().getLon()) <=
                    getDistance(o2.getGeometry().getLocation().getLat()
                            , o2.getGeometry().getLocation().getLon())) {
                return -1;
            }
            else {
                return 1;
            }
        });

        // Append the closed restaurants to the bottom of the list
        listSorted.addAll(closed);

        return listSorted;
    }

    /**
     * This function is called when a star button is clicked. The restaurant where the star is clicked
     * is sent here and is either added or removed from the list with the restaruants marked as favorites.
     * @param view - What was used for the view but it is not used here
     * @param r - Restaurant to check and see if its star is checked or not.
     */
    public void onStarClicked(View view, Restaurant r) {
        boolean checked = ((ToggleButton) view).isChecked();

        if (checked == true){
            listFavRestaurants.add(r);
            //Toast.makeText(MainActivity.this,
                    //"Restaurant added to favorites", Toast.LENGTH_SHORT).show();
        }
        if (checked == false){
            listFavRestaurants.remove(r);
            //Toast.makeText(MainActivity.this,
                    //"Restaurant removed from favorites", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < listFavRestaurants.size(); i++){
            Log.d("KAYYY", listFavRestaurants.get(i).getName());
        }
//        SharedPreferences sharedPreferences = getSharedPreferences("preferences ", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(listFavRestaurants);
//        editor.putString("Restaurant List", json);
//        editor.apply();
    }

    /**
     * This function saves the data in the listFavRestaurants to SharedPreferences, so that the user
     * can come back to the app later and see that all of their favorite restaurants are still marked
     * and there.
     */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferences ", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Log.d("in Save", "in save");
        //String json = gson.toJson(listFavRestaurants);
//        editor.putString("Restaurant List", json);
//        editor.apply();
    }

}