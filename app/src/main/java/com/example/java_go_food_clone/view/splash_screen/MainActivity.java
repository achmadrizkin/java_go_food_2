package com.example.java_go_food_clone.view.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.view.auth.AuthActivity;
import com.example.java_go_food_clone.view.bottomNav.BottomNav;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = getSharedPreferences("login_session",MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (profile.getString("token", null) != null) {
                    startActivity(new Intent(getApplicationContext(), BottomNav.class));
                    finish();
                } else {
                    Intent p = new Intent(MainActivity.this, AuthActivity.class);
                    startActivity(p);
                    finish();
                }
            }
        }, 1000);
    }
}