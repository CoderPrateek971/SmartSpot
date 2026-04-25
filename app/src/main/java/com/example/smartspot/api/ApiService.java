package com.example.smartspot.api;

import com.example.smartspot.User;
import com.example.smartspot.VehicleType;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    // ✅ FIXED ENDPOINT
    @GET("vehicle-types")
    Call<List<VehicleType>> getVehicleTypes();

    @POST("book_slot.php")
    Call<String> bookSlot(@Body HashMap<String, Object> bookingData);
}