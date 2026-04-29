package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class ActiveBookingActivity extends AppCompatActivity {

    private TextView tvTimer, tvSlot, tvVehicle, tvRate;
    private Button btnEndBooking;
    private ImageView btnBack;

    private Handler timerHandler = new Handler();
    private long startTimeMillis = 0L;
    private String rawRate = "0";

    private String slot, vehicleNumber, price;
    private int bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_booking);

        tvTimer = findViewById(R.id.tvTimer);
        tvSlot = findViewById(R.id.tvSlot);
        tvVehicle = findViewById(R.id.tvVehicle);
        tvRate = findViewById(R.id.tvRate);
        btnEndBooking = findViewById(R.id.btnEndBooking);
        btnBack = findViewById(R.id.btnBack);

        loadDataFromIntent();

        startTimeMillis = getIntent().getLongExtra("start_time_millis", System.currentTimeMillis());
        timerHandler.postDelayed(updateTimerThread, 0);

        btnEndBooking.setOnClickListener(v -> endBooking());

        btnBack.setOnClickListener(v -> navigateToSuccess());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToSuccess();
            }
        });
    }

    private void loadDataFromIntent() {
        slot = getIntent().getStringExtra("slot");
        vehicleNumber = getIntent().getStringExtra("vehicle_number");
        price = getIntent().getStringExtra("price");

        bookingId = getIntent().getIntExtra("booking_id", -1);

        tvSlot.setText("Slot: " + (slot != null ? slot : "N/A"));
        tvVehicle.setText("Vehicle: " + (vehicleNumber != null ? vehicleNumber : "Unknown"));

        if (price != null && !price.isEmpty()) {
            rawRate = price;
            tvRate.setText("Rate: ₹" + rawRate + "/hr");
        } else {
            rawRate = "0";
            tvRate.setText("Rate: ₹ -- /hr");
        }
    }

    private final Runnable updateTimerThread = new Runnable() {
        public void run() {
            long timeInMilliseconds = System.currentTimeMillis() - startTimeMillis;

            int seconds = (int) (timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            tvTimer.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            timerHandler.postDelayed(this, 1000);
        }
    };

    private void endBooking() {
        timerHandler.removeCallbacks(updateTimerThread);
        long elapsedMillis = System.currentTimeMillis() - startTimeMillis;

        Intent intent = new Intent(ActiveBookingActivity.this, BillingActivity.class);
        intent.putExtra("elapsed_millis", elapsedMillis);
        intent.putExtra("hourly_rate", rawRate);
        intent.putExtra("slot", slot);

        intent.putExtra("booking_id", bookingId);

        startActivity(intent);
        finish();
    }

    private void navigateToSuccess() {
        Intent intent = new Intent(ActiveBookingActivity.this, BookingSuccessActivity.class);
        intent.putExtra("slot", slot);
        intent.putExtra("vehicle_number", vehicleNumber);
        intent.putExtra("price", rawRate);
        intent.putExtra("booking_id", bookingId); // Pass it back just in case
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(updateTimerThread);
    }
}