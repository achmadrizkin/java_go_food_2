package com.example.java_go_food_clone.model.transaction;

import java.util.List;

public class TransactionDropshipperList {
    private String message, success;
    private List<TransactionDropshipperResponse> data;

    public TransactionDropshipperList(String message, String success, List<TransactionDropshipperResponse> data) {
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

    public List<TransactionDropshipperResponse> getData() {
        return data;
    }

    public void setData(List<TransactionDropshipperResponse> data) {
        this.data = data;
    }
}
