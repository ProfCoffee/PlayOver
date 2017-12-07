package com.steubercreative.www.playover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

        mFeedBackLayout = findViewById(R.id.feedback_layout);
        mActivityStarsLayout = findViewById(R.id.ProviderStars);
        feedbackLoader = new FeedbackLoader(this);

        Intent parentIntent = getIntent();

        final Provider provider = new Provider();
        final int userUID = parentIntent.getIntExtra("userUID", 0);
        provider.setUid(parentIntent.getIntExtra("uid", 0));

        provider.onCompletion(new Procedure() {
            @Override
            public void run() {
                populateFields(provider);
            }
        });

        provider.retrieve(this);

        Button mFeedbackButton = findViewById(R.id.ActivityFeedbackButton);
        if (userUID != 0) {
            mFeedbackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProviderPageActivity.this, FeedbackFormActivity.class);

                    intent.putExtra("uid", provider.getUid());
                    intent.putExtra("isProvider", false);
                    intent.putExtra("userUID", userUID);

                    startActivity(intent);
                }
            });
        } else {
            mFeedbackButton.setVisibility(View.GONE);
        }

        feedbackLoader.populateLayout(mFeedBackLayout, 1213, 7);

    }
    private void populateFields(Provider provider){
        TextView providerNameView = findViewById(R.id.ProviderName);
        providerNameView.setText(provider.getName());

        feedbackLoader.addStars(mActivityStarsLayout, provider.getRating());

        TextView statusView = findViewById(R.id.ProviderStatus);
        boolean isOnline = provider.getStatus() == Status.ONLINE ? true : false;
        String status = isOnline ? "Online" : "Offline";
        if (isOnline) statusView.setTextColor(ContextCompat.getColor(this, R.color.colorActive));
        else statusView.setTextColor(ContextCompat.getColor(this, R.color.colorInactive));;
        statusView.setText(status);

        TextView contactView = findViewById(R.id.ProviderContact);
        contactView.setText("Contact: " + provider.getContact());

        TextView descriptionView = findViewById(R.id.ProviderDescription);
        descriptionView.setText(provider.getAbout());
    }
}
