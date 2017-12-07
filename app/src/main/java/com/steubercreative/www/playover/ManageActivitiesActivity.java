package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ManageActivitiesActivity extends AppCompatActivity {

    private ViewGroup mActivitiesLayout;
    private ActivityLoader activityLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_activities);

        Intent parentIntent = getIntent();

        activityLoader = new ActivityLoader(this);
        mActivitiesLayout = findViewById(R.id.activities_layout);

        activityLoader.populateEditLayout(mActivitiesLayout,23423, 4, parentIntent.getIntExtra("ProviderID", 0));

        Button mCreateNewButton = findViewById(R.id.CreateNewButton);
        mCreateNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageActivitiesActivity.this, ProviderModifiyActivity.class);
                startActivity(intent);
            }
        });
    }
}
