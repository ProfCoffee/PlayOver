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

public class SignupProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_provider);

        Button mSignUpProviderButton = findViewById(R.id.signup_button);
        mSignUpProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProvider();
            }
        });
    }

    private void createProvider(){
        EditText provider_username_field = findViewById(R.id.provider_username);
        EditText provider_contact_field = findViewById(R.id.provider_contact);
        EditText provider_name_field = findViewById(R.id.provider_name);
        EditText provider_password_field = findViewById(R.id.provider_password);

        String userName = provider_username_field.getText().toString();
        String contact = provider_contact_field.getText().toString();
        String name = provider_name_field.getText().toString();
        String password = provider_password_field.getText().toString();

        final Provider provider = new Provider();
        provider.setName(name);
        provider.setUsername(userName);
        provider.setPassword(password);
        provider.setContact(contact);

        provider.onCompletion(new Procedure() {
            @Override
            public void run() {
                if (!provider.error()){
                    Intent intent = new Intent(SignupProviderActivity.this, ProviderMainActivity.class);
                    intent.putExtra("uid", provider.getUid());
                    startActivity(intent);
                }
            }
        });
        provider.create(this);
    }
}
