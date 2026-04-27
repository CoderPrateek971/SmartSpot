package com.example.smartspot.model;

import com.google.gson.annotations.SerializedName;

public class Pricing {

    // Ensure these strings match EXACTLY what your backend API sends and expects
    @SerializedName("car_price")
    private int car_price;

    @SerializedName("motorcycle_price")
    private int motorcycle_price;

    // Getters
    public int getCar_price() { return car_price; }
    public int getMotorcycle_price() { return motorcycle_price; }

    // Setters
    public void setCar_price(int car_price) { this.car_price = car_price; }
    public void setMotorcycle_price(int motorcycle_price) { this.motorcycle_price = motorcycle_price; }
}