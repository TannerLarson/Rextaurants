package com.example.a246_team_project;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * This class is designed to hold all the information needed for a restaurant.  This is a pure data
 * class, and the only functions implemented that aren't getters or setters are used for set
 * organization
 * @author Tanner Larson
 */
public class Restaurant implements ParentObject, Serializable {
    private String formatted_address;
    private Geometry geometry;
    private String name;
    @SerializedName("opening_hours")
    private Hours hours;
    private String place_id;
    private String foodType;
    private int price_level;
    private double rating;
    private String formatted_phone_number;
    private List<Object> rInfo;


    /**
     * Default constructor
     *
     * @param _foodType Food type of restaurant
     * @param _name Name of restaurant
     * @param _address Address of restaurant
     * @param _price Price level of restaurant
     * @param _rating Rating out of 3 of the restaurant
     */
    public Restaurant(String _foodType, String _name, String _address,
                      int _price, double _rating) {
        foodType = _foodType;
        name = _name;
        formatted_address = _address;
        price_level = _price;
        rating = _rating;
    }

    /**
     * This is what the button to get the directions says in the recycler view
     *
     * @return String to print on button
     */
    public String getGet_directions() { return "Get Directions"; }

    /**
     * Get current value of foodType
     * foodType controls what set of restaurants to display
     * @return String of foodType (Mexican, American, Fast, ect.)
     */
    public String getFoodType() { return foodType; }

    /**
     * Set foodType
     * Used for deserialization
     * @param foodType set foodType equal to this
     */
    public void setFoodType(String foodType) { this.foodType = foodType; }

    /**
     * Get address of restaurant the way you would see it normally
     * ie: 345 West 5 South, Rexburg ID, 83440
     * @return String of address
     */
    public String getFormatted_address() { return formatted_address; }

    /**
     * Set formatted_address
     * Used for deserialization
     * @param formatted_address Set formatted_address equal to this
     */
    public void setFormatted_address(String formatted_address) { this.formatted_address = formatted_address; }

    /**
     * Get data in geometry variable
     * geometry is of type Geometry, which contains a Location
     * @return Current value of Geometry
     */
    public Geometry getGeometry() { return geometry; }

    /**
     * Set Geometry
     * Used of deserialization
     * @param geometry Set geometry equal to this
     */
    public void setGeometry(Geometry geometry) { this.geometry = geometry; }

    /**
     * Get name of the restaurant
     * @return String of name of restaurant
     */
    public String getName() { return name; }

    /**
     * Set name of restaurant
     * Used for deserialization
     * @param name set name equal to this
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get data in hours variable
     * hours is of type Hours, which contains an open_now boolean
     * @return Current value of Hours
     */
    public Hours getHours() { return hours; }

    /**
     * Set Hours
     * Used for deserialization
     * @param hours set hours equal to this
     */
    public void setHours(Hours hours) { this.hours = hours; }

    /**
     * Get place id of restaurant
     * This id is used to get any extra details about that specific restaurant in other API calls
     * @return String of place_id
     */
    public String getPlace_id() { return place_id; }

    /**
     * Set place_id
     * Used for deserialization
     * @param place_id Set place_id equal to this
     */
    public void setPlace_id(String place_id) { this.place_id = place_id; }

    /**
     * Get price level of restaurant
     * price_level is a number out of three that describes how expensive overall the restaurant is
     * The lower the number the less expensive the restaurant overall
     * Price level is defined by previous Google user ratings
     * @return Integer of price_level
     */
    public int getPrice_level() { return price_level; }

    /**
     * Set price_level
     * Used for deserialization
     * @param price_level Set price_level equal to this
     */
    public void setPrice_level(int price_level) { this.price_level = price_level; }

    /**
     * Get rating of restaurant
     * rating is a double with one decimal place out of five that describes the overall rating of
     * the restaurant
     * The higher the number the better the rating
     * Rating is defined by previous Google user ratings
     * @return Double of rating
     */
    public double getRating() { return rating; }

    /**
     * Set rating
     * Used for deserialization
     * @param rating Set rating equal to this
     */
    public void setRating(float rating) { this.rating = rating; }

    /**
     * Get phone number in form (xxx) xxx-xxxx
     * NOTE: Only call this method after calling getSpecifics()
     * @return String of formatted_phone_number
     */
    public String getFormatted_phone_number() { return formatted_phone_number; }

    /**
     * Set phone number
     * NOTE: Only call this method after calling getSpecifics()
     * @param formatted_phone_number Set phone number equal to this
     */
    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    /**
     * Getting for its own restaurant
     * @return
     */
    public Restaurant getRestaurant() { return this; }

    /**
     * Will return the child object list
     * @return child object list
     */
    @Override
    public List<Object> getChildObjectList() { return rInfo; }

    /**
     * Sets the child object list
     * @param data Child object list that is passed in
     */
    @Override
    public void setChildObjectList(List<Object> data) { rInfo = data; }

    /**
     * This is just a class designed to read in JSON data
     * @author Tanner Larson
     */
    public class Hours implements Serializable{
        private Boolean open_now;
        private List <Period> periods;
        @SerializedName("weekday_text")
        private List <String> formatted_hours;

        /**
         * Determines whether the restaurant is open or not
         * True means it's open, False means it's closed
         * @return Boolean of open_now
         */
        public Boolean getOpen_now() { return open_now; }

