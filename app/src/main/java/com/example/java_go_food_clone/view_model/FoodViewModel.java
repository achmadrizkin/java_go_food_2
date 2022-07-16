package com.example.java_go_food_clone.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.food.EditFoodRequest;
import com.example.java_go_food_clone.model.food.FoodRequest;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.network.RetroService;
import com.example.java_go_food_clone.repo.FoodRepo;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FoodViewModel extends ViewModel {
    MutableLiveData<ListFoodResponse> liveData;
    MutableLiveData<ListFoodResponse> liveDataTrendingFood;
    MutableLiveData<ListFoodResponse> liveDataSearchFood;
    MutableLiveData<ListFoodResponse> liveDataFoodByKode;
    MutableLiveData<Response> liveDataPostFood;
    MutableLiveData<Response> liveDataDeleteFoodById;
    MutableLiveData<Response> liveDataUpdateFoodById;

    @Inject
    RetroService retroService;

    @Inject
    public FoodViewModel() {
        liveData = new MutableLiveData<>();
        liveDataTrendingFood = new MutableLiveData<>();
        liveDataSearchFood = new MutableLiveData<>();
        liveDataFoodByKode = new MutableLiveData<>();
        liveDataPostFood = new MutableLiveData<>();
        liveDataDeleteFoodById = new MutableLiveData<>();
        liveDataUpdateFoodById = new MutableLiveData<>();
    }

    public MutableLiveData<ListFoodResponse> getAllFoodObservable() {
        return liveData;
    }

    public MutableLiveData<Response> deleteFoodByIdObservable() {
        return liveDataDeleteFoodById;
    }

    public MutableLiveData<ListFoodResponse> getTrendingFoodObservable() {
        return liveDataTrendingFood;
    }

    public MutableLiveData<ListFoodResponse> getFoodByKodeObservable() {
        return liveDataFoodByKode;
    }

    public MutableLiveData<ListFoodResponse> getSearchFoodObservable() {
        return liveDataSearchFood;
    }

    public MutableLiveData<Response> updateFoodByIdObservable() {
        return liveDataUpdateFoodById;
    }

    public MutableLiveData<Response> postFoodFoodObservable() {
        return liveDataPostFood;
    }

    public void getAllFoodOfData(String key) {
        FoodRepo foodRepo = new FoodRepo(retroService);
        foodRepo.getAllFoodFromApiCall(liveData, key);
    }

    public void getFoodByKodeOfData(String key, String kd_food) {
        FoodRepo foodRepo = new FoodRepo(retroService);
        foodRepo.getFoodByKode(liveDataFoodByKode, key, kd_food);
    }

    public void getTrendingFoodOfData(String key) {
        FoodRepo foodRepo = new FoodRepo(retroService);
        foodRepo.getTrendingFood(liveDataTrendingFood, key);
    }

    public void getSearchFoodOfData(String key, String query) {
        FoodRepo foodRepo = new FoodRepo(retroService);
        foodRepo.getSearchFood(liveDataSearchFood, key, query);
    }

    public void postFoodOfData(String key, FoodRequest foodRequest) {
        FoodRepo foodRepo = new FoodRepo(retroService);
        foodRepo.postFoodFromApiCall(liveDataPostFood, key, foodRequest);
    }

    public void deleteFoodByIdOfData(String key, String kd_food) {
        FoodRepo foodRepo = new FoodRepo(retroService);
        foodRepo.deleteFoodById(liveDataDeleteFoodById, key, kd_food);
    }

    public void updateFoodByIdOfData(EditFoodRequest editFoodRequest) {
        FoodRepo foodRepo = new FoodRepo(retroService);
        foodRepo.updateFoodFromApiCall(liveDataUpdateFoodById, editFoodRequest);
    }
}
