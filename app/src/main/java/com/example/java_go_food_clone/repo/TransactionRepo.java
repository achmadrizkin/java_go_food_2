package com.example.java_go_food_clone.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperList;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperRequest;
import com.example.java_go_food_clone.model.transaction.TransactionList;
import com.example.java_go_food_clone.model.transaction.TransactionRequest;
import com.example.java_go_food_clone.network.RetroService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionRepo {
    private RetroService retroServiceInterface;

    public TransactionRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void postTransactionFromApiCall(MutableLiveData<Response> liveData, String key, TransactionRequest transactionRequest) {
        retroServiceInterface.postTransaction(key, transactionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response response) {
                liveData.postValue(response);
            }

            @Override
            public void onError(Throwable e) {
                liveData.postValue(null);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getTransactionDropshipperByIdFromApiCall(MutableLiveData<TransactionDropshipperList> transactionDropshipperListMutableLiveData, String key, String id) {
        retroServiceInterface.getTransactionDropshipperById(key, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransactionDropshipperList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TransactionDropshipperList transactionDropshipperList) {
                        transactionDropshipperListMutableLiveData.postValue(transactionDropshipperList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        transactionDropshipperListMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getTransactionUserByIdFromApiCall(MutableLiveData<TransactionList> liveData, String key, String id) {
        retroServiceInterface.getTransactionUserById(key, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransactionList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TransactionList transactionList) {
                        liveData.postValue(transactionList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getTransactionUserFromApiCall(MutableLiveData<TransactionList> liveData, String key, String email_user) {
        retroServiceInterface.getTransactionUser(key, email_user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TransactionList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TransactionList response) {
                liveData.postValue(response);
            }

            @Override
            public void onError(Throwable e) {
                liveData.postValue(null);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getTransactionDroshipperFromApiCall(MutableLiveData<TransactionDropshipperList> liveData, String key, String email_user) {
        retroServiceInterface.getTransactionDropshipper(key, email_user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransactionDropshipperList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TransactionDropshipperList transactionDropshipperList) {
                        liveData.postValue(transactionDropshipperList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void postTransactionDropshipper(MutableLiveData<Response> liveData, String key, TransactionDropshipperRequest transactionDropshipperRequest) {
        retroServiceInterface.postTransactionDropshipper(key, transactionDropshipperRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response response) {
                        liveData.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
