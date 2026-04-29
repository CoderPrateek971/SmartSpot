package com.example.smartspot.model;

import com.google.gson.annotations.SerializedName;

public class VehicleType {

    @SerializedName("vehicle_type_id")
    private int id;

    @SerializedName("type_name")
    private String typeName;

    @SerializedName("price_per_hour")
    private String price;
    public int getVehicle_type_id() { return id; }

    public String getType_name() { return typeName; }

    public String getPrice_per_hour() { return price; }

    public String getTypeName() { return typeName; }

    public String getPrice() { return price; }
}