package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class ProviderModifiyActivity extends AppCompatActivity {

    private int _activityID;
    private boolean _isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_modify);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.Airport_array));
        Spinner airports = findViewById(R.id.spinner);
        airports.setAdapter(adapter);

        Intent parentIntent = getIntent();
        _activityID = parentIntent.getIntExtra("ActivityID", -1);
        _isEdit = _activityID != -1 ? true : false;

        if (_isEdit) PopulateFields();

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
}
