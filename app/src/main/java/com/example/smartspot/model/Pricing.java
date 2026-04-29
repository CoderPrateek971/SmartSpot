package com.example.smartspot.model;

import com.google.gson.annotations.SerializedName;

public class Pricing {

    @SerializedName("car_price")
    private int car_price;

    @SerializedName("motorcycle_price")
    private int motorcycle_price;

    public int getCar_price() { return car_price; }
    public int getMotorcycle_price() { return motorcycle_price; }

    public void setCar_price(int car_price) { this.car_price = car_price; }
    public void setMotorcycle_price(int motorcycle_price) { this.motorcycle_price = motorcycle_price; }
}