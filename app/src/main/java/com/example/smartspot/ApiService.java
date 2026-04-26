package com.example.smartspot;

import com.example.smartspot.model.Booking;
import com.example.smartspot.model.BookingResponse;
import com.example.smartspot.model.PastBooking;
import com.example.smartspot.model.SupportTicket;
import com.example.smartspot.model.User;
import com.example.smartspot.model.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

//    @GET("users")
//    Call<List<User>> getUsers();
//
//    Call<List<VehicleType>> getVehicleTypes();
//    @GET("past-bookings/{user_id}")
//    Call<List<PastBooking>> getPastBookings(@Path("user_id") int userId);
//
//
////    @POST("book_slot.php")
////    Call<String> bookSlot(@Body HashMap<String, Object> bookingData);
////    @POST("book_slot.php")
////    Call<String> bookSlot(@Body HashMap<String, Object> bookingData);
//
//    @POST("bookings")  // ✅ CORRECT
//    Call<BookingResponse> bookSlot(@Body HashMap<String, Object> bookingData);
//
// //   @POST("login")
////   Call<LoginResponse> login(@Body LoginRequest request);

@GET("users")
Call<List<User>> getUsers();

    @GET("past-bookings/{user_id}")
    Call<List<PastBooking>> getPastBookings(@Path("user_id") int userId);
    // ================= VEHICLE TYPES =================
    @GET("vehicle-types")
    Call<List<VehicleType>> getVehicleTypes();

    // ================= BOOK SLOT (UPDATED) =================
    @POST("book-slot")   // ✅ MUST MATCH BACKEND
    Call<BookingResponse> bookSlot(@Body HashMap<String, Object> bookingData);

    //    @POST("book_slot.php")
//    Call<String> bookSlot(@Body HashMap<String, Object> bookingData);
    // ================= ACTIVE BOOKING =================
    @GET("active-booking/{user_id}")
    Call<BookingResponse> getActiveBooking(@Path("user_id") int userId);

//    @POST("bookings")  // ✅ CORRECT
//    Call<BookingResponse> bookSlot(@Body HashMap<String, Object> bookingData);
    // ================= END BOOKING =================
    @POST("end-booking")
    Call<String> endBooking(@Body HashMap<String, Object> data);

    @GET("api/bookings/details")
    Call<Booking> getBookingById(@Query("booking_id") int id);

    @POST("support/create")
    Call<Map<String, Object>> createComplaint(@Body Map<String, Object> body);

    @GET("support/user/{userId}")
    Call<List<SupportTicket>> getUserComplaints(@Path("userId") int userId);



    // ================= PAST BOOKINGS =================
}
