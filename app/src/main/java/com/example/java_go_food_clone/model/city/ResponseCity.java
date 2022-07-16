package com.example.java_go_food_clone.model.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseCity implements Serializable {
    @SerializedName("rajaongkir")
    @Expose
    public ListCity rajaongkir;

    public ListCity getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(ListCity rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
