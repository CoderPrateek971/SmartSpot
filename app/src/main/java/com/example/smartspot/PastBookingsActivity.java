package com.example.smartspot;

import android.os.Bundle;

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

        int userId = getSharedPreferences("USER", MODE_PRIVATE)
                .getInt("user_id", -1);

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getPastBookings(userId).enqueue(new Callback<List<PastBooking>>() {
            @Override
            public void onResponse(Call<List<PastBooking>> call, Response<List<PastBooking>> response) {
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new PastBookingAdapter(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<PastBooking>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}