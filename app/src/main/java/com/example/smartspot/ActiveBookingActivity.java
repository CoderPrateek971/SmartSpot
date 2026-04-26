package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActiveBookingActivity extends AppCompatActivity {

    private TextView tvTimer, tvSlot, tvVehicle, tvRate;
    private Button btnEndBooking;

    private Handler timerHandler = new Handler();
    private long startTimeMillis = 0L;
    private String rawRate = "0"; // To store the numeric rate safely

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_booking);

        // 1. Initialize Views
        tvTimer = findViewById(R.id.tvTimer);
        tvSlot = findViewById(R.id.tvSlot);
        tvVehicle = findViewById(R.id.tvVehicle);
        tvRate = findViewById(R.id.tvRate);
        btnEndBooking = findViewById(R.id.btnEndBooking);

        // 2. Load Data from Intent with Null Safety
        loadDataFromIntent();

        // 3. Start the Timer
        startTimeMillis = System.currentTimeMillis();
        timerHandler.postDelayed(updateTimerThread, 0);

        // 4. Handle End Booking Click
        btnEndBooking.setOnClickListener(v -> endBooking());
    }

    private void loadDataFromIntent() {
        // Ensure these keys match exactly what you sent from the previous activity
        String slot = getIntent().getStringExtra("slot");
        String vehicle = getIntent().getStringExtra("vehicle_number");
        String rate = getIntent().getStringExtra("price");

        tvSlot.setText("Slot: " + (slot != null ? slot : "N/A"));
        tvVehicle.setText("Vehicle: " + (vehicle != null ? vehicle : "Unknown"));

        // Save the raw numeric rate for calculation later
        if (rate != null && !rate.isEmpty()) {
            rawRate = rate;
            tvRate.setText("Rate: ₹" + rawRate + "/hr");
        } else {
            rawRate = "0";
            tvRate.setText("Rate: ₹ -- /hr"); // Looks much cleaner than "Loading..."
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

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            tvTimer.setText(timeString);

            timerHandler.postDelayed(this, 1000);
        }
    };

    private void endBooking() {
        timerHandler.removeCallbacks(updateTimerThread);
        long elapsedMillis = System.currentTimeMillis() - startTimeMillis;

        // Get the vehicle type ID (this should come from your database/intent)
        // For this example, I'll assume you have it in a variable called vehicleTypeId
        int vehicleTypeId = getIntent().getIntExtra("vehicle_type_id", 1);

        Intent intent = new Intent(ActiveBookingActivity.this, BillingActivity.class);
        intent.putExtra("elapsed_millis", elapsedMillis);
        intent.putExtra("vehicle_type_id", vehicleTypeId); // CRUCIAL: Pass the ID

        startActivity(intent);
        finish();
    }
}