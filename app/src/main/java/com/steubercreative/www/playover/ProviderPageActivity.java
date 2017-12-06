package com.steubercreative.www.playover;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProviderPageActivity extends AppCompatActivity {

    private ViewGroup mFeedBackLayout;
    private ViewGroup mActivityStarsLayout;
    private FeedbackLoader feedbackLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_page);

        Button mFeedbackButton = findViewById(R.id.ActivityFeedbackButton);
        mFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProviderPageActivity.this, FeedbackFormActivity.class);

                intent.putExtra("EntityID", 555);

                startActivity(intent);
            }
        });

        mFeedBackLayout = findViewById(R.id.feedback_layout);
        mActivityStarsLayout = findViewById(R.id.ProviderStars);
        feedbackLoader = new FeedbackLoader(this);

        feedbackLoader.PopulateLayout(mFeedBackLayout, 1213, 7);
        feedbackLoader.addStars(mActivityStarsLayout, 3);
    }

}
