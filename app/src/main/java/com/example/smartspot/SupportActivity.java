package com.example.smartspot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

    int userId;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_support);

        categoryGroup = findViewById(R.id.categoryGroup);
        description = findViewById(R.id.description);
        submitBtn = findViewById(R.id.submitBtn);
        recycler = findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(this));

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

        // 🔥 FALLBACK: If SharedPreferences is empty, force user_id to 1 so the database accepts it.
        // (You can remove this once you implement proper SharedPreferences in LoginActivity)
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
                // 🔥 PROPER CHECK: Only show success if the server returns 200 OK
                if (res.isSuccessful() && res.body() != null) {
                    Toast.makeText(SupportActivity.this, "Complaint Submitted Successfully", Toast.LENGTH_SHORT).show();
                    description.setText(""); // Clear the text box
                    loadTickets(); // Refresh the list
                } else {
                    // Tell us exactly what the server error is!
                    Toast.makeText(SupportActivity.this, "Server rejected it. Error code: " + res.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(SupportActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void loadTickets() {
        // 🔥 Make sure userId is valid before fetching
        int fetchId = (userId == -1) ? 1 : userId;

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getUserComplaints(fetchId).enqueue(new Callback<List<SupportTicket>>() {
            @Override
            public void onResponse(Call<List<SupportTicket>> call, Response<List<SupportTicket>> res) {
                // 🔥 PROTECT AGAINST CRASHES: Ensure body is not null before passing to adapter
                if (res.isSuccessful() && res.body() != null) {
                    recycler.setAdapter(new TicketAdapter(res.body()));
                } else {
                    Toast.makeText(SupportActivity.this, "Failed to load tickets", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SupportTicket>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}