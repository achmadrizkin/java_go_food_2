package com.example.java_go_food_clone.model.transaction;

public class TransactionDropshipperRequest {
    private String code_promo;
    private String kd_food;
    private String name;
    private String email_user;
    private String byk;
    private String harga_total;
    private String origin;
    private String destination;
    private String courier;
    private String harga_courier;
    private String estimate;
    private String type;
    private String curr_date;

    public TransactionDropshipperRequest(String code_promo, String kd_food, String name, String email_user, String byk, String harga_total, String origin, String destination, String courier, String harga_courier, String estimate, String type, String curr_date) {
        this.code_promo = code_promo;
        this.kd_food = kd_food;
        this.name = name;
        this.email_user = email_user;
        this.byk = byk;
        this.harga_total = harga_total;
        this.origin = origin;
        this.destination = destination;
        this.courier = courier;
        this.harga_courier = harga_courier;
        this.estimate = estimate;
        this.type = type;
        this.curr_date = curr_date;
    }

    // Getter Methods
    public String getCode_promo() {
        return code_promo;
    }

    public String getKd_food() {
        return kd_food;
    }

    public String getName() {
        return name;
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

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getCourier() {
        return courier;
    }

    public String getHarga_courier() {
        return harga_courier;
    }

    public String getEstimate() {
        return estimate;
    }

    public String getType() {
        return type;
    }

    public String getCurr_date() {
        return curr_date;
    }

    // Setter Methods

    public void setCode_promo(String code_promo) {
        this.code_promo = code_promo;
    }

    public void setKd_food(String kd_food) {
        this.kd_food = kd_food;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public void setHarga_courier(String harga_courier) {
        this.harga_courier = harga_courier;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurr_date(String curr_date) {
        this.curr_date = curr_date;
    }
}
