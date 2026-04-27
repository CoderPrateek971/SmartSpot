package com.example.smartspot;

import android.app.Activity;
import android.content.Intent;
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

        int userId = 1; // Replace with actual logged-in User ID

        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                if (!(activity instanceof HomeActivity)) {
                    activity.startActivity(new Intent(activity, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
        }

        if (navActive != null) {
            navActive.setOnClickListener(v -> {
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
                // activity.startActivity(new Intent(activity, ProfileActivity.class));
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

                    // Check if server returned a valid booking or the "No active booking" message
                    if (booking.getBooking_id() != 0) {
                        Intent intent = new Intent(activity, ActiveBookingActivity.class);
                        intent.putExtra("slot", booking.getSlot());
                        intent.putExtra("vehicle_number", booking.getVehicle_number());
                        intent.putExtra("price", String.valueOf(booking.getPrice()));
                        intent.putExtra("booking_id", booking.getBooking_id());
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