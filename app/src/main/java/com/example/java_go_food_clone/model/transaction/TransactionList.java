package com.example.java_go_food_clone.model.transaction;

import java.util.List;

public class TransactionList {
    private String message, success;
    private List<TransactionResponse> data;

    public TransactionList(String message, String success, List<TransactionResponse> data) {
        this.message = message;
        this.success = success;
        this.data = data;
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

    public List<TransactionResponse> getData() {
        return data;
    }

    public void setData(List<TransactionResponse> data) {
        this.data = data;
    }
}
