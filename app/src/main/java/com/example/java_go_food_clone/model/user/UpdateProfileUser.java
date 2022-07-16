package com.example.java_go_food_clone.model.user;

public class UpdateProfileUser {
    private String email, password, user_name, image_url, token;

    public UpdateProfileUser(String email, String password, String user_name, String image_url, String token) {
        this.email = email;
        this.password = password;
        this.user_name = user_name;
        this.image_url = image_url;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
