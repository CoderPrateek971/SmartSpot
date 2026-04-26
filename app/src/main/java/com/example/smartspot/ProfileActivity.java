package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername, tvInitial;
    private View btnAdmin;
    android.widget.Button btnLogout;    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUsername);
        tvInitial = findViewById(R.id.tvInitial);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnLogout = findViewById(R.id.btnLogout);

        userId = getIntent().getIntExtra("user_id", -1);

        fetchUserData();

        btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));        });

        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void fetchUserData() {
        String url = "http://10.0.2.2:3000/users";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject user = response.getJSONObject(i);

                            if (user.getInt("user_id") == userId) {
                                String name = user.getString("full_name");

                                tvUsername.setText(name);
                                tvInitial.setText(name.substring(0, 1));
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