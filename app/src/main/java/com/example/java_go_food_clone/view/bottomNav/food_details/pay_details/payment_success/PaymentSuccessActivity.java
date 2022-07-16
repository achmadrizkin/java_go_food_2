package com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.payment_success;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.view.bottomNav.BottomNav;

public class PaymentSuccessActivity extends AppCompatActivity {
    private AppCompatButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomNav.class);
                startActivity(intent);
            }
        });
    }
}