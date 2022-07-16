package com.example.java_go_food_clone.view.bottomNav.user.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.promo.PromoResponse;
import com.example.java_go_food_clone.utils.MCrypt;
import com.example.java_go_food_clone.view.bottomNav.food_details.FoodDetailsActivity;
import com.example.java_go_food_clone.view.bottomNav.food_details.make_qr.MakeQrActivity;
import com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.PayDetailsActivity;
import com.example.java_go_food_clone.view_model.FoodViewModel;
import com.example.java_go_food_clone.view_model.PromoViewModel;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.CompositeDisposable;

@AndroidEntryPoint
public class QrDetailsActivity extends AppCompatActivity {
    private String decrypted;
    private ImageView ivImage, ivBack, btnAdd, btnMinus, ivDelete, ivEdit;
    private TextView tvFoodName, tvFoodDescription, tvPrice, tvDiscountedPrice, tvTotal;
    private TextInputEditText etCoupon;
    private Button btnCheck, btnMakeQR, btnPay;
    private ProgressBar pbLoading;
    private ConstraintLayout clHaveData;
    private FoodViewModel viewModel;
    private SharedPreferences profile;
    private String token, kd_food, price, encrypted, dPrice, pPrice, pImageUrl, role;
    private Integer amount_food = 1, totalPrice, totalCoupon = 1, dTotalPrice;
    private PromoViewModel promoViewModel;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_details);
        compositeDisposable = new CompositeDisposable();

        //
        initView();
        promoViewModel = new ViewModelProvider(this).get(PromoViewModel.class);

        // decrpty first
        Intent intent = getIntent();
        String result = intent.getExtras().getString("result");

        //
        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);
        role = profile.getString("role", null);

        if (role.equals("user")) {
            ivDelete.setVisibility(View.GONE);
            ivEdit.setVisibility(View.GONE);
        } else {
            ivDelete.setVisibility(View.VISIBLE);
            ivEdit.setVisibility(View.VISIBLE);
        }

        if (amount_food == 1) {
            btnMinus.setVisibility(View.GONE);
        } else {
            btnMinus.setVisibility(View.VISIBLE);
        }

        // decrypted
        MCrypt mcrypt = new MCrypt();
        try {
            decrypted = new String( mcrypt.decrypt( result ) );
            encrypted = MCrypt.bytesToHex( mcrypt.encrypt(decrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        clHaveData.setVisibility(View.GONE);
        tvDiscountedPrice.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);


        // call api
        getFoodByKodeAPI(token, decrypted);
        getFoodByKodeObservable();
        getPromoObservable();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                pbLoading.setVisibility(View.GONE);

                if (etCoupon.getText().toString().isEmpty()) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Code Coupon cannot null",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    // call api to check code
                    getPromoKodeAPI(token, etCoupon.getText().toString());

                    //
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount_food == 0) {
                    btnMinus.setVisibility(View.GONE);
                } else {
                    btnMinus.setVisibility(View.VISIBLE);
                }
                //
                amount_food++;
                tvTotal.setText(amount_food.toString());
                totalPrice = Integer.parseInt(price) * amount_food;
                dTotalPrice = Integer.parseInt(dPrice) * amount_food;

                //
                tvPrice.setText("Rp. " + totalPrice.toString());
                tvDiscountedPrice.setText("Rp. " + dTotalPrice.toString());
            }
        });

        btnMakeQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QrDetailsActivity.this, MakeQrActivity.class);
                i.putExtra("kd_food", encrypted.toString());
                startActivity(i);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount_food == 2) {
                    btnMinus.setVisibility(View.GONE);
                } else {
                    btnMinus.setVisibility(View.VISIBLE);
                }
                //
                amount_food--;
                totalPrice = Integer.parseInt(price) * amount_food;
                dTotalPrice = Integer.parseInt(dPrice) * amount_food;

                tvPrice.setText("Rp. " + totalPrice.toString());
                tvDiscountedPrice.setText("Rp. " + dTotalPrice.toString());
                tvTotal.setText(amount_food.toString());
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount_food <= 0) {

                } else {
                    Intent i = new Intent(QrDetailsActivity.this, PayDetailsActivity.class);

                    i.putExtra("kd_food", decrypted);
                    i.putExtra("image_url", pImageUrl);
                    i.putExtra("totalPrice", totalPrice.toString());
                    i.putExtra("food_name", tvFoodName.getText().toString());
                    i.putExtra("total", amount_food);
                    i.putExtra("tvFoodDescription", tvFoodDescription.getText().toString());
                    startActivity(i);

                    finish();
                }
            }
        });

        //
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        ivImage = findViewById(R.id.ivImage);
        tvFoodName = findViewById(R.id.tvFoodName);
        tvFoodDescription = findViewById(R.id.tvFoodDescription);
        tvPrice = findViewById(R.id.tvPrice);
        tvDiscountedPrice = findViewById(R.id.tvDiscountedPrice);
        tvTotal = findViewById(R.id.tvTotal);
        etCoupon = findViewById(R.id.etCoupon);
        btnCheck = findViewById(R.id.btnCheck);
        btnAdd = findViewById(R.id.btnAdd);
        btnMinus = findViewById(R.id.btnMinus);
        btnCheck = findViewById(R.id.btnCheck);
        btnMakeQR = findViewById(R.id.btnMakeQR);
        btnPay = findViewById(R.id.btnPay);
        pbLoading = findViewById(R.id.pbLoading);
        clHaveData = findViewById(R.id.clHaveData);
        ivBack = findViewById(R.id.ivBack);
        ivDelete = findViewById(R.id.ivDelete);
        ivEdit = findViewById(R.id.ivEdit);
    }

    private void getFoodByKodeAPI(String key, String kd_food) {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModel.getFoodByKodeOfData(key, kd_food);
    }

    private void getFoodByKodeObservable() {
        viewModel.getFoodByKodeObservable().observe(this, new Observer<ListFoodResponse>() {
            @Override
            public void onChanged(ListFoodResponse recyclerData) {
                if (recyclerData == null) {
                    // data is null

                } else {
                    if (recyclerData.getSuccess().equals("1")) {
                        // create a ProgressDrawable object which we will show as placeholder
                        CircularProgressDrawable drawable = new CircularProgressDrawable(getApplicationContext());
                        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
                        drawable.setCenterRadius(30f);
                        drawable.setStrokeWidth(5f);
                        // set all other properties as you would see fit and start it
                        drawable.start();

                        // data is not empty
                        price = recyclerData.getUser().get(0).getHarga();
                        dPrice = recyclerData.getUser().get(0).getHarga();
                        pPrice = recyclerData.getUser().get(0).getHarga();
                        pImageUrl = recyclerData.getUser().get(0).getImage_url();
                        totalPrice = Integer.parseInt(recyclerData.getUser().get(0).getHarga());

                        tvFoodName.setText(recyclerData.getUser().get(0).getFood_name());
                        tvFoodDescription.setText(recyclerData.getUser().get(0).getDescription());
                        tvDiscountedPrice.setText(recyclerData.getUser().get(0).getHarga());
                        tvPrice.setText("Rp. " + price); // after get discounted price
                        Glide.with(ivImage).load(recyclerData.getUser().get(0).getImage_url()).centerCrop().placeholder(drawable).into(ivImage);

                        clHaveData.setVisibility(View.VISIBLE);
                    } else if (recyclerData.getSuccess().equals("2")) {
                        // data is null
                    }
                }
                pbLoading.setVisibility(View.GONE);
            }
        });

    }

    private void getPromoObservable() {
        promoViewModel.getPromoByKodeObservable().observe(this, new Observer<PromoResponse>() {
            @Override
            public void onChanged(PromoResponse recyclerData) {
                if (recyclerData == null) {
                    //

                } else {
                    if (recyclerData.getSuccess().equals("1")) {
                        // make only 1 coupon for 1 orders
                        if (totalCoupon == 1) {
                            //
                            String discountPrices = recyclerData.getPromo().get(0).getDiscountPrice();
                            Integer pricex = Integer.parseInt(price) - Integer.parseInt(discountPrices);
                            price = String.valueOf(pricex);
                            totalPrice = Integer.parseInt(price) * amount_food;

                            tvPrice.setText(totalPrice.toString());

                            tvDiscountedPrice.setVisibility(View.VISIBLE);
                            tvDiscountedPrice.setPaintFlags(tvDiscountedPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            //
                            totalCoupon++;

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Coupon Applied",
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "You only can use 1 coupon",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Code Coupon incorrect",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
                pbLoading.setVisibility(View.GONE);
            }
        });

    }

    private void getPromoKodeAPI(String key, String kd_food) {
        promoViewModel.getPromoByKodeOfData(key, kd_food);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}