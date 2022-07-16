package com.example.java_go_food_clone.view.bottomNav.user.admin_only;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperList;
import com.example.java_go_food_clone.model.transaction.TransactionList;
import com.example.java_go_food_clone.utils.MCrypt;
import com.example.java_go_food_clone.view_model.TransactionViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaymentValidationActivity extends AppCompatActivity {
    private ConstraintLayout clIsValid, clIsInvalid;
    private String decrypt, result, token;
    private int i = 0;
    private ProgressBar pbLoading;
    private TransactionViewModel viewModel, viewModel2;
    private SharedPreferences profile;
    private AppCompatButton btnBack, btnBack1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_validation);

        //
        setupView();

        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);

        Intent intent = getIntent();
        result = intent.getStringExtra("result");

        // DECYRPT
        MCrypt mcrypt = new MCrypt();
        try {
            decrypt = new String( mcrypt.decrypt( result ) );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // call api
        getTransactionByIdObservable();
        getTransactionDropshipperByIdObservable();
        getTransactionUserByIdAPI(token, decrypt);

        //
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupView() {
        clIsValid = findViewById(R.id.clIsValid1);
        clIsInvalid = findViewById(R.id.clIsInvalid1);
        pbLoading = findViewById(R.id.pbLoading);
        btnBack = findViewById(R.id.btnBack);
        btnBack1 = findViewById(R.id.btnBack1);
    }

    private void getTransactionByIdObservable() {
        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        viewModel.getTransactionUserListByIdResponseObservable().observe(this, new Observer<TransactionList>() {
            @Override
            public void onChanged(TransactionList transactionList) {
                if (transactionList != null) {
                    if (transactionList.getSuccess().equals("1")) {
                        //
                        ++i;
                        getTransactionDropshipperByIdAPI(token, decrypt);
                    } else {
                        i = 0;
                    }
                    pbLoading.setVisibility(View.GONE);
                } else {
                    i = 0;
                }
                getTransactionDropshipperByIdAPI(token, decrypt);
            }
        });
    }

    private void getTransactionDropshipperByIdObservable() {
        viewModel2 = new ViewModelProvider(this).get(TransactionViewModel.class);
        viewModel2.getTransactionDropshipperByIdResponseObservable().observe(this, new Observer<TransactionDropshipperList>() {
            @Override
            public void onChanged(TransactionDropshipperList transactionList) {
                if (transactionList.getSuccess().equals("1")) {
                    ++i;
                }

                if (i == 2 || i == 1) {
                    clIsInvalid.setVisibility(View.GONE);
                    clIsValid.setVisibility(View.VISIBLE);
                } else {
                    clIsInvalid.setVisibility(View.VISIBLE);
                    clIsValid.setVisibility(View.GONE);
                }
                pbLoading.setVisibility(View.GONE);
            }
        });
    }


    private void getTransactionDropshipperByIdAPI(String key, String id) {
        viewModel2.getTransactionDropshipperByIdResponseOfData(key, id);
    }

    private void getTransactionUserByIdAPI(String key, String id) {
        viewModel.getTransactionUserListByIdResponseOfData(key, id);
    }
}