package com.example.java_go_food_clone.view.bottomNav.user.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.adapter.CouponRecyclerViewAdapter;
import com.example.java_go_food_clone.model.promo.PromoResponse;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.view_model.AuthViewModel;
import com.example.java_go_food_clone.view_model.PromoViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.CompositeDisposable;

@AndroidEntryPoint
public class CheckPromoActivity extends AppCompatActivity {
    private RecyclerView rvPromo;
    private CouponRecyclerViewAdapter couponRecyclerViewAdapter;
    private PromoViewModel promoViewModel;
    private SharedPreferences profile;
    private String token;
    private ProgressBar pbLoading;
    private ConstraintLayout clNullPromo;
    private ImageView ivBack;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_promo);
        compositeDisposable = new CompositeDisposable();

        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);

        rvPromo = findViewById(R.id.rvPromo);
        pbLoading = findViewById(R.id.pbLoading);
        clNullPromo = findViewById(R.id.clNullPromo);
        ivBack = findViewById(R.id.ivBack);

        pbLoading.setVisibility(View.VISIBLE);
        clNullPromo.setVisibility(View.GONE);

        // observable
        getAllPromoFromView(token);

        // adapter
        initRecyclerView();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getAllPromoFromView(String key) {
        promoViewModel = new ViewModelProvider(this).get(PromoViewModel.class);
        promoViewModel.getAllPromoObservable().observe(this, new Observer<PromoResponse>() {
            @Override
            public void onChanged(PromoResponse promoResponse) {
                if (promoResponse != null) {
                    clNullPromo.setVisibility(View.GONE);
                    rvPromo.setVisibility(View.VISIBLE);

                    couponRecyclerViewAdapter.setListDataItems(promoResponse);
                    couponRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    clNullPromo.setVisibility(View.VISIBLE);
                    rvPromo.setVisibility(View.GONE);
                }
                pbLoading.setVisibility(View.GONE);
            }
        });
        promoViewModel.getAllPromoOfData(key);
    }

    private void initRecyclerView() {
        rvPromo.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        couponRecyclerViewAdapter = new CouponRecyclerViewAdapter();
        rvPromo.setAdapter(couponRecyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}