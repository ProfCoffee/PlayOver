package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ActivityPageActivity extends AppCompatActivity {

    private ViewGroup mFeedBackLayout;
    private ViewGroup mActivityStarsLayout;
    private FeedbackLoader feedbackLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        Intent parentIntent = getIntent();
        TextView textView = findViewById(R.id.ActivityName);
        textView.setText(parentIntent.getStringExtra("ActivityName"));

        Button mProviderButton = findViewById(R.id.ActivityProviderNameButton);
        mProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPageActivity.this, ProviderPageActivity.class);

                intent.putExtra("ProviderID", 555);

                startActivity(intent);
            }
        });


        Button mFeedbackButton = findViewById(R.id.ActivityFeedbackButton);
        mFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPageActivity.this, FeedbackFormActivity.class);

                intent.putExtra("EntityID", 555);

                startActivity(intent);
            }
        });

        mFeedBackLayout = findViewById(R.id.feedback_layout);
        mActivityStarsLayout = findViewById(R.id.ActivityStars);
        feedbackLoader = new FeedbackLoader(this);

        feedbackLoader.PopulateLayout(mFeedBackLayout, 1213, 7);
        feedbackLoader.addStars(mActivityStarsLayout, 5);
    }
}
