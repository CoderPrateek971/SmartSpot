package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PaymentSuccessActivity extends AppCompatActivity {

    TextView invoiceId, amountPaid, paymentMethod, viewPastBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        // 🔗 Link UI
        invoiceId = findViewById(R.id.invoiceId);
        amountPaid = findViewById(R.id.amountPaid);
        paymentMethod = findViewById(R.id.paymentMethod);
        viewPastBookings = findViewById(R.id.viewPastBookings);


        paymentMethod.setText("Payment Method: UPI/Card");

        viewPastBookings.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSuccessActivity.this, PastBookingsActivity.class);
            startActivity(intent);
        });



        fetchTransaction(1); // You can later make this dynamic
    }

    private void fetchTransaction(int id) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:3000/transaction/" + id);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream is = conn.getInputStream();
                Scanner sc = new Scanner(is).useDelimiter("\\A");
                String response = sc.hasNext() ? sc.next() : "";
                sc.close();

                JSONObject obj = new JSONObject(response);

                boolean success = obj.optBoolean("success", false);

                if (success) {
                    JSONObject data = obj.getJSONObject("data");

                    int transactionId = data.getInt("transaction_id");
                    String amount = data.getString("amount_paid");

                    runOnUiThread(() -> {
                        invoiceId.setText("Invoice ID: #" + transactionId);
                        amountPaid.setText("Amount Paid: ₹" + amount);
                    });

                } else {
                    runOnUiThread(() ->
                            Toast.makeText(PaymentSuccessActivity.this, "Transaction not found", Toast.LENGTH_SHORT).show()
                    );
                }

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(PaymentSuccessActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}