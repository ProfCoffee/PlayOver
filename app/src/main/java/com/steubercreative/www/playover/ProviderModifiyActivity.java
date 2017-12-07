package com.steubercreative.www.playover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProviderModifiyActivity extends AppCompatActivity {

    private int _activityID;
    private boolean _isEdit = false;
    final private Activity activity = new Activity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_modify);


        final AirportArray airportArray = new AirportArray();
        final Context activityContext = this;

        airportArray.onCompletion(new Procedure() {
            @Override
            public void run() {
                List<String> airportStrings = new ArrayList<>();
                for(Airport a : airportArray.getObjects())
                    airportStrings.add(a.getCode());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activityContext, R.layout.custom_spinner, airportStrings);
                Spinner airports = findViewById(R.id.spinner);
                airports.setAdapter(adapter);

            }
        });
        airportArray.retrieve(this);

        Intent parentIntent = getIntent();
        _activityID = parentIntent.getIntExtra("ActivityID", -1);
        _isEdit = _activityID != -1 ? true : false;
        activity.setUid(_activityID);
        if (_isEdit) {
            activity.onCompletion(new Procedure() {
                @Override
                public void run() {
                    EditText activityNameTextForm = findViewById(R.id.ActivityNameTextField);
                    activityNameTextForm.setText(activity.getName());

                    //must choose correct airport

                    EditText locationTextForm = findViewById(R.id.LocationTextField);
                    locationTextForm.setText(activity.getLocation());

                    EditText costTextForm = findViewById(R.id.CostTextField);
                    costTextForm.setText(String.valueOf(activity.getCost()));

                    EditText durationTextForm = findViewById(R.id.DurationTextField);
                    durationTextForm.setText(String.valueOf(activity.getDuration()));

                    RadioGroup radioStatusGroup = findViewById(R.id.StatusRadioGroup);
                    radioStatusGroup.check(R.id.InactiveRadio);
                }
            });
            activity.retrieve(this);
        }
        else PopulateFields();

        RadioGroup scheduleRadioGroup = findViewById(R.id.SchedulingRadioGroup);
        scheduleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked){
                    ViewGroup scheduleLayout = findViewById(R.id.SchedulingLayout);
                    int visibility = checkedId == R.id.RepetetiveRadio ? View.VISIBLE : View.GONE;
                    scheduleLayout.setVisibility(visibility);
                }
            }
        });


        Button mSignUpProviderButton = findViewById(R.id.saveButton);
        mSignUpProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveActivity();
            }
        });

    }
    
    private void saveActivity() {
        activity.onCompletion(new Procedure() {
            @Override
            public void run() {
                if (!activity.error()){
                    Intent intent = new Intent(ProviderModifiyActivity.this, ManageActivitiesActivity.class);
                    intent.putExtra("ProviderID", activity.getProviderUid());
                    startActivity(intent);
                }
            }
        });
        if(_isEdit) {
            updateActivity();
            //activity.save(this);
        }
        else {
            updateActivity();
            //activity.create(this);
        }

    }

    private void PopulateFields(){
        EditText activityNameTextForm = findViewById(R.id.ActivityNameTextField);
        activityNameTextForm.setText("Current Name");

        //must choose correct airport

        EditText locationTextForm = findViewById(R.id.LocationTextField);
        locationTextForm.setText("My Location");

        EditText costTextForm = findViewById(R.id.CostTextField);
        costTextForm.setText("200");

        EditText durationTextForm = findViewById(R.id.DurationTextField);
        durationTextForm.setText("45");

        RadioGroup radioStatusGroup = findViewById(R.id.StatusRadioGroup);
        radioStatusGroup.check(R.id.InactiveRadio);
    }

    private void updateActivity() {
        EditText activityNameTextForm = findViewById(R.id.ActivityNameTextField);
        activity.setName(activityNameTextForm.getText().toString());

        //must choose correct airport

        EditText locationTextForm = findViewById(R.id.LocationTextField);
        activity.setLocation(locationTextForm.getText().toString());


        EditText costTextForm = findViewById(R.id.CostTextField);
        activity.setCost(Float.valueOf(costTextForm.getText().toString()));

        EditText durationTextForm = findViewById(R.id.DurationTextField);
        activity.setDuration(Integer.valueOf(durationTextForm.getText().toString()));

        Spinner airports = findViewById(R.id.spinner);
        activity.setAirport(airports.getSelectedItem().toString());
    }
}
