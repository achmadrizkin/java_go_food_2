package com.example.java_go_food_clone.view.sign_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.user.UserRequest;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.view_model.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class SignUpActivity extends AppCompatActivity {
    private ProgressBar pbLoading;
    private CheckBox checkBox;
    private ImageView ivBack;
    private AppCompatButton btnSignUp;
    private int isChecked = 0; // even = checked
    private TextInputEditText etInputName, etInputEmail, etPassword, etConfirmPassword;
    private AuthViewModel viewModel;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        checkBox = findViewById(R.id.checkBox);
        btnSignUp = findViewById(R.id.btnSignUp);
        pbLoading = findViewById(R.id.pbLoading);
        etInputName = findViewById(R.id.etInputName);
        etInputEmail = findViewById(R.id.etInputEmail);
        ivBack = findViewById(R.id.ivBack);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        pbLoading.setVisibility(View.GONE);
        btnSignUp.setEnabled(false);

        signUpRequestObservable();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChecked %2 == 0) {
                    isChecked++;
                    btnSignUp.setEnabled(true);
                } else {
                    isChecked++;
                    btnSignUp.setEnabled(false);
                }
            }
        });

        ivBack.setOnClickListener(view -> {
            finish();
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        if (etInputEmail.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Email cannot be empty",
                    Toast.LENGTH_LONG
            ).show();
        } else if (etInputName.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Username cannot be empty",
                    Toast.LENGTH_LONG
            ).show();
        } else if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Password cannot be empty",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            passwordMatched();
        }
    }

    private void passwordMatched() {
        if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            pbLoading.setVisibility(View.VISIBLE);

            UserRequest a = new UserRequest(etInputEmail.getText().toString(), etPassword.getText().toString(), etInputName.getText().toString());
            signUpRequestAPI(a);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Password doesn't matched",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void signUpRequestAPI(UserRequest userRequest) {
        viewModel.signUpOfData(userRequest);
    }

    private void signUpRequestObservable() {
        viewModel.signUpObservable().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse recyclerData) {
                if (recyclerData == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Sign up failed",
                            Toast.LENGTH_LONG
                    ).show();

                    pbLoading.setVisibility(View.GONE);
                } else {
                    if (recyclerData.getStatus().equals("1")) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Sign up success",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                    pbLoading.setVisibility(View.GONE);

                    finish();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}