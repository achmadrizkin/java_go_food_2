package com.example.java_go_food_clone.view.bottomNav.user.admin_only;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.promo.PromoRequest;
import com.example.java_go_food_clone.model.promo.PromoResponse;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.view_model.AuthViewModel;
import com.example.java_go_food_clone.view_model.PromoViewModel;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InsertPromoActivity extends AppCompatActivity {
    private ImageView ivBack;
    private AppCompatButton btnSubmit;
    private TextInputEditText etCoupon, etCouponLimit, etDiscountPrice;
    private PromoViewModel promoViewModel;
    private SharedPreferences profile;
    private String token;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_promo);

        //
        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);

        ivBack = findViewById(R.id.ivBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        etCoupon = findViewById(R.id.etCoupon);
        etCouponLimit = findViewById(R.id.etCouponLimit);
        etDiscountPrice = findViewById(R.id.etDiscountPrice);
        pbLoading = findViewById(R.id.pbLoading);

        //

        // observable
        postPromoObservable();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate
                validate();
            }
        });
    }

    private void validate() {
        if (etCoupon.getText().toString().isEmpty()) {
            Toast.makeText(
                    this,
                    "Code Coupon cannot be null",
                    Toast.LENGTH_LONG
            ).show();

        } else if (etCouponLimit.getText().toString().isEmpty()) {
            Toast.makeText(
                    this,
                    "Coupon limit cannot be null",
                    Toast.LENGTH_LONG
            ).show();
        } else if (etDiscountPrice.getText().toString().isEmpty()) {
            Toast.makeText(
                    this,
                    "Discounted Price cannot be null",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            pbLoading.setVisibility(View.VISIBLE);

            // post to api
            PromoRequest a = new PromoRequest(etDiscountPrice.getText().toString(), etCouponLimit.getText().toString(), etCoupon.getText().toString());
            postPromoAPI(token, a);
        }
    }

    private void postPromoObservable() {
        promoViewModel = new ViewModelProvider(this).get(PromoViewModel.class);
        promoViewModel.postPromoResponseObservable().observe(InsertPromoActivity.this, new Observer<Response>() {
            @Override
            public void onChanged(Response recyclerData) {
                if (recyclerData == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Get data failed",
                            Toast.LENGTH_LONG
                    ).show();

                } else {
                    if (recyclerData.getSuccess().equals("1")) {
                        //
                        Toast.makeText(
                                getApplicationContext(),
                                "Insert Success",
                                Toast.LENGTH_LONG
                        ).show();
                        finish();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Insert failed",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                }
                pbLoading.setVisibility(View.GONE);
            }
        });
    }

    private void postPromoAPI(String key, PromoRequest promoRequest) {
        promoViewModel = new ViewModelProvider(this).get(PromoViewModel.class);
        promoViewModel.postPromoResponseOfData(key, promoRequest);
    }
}