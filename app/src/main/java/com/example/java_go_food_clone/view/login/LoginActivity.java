package com.example.java_go_food_clone.view.login;

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
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.model.user.UserRequest;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.view.bottomNav.BottomNav;
import com.example.java_go_food_clone.view.sign_up.SignUpActivity;
import com.example.java_go_food_clone.view_model.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ImageView ivBack;
    private int isChecked = 0;
    private CheckBox checkBox;
    private AppCompatButton btnLogin;
    private TextInputEditText etEmail, etPassword;
    private AuthViewModel viewModel;
    private ProgressBar pbLoading;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivBack = findViewById(R.id.ivBack);
        checkBox = findViewById(R.id.checkBox);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        pbLoading = findViewById(R.id.pbLoading);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        //
        signUpRequestObservable();
        btnLogin.setVisibility(View.GONE);

        pbLoading.setVisibility(View.GONE);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++isChecked;
                if (isChecked%2  == 1) {
                    btnLogin.setVisibility(View.VISIBLE);
                } else {
                    btnLogin.setVisibility(View.GONE);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                validate();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void validate() {
        if (etEmail.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Email cannot be empty",
                    Toast.LENGTH_LONG
            ).show();
        } else if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Password cannot be empty",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            pbLoading.setVisibility(View.VISIBLE);

            signUpRequestAPI(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    private void signUpRequestAPI(String email, String password) {
        viewModel.signInOfData(email, password);
    }

    private void signUpRequestObservable() {
        viewModel.signInObservable().observe(this, new Observer<UserAuthResponse>() {
            @Override
            public void onChanged(UserAuthResponse recyclerData) {
                if (recyclerData == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Login failed",
                            Toast.LENGTH_LONG
                    ).show();

                    pbLoading.setVisibility(View.GONE);
                } else {
                    if (recyclerData.getSuccess().equals("1")) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Login success",
                                Toast.LENGTH_LONG
                        ).show();

                        // SAVED LOGIN SESSION
                        getSharedPreferences("login_session", MODE_PRIVATE)
                                .edit()
                                .putString("token", recyclerData.getUser().get(0).getToken())
                                .putString("email", recyclerData.getUser().get(0).getEmail())
                                .putString("username", recyclerData.getUser().get(0).getUser_name())
                                .putString("role", recyclerData.getUser().get(0).getRole())
                                .apply();

                        //
                        Intent i = new Intent(LoginActivity.this, BottomNav.class);
                        startActivity(i);
                    } else if (recyclerData.getSuccess().equals("2")) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Password salah",
                                Toast.LENGTH_LONG
                        ).show();
                    } else if (recyclerData.getSuccess().equals("3")) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Email salah",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                    pbLoading.setVisibility(View.GONE);
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