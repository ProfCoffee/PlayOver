package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class FeedbackFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        Button mProviderButton = findViewById(R.id.FeedbackSubmit);
        mProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback(){
        Intent parentIntent = getIntent();
        boolean isProvider = parentIntent.getBooleanExtra("isProvider", false);
        int userUID = parentIntent.getIntExtra("userUID", 0);
        int uid = parentIntent.getIntExtra("uid", 0);
        int providerUid = isProvider ? uid : 0;
        int activityUid = isProvider ? 0 : uid;

        Feedback feedback = new Feedback();
        feedback.setProvider(providerUid);
        feedback.setActivity(activityUid);
        feedback.setAuthor(userUID);

        Spinner ratingSpinner = findViewById(R.id.FeedbackRatingSpinner);
        feedback.setRating(Integer.valueOf((String)ratingSpinner.getSelectedItem()));

        TextView descriptionView = findViewById(R.id.FeedBackDescription);
        feedback.setContent(descriptionView.getText().toString());

        feedback.onCompletion(new Procedure() {
            @Override
            public void run() {
                finish();
            }
        });

        feedback.create(this);
    }
}
