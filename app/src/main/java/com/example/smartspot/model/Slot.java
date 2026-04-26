package com.example.smartspot.model;

public class Slot {
    private String slot_number;
    private String status;

    // Constructor (Needed for the hardcoding part)
    public Slot(String slotNumber, String status) {
        this.slot_number = slotNumber;
        this.status = status;
    }

    // Getter for Slot Number
    public String getSlotNumber() {
        return slot_number;
    }

    // Getter for Status
    public String getStatus() {
        return status;
    }

    // SETTER for Status (This is what you are missing!)
    public void setStatus(String status) {
        this.status = status;
    }
}