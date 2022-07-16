package com.example.java_go_food_clone.view.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.view.login.LoginActivity;
import com.example.java_go_food_clone.view.sign_up.SignUpActivity;

public class AuthActivity extends AppCompatActivity {

    AppCompatButton btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}