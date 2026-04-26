package com.example.smartspot.model;

import com.google.gson.annotations.SerializedName;

public class AdminDashboard {

    @SerializedName("totalSlots")
    private int totalSlots;

    @SerializedName("occupiedSlots")
    private int occupiedSlots;

    @SerializedName("occupibleSlots")
    private int occupibleSlots;

    @SerializedName("totalRevenue")
    private double totalRevenue;

    public int getTotalSlots() { return totalSlots; }
    public int getOccupiedSlots() { return occupiedSlots; }
    public int getOccupibleSlots() { return occupibleSlots; }
    public double getTotalRevenue() { return totalRevenue; }
}