package com.example.smartspot;

import com.google.gson.annotations.SerializedName;

public class VehicleType {

    @SerializedName("vehicle_type_id")
    private int vehicle_type_id;

    @SerializedName("type_name")
    private String type_name;

    @SerializedName("price_per_hour")
    private double price_per_hour;

    // Getters
    public int getVehicle_type_id() { return vehicle_type_id; }
    public String getType_name() { return type_name; }
    public double getPrice_per_hour() { return price_per_hour; }
}