package com.example.a246_team_project;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * View Holder for expandable recycler view child information.
 * Extends the ChildViewHolder from bigNerdRanch
 * @author Rachel Jones
 */
public class RestaurantChildViewHolder extends ChildViewHolder {
    public TextView addressTextView;
    public TextView priceTextView;
    public TextView ratingTextView;
    public Button getDirectionButton;
    public Button getDetailsButton;

    /**
     * Non-default constructor that sets the textViews and buttons for the child view layout -
     * restaurant_details_layout.xml
     * @param itemView
     */
    public RestaurantChildViewHolder(View itemView) {
        super(itemView);

        addressTextView = (TextView) itemView.findViewById(R.id.address);
        priceTextView = (TextView) itemView.findViewById(R.id.price_level);
        ratingTextView = (TextView) itemView.findViewById(R.id.rating);
        getDirectionButton = (Button) itemView.findViewById(R.id.button);
        getDetailsButton = (Button) itemView.findViewById(R.id.button3);
    }
}
