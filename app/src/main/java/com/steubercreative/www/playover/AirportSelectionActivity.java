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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AirportSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_selection);

        Intent parentIntent = getIntent();
        final int userUID = parentIntent.getIntExtra("userUID", 0);


        Button mSearchButton = findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AirportSelectionActivity.this, ActivitiesActivity.class);
                Spinner airports = findViewById(R.id.spinner);
                intent.putExtra("Airport", (String)airports.getSelectedItem());
                intent.putExtra("userUID", userUID);
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

        Button mLogoutButton = findViewById(R.id.LogoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AirportSelectionActivity.this,LoginActivity.class));
            }
        });

        if (userUID != 0) populateUserFields(userUID);
    }
    
    private void populateUserFields (int uid){
        final TextView welcomeView = findViewById(R.id.WelcomeText);
        final User user = new User();
        user.setUid(uid);

        user.onCompletion(new Procedure() {
            @Override
            public void run() {
                welcomeView.setText("Welcome Back, "+ user.getName());
            }
        });

        user.retrieve(this);
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

    public void onBackPressed()
    {

    }

}
