package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
// FIXED: Importing User from the same package as it is not in a 'model' folder in your tree
import com.example.smartspot.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnGoToBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Fix for edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button initialization
        btnGoToBooking = findViewById(R.id.btnGoToBooking);

        if (btnGoToBooking != null) {
            btnGoToBooking.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, BookingActivity.class);
                startActivity(intent);
            });
        }

        // API CALL
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            // FIXED: Added the missing 'e' to make it onResponse
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();

                    for (User user : users) {
                        // Ensure these methods exist in your User.java class
                        Log.d("API_TEST", "Name: " + user.getFullName());
                        Log.d("API_TEST", "Email: " + user.getEmail());
                    }
                } else {
                    Log.d("API_TEST", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("API_TEST", "Error: " + t.getMessage());
            }
        });
    }
}