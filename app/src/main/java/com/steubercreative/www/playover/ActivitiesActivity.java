package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ActivitiesActivity extends AppCompatActivity {

    private ViewGroup mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        mLinearLayout = findViewById(R.id.activities_layout);
        Intent parentIntent = getIntent();
        TextView mAirportName = findViewById(R.id.AirportName);
        mAirportName.setText(parentIntent.getStringExtra("Airport"));

        for (int i = 0; i < 25; i++){
            addActivityView(i + " An Awesome Activity", "Sean Steuber the " + i + "st", 8+i, i%2!=0);
        }

        Button mFilterButton = findViewById(R.id.filter_button);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterActivities();
            }
        });
    }

    private void addActivityView(final String activityName, String providerName, float cost, boolean dark){
        View activityView;
        if (dark) activityView = LayoutInflater.from(this).inflate(R.layout.activity_dark_item, mLinearLayout, false);
        else activityView = LayoutInflater.from(this).inflate(R.layout.activity_item, mLinearLayout, false);

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
            addActivityView(i + " Pandas eating bamboo", "Jortdan Calsean the " + i + "rd", 17+i, i%2!=0);
        }
    }
}
