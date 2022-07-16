package com.example.java_go_food_clone.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_go_food_clone.model.user.UpdateProfileUser;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.model.user.UserRequest;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.network.RetroService;
import com.example.java_go_food_clone.repo.AuthRepo;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends ViewModel {
    MutableLiveData<UserResponse> liveData;
    MutableLiveData<UserAuthResponse> userAuthResponseMutableLiveData;
    MutableLiveData<UserAuthResponse> userDetailsMutableLiveData;
    MutableLiveData<UserResponse> updateProfileUserMutableLiveData;

    @Inject
    RetroService retroService;

    @Inject
    public AuthViewModel() {
        liveData = new MutableLiveData<>();
        userAuthResponseMutableLiveData = new MutableLiveData<>();
        userDetailsMutableLiveData = new MutableLiveData<>();
        updateProfileUserMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<UserResponse> signUpObservable() {
        return liveData;
    }

    public MutableLiveData<UserAuthResponse> signInObservable() {
        return userAuthResponseMutableLiveData;
    }

    public MutableLiveData<UserAuthResponse> getUserDetailsByEmailObservable() {
        return userDetailsMutableLiveData;
    }

    public MutableLiveData<UserResponse> updateProfileUserObservable() {
        return updateProfileUserMutableLiveData;
    }

    public void signUpOfData(UserRequest userRequest) {
        AuthRepo authRepo = new AuthRepo(retroService);
        authRepo.signUpFromApiCall(liveData, userRequest);
    }

    public void updateProfileUserOfData(UpdateProfileUser updateProfileUser) {
        AuthRepo authRepo = new AuthRepo(retroService);
        authRepo.updateProfileUserFromApiCall(updateProfileUserMutableLiveData, updateProfileUser);
    }

    public void signInOfData(String email, String password) {
        AuthRepo authRepo = new AuthRepo(retroService);
        authRepo.signInFromApiCall(userAuthResponseMutableLiveData, email, password);
    }

    public void getUserDetailsByEmailOfData(String user_name, String key) {
        AuthRepo authRepo = new AuthRepo(retroService);
        authRepo.getUserDetailsByEmailFromApiCall(userDetailsMutableLiveData, user_name, key);
    }
}
