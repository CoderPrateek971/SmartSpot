package com.example.smartspot.model;

public class Slot {
    private int slot_id;
    private String slot_number;
    private String status;
    private int isActive; // Class-level variable

    // Constructor
    public Slot(String slotNumber, String status) {
        this.slot_number = slotNumber;
        this.status = status;
        // Logic to keep isActive in sync with status
        try {
            this.isActive = Integer.parseInt(status);
        } catch (NumberFormatException e) {
            this.isActive = 0;
        }
    }

    // Default Constructor (Required for API calls)
    public Slot() {}

    // --- GETTERS ---

    public int getSlot_id() {
        return slot_id;
    }

    // HomeActivity uses this
    public String getSlot_number() {
        return slot_number;
    }

    // SlotAdapter uses this
    public String getSlotNumber() {
        return slot_number;
    }

    public String getStatus() {
        return status;
    }

    public int getIsActive() {
        return isActive; // FIXED: returns the class variable
    }

    // --- SETTERS ---

    public void setStatus(String status) {
        this.status = status;
        // Update isActive whenever status changes
        try {
            this.isActive = Integer.parseInt(status);
        } catch (NumberFormatException e) {
            this.isActive = 0;
        }
    }

    public void setSlot_id(int slot_id) {
        this.slot_id = slot_id;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
        this.status = String.valueOf(isActive);
    }
}