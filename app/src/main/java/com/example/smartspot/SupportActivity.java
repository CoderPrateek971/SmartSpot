package com.example.smartspot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView btnBack;

    int userId;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_support);

        NavbarHelper.setupNavbar(this);

        categoryGroup = findViewById(R.id.categoryGroup);
        description = findViewById(R.id.description);
        submitBtn = findViewById(R.id.submitBtn);
        recycler = findViewById(R.id.recycler);
        btnBack = findViewById(R.id.btnBack);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        userId = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .getInt("userId", -1);

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
            Toast.makeText(this, "Error: User not logged in!", Toast.LENGTH_SHORT).show();
            return;
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
        if (userId == -1) {
            return;
        }

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getUserComplaints(userId).enqueue(new Callback<List<SupportTicket>>() {
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