package com.example.java_go_food_clone.model.transaction;

public class TransactionResponse {
    private String id;
    private String kd_food;
    private String code_promo;
    private String email_user;
    private String byk;
    private String harga_total;
    private String lokasi;
    private String curr_date;
    private String food_name;
    private String description;
    private String image_url;
    private String harga;
    private String rating;
    private String count_rating;
    private String user_token;

    public TransactionResponse(String id, String kd_food, String code_promo, String email_user, String byk, String harga_total, String lokasi, String curr_date, String food_name, String description, String image_url, String harga, String rating, String count_rating, String user_token) {
        this.id = id;
        this.kd_food = kd_food;
        this.code_promo = code_promo;
        this.email_user = email_user;
        this.byk = byk;
        this.harga_total = harga_total;
        this.lokasi = lokasi;
        this.curr_date = curr_date;
        this.food_name = food_name;
        this.description = description;
        this.image_url = image_url;
        this.harga = harga;
        this.rating = rating;
        this.count_rating = count_rating;
        this.user_token = user_token;
    }

    // Getter Methods
    public String getId() {
        return id;
    }

    public String getKd_food() {
        return kd_food;
    }

    public String getCode_promo() {
        return code_promo;
    }

    public String getEmail_user() {
        return email_user;
    }

    public String getByk() {
        return byk;
    }

    public String getHarga_total() {
        return harga_total;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getCurr_date() {
        return curr_date;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getHarga() {
        return harga;
    }

    public String getRating() {
        return rating;
    }

    public String getCount_rating() {
        return count_rating;
    }

    public String getUser_token() {
        return user_token;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setKd_food(String kd_food) {
        this.kd_food = kd_food;
    }

    public void setCode_promo(String code_promo) {
        this.code_promo = code_promo;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public void setByk(String byk) {
        this.byk = byk;
    }

    public void setHarga_total(String harga_total) {
        this.harga_total = harga_total;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setCurr_date(String curr_date) {
        this.curr_date = curr_date;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setCount_rating(String count_rating) {
        this.count_rating = count_rating;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
