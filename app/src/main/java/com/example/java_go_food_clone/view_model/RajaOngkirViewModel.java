package com.example.java_go_food_clone.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_go_food_clone.model.city.ResponseCity;
import com.example.java_go_food_clone.model.cost.ResponseCost;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.promo.PromoRequest;
import com.example.java_go_food_clone.network.RetroService;
import com.example.java_go_food_clone.repo.PromoRepo;
import com.example.java_go_food_clone.repo.RajaOngkirRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RajaOngkirViewModel extends ViewModel {
    MutableLiveData<ResponseCity> liveData;
    MutableLiveData<ResponseCost> costMutableLiveData;

    @Inject
    public RajaOngkirViewModel() {
        liveData = new MutableLiveData<>();
        costMutableLiveData = new MutableLiveData<>();
    }

    @Inject
    RetroService retroService;

    public MutableLiveData<ResponseCity> getAllCityObservable() {
        return liveData;
    }

    public MutableLiveData<ResponseCost> getCostJNEObservable() {
        return costMutableLiveData;
    }

    public void getAllCityOfData(String url, String key) {
        RajaOngkirRepo rajaOngkirRepo = new RajaOngkirRepo(retroService);
        rajaOngkirRepo.getResponseCityFromApiCall(liveData, url, key);
    }

    public void getCostJNEOfData(String url, String key, String origin, String destination, int weight, String courirer) {
        RajaOngkirRepo rajaOngkirRepo = new RajaOngkirRepo(retroService);
        rajaOngkirRepo.getJNE(costMutableLiveData, url, key, origin, destination, weight, courirer);
    }
}
