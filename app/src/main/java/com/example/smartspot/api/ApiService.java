package com.example.smartspot.api;

import com.example.smartspot.model.User;
import com.example.smartspot.model.VehicleType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

//    @POST("login")
//    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("getVehicleTypes.php")
    Call<List<VehicleType>> getVehicleTypes();

}