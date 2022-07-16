package com.example.java_go_food_clone.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.EditFoodRequest;
import com.example.java_go_food_clone.model.food.FoodRequest;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.user.UserRequest;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.network.RetroService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodRepo {
    private RetroService retroServiceInterface;

    public FoodRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void getAllFoodFromApiCall(MutableLiveData<ListFoodResponse> liveData, String key) {
        retroServiceInterface.getAllFood(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListFoodResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ListFoodResponse userResponse) {
                liveData.postValue(userResponse);
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

    public void updateFoodFromApiCall(MutableLiveData<Response> liveData, EditFoodRequest editFoodRequest) {
        retroServiceInterface.updateFoodById(editFoodRequest)
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

    public void postFoodFromApiCall(MutableLiveData<Response> liveData, String key, FoodRequest foodRequest) {
        retroServiceInterface.postFood(key, foodRequest)
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

    public void getFoodByKode(MutableLiveData<ListFoodResponse> liveData, String key, String kd_food) {
        retroServiceInterface.getFoodByKode(key, kd_food)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListFoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ListFoodResponse listFoodResponse) {
                        liveData.postValue(listFoodResponse);
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

    public void getTrendingFood(MutableLiveData<ListFoodResponse> liveData, String key) {
        retroServiceInterface.getTrendingFood(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListFoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ListFoodResponse listFoodResponse) {
                        liveData.postValue(listFoodResponse);
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

    public void getSearchFood(MutableLiveData<ListFoodResponse> liveDataSearchFood, String key, String query) {
        retroServiceInterface.getSearchFood(key, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListFoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ListFoodResponse listFoodResponse) {
                        liveDataSearchFood.postValue(listFoodResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveDataSearchFood.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteFoodById(MutableLiveData<Response> liveData, String key, String kd_food) {
        retroServiceInterface.deleteFoodById(key, kd_food)
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
