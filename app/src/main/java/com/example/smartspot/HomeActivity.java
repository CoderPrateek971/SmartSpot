package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.VehicleType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    Spinner vehicleSpinner;
    TextView priceText;
    Button bookBtn;

    List<VehicleType> vehicleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        priceText = findViewById(R.id.priceText);
        bookBtn = findViewById(R.id.bookBtn);

        List<String> defaultList = new ArrayList<>();
        defaultList.add("Loading...");

        ArrayAdapter<String> defaultAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                defaultList
        );
        vehicleSpinner.setAdapter(defaultAdapter);

        loadVehicleTypes();

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

                if (position > 0 && vehicleList != null && !vehicleList.isEmpty()) {
                    VehicleType selected = vehicleList.get(position - 1);
                    priceText.setText("Price: ₹" + selected.getPrice() + "/hr");
                } else {
                    priceText.setText("Price: ₹0/hr");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bookBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
            startActivity(intent);
        });
    }

    private void loadVehicleTypes() {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        Call<List<VehicleType>> call = api.getVehicleTypes();

        call.enqueue(new Callback<List<VehicleType>>() {
            @Override
            public void onResponse(Call<List<VehicleType>> call, Response<List<VehicleType>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    vehicleList = response.body();

                    List<String> names = new ArrayList<>();
                    names.add("Select Vehicle");

                    for (VehicleType v : vehicleList) {
                        names.add(v.getTypeName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            HomeActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            names
                    );

                    vehicleSpinner.setAdapter(adapter);

                    Log.d("API_DEBUG", "Data: " + vehicleList.toString());

                } else {
                    Toast.makeText(HomeActivity.this, "Empty response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<VehicleType>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "API Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }
}