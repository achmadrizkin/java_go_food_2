package com.example.java_go_food_clone.view.bottomNav.orders.make_qr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.java_go_food_clone.R;

import net.glxn.qrgen.android.QRCode;

public class MakeQrCodeActivity extends AppCompatActivity {
    private ImageView ivImageQrCode;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_qr_code);

        //
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        ivImageQrCode = findViewById(R.id.ivQrCode);

        //
        Bitmap myBitmap = QRCode.from(id).bitmap();
        ivImageQrCode.setImageBitmap(myBitmap);
    }
}