package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

        paymentGroup = findViewById(R.id.paymentGroup);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(v -> {

            int selectedId = paymentGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(PaymentActivity.this, ConfirmationActivity.class);
            startActivity(intent);
        });
    }
}