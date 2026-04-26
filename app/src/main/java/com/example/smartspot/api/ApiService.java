package com.example.smartspot.api;

import com.example.smartspot.model.AdminDashboard;
import com.example.smartspot.model.Booking;
import com.example.smartspot.model.BookingResponse;
import com.example.smartspot.model.PastBooking;
import com.example.smartspot.model.Pricing;
import com.example.smartspot.model.User;
import com.example.smartspot.model.VehicleType;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("past-bookings/{user_id}")
    Call<List<PastBooking>> getPastBookings(@Path("user_id") int userId);

    @GET("vehicle-types")
    Call<List<VehicleType>> getVehicleTypes();

    @POST("book-slot")
    Call<BookingResponse> bookSlot(@Body HashMap<String, Object> bookingData);

    @GET("active-booking/{user_id}")
    Call<BookingResponse> getActiveBooking(@Path("user_id") int userId);

    @POST("end-booking")
    Call<String> endBooking(@Body HashMap<String, Object> data);

    @GET("api/bookings/details")
    Call<Booking> getBookingById(@Query("booking_id") int id);

    @GET("admin/dashboard")
    Call<AdminDashboard> getAdminDashboard();

    // 🔥 PRICING
    @GET("pricing")
    Call<Pricing> getPricing();

    @POST("pricing/update")
    Call<Void> updatePricing(@Body Pricing request);
}

