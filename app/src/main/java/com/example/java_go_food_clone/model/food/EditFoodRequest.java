package com.example.java_go_food_clone.model.food;

public class EditFoodRequest {
    private String food_name, description, harga, image_url, kd_food;

    public EditFoodRequest(String food_name, String description, String harga, String image_url, String kd_food) {
        this.food_name = food_name;
        this.description = description;
        this.harga = harga;
        this.image_url = image_url;
        this.kd_food = kd_food;
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getKd_food() {
        return kd_food;
    }

    public void setKd_food(String kd_food) {
        this.kd_food = kd_food;
    }
}
