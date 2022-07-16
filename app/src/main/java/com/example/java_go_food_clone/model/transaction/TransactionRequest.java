package com.example.java_go_food_clone.model.transaction;

public class TransactionRequest {
    private String byk, email_user, code_promo, harga_total, kd_food, lokasi, curr_date;

    public TransactionRequest(String byk, String email_user, String code_promo, String harga_total, String kd_food, String lokasi, String curr_date) {
        this.byk = byk;
        this.email_user = email_user;
        this.code_promo = code_promo;
        this.harga_total = harga_total;
        this.kd_food = kd_food;
        this.lokasi = lokasi;
        this.curr_date = curr_date;
    }

    public String getByk() {
        return byk;
    }

    public void setByk(String byk) {
        this.byk = byk;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getCode_promo() {
        return code_promo;
    }

    public void setCode_promo(String code_promo) {
        this.code_promo = code_promo;
    }

    public String getHarga_total() {
        return harga_total;
    }

    public void setHarga_total(String harga_total) {
        this.harga_total = harga_total;
    }

    public String getKd_food() {
        return kd_food;
    }

    public void setKd_food(String kd_food) {
        this.kd_food = kd_food;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getCurr_date() {
        return curr_date;
    }

    public void setCurr_date(String curr_date) {
        this.curr_date = curr_date;
    }
}
