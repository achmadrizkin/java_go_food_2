package com.example.java_go_food_clone.view.bottomNav.orders;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.adapter.Food2RecyclerViewAdapter;
import com.example.java_go_food_clone.adapter.FoodRecyclerViewAdapter;
import com.example.java_go_food_clone.adapter.TransactionDropshipperRecyclerViewAdapter;
import com.example.java_go_food_clone.adapter.TransactionRecyclerViewAdapter;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperList;
import com.example.java_go_food_clone.model.transaction.TransactionList;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.view_model.AuthViewModel;
import com.example.java_go_food_clone.view_model.FoodViewModel;
import com.example.java_go_food_clone.view_model.TransactionViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderFragment extends Fragment {
    private RecyclerView rvLatestOrder, rvLatestOrderDropshipper;
    private TransactionViewModel transactionViewModel, transactionDropshipperViewModel;
    private TransactionRecyclerViewAdapter transactionRecyclerViewAdapter;
    private TransactionDropshipperRecyclerViewAdapter transactionDropshipperRecyclerViewAdapter;
    private SharedPreferences profile;
    private String token, email, username;
    private ConstraintLayout clEmptyTransaction, clEmptyDropshipperTransactions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        profile = getActivity().getSharedPreferences("login_session", MODE_PRIVATE);
        token = profile.getString("token", null);
        email = profile.getString("email", null);
        username = profile.getString("username", null);

        rvLatestOrder = view.findViewById(R.id.rvLatestOrder);
        clEmptyTransaction = view.findViewById(R.id.clEmptyTransaction);
        clEmptyDropshipperTransactions = view.findViewById(R.id.clEmptyDropshipperTransactions);
        rvLatestOrderDropshipper = view.findViewById(R.id.rvLatestOrderDropshipper);

        //
        getLatestTransactionDropshipperObservable();
        getLatestTransactionUserObservable();
        initRecyclerViewLatestDropshipperOrder(view);
        initRecyclerViewLatestOrder(view);
        getLatestTransactionUserAPI(token, email);
        getLatestTransactionDropshipperAPI(token, email);

        return view;
    }

    private void initRecyclerViewLatestOrder(View view) {
        transactionRecyclerViewAdapter = new TransactionRecyclerViewAdapter();
        rvLatestOrder = view.findViewById(R.id.rvLatestOrder);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvLatestOrder.setLayoutManager(horizontalLayoutManager);
        rvLatestOrder.setAdapter(transactionRecyclerViewAdapter);
    }

    private void initRecyclerViewLatestDropshipperOrder(View view) {
        transactionDropshipperRecyclerViewAdapter = new TransactionDropshipperRecyclerViewAdapter();
        rvLatestOrderDropshipper = view.findViewById(R.id.rvLatestOrderDropshipper);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvLatestOrderDropshipper.setLayoutManager(horizontalLayoutManager);
        rvLatestOrderDropshipper.setAdapter(transactionDropshipperRecyclerViewAdapter);
    }

    private void getLatestTransactionUserAPI(String key, String email_user) {
        transactionViewModel.getTransactionUserListResponseOfData(key, email_user);
    }

    private void getLatestTransactionUserObservable() {
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.getTransactionUserListResponseObservable().observe(getActivity(), new Observer<TransactionList>() {
            @Override
            public void onChanged(TransactionList transactionList) {
                if (transactionList != null) {
                    if (transactionList.getSuccess().equals("1")) {
                        clEmptyTransaction.setVisibility(View.GONE);

                        transactionRecyclerViewAdapter.setListDataItems(transactionList);
                        transactionRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        clEmptyTransaction.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    private void getLatestTransactionDropshipperObservable() {
        transactionDropshipperViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionDropshipperViewModel.getTransactionDropshipperListObservable().observe(getActivity(), new Observer<TransactionDropshipperList>() {
            @Override
            public void onChanged(TransactionDropshipperList transactionList) {
                if (transactionList != null) {
                    if (transactionList.getSuccess().equals("1")) {
                        clEmptyTransaction.setVisibility(View.GONE);

                        transactionDropshipperRecyclerViewAdapter.setListDataItems(transactionList);
                        transactionDropshipperRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        clEmptyDropshipperTransactions.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    private void getLatestTransactionDropshipperAPI(String key, String email_user) {
        transactionDropshipperViewModel.getTransactionDropshipperListOfData(key, email_user);
    }
}