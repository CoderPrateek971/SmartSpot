//package com.example.smartspot;
//
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.smartspot.model.Slot;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ManageSlotsActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private SlotAdapter adapter;
//    private List<Slot> slotList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manage_slots);
//
//        // 1. Initialize RecyclerView
//        recyclerView = findViewById(R.id.recyclerSlots);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        setupHardcodedSlots();
//
//        adapter = new SlotAdapter(this, slotList);
//        recyclerView.setAdapter(adapter);
//
//        // 2. Back Button Logic
//        ImageView btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(v -> finish());
//
//        // 3. Bottom Nav Home
//        LinearLayout btnHome = findViewById(R.id.btnHome);
//        btnHome.setOnClickListener(v -> {
//            // Goes back to the previous screen (usually dashboard)
//            finish();
//        });
//
//        // 4. Bottom Nav Sign Out
//        LinearLayout btnSignOut = findViewById(R.id.btnSignOut);
//        btnSignOut.setOnClickListener(v -> {
//            // Add your logout logic here (clearing preferences, etc.)
//            finish();
//        });
//    }
//
//    private void setupHardcodedSlots() {
//        slotList.clear();
//        for (int i = 1; i <= 6; i++) {
//            slotList.add(new Slot("A0" + i, "1"));
//        }
//    }
//}

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

        // 1. Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerSlots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup Adapter
        adapter = new SlotAdapter(this, slotList);
        recyclerView.setAdapter(adapter);

        // Fetch real slots from Backend instead of hardcoded ones
        loadSlotsFromAPI();

        // 2. Back Button Logic
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            onBackPressed(); // This properly triggers the back action
        });

        // 3. Bottom Nav Home
        LinearLayout btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            // Change AdminDashboardActivity.class to your actual Home screen for Admin
            Intent intent = new Intent(ManageSlotsActivity.this, AdminDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // 4. Bottom Nav Sign Out
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