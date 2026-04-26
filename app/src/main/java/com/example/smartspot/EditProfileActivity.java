package com.example.smartspot;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditProfileActivity extends AppCompatActivity {

    EditText username, email, password, confirmPassword, phone;
    Button saveBtn;

    int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // ✅ Get user_id from SharedPreferences instead of Intent
        android.content.SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = pref.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Session expired, please login again", Toast.LENGTH_LONG).show();
            Log.e("EDIT_PROFILE", "userId is -1");
        }

        // ... (rest of your findViewById code remains the same)
    }

    private void updateProfile() {

        String name = username.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();
        String phoneNo = phone.getText().toString().trim();

        if (name.isEmpty() || mail.isEmpty()) {
            Toast.makeText(this, "Fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.isEmpty() && !pass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:3000/updateProfile");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("user_id", userId);
                json.put("full_name", name);
                json.put("email", mail);
                json.put("phone", phoneNo);

                if (!pass.isEmpty()) {
                    json.put("password", pass);
                }

                // 🔥 DEBUG
                Log.d("API_SEND", json.toString());

                OutputStream os = conn.getOutputStream();
                os.write(json.toString().getBytes());
                os.close();

                int responseCode = conn.getResponseCode();
                Log.d("API_RESPONSE_CODE", "Code: " + responseCode);

                if (responseCode == 200) {
                    runOnUiThread(() -> {
                        Toast.makeText(EditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } else {
                    // Read the error message from the server
                    java.util.Scanner s = new java.util.Scanner(conn.getErrorStream()).useDelimiter("\\A");
                    String responseString = s.hasNext() ? s.next() : "";
                    Log.e("API_ERROR_BODY", responseString);

                    runOnUiThread(() -> {
                        Toast.makeText(EditProfileActivity.this, "Update Failed: " + responseCode, Toast.LENGTH_SHORT).show();
                    });
                }

            } catch (Exception e) {
                Log.e("EDIT_PROFILE_ERROR", e.toString());
                runOnUiThread(() ->
                        Toast.makeText(EditProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}