package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AirportSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_selection);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.Airport_array));
        Spinner airports = findViewById(R.id.spinner);
        airports.setAdapter(adapter);

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
}
