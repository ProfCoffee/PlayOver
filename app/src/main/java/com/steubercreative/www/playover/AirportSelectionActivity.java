package com.steubercreative.www.playover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AirportSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_selection);


        Button mSearchButton = findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AirportSelectionActivity.this, ActivitiesActivity.class);
                Spinner airports = findViewById(R.id.spinner);
                intent.putExtra("Airport", (String)airports.getSelectedItem());
                startActivity(intent);
            }
        });

        Button mManageAccountButton = findViewById(R.id.ManageAccountButton);
        mManageAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AirportSelectionActivity.this, ManageUserAccountActivity.class));
            }
        });

    }
    public void onStart() {
        super.onStart();

        final AirportArray airportArray = new AirportArray();
        final Context activityContext = this;
        airportArray.onCompletion(new Procedure() {
            @Override
            public void run() {
                List<String> airportStrings = new ArrayList<>();
                for(Airport a : airportArray.getObjects())
                    airportStrings.add(a.getCode() + " (" + a.getAirport() + ")");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activityContext, R.layout.custom_spinner, airportStrings);
                Spinner airports = findViewById(R.id.spinner);
                airports.setAdapter(adapter);

            }
        });
        airportArray.retrieve(this);
    }

}
