package com.example.java_go_food_clone.model.food;

import java.util.List;

public class ListFoodResponse {
    private String message, success;
    private List<Food> user;

    public ListFoodResponse(String message, String success, List<Food> user) {
        this.message = message;
        this.success = success;
        this.user = user;
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

    public List<Food> getUser() {
        return user;
    }

    public void setUser(List<Food> user) {
        this.user = user;
    }
}
