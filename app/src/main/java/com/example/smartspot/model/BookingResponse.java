package com.example.smartspot.model;

public class BookingResponse {

    private int booking_id;
    private String slot;
    private String vehicle_number;
    private String vehicle_type;
    private String price;
    private String start_time;

    public int getBooking_id() { return booking_id; }
    public String getSlot() { return slot; }
    public String getVehicle_number() { return vehicle_number; }
    public String getVehicle_type() { return vehicle_type; }
    public String getPrice() { return price; }
    public String getStart_time() { return start_time; }
}