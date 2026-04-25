package com.example.smartspot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    private Spinner spinnerVehicle;
    private TextView tvPrice, tvSlot;
    private EditText etVehicleNumber;
    private Button btnConfirm;
    private ImageView ivVehicleIcon;

    private List<VehicleType> vehicleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // UI Bindings
        spinnerVehicle = findViewById(R.id.spinnerVehicle);
        tvPrice = findViewById(R.id.tvPrice);
        tvSlot = findViewById(R.id.tvSlot);
        etVehicleNumber = findViewById(R.id.etVehicleNumber);
        btnConfirm = findViewById(R.id.btnConfirm);
        ivVehicleIcon = findViewById(R.id.ivVehicleIcon);

        // Set Slot (static for now)
        tvSlot.setText("Selected Slot: A4");

        loadVehicleTypes();

        btnConfirm.setOnClickListener(v -> bookSlot());
    }

    // ================= LOAD VEHICLE TYPES =================
    private void loadVehicleTypes() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getVehicleTypes().enqueue(new Callback<List<VehicleType>>() {
            @Override
            public void onResponse(Call<List<VehicleType>> call, Response<List<VehicleType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VehicleType> apiList = response.body();
                    vehicleList.clear();
                    List<String> names = new ArrayList<>();

                    // Filtering SUV if needed
                    for (VehicleType v : apiList) {
                        if (!v.getType_name().equalsIgnoreCase("SUV")) {
                            vehicleList.add(v);
                            names.add(v.getType_name());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            BookingActivity.this,
                            android.R.layout.simple_spinner_item,
                            names
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerVehicle.setAdapter(adapter);

                    spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (!vehicleList.isEmpty()) {
                                VehicleType selected = vehicleList.get(position);
                                tvPrice.setText("Price per hour: ₹" + selected.getPrice_per_hour());

                                // Dynamic Icon Logic
                                String type = selected.getType_name().toLowerCase();
                                if (type.contains("bike")) {
                                    ivVehicleIcon.setImageResource(R.drawable.bike_icon);
                                } else {
                                    ivVehicleIcon.setImageResource(R.drawable.car_icon);
                                }
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<List<VehicleType>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    // ================= BOOK SLOT =================
    private void bookSlot() {
        String vehicleNumber = etVehicleNumber.getText().toString().trim();

        if (vehicleNumber.isEmpty()) {
            Toast.makeText(this, "Enter vehicle number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vehicleList.isEmpty()) {
            Toast.makeText(this, "Vehicle data not loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        VehicleType selected = vehicleList.get(spinnerVehicle.getSelectedItemPosition());

        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", 1);
        map.put("slot_id", 4);
        map.put("vehicle_type_id", selected.getVehicle_type_id());
        map.put("vehicle_number", vehicleNumber);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // Inside BookingActivity.java, within bookSlot() method
        apiService.bookSlot(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookingActivity.this, "Booking Successful ✅", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BookingActivity.this, "Booking Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}