package com.example.smartspot;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BookingSuccessActivity extends AppCompatActivity {

    TextView tvSlot, tvVehicleNumber, tvVehicleType, tvPrice, tvStartTime;
    ImageView imgQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        // 1. Initialize Views
        tvSlot = findViewById(R.id.tvSlot);
        tvVehicleNumber = findViewById(R.id.tvVehicleNumber);
        tvVehicleType = findViewById(R.id.tvVehicleType);
        tvPrice = findViewById(R.id.tvPrice);
        tvStartTime = findViewById(R.id.tvStartTime);
        imgQR = findViewById(R.id.imgQR);

        // 2. Get Data with fallback defaults to prevent NullPointerExceptions
        String slot = getIntent().getStringExtra("slot");
        if (slot == null) slot = "TBD";

        String vehicleNumber = getIntent().getStringExtra("vehicle_number");
        if (vehicleNumber == null) vehicleNumber = "Unknown";

        String vehicleType = getIntent().getStringExtra("vehicle_type");
        if (vehicleType == null) vehicleType = "N/A";

        String price = getIntent().getStringExtra("price");
        if (price == null) price = "0";

        String startTime = getIntent().getStringExtra("start_time");
        if (startTime == null) startTime = "--:--";

        int bookingId = getIntent().getIntExtra("booking_id", -1);

        // 3. Set Text safely
        if (tvSlot != null) tvSlot.setText(slot);
        if (tvVehicleNumber != null) tvVehicleNumber.setText(vehicleNumber);
        if (tvVehicleType != null) tvVehicleType.setText(vehicleType);
        if (tvPrice != null) tvPrice.setText("₹" + price + "/hour");
        if (tvStartTime != null) tvStartTime.setText(startTime);

        // 4. Generate QR only if we have a valid ID or a placeholder
        generateQR(bookingId != -1 ? String.valueOf(bookingId) : "TEST_QR");
    }

    private void generateQR(String text) {
        // Double check text isn't null
        if (text == null || text.isEmpty() || imgQR == null) return;

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