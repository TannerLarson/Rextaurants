package com.example.a246_team_project;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * View Holder for the restaurant row names
 * Extends the ParentViewHolder from bigNerdRanch
 * @author Rachel Jones
 */
public class RestaurantParentViewHolder extends ParentViewHolder {
    public TextView rTextview;
    public ToggleButton rToggle;

    /**
     * Non-default constructor that sets the textView for the parent view layout -
     * restaurant_row_layout.xml, as well as the toggle button
     * @param itemView
     */
    public RestaurantParentViewHolder(View itemView) {
        super(itemView);
        rTextview = (TextView) itemView.findViewById(R.id.restaurant_name);
        rToggle = (ToggleButton) itemView.findViewById(R.id.toggle1);
    }
}
