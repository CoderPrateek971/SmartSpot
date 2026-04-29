package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup paymentGroup;
    private Button continueBtn;

    private int bookingId;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentGroup = findViewById(R.id.paymentGroup);
        continueBtn = findViewById(R.id.continueBtn);

        bookingId = getIntent().getIntExtra("booking_id", -1);
        amount = getIntent().getStringExtra("amount");

        if (amount != null) {
            continueBtn.setText("Pay ₹" + amount);
        }

        continueBtn.setOnClickListener(v -> {

            if (paymentGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            continueBtn.setEnabled(false);
            continueBtn.setText("Processing...");

            completePaymentAndEndBooking();
        });
    }

    private void completePaymentAndEndBooking() {
        if (bookingId == -1) {
            Toast.makeText(this, "Error: Booking ID lost!", Toast.LENGTH_SHORT).show();
            continueBtn.setEnabled(true);
            continueBtn.setText("Pay ₹" + amount);
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("booking_id", bookingId);
        map.put("total_amount", amount);
        map.put("total_hours", "1");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.endBooking(map).enqueue(new Callback<HashMap<String, Object>>() {
            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
                    intent.putExtra("booking_id", bookingId);
                    intent.putExtra("amount", amount);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Log.e("API_ERROR", "End Booking Failed: " + response.code());
                    Toast.makeText(PaymentActivity.this, "Failed to update server. Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    continueBtn.setEnabled(true);
                    continueBtn.setText("Pay ₹" + amount);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                Log.e("API_ERROR", "Network Error: " + t.getMessage());
                Toast.makeText(PaymentActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                continueBtn.setEnabled(true);
                continueBtn.setText("Pay ₹" + amount);
            }
        });
    }
}