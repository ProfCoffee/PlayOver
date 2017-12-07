package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ManageProviderAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_provider_account);

        final EditText mAccountName = findViewById(R.id.ManageNameTextField);
        final EditText mAbout = findViewById(R.id.ManageAbout);
        final EditText mContact = findViewById(R.id.ManageContact);

        Intent parentIntent = getIntent();
        final int providerUID = parentIntent.getIntExtra("ProviderID", 0);

        final Provider provider = new Provider();
        provider.setUid(providerUID);

        provider.onCompletion(new Procedure() {
            @Override
            public void run() {

                mAccountName.setText(provider.getName());
                mAbout.setText(provider.getAbout());
                mContact.setText(provider.getContact());

            }
        });
        provider.retrieve(this);

        Button mSubmitButton = findViewById(R.id.ManageAccountSaveChangesButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provider.setName(mAccountName.getText().toString());
                provider.setAbout(mAbout.getText().toString());
                provider.setContact(mContact.getText().toString());
                provider.save(ManageProviderAccount.this);
                startActivity(new Intent(ManageProviderAccount.this,ProviderMainActivity.class));

            }
        });
    }
}
