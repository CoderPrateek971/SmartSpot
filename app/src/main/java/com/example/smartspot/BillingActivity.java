package com.example.smartspot;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// This "extends" part is what the Manifest is looking for
public class BillingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure you have a layout file named activity_billing.xml
        setContentView(R.layout.activity_billing);
    }
}