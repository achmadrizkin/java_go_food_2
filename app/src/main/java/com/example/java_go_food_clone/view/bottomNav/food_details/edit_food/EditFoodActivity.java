package com.example.java_go_food_clone.view.bottomNav.food_details.edit_food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.EditFoodRequest;
import com.example.java_go_food_clone.utils.ImageBase64Converter;
import com.example.java_go_food_clone.view.bottomNav.user.account.EditProfileActivity;
import com.example.java_go_food_clone.view_model.FoodViewModel;
import com.example.java_go_food_clone.view_model.TransactionViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditFoodActivity extends AppCompatActivity {
    private ImageView ivPicture;
    private TextInputEditText etFoodName, etFoodDetails, etHarga;
    private AppCompatButton btnSave;
    private EditFoodRequest editFoodRequest;
    private String imageBase64 = "";
    public static String POST_KEY = "POST_KEY";
    private static final int PICK_IMAGE = 100;
    private ProgressBar pbLoading;
    private String kd_food, food_name, totalPrice, tvFoodDescription;
    private FoodViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        Intent intent = getIntent();
        kd_food = intent.getStringExtra("kd_food");
        food_name = intent.getStringExtra("food_name");
        tvFoodDescription = intent.getStringExtra("tvFoodDescription");
        totalPrice = intent.getStringExtra("totalPrice");

        //
        setupView();
        updateFoodByIdObservable();

        handleIntent();

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

    private void updateFoodByIdObservable() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModel.updateFoodByIdObservable().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response.getSuccess().equals("1")) {
                    Toast.makeText(EditFoodActivity.this, "Update Success", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(EditFoodActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
                }
                pbLoading.setVisibility(View.GONE);
            }
        });
    }

    private void handleIntent() {
        editFoodRequest = getIntent().getParcelableExtra(POST_KEY);
        if (editFoodRequest != null) {
            etFoodName.setText(editFoodRequest.getFood_name());
            etFoodDetails.setText(editFoodRequest.getDescription());
            etHarga.setText(editFoodRequest.getHarga());

            Glide.with(this)
                    .asBitmap()
                    .load(editFoodRequest.getImage_url())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            ivPicture.setImageBitmap(resource);
                            imageBase64 = ImageBase64Converter.bitmapToBase64(EditFoodActivity.this, resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
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

    private void validate() {
        if (etFoodName.getText().toString().isEmpty()) {

        } else if (etFoodDetails.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Password Cannot Null",
                    Toast.LENGTH_LONG
            ).show();

        } else if (etHarga.getText().toString().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Name Cannot Null",
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

            EditFoodRequest a = new EditFoodRequest(etFoodName.getText().toString(), etFoodDetails.getText().toString(), etHarga.getText().toString(), imageBase64, kd_food);
            updateFoodByIdOfData(a);
        }
    }

    //
    private void updateFoodByIdOfData(EditFoodRequest editFoodRequest) {
        viewModel.updateFoodByIdOfData(editFoodRequest);
    }

    //
    private void setupView() {
        etFoodName = findViewById(R.id.etFoodName);
        etHarga = findViewById(R.id.etHarga);
        etFoodDetails = findViewById(R.id.etFoodDetails);
        btnSave = findViewById(R.id.btnSave);
        ivPicture = findViewById(R.id.ivPicture);
        pbLoading = findViewById(R.id.pbLoading);

        etFoodDetails.setText(tvFoodDescription);
        etFoodName.setText(food_name);
        etHarga.setText(totalPrice);
    }
}