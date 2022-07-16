package com.example.java_go_food_clone.view.bottomNav.promo;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.adapter.Food2RecyclerViewAdapter;
import com.example.java_go_food_clone.adapter.FoodRecyclerViewAdapter;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.view_model.FoodViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class PromoFragment extends Fragment {
    private FoodRecyclerViewAdapter foodRecyclerViewAdapter, foodRecyclerViewAdapter2, foodRecyclerViewAdapter3;
    private Food2RecyclerViewAdapter food2RecyclerViewAdapter;
    private RecyclerView rvPromo, rvEvent, rvTrending, rvSearch;
    private SharedPreferences profile;
    private FoodViewModel foodViewModel, foodViewModel2, foodViewModel3;
    private String token;
    private ProgressBar pbLoading;
    private ConstraintLayout clHome, clNoResult;
    private TextView textView15;
    private EditText inputBookName;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promo, container, false);
        compositeDisposable = new CompositeDisposable();

        //
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //
        profile = getActivity().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);

        rvPromo = view.findViewById(R.id.rvPromo);
        pbLoading = view.findViewById(R.id.pbLoading);
        clHome = view.findViewById(R.id.clHome);
        rvEvent = view.findViewById(R.id.rvEvent);
        rvTrending = view.findViewById(R.id.rvTrending);
        textView15 = view.findViewById(R.id.textView15);
        rvSearch = view.findViewById(R.id.rvSearch);
        inputBookName = view.findViewById(R.id.inputBookName);
        clNoResult = view.findViewById(R.id.clNoResult);

        //
        foodViewModel3 = new ViewModelProvider(this).get(FoodViewModel.class);

        //
        clNoResult.setVisibility(View.GONE);
        clHome.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
        //
        getSearchFoodObservable();
        getTrendingFoodObservable();
        getAllFoodObservable();
        getDiscountedPriceObservable();

        initSearchFood();
        initRecyclerViewSearchFood(view);
        initRecyclerViewAllFood(view);
        initRecyclerViewAllEvent(view);
        initRecyclerViewTrendingFood(view);

        getAllFoodAPI(token);
        getDiscountedPrice(token);
        getTrendingFoodAPI(token);

        return view;
    }

    private void initSearchFood() {
        inputBookName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() < 1) {
                    clHome.setVisibility(View.VISIBLE);
                    rvSearch.setVisibility(View.GONE);
                    clNoResult.setVisibility(View.GONE);
                } else {
                    clHome.setVisibility(View.GONE);
                    rvSearch.setVisibility(View.VISIBLE);

                    //
                    pbLoading.setVisibility(View.VISIBLE);
                    clNoResult.setVisibility(View.GONE);

                    //
                    getSearchFoodAPI(token, charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initRecyclerViewSearchFood(View view) {
        foodRecyclerViewAdapter3 = new FoodRecyclerViewAdapter();
        rvSearch = view.findViewById(R.id.rvSearch);

        RecyclerView.LayoutManager mLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getActivity(), 2);
        rvSearch.setLayoutManager(mLayoutManager);
        foodRecyclerViewAdapter3 = new FoodRecyclerViewAdapter();
        rvSearch.setAdapter(foodRecyclerViewAdapter3);
    }

    private void initRecyclerViewAllFood(View view) {
        foodRecyclerViewAdapter = new FoodRecyclerViewAdapter();
        rvPromo = view.findViewById(R.id.rvPromo);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvPromo.setLayoutManager(horizontalLayoutManager);
        rvPromo.setAdapter(foodRecyclerViewAdapter);
    }

    private void initRecyclerViewTrendingFood(View view) {
        foodRecyclerViewAdapter2 = new FoodRecyclerViewAdapter();
        rvTrending = view.findViewById(R.id.rvTrending);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTrending.setLayoutManager(horizontalLayoutManager);
        rvTrending.setAdapter(foodRecyclerViewAdapter2);
    }

    private void initRecyclerViewAllEvent(View view) {
        food2RecyclerViewAdapter = new Food2RecyclerViewAdapter();
        rvEvent = view.findViewById(R.id.rvEvent);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvEvent.setLayoutManager(horizontalLayoutManager);
        rvEvent.setAdapter(food2RecyclerViewAdapter);
    }

    private void getAllFoodAPI(String key) {
        foodViewModel.getAllFoodOfData(key);
    }

    private void getAllFoodObservable() {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getAllFoodObservable().observe(getActivity(), new Observer<ListFoodResponse>() {
            @Override
            public void onChanged(ListFoodResponse recyclerData) {
                if (recyclerData == null) {

                } else {
                    if (recyclerData.getSuccess().equals("1")) {

                        clHome.setVisibility(View.VISIBLE);
                        pbLoading.setVisibility(View.GONE);

                        //
                        foodRecyclerViewAdapter.setListDataItems(recyclerData);
                        foodRecyclerViewAdapter.notifyDataSetChanged();

                    } else if (recyclerData.getSuccess().equals("2")) {

                    }

                }
            }
        });
    }

    private void getTrendingFoodAPI(String key) {
        foodViewModel.getTrendingFoodOfData(key);
    }

    private void getTrendingFoodObservable() {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getTrendingFoodObservable().observe(getActivity(), new Observer<ListFoodResponse>() {
            @Override
            public void onChanged(ListFoodResponse recyclerData) {
                if (recyclerData == null) {

                } else {
                    if (recyclerData.getSuccess().equals("1")) {

                        clHome.setVisibility(View.VISIBLE);
                        pbLoading.setVisibility(View.GONE);

                        //
                        foodRecyclerViewAdapter2.setListDataItems(recyclerData);
                        foodRecyclerViewAdapter2.notifyDataSetChanged();

                    } else if (recyclerData.getSuccess().equals("2")) {

                    }

                }
            }
        });
    }

    private void getSearchFoodAPI(String key, String query) {
        foodViewModel3.getSearchFoodOfData(key, query);
    }

    private void getSearchFoodObservable() {
        foodViewModel3.getSearchFoodObservable().observe(getViewLifecycleOwner(), new Observer<ListFoodResponse>() {
            @Override
            public void onChanged(ListFoodResponse recyclerData) {
                if (recyclerData == null) {
                    clNoResult.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.GONE);
                    rvSearch.setVisibility(View.GONE);
                } else {
                    if (recyclerData.getSuccess().equals("1")) {

                        pbLoading.setVisibility(View.GONE);
                        clNoResult.setVisibility(View.GONE);

                        //

                        foodRecyclerViewAdapter3.setListDataItems(recyclerData);
                        foodRecyclerViewAdapter3.notifyDataSetChanged();
                    }
                }
            }
        });

    }


    private void getDiscountedPrice(String key) {
        foodViewModel2.getAllFoodOfData(key);
    }

    private void getDiscountedPriceObservable() {
        foodViewModel2 = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel2.getAllFoodObservable().observe(getActivity(), new Observer<ListFoodResponse>() {
            @Override
            public void onChanged(ListFoodResponse recyclerData) {
                if (recyclerData == null) {

                } else {
                    if (recyclerData.getSuccess().equals("1")) {

                        clHome.setVisibility(View.VISIBLE);
                        pbLoading.setVisibility(View.GONE);

                        //
                        food2RecyclerViewAdapter.setListDataItems(recyclerData);
                        food2RecyclerViewAdapter.notifyDataSetChanged();

                    } else if (recyclerData.getSuccess().equals("2")) {

                    }

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}