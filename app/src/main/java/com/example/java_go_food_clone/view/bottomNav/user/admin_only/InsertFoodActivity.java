package com.example.java_go_food_clone.view.bottomNav.user.admin_only;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.FoodRequest;
import com.example.java_go_food_clone.model.user.UpdateProfileUser;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.utils.ImageBase64Converter;
import com.example.java_go_food_clone.view_model.AuthViewModel;
import com.example.java_go_food_clone.view_model.FoodViewModel;

import java.io.FileNotFoundException;
import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InsertFoodActivity extends AppCompatActivity {
    private ImageView ivBack, ivInsertImage;
    private static final int PICK_IMAGE = 100;
    private String imageBase64 = "";
    private Button btnSubmit;
    public static String POST_KEY = "POST_KEY", token, email;
    private FoodRequest foodRequest;
    private EditText etFoodName, etFoodPrice, etFoodDescription;
    private FoodViewModel viewModel;
    private ProgressBar pbLoading;
    private SharedPreferences profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);

        //
        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);
        email = profile.getString("email", null);

        ivBack = findViewById(R.id.ivBack);
        ivInsertImage = findViewById(R.id.ivInsertImage);
        etFoodName = findViewById(R.id.etCoupon);
        etFoodPrice = findViewById(R.id.etFoodPrice);
        etFoodDescription = findViewById(R.id.etFoodDescription);
        btnSubmit = findViewById(R.id.btnSubmit);
        pbLoading = findViewById(R.id.pbLoading);

        //
        handleIntent();
        updateProfileUserObservable();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivInsertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        if (imageBase64.isEmpty()) {
            Toast.makeText(InsertFoodActivity.this, "Image Cannot Empty", Toast.LENGTH_LONG).show();
        } else if (etFoodPrice.getText().toString().isEmpty()) {
            Toast.makeText(InsertFoodActivity.this, "Price Cannot Empty", Toast.LENGTH_LONG).show();
        } else if (etFoodName.getText().toString().isEmpty()) {
            Toast.makeText(InsertFoodActivity.this, "Food Name Cannot Empty", Toast.LENGTH_LONG).show();
        } else if (etFoodDescription.getText().toString().isEmpty()) {
            Toast.makeText(InsertFoodActivity.this, "Food Description Cannot Empty", Toast.LENGTH_LONG).show();
        } else {
            // post api
            pbLoading.setVisibility(View.VISIBLE);

            String uniqueId = UUID.randomUUID().toString();
            FoodRequest foodRequest = new FoodRequest(etFoodName.getText().toString(), etFoodDescription.getText().toString(), imageBase64, etFoodPrice.getText().toString(), uniqueId);
            postFoodUserAPI(token, foodRequest);
        }
    }

    private void postFoodUserAPI(String key, FoodRequest foodRequest) {
        viewModel.postFoodOfData(key, foodRequest);
    }

    private void updateProfileUserObservable() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModel.postFoodFoodObservable().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response recyclerData) {
                if (recyclerData == null) {
                    //

                } else {
                    if (recyclerData.getSuccess().equals("1")) {
                        Toast.makeText(InsertFoodActivity.this, "Insert Success", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(InsertFoodActivity.this, "Insert Failed", Toast.LENGTH_LONG).show();
                    }
                    pbLoading.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();

            ivInsertImage.setImageURI(imageUri);
            ivInsertImage.setVisibility(View.VISIBLE);

            try {
                imageBase64 = ImageBase64Converter.uriToBase64(this, imageUri);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleIntent() {
        foodRequest = getIntent().getParcelableExtra(POST_KEY);
        if (foodRequest != null) {
            etFoodDescription.setText(foodRequest.getDescription());
            etFoodName.setText(foodRequest.getFood_name());
            etFoodPrice.setText(foodRequest.getHarga());

            Glide.with(this)
                    .asBitmap()
                    .load(foodRequest.getImage_url())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            ivInsertImage.setImageBitmap(resource);
                            imageBase64 = ImageBase64Converter.bitmapToBase64(InsertFoodActivity.this, resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

}