package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BillingActivity extends AppCompatActivity {

    TextView tvTime, tvRate, tvTotal;
    Button btnPayNow;

    double totalAmount = 0.0;
    int bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        tvTime = findViewById(R.id.tvTime);
        tvRate = findViewById(R.id.tvRate);
        tvTotal = findViewById(R.id.tvTotal);
        btnPayNow = findViewById(R.id.btnPayNow);

        bookingId = getIntent().getIntExtra("booking_id", -1);

        long totalMillis = getIntent().getLongExtra("elapsed_millis", 0);
        String hourlyRateStr = getIntent().getStringExtra("hourly_rate");
        double hourlyRate = (hourlyRateStr != null) ? Double.parseDouble(hourlyRateStr) : 0.0;

        double totalHoursDecimal = totalMillis / (1000.0 * 60 * 60);
        totalAmount = totalHoursDecimal * hourlyRate;

        int hours = (int) (totalMillis / (1000 * 60 * 60));
        int minutes = (int) ((totalMillis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((totalMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        tvTime.setText(String.format("%d hr %d mins %d secs", hours, minutes, seconds));
        tvRate.setText("₹" + (int) hourlyRate + "/hr");

        if (totalAmount < 1.0 && totalMillis > 1000) {
            tvTotal.setText("₹1.00");
            totalAmount = 1.0;
        } else {
            tvTotal.setText("₹" + String.format("%.2f", totalAmount));
        }

        btnPayNow.setOnClickListener(v -> {
            String finalAmountString = String.format("%.2f", totalAmount);

            Intent intent = new Intent(BillingActivity.this, PaymentActivity.class);
            intent.putExtra("amount", finalAmountString);
            intent.putExtra("booking_id", bookingId);
            startActivity(intent);
        });
    }
}