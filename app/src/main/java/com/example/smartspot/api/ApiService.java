package com.example.smartspot.api;

import com.example.smartspot.model.User;
import com.example.smartspot.model.VehicleType;
import com.example.smartspot.model.BookingResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // ================= USERS =================
    @GET("users")
    Call<List<User>> getUsers();

    // ================= VEHICLE TYPES =================
    @GET("vehicle-types")
    Call<List<VehicleType>> getVehicleTypes();

    // ================= BOOK SLOT (UPDATED) =================
    @POST("bookings")  // ✅ CORRECT
    Call<BookingResponse> bookSlot(@Body HashMap<String, Object> bookingData);
}