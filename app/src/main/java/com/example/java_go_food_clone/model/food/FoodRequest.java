package com.example.java_go_food_clone.model.food;

public class FoodRequest {

    // user token -> only single user can delete / update the food
    private String food_name, description, image_url, harga, kd_food;

    public FoodRequest(String food_name, String description, String image_url, String harga, String userToken) {
        this.food_name = food_name;
        this.description = description;
        this.image_url = image_url;
        this.harga = harga;
        this.kd_food = userToken;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getUserToken() {
        return kd_food;
    }

    public void setUserToken(String userToken) {
        this.kd_food = userToken;
    }
}
