package com.example.smartspot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BookingSuccessActivity extends AppCompatActivity {

    TextView tvSlot, tvVehicleNumber, tvVehicleType, tvPrice, tvStartTime;
    ImageView imgQR, btnBack;
    Button btnActiveBooking, btnPastBooking;

    String slot, vehicleNumber, vehicleType, price, startTime;
    int bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        NavbarHelper.setupNavbar(this);

        tvSlot = findViewById(R.id.tvSlot);
        tvVehicleNumber = findViewById(R.id.tvVehicleNumber);
        tvVehicleType = findViewById(R.id.tvVehicleType);
        tvPrice = findViewById(R.id.tvPrice);
        tvStartTime = findViewById(R.id.tvStartTime);
        imgQR = findViewById(R.id.imgQR);
        btnBack = findViewById(R.id.btnBack);

        btnActiveBooking = findViewById(R.id.btnActiveBooking);
        btnPastBooking = findViewById(R.id.btnPastBooking);

        slot = getIntent().getStringExtra("slot");
        if (slot == null) slot = "TBD";

        vehicleNumber = getIntent().getStringExtra("vehicle_number");
        if (vehicleNumber == null) vehicleNumber = "Unknown";

        vehicleType = getIntent().getStringExtra("vehicle_type");
        if (vehicleType == null) vehicleType = "N/A";

        price = getIntent().getStringExtra("price");
        if (price == null) price = "0";

        startTime = getIntent().getStringExtra("start_time");
        if (startTime == null) startTime = "--:--";

        bookingId = getIntent().getIntExtra("booking_id", -1);

        tvSlot.setText(slot);
        tvVehicleNumber.setText(vehicleNumber);
        tvVehicleType.setText(vehicleType);
        tvPrice.setText("₹" + price + "/hour");
        tvStartTime.setText(startTime);

        generateQR(bookingId != -1 ? String.valueOf(bookingId) : "TEST_QR");

        btnBack.setOnClickListener(v -> navigateToBooking());

        btnActiveBooking.setOnClickListener(v -> {
            Intent intent = new Intent(BookingSuccessActivity.this, ActiveBookingActivity.class);
            intent.putExtra("slot", slot);
            intent.putExtra("vehicle_number", vehicleNumber);
            intent.putExtra("price", price);
            intent.putExtra("start_time_millis", System.currentTimeMillis());

            // 🚨 THIS IS THE FIX! We pass the bookingId forward! 🚨
            intent.putExtra("booking_id", bookingId);

            startActivity(intent);
        });

        btnPastBooking.setOnClickListener(v -> {
            Intent intent = new Intent(BookingSuccessActivity.this, PastBookingsActivity.class);
            startActivity(intent);
        });
    }

    private void navigateToBooking() {
        Intent intent = new Intent(BookingSuccessActivity.this, BookingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        navigateToBooking();
    }

    private void generateQR(String text) {
        if (text == null || text.isEmpty()) return;
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
            imgQR.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "QR Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}