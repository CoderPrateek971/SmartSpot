package com.example.smartspot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.PastBooking;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastBookingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_bookings);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int userId = 1;
        Log.d("API_DEBUG", "Attempting to fetch bookings for User ID: " + userId);

        if (userId == -1) {
            Toast.makeText(this, "Error: User not logged in (ID is -1)", Toast.LENGTH_LONG).show();
            return;
        }

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getPastBookings(userId).enqueue(new Callback<List<PastBooking>>() {
            @Override
            public void onResponse(Call<List<PastBooking>> call, Response<List<PastBooking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PastBooking> bookings = response.body();

                    Log.d("API_DEBUG", "API Success! Number of bookings: " + bookings.size());

                    if (bookings.isEmpty()) {
                        Toast.makeText(PastBookingsActivity.this, "No past bookings found for this user.", Toast.LENGTH_SHORT).show();
                    } else {
                        recyclerView.setAdapter(new PastBookingAdapter(bookings));
                    }
                } else {
                    Log.e("API_DEBUG", "Server Error: " + response.code());
                    Toast.makeText(PastBookingsActivity.this, "Server Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PastBooking>> call, Throwable t) {
                Log.e("API_DEBUG", "API Call Failed completely: " + t.getMessage());
                Toast.makeText(PastBookingsActivity.this, "Network/API Failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}