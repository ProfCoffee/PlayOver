package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivitiesActivity extends AppCompatActivity {

    private ViewGroup mLinearLayout;
    private ViewGroup mAdvancedOptionsLayout;

    private ActivityLoader activityLoader;

    private boolean _advancedOptionsShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        activityLoader = new ActivityLoader(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.sorting_array));
        Spinner sorting = findViewById(R.id.OrderActivitiesSpinner);
        sorting.setAdapter(adapter);

        TextView mToggleAdvancedOptions = findViewById(R.id.sort_advanced_options_toggle);
        mToggleAdvancedOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleAdvancedOptions();
            }
        });

        mLinearLayout = findViewById(R.id.activities_layout);
        Intent parentIntent = getIntent();
        final int userUID = parentIntent.getIntExtra("userUID", 0);

        TextView mAirportName = findViewById(R.id.AirportName);
        mAirportName.setText(parentIntent.getStringExtra("Airport"));

        activityLoader.PopulateLayout(mLinearLayout, 2324, userUID, 10);
        mAdvancedOptionsLayout = findViewById(R.id.SortAdvancedOptionsLayout);

        Button mFilterButton = findViewById(R.id.filter_button);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterActivities();
            }
        });
    }

    private void toggleAdvancedOptions(){
        int visibility = _advancedOptionsShowing ? View.GONE : View.VISIBLE;
        mAdvancedOptionsLayout.setVisibility(visibility);
        _advancedOptionsShowing = _advancedOptionsShowing ? false : true;
    }

    private void addActivityView(final String activityName, String providerName, float cost){
        View activityView = LayoutInflater.from(this).inflate(R.layout.activity_item, mLinearLayout, false);

        TextView activityNameTextView = activityView.findViewById(R.id.ActivityName);
        TextView activityProviderNameTextView = activityView.findViewById(R.id.ProviderName);
        TextView activityCostTextView = activityView.findViewById(R.id.Cost);

        activityNameTextView.setText(activityName);
        activityProviderNameTextView.setText(providerName);
        activityCostTextView.setText("$"+cost);

        activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitiesActivity.this, ActivityPageActivity.class);

                intent.putExtra("ActivityName", activityName);

                startActivity(intent);
            }
        });

        mLinearLayout.addView(activityView);
    }

    private void filterActivities(){
        mLinearLayout.removeAllViews();

        for (int i = 0; i < 11; i++){
            addActivityView(i + " Pandas eating bamboo", "Jortdan Calsean the " + i + "rd", 17+i);
        }
    }
}
