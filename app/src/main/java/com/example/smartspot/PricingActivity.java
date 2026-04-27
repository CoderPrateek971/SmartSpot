package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.Pricing;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PricingActivity extends AppCompatActivity {

    EditText etCar, etBike;
    Button btnSave;

    // 1. Declare the missing views
    ImageView btnBack;
    LinearLayout navHome, navLogout;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing);

        // 2. Initialize the views
        etCar = findViewById(R.id.etCar);
        etBike = findViewById(R.id.etBike);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        navHome = findViewById(R.id.navHome);
        navLogout = findViewById(R.id.navLogout);

        apiService = ApiClient.getClient().create(ApiService.class);

        loadPricing();

        // ================= BUTTON LISTENERS =================

        // Back Button
        btnBack.setOnClickListener(v -> {
            onBackPressed(); // This goes back to the previous screen
        });

        // Home Navigation Button
        navHome.setOnClickListener(v -> {
            // Change 'AdminDashboardActivity.class' to whatever your actual home screen is named!
            Intent intent = new Intent(PricingActivity.this, AdminDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Logout Navigation Button
        navLogout.setOnClickListener(v -> {
            // TODO: Clear any SharedPreferences/User Session here if you have them

            Toast.makeText(PricingActivity.this, "Signing Out...", Toast.LENGTH_SHORT).show();
            // Change 'LoginActivity.class' to your actual login screen name!
            Intent intent = new Intent(PricingActivity.this, LoginActivity.class);
            // These flags clear the back-stack so the user can't press "Back" to re-enter the app
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Save Pricing Button
        btnSave.setOnClickListener(v -> {
            String carText = etCar.getText().toString();
            String bikeText = etBike.getText().toString();

            if (carText.isEmpty() || bikeText.isEmpty()) {
                Toast.makeText(this, "Please enter prices", Toast.LENGTH_SHORT).show();
                return;
            }

            int car = Integer.parseInt(carText);
            int bike = Integer.parseInt(bikeText);

            Pricing pricing = new Pricing();
            pricing.setCar_price(car);
            pricing.setMotorcycle_price(bike);

            apiService.updatePricing(pricing).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(PricingActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        android.util.Log.e("API_ERROR", "Code: " + response.code());
                        Toast.makeText(PricingActivity.this, "Update Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    android.util.Log.e("API_ERROR", "Error: " + t.getMessage());
                    Toast.makeText(PricingActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadPricing() {
        apiService.getPricing().enqueue(new Callback<Pricing>() {
            @Override
            public void onResponse(Call<Pricing> call, Response<Pricing> response) {
                if (response.isSuccessful() && response.body() != null) {
                    etCar.setText(String.valueOf(response.body().getCar_price()));
                    etBike.setText(String.valueOf(response.body().getMotorcycle_price()));
                } else {
                    android.util.Log.e("API_ERROR", "Failed to load GET Pricing. Code: " + response.code());
                    Toast.makeText(PricingActivity.this, "Error loading prices: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pricing> call, Throwable t) {
                android.util.Log.e("API_ERROR", "GET Pricing Network Error: " + t.getMessage());
                Toast.makeText(PricingActivity.this, "Network Error loading prices", Toast.LENGTH_SHORT).show();
            }
        });
    }
}