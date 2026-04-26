package com.example.smartspot;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.Booking;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationActivity extends AppCompatActivity {

    TextView slotText, dateText, durationText, amountText, vehicleText, paymentText, priceText, invoiceText;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        slotText = findViewById(R.id.slotText);
        dateText = findViewById(R.id.dateText);
        durationText = findViewById(R.id.durationText);
        amountText = findViewById(R.id.amountText);
        vehicleText = findViewById(R.id.vehicleText);
        paymentText = findViewById(R.id.paymentText);
        priceText = findViewById(R.id.priceText);
        invoiceText = findViewById(R.id.invoiceText);
        backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(v -> finish());

        int bookingId = -1;

        if (getIntent() != null && getIntent().getExtras() != null) {
            Object idObj = getIntent().getExtras().get("booking_id");
            if (idObj != null) {
                try {
                    bookingId = Integer.parseInt(idObj.toString());
                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (bookingId > 0) {
            loadBooking(bookingId);
        } else {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void loadBooking(int id) {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getBookingById(id).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Booking b = response.body();

                    invoiceText.setText("Invoice ID : #PKG-2026-" + String.format("%04d", b.getBooking_id()));
                    slotText.setText("Slot: " + b.getSlot());
                    durationText.setText("Duration: " + b.getDuration() + " hr");
                    amountText.setText("Total amount: ₹" + b.getAmount());
                    vehicleText.setText("Vehicle: " + b.getVehicle_no());

                    // ==========================================
                    // BULLETPROOF PRICE CHECK
                    // ==========================================
                    double hourlyPrice = b.getPrice();

                    // If Node.js sends 0, calculate it manually (Total Amount / Total Hours)
                    if (hourlyPrice == 0.0) {
                        try {
                            double hours = Double.parseDouble(b.getDuration());
                            if (hours > 0) {
                                hourlyPrice = b.getAmount() / hours;
                            }
                        } catch (Exception ignored) { }
                    }

                    priceText.setText("Price : ₹" + hourlyPrice + "/hr");
                    // ==========================================

                    String cleanDate = b.getDate();
                    if (cleanDate != null && cleanDate.contains("T")) {
                        cleanDate = cleanDate.substring(0, cleanDate.indexOf("T"));
                    }
                    dateText.setText("Date: " + cleanDate);

                    if (b.getPayment_method() != null) {
                        paymentText.setText("Paid via: " + b.getPayment_method());
                    } else {
                        paymentText.setText("Paid via: Card/UPI");
                    }
                } else {
                    Toast.makeText(ConfirmationActivity.this, "Server Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                Toast.makeText(ConfirmationActivity.this, "API Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}