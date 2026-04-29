package com.example.smartspot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.BookingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavbarHelper {

    public static void setupNavbar(final Activity activity) {
        LinearLayout navHome = activity.findViewById(R.id.navHome);
        LinearLayout navActive = activity.findViewById(R.id.navActive);
        LinearLayout navPast = activity.findViewById(R.id.navPastBookings);
        LinearLayout navProfile = activity.findViewById(R.id.navProfile);

        SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = pref.getInt("userId", -1);

        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                if (!(activity instanceof HomeActivity)) {
                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.putExtra("user_id", userId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                }
            });
        }

        if (navActive != null) {
            navActive.setOnClickListener(v -> {
                if (userId == -1) {
                    Toast.makeText(activity, "Error: User not logged in", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(activity, "Checking...", Toast.LENGTH_SHORT).show();
                checkActiveBooking(activity, userId);
            });
        }

        if (navPast != null) {
            navPast.setOnClickListener(v -> {
                if (!(activity instanceof PastBookingsActivity)) {
                    activity.startActivity(new Intent(activity, PastBookingsActivity.class));
                }
            });
        }

        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                if (!(activity instanceof ProfileActivity)) {
                    Intent intent = new Intent(activity, ProfileActivity.class);
                    intent.putExtra("user_id", userId);
                    activity.startActivity(intent);
                }
            });
        }
    }

    private static void checkActiveBooking(Activity activity, int userId) {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.getActiveBooking(userId).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookingResponse booking = response.body();

                    if (booking.getBooking_id() != 0) {
                        Intent intent = new Intent(activity, ActiveBookingActivity.class);
                        intent.putExtra("slot", booking.getSlot());
                        intent.putExtra("vehicle_number", booking.getVehicle_number());
                        intent.putExtra("price", String.valueOf(booking.getPrice()));

                        intent.putExtra("booking_id", booking.getBooking_id());

                        intent.putExtra("start_time_millis", System.currentTimeMillis());

                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(activity, "No active booking found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "No active booking found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}