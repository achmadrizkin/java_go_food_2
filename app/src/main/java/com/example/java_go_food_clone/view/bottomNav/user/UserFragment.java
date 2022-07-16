package com.example.java_go_food_clone.view.bottomNav.user;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.bumptech.glide.Glide;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.view.auth.AuthActivity;
import com.example.java_go_food_clone.view.bottomNav.food_details.FoodDetailsActivity;
import com.example.java_go_food_clone.view.bottomNav.user.account.CheckPromoActivity;
import com.example.java_go_food_clone.view.bottomNav.user.account.EditProfileActivity;
import com.example.java_go_food_clone.view.bottomNav.user.account.QrDetailsActivity;
import com.example.java_go_food_clone.view.bottomNav.user.admin_only.InsertFoodActivity;
import com.example.java_go_food_clone.view.bottomNav.user.admin_only.InsertPromoActivity;
import com.example.java_go_food_clone.view.bottomNav.user.admin_only.PaymentValidationActivity;
import com.example.java_go_food_clone.view_model.AuthViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.CompositeDisposable;

@AndroidEntryPoint
public class UserFragment extends Fragment {
    private ProgressBar pbLoading;
    private String token, email, username, imageUrl;
    private SharedPreferences profile;
    private AuthViewModel viewModel;
    private ImageView ivEdit, ivPicture;
    private TextView tvDisplayName, tvEmail, tvRole;
    private ConstraintLayout clHaveData, clAdmin, clAddFood, clAddPromo, clScanBarcode, clLogOut, clPromo, clPaymentValidation;
    private static final int REQUEST_CODE_QR_SCAN = 101, REQUEST_SCAN_VALIDATION = 200;
    private CompositeDisposable compositeDisposable;
    private int x = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        compositeDisposable = new CompositeDisposable();

        initAllView(view);

        //

        //
        profile = getActivity().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);
        email = profile.getString("email", null);
        username = profile.getString("username", null);

        clHaveData.setVisibility(View.GONE);
        getUserDetailsByEmailAPI(username, token);
        getUserDetailsObservable();

        //
        clAddPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InsertPromoActivity.class);
                startActivity(i);
            }
        });

        clPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CheckPromoActivity.class);
                startActivity(i);
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                i.putExtra("imageUrl", imageUrl);
                startActivity(i);
            }
        });

        clPaymentValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PaymentValidationActivity.class);
                startActivity(i);
            }
        });

        clAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InsertFoodActivity.class);
                startActivity(i);
            }
        });

        clLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CLEAR DATA IN SHARED PREFF
                profile.edit().clear().apply();

                // INTENT
                startActivity(new Intent(getActivity(), AuthActivity.class));
                getActivity().finish();
            }
        });

        clScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SCAN BARCODE
                x = 1;
                permissionHandler();
            }
        });

        clPaymentValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 2;
                permissionHandler();
            }
        });

        return view;
    }

    private void initAllView(View view) {
        pbLoading = view.findViewById(R.id.pbLoading);
        tvDisplayName = view.findViewById(R.id.textView10);
        tvEmail = view.findViewById(R.id.textView11);
        clHaveData = view.findViewById(R.id.clHaveData);
        tvRole = view.findViewById(R.id.tvRole);
        clAdmin = view.findViewById(R.id.clAdmin);
        clAddFood = view.findViewById(R.id.clAddFood);
        clAddPromo = view.findViewById(R.id.clAddPromo);
        clScanBarcode = view.findViewById(R.id.clScanBarcode);
        clLogOut = view.findViewById(R.id.clLogOut);
        clPromo = view.findViewById(R.id.clPromo);
        ivEdit = view.findViewById(R.id.ivEdit);
        clPaymentValidation = view.findViewById(R.id.clPaymentValidation);
        ivPicture = view.findViewById(R.id.imageView4);
    }

    private void getUserDetailsByEmailAPI(String username1, String key) {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.getUserDetailsByEmailOfData(username1, key);
    }

    private void getUserDetailsObservable() {
        viewModel.getUserDetailsByEmailObservable().observe(getActivity(), new Observer<UserAuthResponse>() {
            @Override
            public void onChanged(UserAuthResponse recyclerData) {
                if (recyclerData == null) {
                    Toast.makeText(
                            getActivity(),
                            "Get data failed",
                            Toast.LENGTH_LONG
                    ).show();

                    pbLoading.setVisibility(View.GONE);
                } else {
                    if (recyclerData.getSuccess().equals("1")) {
                        //
                        clHaveData.setVisibility(View.VISIBLE);
                        tvDisplayName.setText(recyclerData.getUser().get(0).getUser_name());
                        tvEmail.setText(recyclerData.getUser().get(0).getEmail());
                        tvRole.setText(recyclerData.getUser().get(0).getRole());
                        imageUrl = recyclerData.getUser().get(0).getImage_url();

                        if (imageUrl.isEmpty() || imageUrl.equals("")) {

                        } else {
                            CircularProgressDrawable drawable = new CircularProgressDrawable(ivPicture.getContext());
                            drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
                            drawable.setCenterRadius(30f);
                            drawable.setStrokeWidth(5f);
                            // set all other properties as you would see fit and start it
                            drawable.start();

                            Glide.with(ivPicture).load(imageUrl).placeholder(drawable).centerCrop().into(ivPicture);
                        }

                        if (recyclerData.getUser().get(0).getRole().equals("user")) {
                            clAdmin.setVisibility(View.GONE);
                        } else if (recyclerData.getUser().get(0).getRole().equals("admin")) {
                            clAdmin.setVisibility(View.VISIBLE);
                        }

                        pbLoading.setVisibility(View.GONE);
                    } else if (recyclerData.getSuccess().equals("2")) {
                        // CLEAR DATA IN SHARED PREFF
                        profile.edit().clear().apply();

                        // INTENT
                        startActivity(new Intent(getActivity(), AuthActivity.class));
                        getActivity().finish();
                    }

                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) {
            Log.d(TAG,"COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;

            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;
        }

        // QR CODE SUCCESS
        //TODO: PASS THIS TO NEW ACTIVITY
        if(requestCode == REQUEST_CODE_QR_SCAN) {
            if(data==null)
                return;

            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
//            Log.d(TAG,"Have scan result in your app activity :"+ result);
//            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//            alertDialog.setTitle("Scan result");
//            alertDialog.setMessage(result);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();

            // intent
            if (x == 1) {
                Intent p = new Intent(getActivity(), FoodDetailsActivity.class);
                p.putExtra("result", result);
                startActivity(p);
            } else if (x == 2) {
                Intent p = new Intent(getActivity(), PaymentValidationActivity.class);
                p.putExtra("result", result);
                startActivity(p);
            }
        }
    }

    private void permissionHandler() {
        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent(getActivity(), QrCodeActivity.class);
                        startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        permissionDeniedResponse.getRequestedPermission();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}