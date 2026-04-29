package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
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
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_bookings);

        NavbarHelper.setupNavbar(this);

        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(v -> navigateToHome());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToHome();
            }
        });

        android.content.SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        int userId = pref.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_LONG).show();
            return;
        }

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getPastBookings(userId).enqueue(new Callback<List<PastBooking>>() {
            @Override
            public void onResponse(Call<List<PastBooking>> call, Response<List<PastBooking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PastBooking> bookings = response.body();
                    if (bookings.isEmpty()) {
                        Toast.makeText(PastBookingsActivity.this, "No past bookings found.", Toast.LENGTH_SHORT).show();
                    } else {
                        recyclerView.setAdapter(new PastBookingAdapter(bookings));
                    }
                } else {
                    Toast.makeText(PastBookingsActivity.this, "Server Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PastBooking>> call, Throwable t) {
                Toast.makeText(PastBookingsActivity.this, "Network Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(PastBookingsActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}