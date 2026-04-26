package com.example.smartspot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing);

        etCar = findViewById(R.id.etCar);
        etBike = findViewById(R.id.etBike);
        btnSave = findViewById(R.id.btnSave);

        apiService = ApiClient.getClient().create(ApiService.class);

        loadPricing();

        btnSave.setOnClickListener(v -> {
            int car = Integer.parseInt(etCar.getText().toString());
            int bike = Integer.parseInt(etBike.getText().toString());

            Pricing pricing = new Pricing();

            // manual set (since no setter)
            try {
                java.lang.reflect.Field f1 = pricing.getClass().getDeclaredField("car_price");
                f1.setAccessible(true);
                f1.set(pricing, car);

                java.lang.reflect.Field f2 = pricing.getClass().getDeclaredField("motorcycle_price");
                f2.setAccessible(true);
                f2.set(pricing, bike);
            } catch (Exception e) {
                e.printStackTrace();
            }

            apiService.updatePricing(pricing).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(PricingActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(PricingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadPricing() {
        apiService.getPricing().enqueue(new Callback<Pricing>() {
            @Override
            public void onResponse(Call<Pricing> call, Response<Pricing> response) {
                if (response.body() != null) {
                    etCar.setText(String.valueOf(response.body().getCar_price()));
                    etBike.setText(String.valueOf(response.body().getMotorcycle_price()));
                }
            }

            @Override
            public void onFailure(Call<Pricing> call, Throwable t) {
                Toast.makeText(PricingActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }
}