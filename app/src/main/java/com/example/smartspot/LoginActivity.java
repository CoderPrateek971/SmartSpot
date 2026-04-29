package com.example.smartspot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;

import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginBtn;
    TextView signupText, toggleUser, toggleAdmin;

    private boolean isAdminSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signupText = findViewById(R.id.signupText);
        toggleUser = findViewById(R.id.toggleUser);
        toggleAdmin = findViewById(R.id.toggleAdmin);

        toggleUser.setOnClickListener(v -> {
            isAdminSelected = false;
            toggleUser.setBackgroundResource(R.drawable.selected_toggle);
            toggleUser.setTextColor(Color.WHITE);
            toggleAdmin.setBackgroundResource(0); // Remove background
            toggleAdmin.setTextColor(Color.parseColor("#888888"));
        });

        toggleAdmin.setOnClickListener(v -> {
            isAdminSelected = true;
            toggleAdmin.setBackgroundResource(R.drawable.selected_toggle);
            toggleAdmin.setTextColor(Color.WHITE);
            toggleUser.setBackgroundResource(0); // Remove background
            toggleUser.setTextColor(Color.parseColor("#888888"));
        });

        loginBtn.setOnClickListener(v -> loginUser());

        signupText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void loginUser() {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                URL url = new URL(ApiClient.BASE_URL+"login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("username", user);
                json.put("password", pass);
                json.put("isAdminMode", isAdminSelected);

                OutputStream os = conn.getOutputStream();
                os.write(json.toString().getBytes());
                os.flush();
                os.close();

                Scanner sc = new Scanner(conn.getInputStream());
                String response = sc.useDelimiter("\\A").next();
                sc.close();

                JSONObject resObj = new JSONObject(response);
                boolean success = resObj.getBoolean("success");

                int tempId = -1;

                if (success) {
                    tempId = resObj.getJSONObject("user").getInt("user_id");
                    android.content.SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    pref.edit().putInt("userId", tempId).apply();
                }

                final int finalLoggedInId = tempId;

                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent intent;
                        if (isAdminSelected) {
                            intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        } else {
                            intent = new Intent(LoginActivity.this, HomeActivity.class);

                            intent.putExtra("user_id", finalLoggedInId);
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        String message = resObj.optString("message", "Invalid Credentials");
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                Log.e("LOGIN_ERROR", e.toString());
                runOnUiThread(() -> Toast.makeText(this, "Connection Error", Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}