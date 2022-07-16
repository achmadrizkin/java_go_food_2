package com.example.java_go_food_clone.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.FoodRequest;
import com.example.java_go_food_clone.model.promo.PromoRequest;
import com.example.java_go_food_clone.model.promo.PromoResponse;
import com.example.java_go_food_clone.network.RetroService;
import com.example.java_go_food_clone.repo.PromoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PromoViewModel extends ViewModel {
    MutableLiveData<PromoResponse> liveData;
    MutableLiveData<Response> liveDataPostPromoResponse;
    MutableLiveData<PromoResponse> promoResponseMutableLiveData;

    @Inject
    RetroService retroService;

    @Inject
    public PromoViewModel() {
        liveData = new MutableLiveData<>();
        liveDataPostPromoResponse = new MutableLiveData<>();
        promoResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<PromoResponse> getPromoByKodeObservable() {
        return liveData;
    }

    public MutableLiveData<Response> postPromoResponseObservable() {
        return liveDataPostPromoResponse;
    }

    public MutableLiveData<PromoResponse> getAllPromoObservable() {
        return promoResponseMutableLiveData;
    }

    public void getPromoByKodeOfData(String key, String kd_promo) {
        PromoRepo promoRepo = new PromoRepo(retroService);
        promoRepo.getPromoByKode(liveData, key, kd_promo);
    }

    public void getAllPromoOfData(String key) {
        PromoRepo promoRepo = new PromoRepo(retroService);
        promoRepo.getAllPromo(promoResponseMutableLiveData, key);
    }

    public void postPromoResponseOfData(String key, PromoRequest promoRequest) {
        PromoRepo promoRepo = new PromoRepo(retroService);
        promoRepo.postPromo(liveDataPostPromoResponse, key, promoRequest);
    }
}
