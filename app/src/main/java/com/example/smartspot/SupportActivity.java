package com.example.smartspot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView; // Added
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.SupportTicket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity {

    RadioGroup categoryGroup;
    EditText description;
    Button submitBtn;
    RecyclerView recycler;
    ImageView btnBack; // Added

    int userId;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_support);

        // Navbar logic (if applicable)
        NavbarHelper.setupNavbar(this);

        categoryGroup = findViewById(R.id.categoryGroup);
        description = findViewById(R.id.description);
        submitBtn = findViewById(R.id.submitBtn);
        recycler = findViewById(R.id.recycler);
        btnBack = findViewById(R.id.btnBack); // Make sure this ID matches your XML

        recycler.setLayoutManager(new LinearLayoutManager(this));

        // Setup Back Button click
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        userId = getSharedPreferences("USER", MODE_PRIVATE)
                .getInt("user_id", -1);

        loadTickets();

        submitBtn.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        int selectedId = categoryGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Please select an issue category", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        String category = rb.getText().toString();
        String desc = description.getText().toString().trim();

        if (desc.isEmpty()) {
            description.setError("Please describe your issue");
            return;
        }

        if (userId == -1) {
            userId = 1;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("user_id", userId);
        body.put("subject", category);
        body.put("message", desc);

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.createComplaint(body).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> res) {
                if (res.isSuccessful() && res.body() != null) {
                    Toast.makeText(SupportActivity.this, "Complaint Submitted Successfully", Toast.LENGTH_SHORT).show();
                    description.setText("");
                    loadTickets();
                } else {
                    Toast.makeText(SupportActivity.this, "Server error code: " + res.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(SupportActivity.this, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadTickets() {
        int fetchId = (userId == -1) ? 1 : userId;

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getUserComplaints(fetchId).enqueue(new Callback<List<SupportTicket>>() {
            @Override
            public void onResponse(Call<List<SupportTicket>> call, Response<List<SupportTicket>> res) {
                if (res.isSuccessful() && res.body() != null) {
                    recycler.setAdapter(new TicketAdapter(res.body()));
                }
            }

            @Override
            public void onFailure(Call<List<SupportTicket>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}