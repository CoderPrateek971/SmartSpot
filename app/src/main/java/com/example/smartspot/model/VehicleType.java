package com.example.smartspot.model;

import com.google.gson.annotations.SerializedName;

public class VehicleType {

    @SerializedName("vehicle_type_id")
    private int id;

    @SerializedName("type_name")
    private String typeName;

    @SerializedName("price_per_hour")
    private String price;

    public String getTypeName() {
        return typeName;
    }

    public String getPrice() {
        return price;
    }
}