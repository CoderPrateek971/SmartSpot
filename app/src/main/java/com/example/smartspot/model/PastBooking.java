package com.example.smartspot.model;

import com.google.gson.annotations.SerializedName;

public class PastBooking {
    @SerializedName(value = "booking_id", alternate = {"id", "bookingId"})
    private int booking_id;
    private String slot_number;
    private String date;
    private String total_hours;
    private String total_amount;
    private String booking_status;

    public int getBooking_id() { return booking_id; }
    public String getSlot_number() { return slot_number; }
    public String getDate() { return date; }
    public String getTotal_hours() { return total_hours; }
    public String getTotal_amount() { return total_amount; }
    public String getBooking_status() { return booking_status; }
}