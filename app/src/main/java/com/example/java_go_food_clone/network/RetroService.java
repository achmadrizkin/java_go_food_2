package com.example.java_go_food_clone.network;

import com.example.java_go_food_clone.model.Response;
import com.example.java_go_food_clone.model.city.ResponseCity;
import com.example.java_go_food_clone.model.cost.ResponseCost;
import com.example.java_go_food_clone.model.food.EditFoodRequest;
import com.example.java_go_food_clone.model.food.FoodRequest;
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.model.promo.PromoRequest;
import com.example.java_go_food_clone.model.promo.PromoResponse;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperList;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperRequest;
import com.example.java_go_food_clone.model.transaction.TransactionList;
import com.example.java_go_food_clone.model.transaction.TransactionRequest;
import com.example.java_go_food_clone.model.user.UpdateProfileUser;
import com.example.java_go_food_clone.model.user.UserAuthResponse;
import com.example.java_go_food_clone.model.user.UserRequest;
import com.example.java_go_food_clone.model.user.UserResponse;


import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetroService {
    // api: https://achmadrizkin.my.id/java_go_mbe/sign_up.php
    @POST("sign_up.php")
    Observable<UserResponse> signUp(@Body UserRequest userRequest);

    @GET("sign_in.php")
    Observable<UserAuthResponse> signIn(@Query("email") String email, @Query("password") String password);

    @GET("select_user_by_email.php")
    Observable<UserAuthResponse> getUserDetailsByEmail(@Header("key") String key, @Query("user_name") String user_name);

    @GET("select_all_food.php")
    Observable<ListFoodResponse> getAllFood(@Header("key") String key);

    @GET("select_trending_food.php")
    Observable<ListFoodResponse> getTrendingFood(@Header("key") String key);

    @GET("search_food.php")
    Observable<ListFoodResponse> getSearchFood(@Header("key") String key, @Query("query") String query);

    @GET("select_food_by_kode.php")
    Observable<ListFoodResponse> getFoodByKode(@Header("key") String key, @Query("kd_food") String kd_food);

    @GET("select_promo_by_kd.php")
    Observable<PromoResponse> getPromoByKode(@Header("key") String promo, @Query("kd_promo") String kd_promo);

    @POST("insert_promo.php")
    Observable<Response> postPromo(@Header("key") String key, @Body PromoRequest promoRequest);

    @GET("select_promo.php")
    Observable<PromoResponse> getAllPromo(@Header("key") String key);

    @PUT("update_user.php")
    Observable<UserResponse> updateProfile(@Body UpdateProfileUser updateProfileUser);

    @POST("insert_food.php")
    Observable<Response> postFood(@Header("key") String key, @Body FoodRequest foodRequest);

    @GET("delete_food_by_id.php")
    Observable<Response> deleteFoodById(@Header("key") String key, @Query("kd_food") String kd_food);

    // USER
    @POST("insert_transaksi.php")
    Observable<Response> postTransaction(@Header("key") String key, @Body TransactionRequest transactionRequest);

    // TRANSACTION DROPSIPPER
    @POST("insert_transaksi_dropshipper.php")
    Observable<Response> postTransactionDropshipper(@Header("key") String key, @Body TransactionDropshipperRequest transactionDropshipperRequest);

    @GET("select_transaction_user.php")
    Observable<TransactionList> getTransactionUser(@Header("key") String key, @Query("email_user") String email_user);

    @GET("select_transaction_dropshipper.php")
    Observable<TransactionDropshipperList> getTransactionDropshipper(@Header("key") String key, @Query("email_user") String email_user);

    @GET("select_transaction_user_by_id.php")
    Observable<TransactionList> getTransactionUserById(@Header("key") String key, @Query("id") String id);

    @GET("select_transaction_dropshipper_by_id.php")
    Observable<TransactionDropshipperList> getTransactionDropshipperById(@Header("key") String key, @Query("id") String id);

    @POST("update_food_by_id.php")
    Observable<Response> updateFoodById(@Body EditFoodRequest editFoodRequest);

    // https://api.rajaongkir.com/starter/city
    @GET
    Single<ResponseCity> getCity(@Url String url, @Header("key") String key);

    @FormUrlEncoded
    @POST()
    Single<ResponseCost> postCost(
            @Url String url,
            @Header("key") String key,
            @Field("origin") String origin,
            @Field("destination") String destination,
            @Field("weight") int weight,
            @Field("courier") String courier
    );

}
