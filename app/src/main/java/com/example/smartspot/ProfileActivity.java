package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartspot.api.ApiClient;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername, tvInitial;
    View btnAdmin, btnEditProfile, btnSupport;
    android.widget.Button btnLogout;
    ImageView btnBack;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        NavbarHelper.setupNavbar(this);

        tvUsername = findViewById(R.id.tvUsername);
        tvInitial = findViewById(R.id.tvInitial);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnSupport = findViewById(R.id.btnSupport);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);

        userId = getIntent().getIntExtra("user_id", 1);

        fetchUserData();

        btnBack.setOnClickListener(v -> finish());

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);

            intent.putExtra("user_id", userId);

            startActivity(intent);
        });

        btnSupport.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SupportActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        });

        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void fetchUserData() {
        String url = ApiClient.BASE_URL+"users";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject user = response.getJSONObject(i);
                            if (user.getInt("user_id") == userId) {
                                String name = user.getString("full_name");
                                tvUsername.setText(name);
                                if (!name.isEmpty()) {
                                    tvInitial.setText(name.substring(0, 1).toUpperCase());
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );
        queue.add(request);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Sign Out", (dialog, which) -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        builder.show();
    }
}