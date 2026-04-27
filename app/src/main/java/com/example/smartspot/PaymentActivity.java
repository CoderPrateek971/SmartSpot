package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    RadioGroup paymentGroup;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ImageView btnBack = findViewById(R.id.btnBack);

        // 2. Set the click listener to finish the activity
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                finish();
            });
        }

        paymentGroup = findViewById(R.id.paymentGroup);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(v -> {

            int selectedId = paymentGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get any data passed from BillingActivity (like amount or bookingId)
            String amount = getIntent().getStringExtra("amount");
            int bookingId = getIntent().getIntExtra("booking_id", -1);

            // Redirect to PaymentSuccessActivity instead of ConfirmationActivity
            Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);

            // Pass the data forward so the success screen knows what was paid
            intent.putExtra("amount", amount);
            intent.putExtra("booking_id", bookingId);

            startActivity(intent);
            finish(); // Finish this activity so they can't go back to the pay screen
        });
    }
}