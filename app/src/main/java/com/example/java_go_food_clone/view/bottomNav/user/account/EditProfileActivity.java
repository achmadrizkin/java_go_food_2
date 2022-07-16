package com.example.java_go_food_clone.view.bottomNav.user.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.user.UpdateProfileUser;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.utils.ImageBase64Converter;
import com.example.java_go_food_clone.view.auth.AuthActivity;
import com.example.java_go_food_clone.view.bottomNav.BottomNav;
import com.example.java_go_food_clone.view.login.LoginActivity;
import com.example.java_go_food_clone.view_model.AuthViewModel;

import java.io.FileNotFoundException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditProfileActivity extends AppCompatActivity {
    private ImageView ivBack, ivPicture;
    private EditText etName, etPassword, etEmail, etPasswordNew;
    private AppCompatButton btnSave;
    private static final int PICK_IMAGE = 100;
    private String imageBase64 = "", token, email, username, imageUrl;
    private UpdateProfileUser updateProfileUser;
    public static String POST_KEY = "POST_KEY";
    private SharedPreferences profile;
    private ProgressBar pbLoading;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //
        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);
        email = profile.getString("email", null);
        username = profile.getString("username", null);

        Intent intent = getIntent();
        imageUrl = intent.getStringExtra("imageUrl");;

        ivBack = findViewById(R.id.ivBack);
        ivPicture = findViewById(R.id.ivPicture);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);
        pbLoading = findViewById(R.id.pbLoading);
        etPasswordNew = findViewById(R.id.etPasswordNew);

        //
        handleIntent();

        etName.setText(username);
        etEmail.setText(email);

        //
        updateProfileUserObservable();
        signUpRequestObservable();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        if (etEmail.getText().toString().isEmpty()) {

        } else if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Password Cannot Null",
                    Toast.LENGTH_LONG
            ).show();

        } else if (etName.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Name Cannot Null",
                    Toast.LENGTH_LONG
            ).show();

        } else if (etPasswordNew.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Password New Cannot Null",
                    Toast.LENGTH_LONG
            ).show();

        } else if(imageBase64.isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Image cannot Null",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            pbLoading.setVisibility(View.VISIBLE);

            // check password first
            signUpRequestAPI(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();

            ivPicture.setImageURI(imageUri);
            ivPicture.setVisibility(View.VISIBLE);
            try {
                imageBase64 = ImageBase64Converter.uriToBase64(this, imageUri);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleIntent() {
        updateProfileUser = getIntent().getParcelableExtra(POST_KEY);
        if (updateProfileUser != null) {
            etName.setText(updateProfileUser.getUser_name());
            etPassword.setText(updateProfileUser.getPassword());

            Glide.with(this)
                    .asBitmap()
                    .load(updateProfileUser.getImage_url())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            ivPicture.setImageBitmap(resource);
                            imageBase64 = ImageBase64Converter.bitmapToBase64(EditProfileActivity.this, resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    private void updateProfileUserAPI(UpdateProfileUser updateProfileUser) {
        viewModel.updateProfileUserOfData(updateProfileUser);
    }

    private void updateProfileUserObservable() {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.updateProfileUserObservable().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse recyclerData) {
                if (recyclerData == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Update Failed",
                            Toast.LENGTH_LONG
                    ).show();

                    pbLoading.setVisibility(View.GONE);
                } else {
                    if (recyclerData.getStatus().equals("1")) {
                        // CLEAR DATA IN SHARED PREFF
                        profile.edit().clear().apply();

                        // INTENT
                        Intent intent = new Intent(EditProfileActivity.this,AuthActivity.class);
                        startActivity(intent);

                        Toast.makeText(
                                getApplicationContext(),
                                "Login Again",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                    pbLoading.setVisibility(View.GONE);
                }
            }
        });
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
                            "Password Failed",
                            Toast.LENGTH_LONG
                    ).show();

                    pbLoading.setVisibility(View.GONE);
                } else {
                    if (recyclerData.getSuccess().equals("1")) {
                        if (imageBase64.isEmpty()) {
                            UpdateProfileUser a = new UpdateProfileUser(etEmail.getText().toString(), etPasswordNew.getText().toString(), etName.getText().toString(), imageUrl, token);
                            updateProfileUserAPI(a);
                        } else {
                            UpdateProfileUser a = new UpdateProfileUser(etEmail.getText().toString(), etPasswordNew.getText().toString(), etName.getText().toString(), imageBase64, token);
                            updateProfileUserAPI(a);
                        }

                        finish();
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
}