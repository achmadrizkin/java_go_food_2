package com.example.java_go_food_clone.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.java_go_food_clone.model.user.UpdateProfileUser;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.model.user.UserRequest;
import com.example.java_go_food_clone.model.user.UserResponse;
import com.example.java_go_food_clone.network.RetroService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthRepo {
    private RetroService retroServiceInterface;

    public AuthRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void signUpFromApiCall(MutableLiveData<UserResponse> liveData, UserRequest userRequest) {
        retroServiceInterface.signUp(userRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResponse userResponse) {
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

    public void updateProfileUserFromApiCall(MutableLiveData<UserResponse> liveData, UpdateProfileUser updateProfileUser) {
        retroServiceInterface.updateProfile(updateProfileUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResponse userResponse) {
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

    public void signInFromApiCall(MutableLiveData<UserAuthResponse> liveData, String email, String password) {
        retroServiceInterface.signIn(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserAuthResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserAuthResponse userResponse) {
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

    public void getUserDetailsByEmailFromApiCall(MutableLiveData<UserAuthResponse> liveData, String user_name, String key) {
        retroServiceInterface.getUserDetailsByEmail(key, user_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserAuthResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserAuthResponse userResponse) {
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
}
