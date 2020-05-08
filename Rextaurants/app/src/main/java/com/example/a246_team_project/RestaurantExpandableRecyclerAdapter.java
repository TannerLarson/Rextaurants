package com.example.a246_team_project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * An expandable recycler adapter that extends an ExpandableRecyclerAdapter from bigNerdRanch.
 * This adapter connect the data from Restaurant to the recycler view in the activity_main.xml
 * @author Rachel Jones
 */
public class RestaurantExpandableRecyclerAdapter extends ExpandableRecyclerAdapter
        <RestaurantParentViewHolder, RestaurantChildViewHolder> {

    private List<ParentObject> _restaurantList;
    private Context _context;
    private List<Object> _objects;

    /**
     * Non-default constructor to create the expandable adapter
     * @param context The activity context (main activity)
     * @param parentList A list of restaurant objects
     */
    public RestaurantExpandableRecyclerAdapter(Context context, List<ParentObject> parentList) {
        super(context, parentList);
        _context = context;
        _restaurantList = parentList;
    }

    /**
     * Creates the parent view holder - restaurant_row_layout.xml
     * @param viewGroup The views past in, comes from the overridden function
     * @return The created parent view holder - restaurant_row_layout.xml
     */
    @Override
    public RestaurantParentViewHolder onCreateParentViewHolder (ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.restaurant_row_layout, viewGroup, false);
        return new RestaurantParentViewHolder(view);
    }

    /**
     * Sets the text view for the restaurant row layouts. The color of the restaurant name
     * will change depending on whether the restaurant is open or not. Null open times will default
     * to red.
     * Also listens to the onClickListeners for each of the togglebutton stars.
     * @param rParentViewHolder The parent view
     * @param i An index that is not used for this application but required
     * @param parentObject The parent object or restaurant to be displayed
     */
    @Override
    public void onBindParentViewHolder(RestaurantParentViewHolder rParentViewHolder, int i,
                                       Object parentObject) {
        Restaurant r = (Restaurant) parentObject;
        rParentViewHolder.rTextview.setText(r.getName());
        if (r.getHours().getOpen_now() == null) {                         //IndianRed
            rParentViewHolder.rTextview.setTextColor(Color.parseColor("#CC0000"));
        } else if (r.getHours().getOpen_now().booleanValue() == Boolean.TRUE) {  //dark green
            rParentViewHolder.rTextview.setTextColor(Color.parseColor("#0a7e07"));
        } else {                                                          //IndianRed
            rParentViewHolder.rTextview.setTextColor(Color.parseColor("#CC0000"));
        }

        rParentViewHolder.rToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity a = (MainActivity) _context;
                ((MainActivity) _context).onStarClicked(v, r);
            }
        });
    }

    /**
     * Creates the child view holder - restaurant_details_layout.xml
     * @param viewGroup The views past in, comes from the overridden function
     * @return The created child view holder - restaurant_details_layout.xml
     */
    @Override
    public RestaurantChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        //View view = rInflater.inflate(R.layout.restaurant_details_layout, viewGroup, false);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .restaurant_details_layout, viewGroup, false);
        return new RestaurantChildViewHolder(view);
    }

    /**
     * Sets the text views and buttons for the expanded portion of the recycler view. Will insert
     * dollar sign icons for the price level. Has a listener for the details button and the
     * directions button
     * @param rChildViewHolder The child view
     * @param i An index that is not used but required
     * @param childObject The child object/restaurant details to be displayed
     */
    @Override
    public void onBindChildViewHolder(RestaurantChildViewHolder rChildViewHolder, int i,
                                      Object childObject) {
        RestaurantAdapterChild data = (RestaurantAdapterChild) childObject;
        rChildViewHolder.addressTextView.setText(data.getRestaurant().getFormatted_address());
        if (data.getRestaurant().getPrice_level() == 1) {
            rChildViewHolder.priceTextView.setText("Price Level: \uD83D\uDCB2");
        } else if (data.getRestaurant().getPrice_level() == 2) { //^^for dollar signs
            rChildViewHolder.priceTextView.setText("Price Level: \uD83D\uDCB2\uD83D\uDCB2");
        } else if (data.getRestaurant().getPrice_level() == 3) {
            rChildViewHolder.priceTextView.setText("Price Level: " +
                    "\uD83D\uDCB2\uD83D\uDCB2\uD83D\uDCB2");
        } else {
            rChildViewHolder.priceTextView.setText("Price Level: Unknown");
        }
        rChildViewHolder.getDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity a = (MainActivity) _context;
                ((MainActivity) _context).goToSpecifics(data.getRestaurant());
            }
        });
        rChildViewHolder.ratingTextView.setText("Rating: " + data.getRestaurant().getRating());
        rChildViewHolder.getDirectionButton.setText(data.getRestaurant().getGet_directions());
        rChildViewHolder.getDirectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity a = (MainActivity) _context;
                ((MainActivity) a).getDirections(data.getRestaurant());
            }
        });
    }
}
