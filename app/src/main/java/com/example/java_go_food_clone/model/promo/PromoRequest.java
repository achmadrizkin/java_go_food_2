package com.example.java_go_food_clone.model.promo;

public class PromoRequest {
    private String discountPrice, couponLimit, kd_promo;

    public PromoRequest(String discountPrice, String couponLimit, String kd_promo) {
        this.discountPrice = discountPrice;
        this.couponLimit = couponLimit;
        this.kd_promo = kd_promo;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(String couponLimit) {
        this.couponLimit = couponLimit;
    }

    public String getKd_promo() {
        return kd_promo;
    }

    public void setKd_promo(String kd_promo) {
        this.kd_promo = kd_promo;
    }
}
