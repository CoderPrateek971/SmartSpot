package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.AdminDashboard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {
    TextView totalSlots, occupiedSlots, occupibleSlots, totalRevenue;
    CardView btnManageSlots, btnPriceManagement;
    ImageView backArrow;

    // 1. Declare the bottom navigation buttons
    LinearLayout btnHome, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        totalSlots = findViewById(R.id.totalSlots);
        occupiedSlots = findViewById(R.id.occupiedSlots);
        occupibleSlots = findViewById(R.id.occupibleSlots);
        totalRevenue = findViewById(R.id.totalRevenue);
        btnManageSlots = findViewById(R.id.btnManageSlots);
        btnPriceManagement = findViewById(R.id.btnPriceManagement);
        backArrow = findViewById(R.id.backArrow);

        // 2. Initialize bottom navigation buttons
        btnHome = findViewById(R.id.btnHome);
        btnSignOut = findViewById(R.id.btnSignOut);

        // --- CLICK LISTENERS ---

        // Back Arrow
        backArrow.setOnClickListener(v -> {
            onBackPressed(); // Better than finish(), triggers standard system back behavior
        });

        // Manage Slots Card
        btnManageSlots.setOnClickListener(v ->
                startActivity(new Intent(this, ManageSlotsActivity.class)));

        // Price Management Card
        btnPriceManagement.setOnClickListener(v ->
                startActivity(new Intent(this, PricingActivity.class)));

        // Bottom Nav: Home Button
        btnHome.setOnClickListener(v -> {
            // Since this IS the home screen for the admin, we just refresh the data
            Toast.makeText(AdminDashboardActivity.this, "Refreshing Dashboard...", Toast.LENGTH_SHORT).show();
            loadDashboard();
        });

        // Bottom Nav: Sign Out Button
        btnSignOut.setOnClickListener(v -> {
            Toast.makeText(AdminDashboardActivity.this, "Signing Out...", Toast.LENGTH_SHORT).show();

            // Go to Login Activity and clear back stack so user can't press back to return
            Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Load data from backend
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
                    totalRevenue.setText("Rs." + data.getTotalRevenue());
                }
            }
            @Override
            public void onFailure(Call<AdminDashboard> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}