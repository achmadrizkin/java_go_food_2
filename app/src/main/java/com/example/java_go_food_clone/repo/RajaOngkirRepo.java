package com.example.java_go_food_clone.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.java_go_food_clone.model.city.ResponseCity;
import com.example.java_go_food_clone.model.cost.ResponseCost;
import com.example.java_go_food_clone.network.RetroService;
import com.example.java_go_food_clone.utils.Helper;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RajaOngkirRepo {
    private RetroService retroServiceInterface;

    public RajaOngkirRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void getResponseCityFromApiCall(MutableLiveData<ResponseCity> liveData, String url,String key) {
        retroServiceInterface.getCity(url, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseCity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseCity responseCity) {
                        liveData.setValue(responseCity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(null);
                    }
                });
    }

    public void getJNE(MutableLiveData<ResponseCost> liveData, String url, String key, String origin, String destination, int weight, String courier) {
        retroServiceInterface.postCost(url, key, origin, destination, weight, courier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseCost>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseCost responseCost) {
                        Helper.jne = true;
                        liveData.setValue(responseCost);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(null);
                    }
                });
    }

}
