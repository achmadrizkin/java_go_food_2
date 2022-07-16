package com.example.java_go_food_clone.model.user;

import java.util.ArrayList;
import java.util.List;

public class UserAuthResponse {
    private String success, message;
    private List<UserAuth> user;

    public UserAuthResponse(String success, String message, List<UserAuth> user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserAuth> getUser() {
        return user;
    }

    public void setUser(List<UserAuth> user) {
        this.user = user;
    }
}
