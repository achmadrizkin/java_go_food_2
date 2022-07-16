package com.example.java_go_food_clone.view.bottomNav.food_details.pay_details;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.adapter.OrderRecyclerViewAdapter;
import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.cost.DataType;
import com.example.java_go_food_clone.model.cost.ResponseCost;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperRequest;
import com.example.java_go_food_clone.model.transaction.TransactionRequest;
import com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.maps.MapsActivity;
import com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.payment_success.PaymentSuccessActivity;
import com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.searchCity.SearchCityActivity;
import com.example.java_go_food_clone.view_model.RajaOngkirViewModel;
import com.example.java_go_food_clone.view_model.TransactionViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PayDetailsActivity extends AppCompatActivity implements OrderRecyclerViewAdapter.OrderRecyclerViewClickListener {
    private Spinner spOrderAs;
    private ConstraintLayout clDropshipper, clCostumer;
    private TextInputEditText etCurrentCity, etDestinationCity, etGetCurrentLocation, etName;
    private RecyclerView rvOngkir;
    private OrderRecyclerViewAdapter orderRecyclerViewAdapter;
    private RajaOngkirViewModel viewModel;
    private AppCompatButton btnSubmit, btnPay, btnPayCostumer, btnPayDropshipper;
    private String sIx, image_url, kd_food, totalPrice, food_name, foodDescription, valSpinner, source_id, destination_id, getLocation_id, couponCode = "0", token, amount_food, email, courier2, estimate, type, priceRajaOngkir;
    private ImageView ivImage;
    private TextView tvHarga, tvFoodDescription, tvFoodName, tvOngkir, tvTotal;
    private ProgressBar pbLoading;
    private int i = 0, total;
    private TransactionViewModel transactionViewModel, transactionViewModel2;
    private SharedPreferences profile;
    private long timeNow = Instant.now().toEpochMilli();


    private static final int REQUEST_SOURCE = 1;
    private static final int REQUEST_DESTINATION = 2;
    private static final int REQUEST_GET_CURRENT_LOCATION = 200;

    List<DataType> output = new ArrayList<>();
    List<String> courier = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_details);

        initView();
        btnPayDropshipper.setVisibility(View.GONE);

        //
        profile = getApplicationContext().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);
        email = profile.getString("email", null);

        Intent intent = getIntent();
        image_url = intent.getStringExtra("image_url");
        kd_food = intent.getStringExtra("kd_food");
        totalPrice = intent.getStringExtra("totalPrice");
        food_name = intent.getStringExtra("food_name");
        foodDescription = intent.getStringExtra("tvFoodDescription");
        couponCode = intent.getStringExtra("couponCode");
        amount_food = intent.getStringExtra("amount_food");

        // create a ProgressDrawable object which we will show as placeholder
        CircularProgressDrawable drawable = new CircularProgressDrawable(getApplicationContext());
        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        // set all other properties as you would see fit and start it
        drawable.start();

        Glide.with(ivImage).load(image_url).centerCrop().placeholder(drawable).into(ivImage);
        tvHarga.setText("Rp. " + totalPrice);
        tvTotal.setText("Total: Rp. " + totalPrice);
        tvFoodName.setText(food_name);
        tvFoodDescription.setText(foodDescription);

        //
        pbLoading.setVisibility(View.GONE);
        initRecyclerView();
        getCostObservable();
        getPOSCostObservable();
        getTIKICostObservable();
        postTransactionObservable();
        postTransactionDropshipperObservable();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderAs.setAdapter(adapter);

        spOrderAs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valSpinner = adapterView.getItemAtPosition(i).toString();

                if (valSpinner.equals("customer")) {
                    clCostumer.setVisibility(View.VISIBLE);
                    clDropshipper.setVisibility(View.GONE);

                } else {
                    clDropshipper.setVisibility(View.VISIBLE);
                    clCostumer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //
        etCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchCityActivity.class);
                intent.putExtra("requestCode", REQUEST_SOURCE);
                startActivityForResult(intent, REQUEST_SOURCE);
            }
        });

        etDestinationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchCityActivity.class);
                intent.putExtra("requestCode", REQUEST_DESTINATION);
                startActivityForResult(intent, REQUEST_DESTINATION);
            }
        });

        etGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("requestCode", REQUEST_GET_CURRENT_LOCATION);
                startActivityForResult(intent, REQUEST_GET_CURRENT_LOCATION);
            }
        });

        btnPayCostumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                if (etGetCurrentLocation.getText().toString().isEmpty() || etName.getText().toString().isEmpty() || etName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    //
                    pbLoading.setVisibility(View.VISIBLE);

                    //
                    String now = Long.toString(timeNow);
                    TransactionRequest a = new TransactionRequest(amount_food, email, couponCode, totalPrice, kd_food, etGetCurrentLocation.getText().toString(), now);
                    postTransaction(token, a);
                }
            }
        });

        btnPayDropshipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCurrentCity.getText().toString().isEmpty() || etDestinationCity.getText().toString().isEmpty() || etName.getText().toString().isEmpty()) {

                } else {


                    if (source_id.isEmpty() || destination_id.isEmpty()) {

                    } else {
                        String now = Long.toString(timeNow);

                        // food price + ongkir price
                        int foodPrice = Integer.parseInt(totalPrice) + Integer.parseInt(priceRajaOngkir);
                        TransactionDropshipperRequest a = new TransactionDropshipperRequest(couponCode, kd_food, etName.getText().toString(), email, amount_food, String.valueOf(foodPrice), etCurrentCity.getText().toString(), etDestinationCity.getText().toString(), courier2, priceRajaOngkir, estimate, type, now);
                        postTransactionDropshipper(token, a);
                    }
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etCurrentCity.getText().toString().isEmpty() || etDestinationCity.getText().toString().isEmpty()) {
                    btnPay.setVisibility(View.GONE);
                } else {
                    pbLoading.setVisibility(View.VISIBLE);
                    output.clear();
                    courier.clear();

                    getCostJNE("https://api.rajaongkir.com/starter/cost", "12c633827bd624c0c1696ac8186526db", source_id, destination_id);
                    getCostPOS("https://api.rajaongkir.com/starter/cost", "12c633827bd624c0c1696ac8186526db", source_id, destination_id);
                    getCostTIKI("https://api.rajaongkir.com/starter/cost", "12c633827bd624c0c1696ac8186526db", source_id, destination_id);

                    btnPayDropshipper.setVisibility(View.VISIBLE);

                    if (i == 1) {
                        tvOngkir.setVisibility(View.VISIBLE);
                    } else {
                        tvTotal.setText("Total: Rp. " + totalPrice);
                        tvOngkir.setVisibility(View.GONE);
                    }

                    i = 1;
                }
            }
        });
    }

    private void initView() {
        spOrderAs = findViewById(R.id.spOrderAs);
        clDropshipper = findViewById(R.id.clDropshipper);
        clCostumer = findViewById(R.id.clCostumer);
        etCurrentCity = findViewById(R.id.etCurrentCity);
        etDestinationCity = findViewById(R.id.etDestinationCity);
        rvOngkir = findViewById(R.id.rvOngkir);
        btnSubmit = findViewById(R.id.btnSubmit);
        ivImage = findViewById(R.id.ivImage);
        tvFoodDescription = findViewById(R.id.tvFoodDescription);
        tvHarga = findViewById(R.id.tvHarga);
        tvFoodName = findViewById(R.id.tvFoodName);
        tvFoodDescription = findViewById(R.id.tvFoodDescription);
        pbLoading = findViewById(R.id.pbLoading2);
        tvOngkir = findViewById(R.id.tvOngkir);
        tvTotal = findViewById(R.id.tvTotal);
        btnPayCostumer = findViewById(R.id.btnPayCostumer);
        etName = findViewById(R.id.etName);
        btnPayDropshipper = findViewById(R.id.btnPayDropshipper);
        etGetCurrentLocation = findViewById(R.id.etGetCurrentLocation);

        tvOngkir.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //
        if (requestCode == REQUEST_SOURCE && resultCode == RESULT_OK) {
            etCurrentCity.setText(data.getStringExtra("city"));
            source_id = data.getStringExtra("city_id");
        } else if (requestCode == REQUEST_DESTINATION && resultCode == RESULT_OK) {
            etDestinationCity.setText(data.getStringExtra("city"));
            destination_id = data.getStringExtra("city_id");
        } else if (requestCode == REQUEST_GET_CURRENT_LOCATION) {
            if (data != null) {
                etGetCurrentLocation.setText(data.getStringExtra("getLocation_id"));
                getLocation_id = data.getStringExtra("getLocation_id");
            }
        }
    }

    private void initRecyclerView() {
        rvOngkir.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        orderRecyclerViewAdapter = new OrderRecyclerViewAdapter();
        rvOngkir.setAdapter(orderRecyclerViewAdapter);
    }

    private void getCostObservable() {
        viewModel = new ViewModelProvider(this).get(RajaOngkirViewModel.class);
        viewModel.getCostJNEObservable().observe(this, new Observer<ResponseCost>() {
            @Override
            public void onChanged(ResponseCost responseCost) {
                if (responseCost != null) {
                    for (DataType data : responseCost.getRajaongkir().getResults().get(0).getCosts()) {
                        output.add(data);
                        courier.add("JNE");
                    }
                    pbLoading.setVisibility(View.GONE);
                    orderRecyclerViewAdapter.setListDataItems(output, courier, PayDetailsActivity.this);
                    orderRecyclerViewAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
    }

    private void postTransactionObservable() {
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.postUserRequestResponseObservable().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response != null) {
                    if (response.getSuccess().equals("1")) {
                        pbLoading.setVisibility(View.GONE);

                        //
                        Intent i = new Intent(PayDetailsActivity.this, PaymentSuccessActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
    }

    private void postTransactionDropshipperObservable() {
        transactionViewModel2 = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel2.postTransactionDropshipperRequestObservable().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response != null) {
                    if (response.getSuccess().equals("1")) {
                        pbLoading.setVisibility(View.GONE);

                        //
                        Intent i = new Intent(PayDetailsActivity.this, PaymentSuccessActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
    }

    private void getPOSCostObservable() {
        viewModel = new ViewModelProvider(this).get(RajaOngkirViewModel.class);
        viewModel.getCostJNEObservable().observe(this, new Observer<ResponseCost>() {
            @Override
            public void onChanged(ResponseCost responseCost) {
                if (responseCost != null) {
                    for (DataType data : responseCost.getRajaongkir().getResults().get(0).getCosts()) {
                        output.add(data);
                        courier.add("POS");
                    }
                    pbLoading.setVisibility(View.GONE);
                    orderRecyclerViewAdapter.setListDataItems(output, courier, PayDetailsActivity.this);
                    orderRecyclerViewAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
    }

    private void getTIKICostObservable() {
        viewModel = new ViewModelProvider(this).get(RajaOngkirViewModel.class);
        viewModel.getCostJNEObservable().observe(this, new Observer<ResponseCost>() {
            @Override
            public void onChanged(ResponseCost responseCost) {
                if (responseCost != null) {
                    for (DataType data : responseCost.getRajaongkir().getResults().get(0).getCosts()) {
                        output.add(data);
                        courier.add("TIKI");
                    }
                    pbLoading.setVisibility(View.GONE);
                    orderRecyclerViewAdapter.setListDataItems(output, courier, PayDetailsActivity.this);
                    orderRecyclerViewAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
    }

    private void getCostJNE(String url, String key, String origin, String destination) {
        viewModel.getCostJNEOfData(url, key, origin, destination, 1000, "jne");
    }

    private void getCostTIKI(String url, String key, String origin, String destination) {
        viewModel.getCostJNEOfData(url, key, origin, destination, 1000, "tiki");
    }

    private void getCostPOS(String url, String key, String origin, String destination) {
        viewModel.getCostJNEOfData(url, key, origin, destination, 1000, "pos");
    }

    private void postTransaction(String key, TransactionRequest transactionRequest) {
        transactionViewModel.postUserRequestResponseOfData(key, transactionRequest);
    }

    private void postTransactionDropshipper(String key, TransactionDropshipperRequest transactionDropshipperRequest) {
        transactionViewModel2.postTransactionDropshipperRequestOfData(key, transactionDropshipperRequest);
    }

    @Override
    public void onClick(View v, int position, DataType data, String courier1) {
        output.clear();
        courier.clear();

        output.add(data);
        courier.add(courier1);

        //
        int ix = data.getCost().get(0).getValue();
        sIx = String.valueOf(ix);
        total = ix + Integer.parseInt(totalPrice);
        String total1 = String.valueOf(total);

        courier2 = courier.get(0);
        estimate = output.get(0).getCost().get(0).getEtd(); // data.getCost().get(0).getEtd()
        type = output.get(0).getService();
        priceRajaOngkir = String.valueOf(output.get(0).getCost().get(0).getValue());

        if (i == 1) {
            tvTotal.setText("Total: Rp. " + total1.toString());
            tvOngkir.setVisibility(View.VISIBLE);
            tvOngkir.setText("+ Rp. " + data.getCost().get(0).getValue());
        } else {
            tvTotal.setText("Total: Rp. " + sIx);
            tvOngkir.setVisibility(View.GONE);
            tvOngkir.setText("");
            priceRajaOngkir = "0";
        }
        i = 0;
    }
}