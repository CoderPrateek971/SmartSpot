package com.example.smartspot.api;

import com.example.smartspot.model.AdminDashboard;
import com.example.smartspot.model.Booking;
import com.example.smartspot.model.BookingResponse;
import com.example.smartspot.model.PastBooking;
import com.example.smartspot.model.Pricing;
import com.example.smartspot.model.Slot;
import com.example.smartspot.model.SupportTicket;
import com.example.smartspot.model.User;
import com.example.smartspot.model.VehicleType;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    // ================= USERS =================
    @GET("users")
    Call<List<User>> getUsers();

    // ================= VEHICLE TYPES =================
    @GET("vehicle-types")
    Call<List<VehicleType>> getVehicleTypes();

    // ================= SLOTS =================
    @GET("slots")
    Call<List<Slot>> getSlots();

    // ADD NEW SLOT (🔥 for + button)
    @POST("add-slot")
    Call<HashMap<String, Object>> addSlot(@Body HashMap<String, Object> data);

    // ================= BOOK SLOT =================
    @POST("book-slot")
    Call<BookingResponse> bookSlot(@Body HashMap<String, Object> bookingData);

    // ================= ACTIVE BOOKING =================
    @GET("active-booking/{user_id}")
    Call<BookingResponse> getActiveBooking(@Path("user_id") int userId);

    // ================= END BOOKING =================
    @POST("end-booking")
    Call<HashMap<String, Object>> endBooking(@Body HashMap<String, Object> data);

    @GET("admin/dashboard")
    Call<AdminDashboard> getAdminDashboard();

    // ================= PAST BOOKINGS =================
    @GET("past-bookings/{user_id}")
    Call<List<PastBooking>> getPastBookings(@Path("user_id") int userId);

    @GET("booking/{id}")
    Call<Booking> getBookingById(@Path("id") int id);
    @POST("support/create")
    Call<Map<String, Object>> createComplaint(@Body Map<String, Object> body);

    @GET("support/user/{userId}")
    Call<List<SupportTicket>> getUserComplaints(@Path("userId") int userId);
    @GET("pricing")
    Call<Pricing> getPricing();

    @POST("pricing/update")
    Call<Void> updatePricing(@Body Pricing request);

}