package com.example.java_go_food_clone.view.bottomNav.food_details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.promo.PromoResponse;
import com.example.java_go_food_clone.utils.MCrypt;
import com.example.java_go_food_clone.view.bottomNav.BottomNav;
import com.example.java_go_food_clone.view.bottomNav.food_details.edit_food.EditFoodActivity;
import com.example.java_go_food_clone.view.bottomNav.food_details.make_qr.MakeQrActivity;
import com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.PayDetailsActivity;
import com.example.java_go_food_clone.view_model.FoodViewModel;
import com.example.java_go_food_clone.view_model.PromoViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.CompositeDisposable;

@AndroidEntryPoint
public class FoodDetailsActivity extends AppCompatActivity {
    private ImageView ivImage, ivBack, btnAdd, btnMinus, ivShare, ivDelete, ivEdit;
    private TextView tvFoodName, tvFoodDescription, tvPrice, tvDiscountedPrice, tvTotal;
    private TextInputEditText etCoupon;
    private Button btnCheck, btnMakeQR, btnPay;
    private ProgressBar pbLoading;
    private ConstraintLayout clHaveData;
    private FoodViewModel viewModel, viewModelDelete;
    private SharedPreferences profile;
    private String token, kd_food, price, encrypted, dPrice, pPrice, pImageUrl, role, couponCode = "", result, decrypt;
    private Integer amount_food = 1, totalPrice, totalCoupon = 1, dTotalPrice;
    private PromoViewModel promoViewModel;
    private AlertDialog.Builder builder;
    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        disposable = new CompositeDisposable();

        //
        initView();
        promoViewModel = new ViewModelProvider(this).get(PromoViewModel.class);

        //
        deleteFoodByIdObservable();
        getPromoObservable();
        builder = new AlertDialog.Builder(this);

        //
        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);
        role = profile.getString("role", null);

        //
        Uri uri = getIntent().getData();
        if (uri != null) {
            List<String> params = uri.getPathSegments();
            String kd = params.get(params.size()-1);

            kd_food = kd;
        } else {
            Intent intent = getIntent();
            kd_food = intent.getStringExtra("kd_food");
            result = intent.getStringExtra("result");

            if (kd_food == null) {
                MCrypt mcrypt = new MCrypt();
                try {
                    decrypt = new String( mcrypt.decrypt( result ) );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                kd_food = decrypt;
            }
        }

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


        // encrypted
        MCrypt mcrypt = new MCrypt();
        try {
            encrypted = MCrypt.bytesToHex( mcrypt.encrypt(kd_food));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        clHaveData.setVisibility(View.GONE);
        tvDiscountedPrice.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);

        // call api
        getFoodByKodeAPI(token, kd_food);
        getFoodByKodeObservable();

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


                Intent i = new Intent(FoodDetailsActivity.this, MakeQrActivity.class);
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

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodDetailsActivity.this, EditFoodActivity.class);
                i.putExtra("kd_food", kd_food);
                i.putExtra("food_name", tvFoodName.getText().toString());
                i.putExtra("tvFoodDescription", tvFoodDescription.getText().toString());
                i.putExtra("totalPrice", totalPrice.toString());
                startActivity(i);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount_food <= 0) {

                } else {
                    Intent i = new Intent(FoodDetailsActivity.this, PayDetailsActivity.class);

                    i.putExtra("kd_food", kd_food);
                    i.putExtra("image_url", pImageUrl);
                    i.putExtra("totalPrice", totalPrice.toString());
                    i.putExtra("food_name", tvFoodName.getText().toString());
                    i.putExtra("tvFoodDescription", tvFoodDescription.getText().toString());
                    if (couponCode.isEmpty()) {
                        i.putExtra("couponCode", "NULL");
                    } else {
                        i.putExtra("couponCode", couponCode);
                    }
                    i.putExtra("amount_food", amount_food.toString());
                    startActivity(i);

                    finish();
                }
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Are you sure to delete ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFoodByKodeAPI(token, kd_food);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String body = "Temukan " + tvFoodName.getText().toString() + " seharga Rp. " + pPrice + " Dapatkan sekarang juga di Go-Mbe! " + " https://www.achmadrizkin.my.id/java_go_mbe/" + kd_food;
                i.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(i, "Share using"));
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
        ivShare = findViewById(R.id.ivShare);
        ivDelete = findViewById(R.id.ivDelete);
        ivEdit = findViewById(R.id.ivEdit);
    }

    private void getFoodByKodeAPI(String key, String kd_food) {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModel.getFoodByKodeOfData(key, kd_food);
    }

    private void deleteFoodByKodeAPI(String key, String kd_food) {
        viewModelDelete = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModelDelete.deleteFoodByIdOfData(key, kd_food);
    }

    private void deleteFoodByIdObservable() {
        viewModelDelete = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModelDelete.deleteFoodByIdObservable().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response != null) {
                    if (response.getSuccess().equals("1")) {
                        Toast.makeText(getApplicationContext(), "Delete Success", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(FoodDetailsActivity.this, BottomNav.class);
                        startActivity(i);
                    }
                }
            }
        });
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
                        tvDiscountedPrice.setText("Rp. "  + dPrice);
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

                            //
                            couponCode = recyclerData.getPromo().get(0).getKd_promo();

                            tvPrice.setText("Rp. " + totalPrice.toString());

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
        disposable.clear();
    }
}