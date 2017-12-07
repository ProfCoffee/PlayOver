package com.steubercreative.www.playover;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginProviderActivity extends AppCompatActivity {
    TextView mForgotPassword;
    Button mSignInButton;
    AutoCompleteTextView mEmailText;
    EditText mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_provider);
        mEmailText = findViewById(R.id.email);
        mPasswordText = findViewById(R.id.password);
        mForgotPassword = findViewById(R.id.forgot_password_link);
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginProviderActivity.this, ForgotLoginActivity.class));
            }
        });
        mSignInButton = findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    private void attemptLogin() {
        mEmailText.setError("");
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        final Provider provider = new Provider();
        provider.setPassword(password);
        provider.setUsername(email);
        provider.onCompletion(new Procedure() {
            @Override
            public void run() {
                if(provider.error()) {
                    mEmailText.setError("Invalid username or password");
                }
                else {
                    Intent intent = new Intent(LoginProviderActivity.this, ProviderMainActivity.class);
                    intent.putExtra("ProviderID", provider.getUid());
                    startActivity(intent);
                }
            }
        });
        provider.login(this);
    }

}
