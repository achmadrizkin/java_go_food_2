package com.example.java_go_food_clone.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperList;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperRequest;
import com.example.java_go_food_clone.model.transaction.TransactionList;
import com.example.java_go_food_clone.model.transaction.TransactionRequest;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.model.user.UserRequest;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.network.RetroService;
import com.example.java_go_food_clone.repo.AuthRepo;
import com.example.java_go_food_clone.repo.TransactionRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TransactionViewModel extends ViewModel {
    MutableLiveData<Response> postUserRequestResponse;
    MutableLiveData<Response> postTransactionDropshipperRequest;
    MutableLiveData<TransactionList> getTransactionUserListResponse;
    MutableLiveData<TransactionList> getTransactionUserListByIdResponse;
    MutableLiveData<TransactionDropshipperList> getTransactionDropshipperByIdResponse;
    MutableLiveData<TransactionDropshipperList> getTransactionDropshipperResponse;

    @Inject
    RetroService retroService;

    @Inject
    public TransactionViewModel() {
        postUserRequestResponse = new MutableLiveData<>();
        postTransactionDropshipperRequest = new MutableLiveData<>();
        getTransactionUserListResponse = new MutableLiveData<>();
        getTransactionDropshipperResponse = new MutableLiveData<>();
        getTransactionUserListByIdResponse = new MutableLiveData<>();
        getTransactionDropshipperByIdResponse = new MutableLiveData<>();
    }

    public MutableLiveData<Response> postUserRequestResponseObservable() {
        return postUserRequestResponse;
    }

    public MutableLiveData<Response> postTransactionDropshipperRequestObservable() {
        return postTransactionDropshipperRequest;
    }

    public MutableLiveData<TransactionList> getTransactionUserListResponseObservable() {
        return getTransactionUserListResponse;
    }

    public MutableLiveData<TransactionDropshipperList> getTransactionDropshipperListObservable() {
        return getTransactionDropshipperResponse;
    }

    public MutableLiveData<TransactionList> getTransactionUserListByIdResponseObservable() {
        return getTransactionUserListByIdResponse;
    }

    public MutableLiveData<TransactionDropshipperList> getTransactionDropshipperByIdResponseObservable() {
        return getTransactionDropshipperByIdResponse;
    }

    public void postUserRequestResponseOfData(String key, TransactionRequest transactionRequest) {
        TransactionRepo transactionRepo = new TransactionRepo(retroService);
        transactionRepo.postTransactionFromApiCall(postUserRequestResponse, key, transactionRequest);
    }

    public void postTransactionDropshipperRequestOfData(String key, TransactionDropshipperRequest transactionDropshipperRequest) {
        TransactionRepo transactionRepo = new TransactionRepo(retroService);
        transactionRepo.postTransactionDropshipper(postTransactionDropshipperRequest, key, transactionDropshipperRequest);
    }

    public void getTransactionUserListResponseOfData(String key, String email) {
        TransactionRepo transactionRepo = new TransactionRepo(retroService);
        transactionRepo.getTransactionUserFromApiCall(getTransactionUserListResponse, key, email);
    }

    public void getTransactionDropshipperListOfData(String key, String email) {
        TransactionRepo transactionRepo = new TransactionRepo(retroService);
        transactionRepo.getTransactionDroshipperFromApiCall(getTransactionDropshipperResponse, key, email);
    }

    public void getTransactionUserListByIdResponseOfData(String key, String id) {
        TransactionRepo transactionRepo = new TransactionRepo(retroService);
        transactionRepo.getTransactionUserByIdFromApiCall(getTransactionUserListByIdResponse, key, id);
    }

    public void getTransactionDropshipperByIdResponseOfData(String key, String id) {
        TransactionRepo transactionRepo = new TransactionRepo(retroService);
        transactionRepo.getTransactionDropshipperByIdFromApiCall(getTransactionDropshipperByIdResponse, key, id);
    }
}
