package com.example.smartspot.api;

import com.example.smartspot.model.User;
import com.example.smartspot.model.PastBooking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("past-bookings/{user_id}")
    Call<List<PastBooking>> getPastBookings(@Path("user_id") int userId);

}