        /**
         * Set open_now
         * Used for deserialization
         * @param open_now Set open_now equal to this
         */
        public void setOpen_now(Boolean open_now) { this.open_now = open_now; }

        /**
         * Get list of hours in this format: Monday: 10:00 AM – 10:00 PM
         * The first item of this list will be Monday, the second will be Tuesday, and so on
         * NOTE: Only call this method after calling getSpecifics()
         * @return List of strings, one for each day
         */
        public List<String> getFormatted_hours() { return formatted_hours; }

        /**
         * Set list of hours
         * NOTE: Only call this method after calling getSpecifics()
         * @param formatted_hours Set formatted_hours equal to this
         */
        public void setFormatted_hours(List<String> formatted_hours) {
            this.formatted_hours = formatted_hours;
        }

        /**
         * Gets a list of Period data, one for each day of the week.
         * NOTE: Only call this method after calling getSpecifics()
         * @return List of periods
         */
        public List<Period> getPeriods() { return periods; }

        /**
         * Set periods
         * NOTE: Only call this method after calling getSpecifics()
         * @param periods Set periods equal to this
         */
        public void setPeriods(List<Period> periods) { this.periods = periods; }

        /**
         * This class is just to deserialize data, it holds the open time and close time for each
         * day
         * NOTE: Only call this method after calling getSpecifics()
         * @author Tanner Larson
         */
        public class Period implements Serializable{
            private Time open;
            private Time close;

            /**
             * Gets the time and day restaurant opens
             * NOTE: Only call this method after calling getSpecifics()
             * @return open Time object
             */
            public Time getOpen() { return open; }

            /**
             * Sets open variable
             * NOTE: Only call this method after calling getSpecifics()
             * @param open Set open equal to this
             */
            public void setOpen(Time open) { this.open = open; }

            /**
             * Gets the time and day restaurant closes
             * NOTE: Only call this method after calling getSpecifics()
             * @return close Time object
             */
            public Time getClose() { return close; }

            /**
             * Sets close variable
             * NOTE: Only call this method after calling getSpecifics()
             * @param close Set close equal to this
             */
            public void setClose(Time close) { this.close = close; }

            /**
             * This class is just to deserialize data, it holds a day and a time
             * NOTE: Only call this method after calling getSpecifics()
             * @author Tanner Larson
             */
            public class Time implements Serializable{
                private int day;
                private int time;

                /**
                 * Gets the day
                 * Monday is day 0, Tuesday is day 1, and so on
                 *NOTE: Only call this method after calling getSpecifics()
                 * @return Integer of day
                 */
                public int getDay() { return day; }

                /**
                 * Sets the day
                 * Should only be an int between 0 and 7
                 * NOTE: Only call this method after calling getSpecifics()
                 * @param day Set day equal to this
                 */
                public void setDay(int day) { this.day = day; }

                /**
                 * Gets the time in military time (Doesn't use AM or PM)
                 * NOTE: Only call this method after calling getSpecifics()
                 * @return Integer of time
                 */
                public int getTime() { return time; }

                /**
                 * Sets the time
                 * NOTE: Only call this method after calling getSpecifics()
                 * @param time Set time equal to this
                 */
                public void setTime(int time) { this.time = time; }
            }
        }
    }

    /**
     * This is just a class to read in JSON data
     * @author Tanner Larson
     */
    public class Geometry implements Serializable{
        private Location location;

        /**
         * Set location
         * Used for deserialization
         * @param location Set location equal to this
         */
        public void setLocation(Location location) { this.location = location; }

        /**
         * Get location of restaurant
         * location is of type Location, defined in the Restaurant class
         * NOTE: This location is not the android.location.Location object
         * @return Location object defined in Restaurant
         */
        public Location getLocation() { return location; }

        /**
         * This is just a class to read in JSON data
         * @author Tanner Larson
         */
        public class Location implements Serializable{
            private double lat;
            @SerializedName("lng")
            private double lon;

            /**
             * Get latitude of restaurant
             * @return Double of latitude
             */
            public double getLat() { return lat; }

            /**
             * Set latitude of restaurant
             * Used for deserialization
             * @param lat Set lat variable equal to this
             */
            public void setLat(float lat) { this.lat = lat; }

            /**
             * Get Longitude of restaurant
             * @return Double of longitude
             */
            public double getLon() { return lon; }

            /**
             * Set longitude of restaurant
             * Used for deserialization
             * @param lon Set lon variable equal to this
             */
            public void setLon(float lon) { this.lon = lon; }

        }
    }

    // If I put this into a set, look for duplicates based on address

    /**
     * Used to organize a set of restaurants
     * @return Integer of hashcode of restaurant
     */
    @Override
    public int hashCode() {
        return this.getFormatted_address().hashCode();
    }


    // Comparator for set
    /**
     * Used to remove duplicates of restaurants based on similar formatted address
     * @param obj Restaurant to be compared
     * @return True if obj is a duplicate, False if obj is not a duplicate
     */
    @Override
    public boolean equals(Object obj) {
        // Create and set a new restaurant if obj is a restaurant
        Restaurant r = null;
        if(obj instanceof Restaurant) { r = (Restaurant) obj; }

        // Compare r to this restaurant based on formatted address
        if(this.getFormatted_address().contains(r.getFormatted_address())){ return true; }
        else { return false; }
    }
}
