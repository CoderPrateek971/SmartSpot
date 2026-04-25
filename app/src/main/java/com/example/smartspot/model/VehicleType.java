package com.example.smartspot.model;

public class VehicleType {

    private int vehicle_type_id;
    private String type_name;
    private double price_per_hour;

    public String getTypeName() {
        return type_name;
    }

    public double getPrice() {
        return price_per_hour;
    }
}
