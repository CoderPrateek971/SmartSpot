package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        NavbarHelper.setupNavbar(this);

        TextView invoiceId = findViewById(R.id.invoiceId);
        TextView amountPaid = findViewById(R.id.amountPaid);
        TextView viewPastBookings = findViewById(R.id.viewPastBookings);

        int bookingId = getIntent().getIntExtra("booking_id", 0);
        String amount = getIntent().getStringExtra("amount");

        if (invoiceId != null) {
            invoiceId.setText("Invoice ID: #PKNG-" + String.format("%04d", bookingId));
        }
        if (amountPaid != null) {
            amountPaid.setText("Amount Paid: ₹" + amount);
        }

        if (viewPastBookings != null) {
            viewPastBookings.setOnClickListener(v -> {
                Intent intent = new Intent(PaymentSuccessActivity.this, PastBookingsActivity.class);

                int userId = getSharedPreferences("UserPrefs", MODE_PRIVATE).getInt("userId", -1);
                intent.putExtra("user_id", userId);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}