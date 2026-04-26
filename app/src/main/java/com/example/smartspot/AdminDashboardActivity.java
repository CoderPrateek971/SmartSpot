package com.example.smartspot;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.AdminDashboard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {

    TextView totalSlots, occupiedSlots, occupibleSlots, totalRevenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        totalSlots = findViewById(R.id.totalSlots);
        occupiedSlots = findViewById(R.id.occupiedSlots);
        occupibleSlots = findViewById(R.id.occupibleSlots);
        totalRevenue = findViewById(R.id.totalRevenue);

        loadDashboard();
    }

    private void loadDashboard() {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getAdminDashboard().enqueue(new Callback<AdminDashboard>() {
            @Override
            public void onResponse(Call<AdminDashboard> call, Response<AdminDashboard> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AdminDashboard data = response.body();

                    totalSlots.setText(String.valueOf(data.getTotalSlots()));
                    occupiedSlots.setText(String.valueOf(data.getOccupiedSlots()));
                    occupibleSlots.setText(String.valueOf(data.getOccupibleSlots()));
                    totalRevenue.setText("₹" + data.getTotalRevenue());

                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Server Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AdminDashboard> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "API Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}