package com.example.java_go_food_clone.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.promo.PromoRequest;
import com.example.java_go_food_clone.model.promo.PromoResponse;
import com.example.java_go_food_clone.network.RetroService;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PromoRepo {
    private RetroService retroServiceInterface;

    public PromoRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void postPromo(MutableLiveData<Response> liveData, String key, PromoRequest promoRequest) {
        retroServiceInterface.postPromo(key, promoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response promoResponse) {
                        liveData.postValue(promoResponse);
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

    public void getAllPromo(MutableLiveData<PromoResponse> liveData, String key) {
        retroServiceInterface.getAllPromo(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PromoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PromoResponse promoResponse) {
                        liveData.postValue(promoResponse);
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

    public void getPromoByKode(MutableLiveData<PromoResponse> liveData, String key, String kd_promo) {
        retroServiceInterface.getPromoByKode(key, kd_promo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PromoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PromoResponse promoResponse) {
                        liveData.postValue(promoResponse);
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
