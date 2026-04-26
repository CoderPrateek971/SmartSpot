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
    ImageView imgQR;
    Button btnActiveBooking, btnPastBooking;

    String slot, vehicleNumber, vehicleType, price, startTime;
    int bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        // ================= INIT VIEWS =================
        tvSlot = findViewById(R.id.tvSlot);
        tvVehicleNumber = findViewById(R.id.tvVehicleNumber);
        tvVehicleType = findViewById(R.id.tvVehicleType);
        tvPrice = findViewById(R.id.tvPrice);
        tvStartTime = findViewById(R.id.tvStartTime);
        imgQR = findViewById(R.id.imgQR);

        btnActiveBooking = findViewById(R.id.btnActiveBooking);
        btnPastBooking = findViewById(R.id.btnPastBooking);

        // ================= GET DATA =================
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

        // ================= SET DATA =================
        tvSlot.setText(slot);
        tvVehicleNumber.setText(vehicleNumber);
        tvVehicleType.setText(vehicleType);
        tvPrice.setText("₹" + price + "/hour");
        tvStartTime.setText(startTime);

        // ================= QR =================
        generateQR(bookingId != -1 ? String.valueOf(bookingId) : "TEST_QR");

        // ================= BUTTON ACTIONS =================

        // 🔥 GO TO ACTIVE BOOKING (IMPORTANT)
        btnActiveBooking.setOnClickListener(v -> {

            Intent intent = new Intent(BookingSuccessActivity.this, ActiveBookingActivity.class);

            intent.putExtra("slot", slot);
            intent.putExtra("vehicle_number", vehicleNumber);
            intent.putExtra("price", price);

            // 🔥 TIMER START HERE
            intent.putExtra("start_time_millis", System.currentTimeMillis());

            startActivity(intent);
        });

        // (optional) past booking
        btnPastBooking.setOnClickListener(v -> {
            Toast.makeText(this, "Past Bookings Page", Toast.LENGTH_SHORT).show();
        });
    }

    // ================= QR FUNCTION =================
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