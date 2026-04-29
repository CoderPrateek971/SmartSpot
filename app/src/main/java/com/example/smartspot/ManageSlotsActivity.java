package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.Slot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSlotsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SlotAdapter adapter;
    private List<Slot> slotList = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_slots);

        apiService = ApiClient.getClient().create(ApiService.class);

        recyclerView = findViewById(R.id.recyclerSlots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SlotAdapter(this, slotList);
        recyclerView.setAdapter(adapter);

        loadSlotsFromAPI();

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        LinearLayout btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ManageSlotsActivity.this, AdminDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        LinearLayout btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(v -> {
            Toast.makeText(this, "Signing out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ManageSlotsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadSlotsFromAPI() {
        apiService.getAllSlots().enqueue(new Callback<List<Slot>>() {
            @Override
            public void onResponse(Call<List<Slot>> call, Response<List<Slot>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    slotList.clear();
                    slotList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ManageSlotsActivity.this, "Failed to load slots", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Slot>> call, Throwable t) {
                Log.e("API_ERROR", "Error fetching slots: " + t.getMessage());
                Toast.makeText(ManageSlotsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}