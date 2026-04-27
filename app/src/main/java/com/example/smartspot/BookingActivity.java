package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.BookingResponse;
import com.example.smartspot.model.VehicleType;

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
    private ProgressBar progressBar;

    private List<VehicleType> vehicleList = new ArrayList<>();

    private int receivedUserId;
    private int receivedSlotId;
    private String receivedSlotNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Complete Booking");
        }

        spinnerVehicle = findViewById(R.id.spinnerVehicle);
        tvPrice = findViewById(R.id.tvPrice);
        tvSlot = findViewById(R.id.tvSlot);
        etVehicleNumber = findViewById(R.id.etVehicleNumber);
        btnConfirm = findViewById(R.id.btnConfirm);
        ivVehicleIcon = findViewById(R.id.ivVehicleIcon);

        receivedUserId = getIntent().getIntExtra("user_id", 1);
        receivedSlotId = getIntent().getIntExtra("slot_id", -1);
        receivedSlotNumber = getIntent().getStringExtra("slot_number");

        if (receivedSlotNumber != null) {
            tvSlot.setText("Selected Slot: " + receivedSlotNumber);
        } else {
            tvSlot.setText("Selected Slot: N/A");
        }

        loadVehicleTypes();

        btnConfirm.setOnClickListener(v -> bookSlot());

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadVehicleTypes() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getVehicleTypes().enqueue(new Callback<List<VehicleType>>() {
            @Override
            public void onResponse(Call<List<VehicleType>> call, Response<List<VehicleType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    vehicleList = response.body();
                    List<String> names = new ArrayList<>();

                    for (VehicleType v : vehicleList) {
                        names.add(v.getType_name());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            BookingActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            names
                    );

                    spinnerVehicle.setAdapter(adapter);
                    setupSpinnerListener();
                } else {
                    Toast.makeText(BookingActivity.this, "Failed to load vehicle types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<VehicleType>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(BookingActivity.this, "Network Error. Check connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSpinnerListener() {
        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (vehicleList != null && !vehicleList.isEmpty()) {
                    VehicleType selected = vehicleList.get(position);
                    tvPrice.setText("₹" + selected.getPrice_per_hour() + "/hour");

                    String type = selected.getType_name().toLowerCase();
                    if (type.contains("bike") || type.contains("two-wheeler")) {
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

    private void bookSlot() {
        String vehicleNumber = etVehicleNumber.getText().toString().trim();

        if (vehicleNumber.isEmpty()) {
            etVehicleNumber.setError("Vehicle number required");
            return;
        }

        if (vehicleList.isEmpty() || spinnerVehicle.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, "Please wait for vehicle types to load", Toast.LENGTH_SHORT).show();
            return;
        }

        if (receivedSlotId == -1) {
            Toast.makeText(this, "Error: Invalid Slot Selected", Toast.LENGTH_SHORT).show();
            return;
        }

        btnConfirm.setEnabled(false);

        VehicleType selected = vehicleList.get(spinnerVehicle.getSelectedItemPosition());

        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", receivedUserId);
        map.put("slot_id", receivedSlotId);
        map.put("vehicle_type_id", selected.getVehicle_type_id());
        map.put("vehicle_number", vehicleNumber);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.bookSlot(map).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                btnConfirm.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    BookingResponse booking = response.body();
                    navigateToSuccess(booking);
                } else {
                    Toast.makeText(BookingActivity.this, "Booking failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                btnConfirm.setEnabled(true);
                Toast.makeText(BookingActivity.this, "Server error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToSuccess(BookingResponse booking) {
        Intent intent = new Intent(this, BookingSuccessActivity.class);

        intent.putExtra("slot", booking.getSlot());
        intent.putExtra("vehicle_number", booking.getVehicle_number());
        intent.putExtra("vehicle_type", booking.getVehicle_type());
        intent.putExtra("price", booking.getPrice());
        intent.putExtra("start_time", booking.getStart_time());
        intent.putExtra("booking_id", booking.getBooking_id());

        startActivity(intent);
        finish();
    }
}