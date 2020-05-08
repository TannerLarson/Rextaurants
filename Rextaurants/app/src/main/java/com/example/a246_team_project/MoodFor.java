package com.example.a246_team_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * This activity lets the user pick what types of food they are in the mood for, and sends a list of
 * those food types back to the main activity for it to use.
 * @author Kaylee Hartzog
 */
public class MoodFor extends AppCompatActivity {
    ArrayList<String> selectedMoods = new ArrayList<String>();
    CheckBox American, Asian, BBQ, Buffet, Diner, Drink_Shop, Fast_Food, Mexican, Pizza, Sandwich;

    /**
     * Creates the MoodFor activity. It sets values for each of the checkboxes for the foodtypes and
     * makes onClickListeners for each of the foodtypes. the listeners add or remove the foodtype from
     * the list of selected foodtypes the user wants.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_for);

        American = (CheckBox)findViewById(R.id.American);
        Asian = (CheckBox)findViewById(R.id.Asian);
        BBQ = (CheckBox)findViewById(R.id.BBQ);
        Buffet = (CheckBox)findViewById(R.id.Buffet);
        Diner = (CheckBox)findViewById(R.id.Diner);
        Drink_Shop = (CheckBox)findViewById(R.id.Drink_Shop);
        Fast_Food = (CheckBox)findViewById(R.id.Fast_Food);
        Mexican = (CheckBox)findViewById(R.id.Mexican);
        Pizza = (CheckBox)findViewById(R.id.Pizza);
        Sandwich = (CheckBox)findViewById(R.id.Sandwich);



        //First CheckBox
        American.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(American.isChecked())
                {
                    selectedMoods.add("American");
                }
                else
                {
                    selectedMoods.remove("American");
                }

            }
        });

        Asian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Asian.isChecked())
                {
                    selectedMoods.add("Asian");
                }
                else
                {
                    selectedMoods.remove("Asian");
                }

            }
        });

        BBQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(BBQ.isChecked())
                {
                    selectedMoods.add("BBQ");
                }
                else
                {
                    selectedMoods.remove("BBQ");
                }

            }
        });

        Buffet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Buffet.isChecked())
                {
                    selectedMoods.add("Buffet");
                }
                else
                {
                    selectedMoods.remove("Buffer");
                }

            }
        });

        Diner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Diner.isChecked())
                {
                    selectedMoods.add("Diner");
                }
                else
                {
                    selectedMoods.remove("Diner");
                }

            }
        });

        Drink_Shop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Drink_Shop.isChecked())
                {
                    selectedMoods.add("Drink Shop");
                }
                else
                {
                    selectedMoods.remove("Drink Shop");
                }

            }
        });

        Fast_Food.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Fast_Food.isChecked())
                {
                    selectedMoods.add("Fast Food");
                }
                else
                {
                    selectedMoods.remove("Fast Food");
                }

            }
        });

        Mexican.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Mexican.isChecked())
                {
                    selectedMoods.add("Mexican");
                }
                else
                {
                    selectedMoods.remove("Mexican");
                }

            }
        });

        Pizza.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Pizza.isChecked())
                {
                    selectedMoods.add("Pizza");
                }
                else
                {
                    selectedMoods.add("Pizza");
                }

            }
        });

        Sandwich.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Sandwich.isChecked())
                {
                    selectedMoods.add("Sandwich");
                }
                else
                {
                    selectedMoods.remove("Sandwich");
                }

            }
        });
    }

    /**
     * Takes the list with the foodtypes the user selected and sends it back to the main activity.
     * @param view - What was used for the view but it is not used here
     */
    public void doApply(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("moodArray",selectedMoods);
        startActivity(intent);
    }
}
