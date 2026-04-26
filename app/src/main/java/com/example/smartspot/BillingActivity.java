package com.example.smartspot;

import android.content.Intent; // Add this import
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BillingActivity extends AppCompatActivity {

    private TextView tvTime, tvRate, tvTotal;
    private Button btnPayNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        tvTime = findViewById(R.id.tvTime);
        tvRate = findViewById(R.id.tvRate);
        tvTotal = findViewById(R.id.tvTotal);
        btnPayNow = findViewById(R.id.btnPayNow);

        // 1. Get raw data from Intent
        long totalMillis = getIntent().getLongExtra("elapsed_millis", 0);
        int vehicleTypeId = getIntent().getIntExtra("vehicle_type_id", 1);

        // 2. Logic: Price as per vehicle type
        double hourlyRate;
        if (vehicleTypeId == 1) {
            hourlyRate = 20.0;
        } else if (vehicleTypeId == 2) {
            hourlyRate = 50.0;
        } else {
            hourlyRate = 30.0;
        }

        // 3. Calculate Total Amount
        double totalHoursDecimal = totalMillis / (1000.0 * 60 * 60);
        double totalAmount = totalHoursDecimal * hourlyRate;

        // 4. Time Bifurcation (H:M:S)
        int hours = (int) (totalMillis / (1000 * 60 * 60));
        int minutes = (int) (totalMillis % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((totalMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        // 5. Update UI
        tvTime.setText(String.format("%d hr %d mins %d secs", hours, minutes, seconds));
        tvRate.setText("₹" + (int) hourlyRate + "/hr");

        if (totalAmount < 0.01 && totalMillis > 1000) {
            tvTotal.setText("₹0.01");
        } else {
            tvTotal.setText("₹" + String.format("%.2f", totalAmount));
        }

        // ==========================================
        // REDIRECT LOGIC
        // ==========================================
        btnPayNow.setOnClickListener(v -> {
            // Create intent to go to PaymentActivity
            Intent intent = new Intent(BillingActivity.this, PaymentActivity.class);

            // Pass the calculated amount so the next page can show it
            intent.putExtra("total_amount", totalAmount);

            // Start the activity
            startActivity(intent);

            // Optional: finish() this activity so user can't go back to the bill after paying
            // finish();
        });
    }
}