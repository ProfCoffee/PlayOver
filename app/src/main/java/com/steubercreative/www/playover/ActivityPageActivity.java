package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;

public class ActivityPageActivity extends AppCompatActivity {

    private ViewGroup mFeedBackLayout;
    private ViewGroup mActivityStarsLayout;
    private FeedbackLoader feedbackLoader;

    private int _userUID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        Intent parentIntent = getIntent();
        _userUID = parentIntent.getIntExtra("userUID", 0);

        feedbackLoader = new FeedbackLoader(this);

        final Activity activity = new Activity();
        activity.setUid(parentIntent.getIntExtra("uid", 0));

        activity.onCompletion(new Procedure() {
            @Override
            public void run() {
                feedbackLoader.populateLayout(mFeedBackLayout, activity.getUid(), 7);
                populateFields(activity);
            }
        });

        activity.retrieve(this);

        Button mFeedbackButton = findViewById(R.id.ActivityFeedbackButton);
        if (_userUID != 0) {
            mFeedbackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityPageActivity.this, FeedbackFormActivity.class);

                    intent.putExtra("uid", activity.getUid());
                    intent.putExtra("isProvider", false);
                    intent.putExtra("userUID", _userUID);

                    startActivity(intent);
                }
            });
        } else {
            mFeedbackButton.setVisibility(View.GONE);
        }


        mFeedBackLayout = findViewById(R.id.feedback_layout);
        mActivityStarsLayout = findViewById(R.id.ActivityStars);

    }

    private void populateFields(Activity activity){
        TextView activityNameView = findViewById(R.id.ActivityName);
        activityNameView.setText(activity.getName());

        feedbackLoader.addStars(mActivityStarsLayout, activity.getRating());

        TextView airportView = findViewById(R.id.ActivityAirports);
        airportView.setText(activity.getAirport());

        TextView locationView = findViewById(R.id.ActivityLocation);
        locationView.setText(activity.getLocation());

        TextView costView = findViewById(R.id.ActivityCost);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        costView.setText(String.valueOf(formatter.format(activity.getCost())));

        TextView durationView = findViewById(R.id.ActivityDuration);
        durationView.setText(activity.getDuration() + "mins");

        TextView descriptionView = findViewById(R.id.ActivityDescription);
        descriptionView.setText(activity.getDescription());

        final Provider provider = new Provider();
        provider.setUid(activity.getProviderUid());

        provider.onCompletion(new Procedure() {
            @Override
            public void run() {
                Button mProviderButton = findViewById(R.id.ActivityProviderNameButton);
                mProviderButton.setText("View " + provider.getName());
                mProviderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityPageActivity.this, ProviderPageActivity.class);

                        intent.putExtra("uid", provider.getUid());
                        intent.putExtra("userUID", _userUID);

                        startActivity(intent);
                    }
                });
            }
        });

        provider.retrieve(this);
    }
}
