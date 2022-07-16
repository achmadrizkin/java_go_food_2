package com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.searchCity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.adapter.SearchRecyclerViewAdapter;
import com.example.java_go_food_clone.model.city.ResponseCity;
import com.example.java_go_food_clone.view_model.RajaOngkirViewModel;


import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.CompositeDisposable;

@AndroidEntryPoint
public class SearchCityActivity extends AppCompatActivity {
    private EditText etSearch;
    private RecyclerView rvCity;
    private RajaOngkirViewModel viewModel;
    String query = "";
    private ProgressBar pbLoading;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        compositeDisposable = new CompositeDisposable();

        etSearch = findViewById(R.id.etSearch);
        rvCity = findViewById(R.id.rvCity);
        pbLoading = findViewById(R.id.pbLoading);

        //
        initRecyclerView();
        getAllCityObservable(this, query);
        getSearchCity("https://api.rajaongkir.com/starter/city", "12c633827bd624c0c1696ac8186526db");

        pbLoading.setVisibility(View.VISIBLE);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // call api (not yet implemented)
                query = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initRecyclerView() {
        rvCity.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter();
        rvCity.setAdapter(searchRecyclerViewAdapter);
    }

    private void getAllCityObservable(Context context, String query) {
        viewModel = new ViewModelProvider(this).get(RajaOngkirViewModel.class);
        viewModel.getAllCityObservable().observe(this, new Observer<ResponseCity>() {
            @Override
            public void onChanged(ResponseCity responseCity) {
                if (responseCity != null) {
                    searchRecyclerViewAdapter.setListDataItems(responseCity.rajaongkir.getResults(), context);
                    searchRecyclerViewAdapter.notifyDataSetChanged();

                    pbLoading.setVisibility(View.GONE);

                } else {

                }
            }
        });
    }

    private void getSearchCity(String url, String key) {
        viewModel.getAllCityOfData(url, key);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}