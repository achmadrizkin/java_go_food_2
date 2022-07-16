package com.example.java_go_food_clone.model.user;

public class UserAuth {
    private String email, token, user_name, role, image_url;

    public UserAuth(String email, String token, String user_name, String role, String image_url) {
        this.email = email;
        this.token = token;
        this.user_name = user_name;
        this.role = role;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
