package com.example.smartspot.model;

import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName(value = "booking_id", alternate = {"id", "bookingId"})
    private int booking_id;
    @SerializedName(value = "slot", alternate = {"slot_id", "slot_number"})
    private String slot;
    @SerializedName(value = "date", alternate = {"start_time"})
    private String date;
    @SerializedName(value = "duration", alternate = {"total_hours"})
    private String duration;
    @SerializedName(value = "amount", alternate = {"total_amount"})
    private double amount;
    @SerializedName(value = "vehicle_no", alternate = {"vehicle_number"})
    private String vehicle_no;

    @SerializedName("price")
    private double price;

    private String payment_method;

    public int getBooking_id() { return booking_id; }
    public String getSlot() { return slot; }
    public String getDate() { return date; }
    public String getDuration() { return duration; }
    public double getAmount() { return amount; }
    public String getVehicle_no() { return vehicle_no; }
    public String getPayment_method() { return payment_method; }
    public double getPrice() { return price; }
}