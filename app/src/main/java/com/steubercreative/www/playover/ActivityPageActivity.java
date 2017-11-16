package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        Intent parentIntent = getIntent();
        TextView textView = findViewById(R.id.ActivityName);
        textView.setText(parentIntent.getStringExtra("ActivityName"));
    }
}
