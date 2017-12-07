package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProviderMainActivity extends AppCompatActivity {

    private boolean isActive = true;
    private int providerUid;
    private Provider provider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);

        providerUid = getIntent().getIntExtra("ProviderID", 0);
        provider = new Provider();
        provider.setUid(providerUid);
        provider.retrieve(this);

        Button mManageActivitiesButton = findViewById(R.id.manage_activities_button);
        mManageActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProviderMainActivity.this, ManageActivitiesActivity.class);

                intent.putExtra("ProviderID", providerUid);

                startActivity(intent);
            }
        });

        Button mManageAccount = findViewById(R.id.ManageAccountButton);
        mManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProviderMainActivity.this, ManageProviderAccount.class);

                intent.putExtra("ProviderID", providerUid);

                startActivity(intent);
            }
        });

        Button mToggleStatus = findViewById(R.id.ToggleStatus);
        mToggleStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleStatus();
            }
        });

        Button mLogoutButton = findViewById(R.id.LogoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProviderMainActivity.this,LoginActivity.class));
            }
        });
    }


    private void ToggleStatus(){
        TextView mStatusTest = findViewById(R.id.StatusText);
        if (isActive){
            mStatusTest.setText("Inactive");
            mStatusTest.setTextColor(ContextCompat.getColor(this, R.color.colorInactive));
            provider.setStatus(Status.OFFLINE);
            provider.save(this);
            isActive = false;
        } else {
            mStatusTest.setText("Active");
            mStatusTest.setTextColor(ContextCompat.getColor(this, R.color.colorActive));
            provider.setStatus(Status.ONLINE);
            provider.save(this);
            isActive = true;
        }
    }
}
