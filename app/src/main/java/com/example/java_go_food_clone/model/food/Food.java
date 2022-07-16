package com.example.java_go_food_clone.model.food;

public class Food {
    private String food_name, description, image_url, harga, rating, kd_food, count_rating;

    public Food(String food_name, String description, String image_url, String harga, String rating, String kd_food, String count_rating) {
        this.food_name = food_name;
        this.description = description;
        this.image_url = image_url;
        this.harga = harga;
        this.rating = rating;
        this.kd_food = kd_food;
        this.count_rating = count_rating;
    }

    public String getCount_rating() {
        return count_rating;
    }

    public void setCount_rating(String count_rating) {
        this.count_rating = count_rating;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getKd_food() {
        return kd_food;
    }

    public void setKd_food(String kd_food) {
        this.kd_food = kd_food;
    }
}
