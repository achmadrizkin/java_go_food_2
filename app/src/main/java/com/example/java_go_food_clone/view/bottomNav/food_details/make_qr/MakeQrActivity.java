package com.example.java_go_food_clone.view.bottomNav.food_details.make_qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.java_go_food_clone.R;

import net.glxn.qrgen.android.QRCode;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MakeQrActivity extends AppCompatActivity {
    private String kd_food;
    private ImageView ivQrCode, ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_qr);

        Intent intent = getIntent();
        kd_food = intent.getStringExtra("kd_food");;

        ivQrCode = findViewById(R.id.ivQrCode);
        ivBack = findViewById(R.id.ivBack);

        //
        Bitmap myBitmap = QRCode.from(kd_food).bitmap();
        ivQrCode.setImageBitmap(myBitmap);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}