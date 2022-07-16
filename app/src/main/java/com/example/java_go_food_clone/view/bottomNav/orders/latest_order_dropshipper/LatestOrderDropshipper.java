package com.example.java_go_food_clone.view.bottomNav.orders.latest_order_dropshipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperList;
import com.example.java_go_food_clone.utils.MCrypt;
import com.example.java_go_food_clone.view.bottomNav.orders.make_qr_code.MakeQrCodeActivity;
import com.example.java_go_food_clone.view_model.TransactionViewModel;

import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LatestOrderDropshipper extends AppCompatActivity {
    private String id, token, encrypted;
    private ImageView ivImage, imgLogoKurir;
    private TransactionViewModel viewModel;
    private ProgressBar pbLoading;
    private SharedPreferences profile;
    private TextView tvFoodName, tvDetailsFood, tvTgl, tvAmountFood, tvTotalPrice, tvLokasi, tvType, tvPrice, tvEst;
    private AppCompatButton btnMakeQr, btnPrintPDF;
    private Bitmap bitmap, scaleBitmap, scaleBitmapQrCode;
    private int imgLogo;
    private ScrollView svHaveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_order_dropshipper);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);

        // encrypted
        MCrypt mcrypt = new MCrypt();
        try {
            encrypted = MCrypt.bytesToHex( mcrypt.encrypt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        initView();

        //
        getTransactionDropshipperByIdObservable();
        getTransactionDropshipperByIdAPI(token, id);

        Bitmap myBitmap = QRCode.from(encrypted).bitmap();
        scaleBitmapQrCode = Bitmap.createScaledBitmap(myBitmap, 500, 500, false);

        // PERMISSION
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //cover header
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.food_delivery);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 850, false);

        //
        btnMakeQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MakeQrCodeActivity.class);
                i.putExtra("id", encrypted);
                startActivity(i);
            }
        });

        btnPrintPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });
    }

    private void initView() {
        ivImage = findViewById(R.id.ivImage);
        pbLoading = findViewById(R.id.pbLoading);
        tvFoodName = findViewById(R.id.tvFoodName);
        tvTgl = findViewById(R.id.tvTanggal);
        tvAmountFood = findViewById(R.id.tvAmountFood);
        tvDetailsFood = findViewById(R.id.tvDetailsFood);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnMakeQr = findViewById(R.id.btnMakeQR);
        tvLokasi = findViewById(R.id.tvLokasi);
        imgLogoKurir = findViewById(R.id.imgLogo);
        tvType = findViewById(R.id.tvType);
        tvPrice = findViewById(R.id.tvPrice);
        tvEst = findViewById(R.id.tvEst);
        svHaveData = findViewById(R.id.svHaveData);
        btnPrintPDF = findViewById(R.id.btnPrintPDF);

        pbLoading.setVisibility(View.VISIBLE);
        svHaveData.setVisibility(View.GONE);
    }

    private void getTransactionDropshipperByIdObservable() {
        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        viewModel.getTransactionDropshipperByIdResponseObservable().observe(this, new Observer<TransactionDropshipperList>() {
            @Override
            public void onChanged(TransactionDropshipperList transactionList) {
                if (transactionList != null) {
                    if (transactionList.getSuccess().equals("1")) {
                        //
                        initSuccess(transactionList);

                        pbLoading.setVisibility(View.GONE);
                        svHaveData.setVisibility(View.VISIBLE);
                    }
                } else {

                }
            }
        });
    }

    private void createPDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        //
        PdfDocument.PageInfo pageInfo
                = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(scaleBitmap, 0, 0, paint);

        //
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(70);
        canvas.drawText("Your Invoice", 1800, 500, titlePaint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setTextSize(35f);

        canvas.drawText("Food Name: " + tvFoodName.getText().toString(), 20, 740, paint);
        canvas.drawText("Food Description: " + tvDetailsFood.getText().toString(), 20, 790, paint);
        canvas.drawText(tvAmountFood.getText().toString(), 20, 840, paint);
        canvas.drawText(tvTgl.getText().toString(), 20, 890, paint);
        canvas.drawText(tvLokasi.getText().toString(), 20, 940, paint);

        paint.setColor(Color.rgb(247, 147, 30));
        canvas.drawRect(680, 1515, 1200 - 20, 1615, paint);

        // QR CODE
        Canvas canvas1 = page.getCanvas();
        canvas1.drawBitmap(scaleBitmapQrCode, 700, 1000, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(50f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total", 700, 1615 - 40, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(tvTotalPrice.getText().toString(), 1200 - 40, 1615 - 40, paint);

        //
        pdfDocument.finishPage(page);

        File file = new File(Environment.getExternalStorageDirectory(), "/Order-Dropshipper-" + id + ".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
        Toast.makeText(LatestOrderDropshipper.this, "PDF Already Downloaded", Toast.LENGTH_LONG).show();
    }


    private void getTransactionDropshipperByIdAPI(String key, String id) {
        viewModel.getTransactionDropshipperByIdResponseOfData(key, id);
    }

    private void initSuccess(TransactionDropshipperList transactionList) {
        //
        if (transactionList.getData().get(0).getCourier().equals("JNE"))
            imgLogo = R.drawable.logo_jne;
        else if (transactionList.getData().get(0).getCourier().equals("POS"))
            imgLogo = R.drawable.logo_pos;
        else if (transactionList.getData().get(0).getCourier().equals("TIKI"))
            imgLogo = R.drawable.logo_tiki;

        imgLogoKurir.setImageResource(imgLogo);
        tvFoodName.setText(transactionList.getData().get(0).getFood_name());
        tvDetailsFood.setText(transactionList.getData().get(0).getDescription());
        tvTotalPrice.setText("Rp " + transactionList.getData().get(0).getHarga_total());
        tvLokasi.setText("Dari " + transactionList.getData().get(0).getOrigin() + " ke " + transactionList.getData().get(0).getDestination());
        tvType.setText(transactionList.getData().get(0).getType());
        tvPrice.setText("Rp " + transactionList.getData().get(0).getHarga_courier());
        tvEst.setText(transactionList.getData().get(0).getEstimate());

        //
        long seconds  = Long.parseLong(transactionList.getData().get(0).getCurr_date());
        Date expiry = new Date(seconds);
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String java_date = jdf.format(expiry);
        tvTgl.setText("Date Transaction: " + java_date);
        tvAmountFood.setText("Amount Food: " + transactionList.getData().get(0).getByk());


        // create a ProgressDrawable object which we will show as placeholder
        CircularProgressDrawable drawable = new CircularProgressDrawable(getApplicationContext());
        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);

        // set all other properties as you would see fit and start it
        drawable.start();
        Glide.with(ivImage).load(transactionList.getData().get(0).getImage_url()).centerCrop().placeholder(drawable).into(ivImage);
    }
}