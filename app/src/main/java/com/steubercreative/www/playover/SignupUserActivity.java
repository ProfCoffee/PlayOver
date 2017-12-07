package com.steubercreative.www.playover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        Button mSignUpProviderButton = findViewById(R.id.signup_button);
        mSignUpProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser(){
        EditText user_username_field = findViewById(R.id.user_username);
        EditText user_name_field = findViewById(R.id.user_name);
        EditText user_password_field = findViewById(R.id.user_password);

        String userName = user_username_field.getText().toString();
        String name = user_name_field.getText().toString();
        String password = user_password_field.getText().toString();

        final User user = new User();
        user.setName(name);
        user.setUsername(userName);
        user.setPassword(password);

        user.onCompletion(new Procedure() {
            @Override
            public void run() {
                if (!user.error()){
                    Intent intent = new Intent(SignupUserActivity.this, AirportSelectionActivity.class);
                    intent.putExtra("userUID", user.getUid());
                    startActivity(intent);
                }
            }
        });
        user.create(this);
    }
}
