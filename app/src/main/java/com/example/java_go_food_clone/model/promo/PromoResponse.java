package com.example.java_go_food_clone.model.promo;

import java.util.List;

public class PromoResponse {
    private String message, success;
    private List<PromoRequest> promo;

    public PromoResponse(String message, String success, List<PromoRequest> promo) {
        this.message = message;
        this.success = success;
        this.promo = promo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PromoRequest> getPromo() {
        return promo;
    }

    public void setPromo(List<PromoRequest> promo) {
        this.promo = promo;
    }
}
