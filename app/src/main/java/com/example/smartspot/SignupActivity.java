package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword, etPhone;
    AutoCompleteTextView spVehicle;
    Button btnSignup;
    ImageView btnBack; // Declared Back Button

    String[] types = {"Bike", "Car"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);
        spVehicle = findViewById(R.id.spVehicle);
        btnSignup = findViewById(R.id.btnSignup);
        btnBack = findViewById(R.id.btnBack); // Initialize Back Button

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, types);
        spVehicle.setAdapter(adapter);

        btnSignup.setOnClickListener(v -> registerUser());

        // Handle on-screen Back button click
        btnBack.setOnClickListener(v -> navigateToLogin());

        // Handle physical device back button / gesture swipe
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToLogin();
            }
        });
    }

    // Helper method to go back to login screen smoothly
    private void navigateToLogin() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String vehicle = spVehicle.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || vehicle.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        sendDataToServer(name, email, password, phone, vehicle);
    }

    private void sendDataToServer(String name, String email, String password, String phone, String vehicle) {

        // Change this URL to match your actual server IP if you are testing on a real device
        String url = "http://10.7.34.70:3000/signup";

        JSONObject json = new JSONObject();
        try {
            json.put("full_name", name);
            json.put("email", email);
            json.put("password", password);
            json.put("phone", phone);
            json.put("vehicle", vehicle);
        } catch (Exception e) { e.printStackTrace(); }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                json,
                response -> {
                    Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    navigateToLogin(); // Use the helper method here too!
                },
                error -> {
                    Toast.makeText(SignupActivity.this, "User Already Exist or Network Error", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}