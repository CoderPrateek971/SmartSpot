package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.Slot;
import com.example.smartspot.model.VehicleType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    Spinner vehicleSpinner, slotSpinner;
    TextView priceText;
    Button bookBtn;
    Map<String, View> mapButtons = new HashMap<>();
    List<VehicleType> vehicleList = new ArrayList<>();
    List<Slot> slotList = new ArrayList<>();
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NavbarHelper.setupNavbar(this);

        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        slotSpinner = findViewById(R.id.slotSpinner);
        priceText = findViewById(R.id.priceText);
        bookBtn = findViewById(R.id.bookBtn);

        mapButtons.put("A1", findViewById(R.id.btn_A1));
        mapButtons.put("A2", findViewById(R.id.btn_A2));
        mapButtons.put("A3", findViewById(R.id.btn_A3));
        mapButtons.put("A4", findViewById(R.id.btn_A4));
        mapButtons.put("A5", findViewById(R.id.btn_A5));
        mapButtons.put("A6", findViewById(R.id.btn_A6));

        userId = getIntent().getIntExtra("user_id", -1);

        loadVehicleTypes();
        loadSlots();

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!vehicleList.isEmpty()) {
                    VehicleType selected = vehicleList.get(position);
                    priceText.setText("Price : Rs" + selected.getPrice_per_hour() + "/hr");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        bookBtn.setOnClickListener(v -> {
            if (slotList.isEmpty() || vehicleList.isEmpty()) {
                Toast.makeText(this, "No active slots available", Toast.LENGTH_SHORT).show();
                return;
            }
            int vehiclePos = vehicleSpinner.getSelectedItemPosition();
            int slotPos = slotSpinner.getSelectedItemPosition();

            Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("vehicle_type_id", vehicleList.get(vehiclePos).getVehicle_type_id());
            intent.putExtra("slot_id", slotList.get(slotPos).getSlot_id());
            intent.putExtra("slot_number", slotList.get(slotPos).getSlot_number());
            startActivity(intent);
        });
    }

    private void loadSlots() {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.getAvailableSlots().enqueue(new Callback<List<Slot>>() {
            @Override
            public void onResponse(Call<List<Slot>> call, Response<List<Slot>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    slotList.clear();
                    List<String> names = new ArrayList<>();

                    for (View pin : mapButtons.values()) {
                        if (pin != null) pin.setVisibility(View.GONE);
                    }

                    for (Slot s : response.body()) {


                        slotList.add(s);
                        String sNum = s.getSlot_number();
                        names.add(sNum);

                        if (mapButtons.containsKey(sNum)) {
                            View targetPin = mapButtons.get(sNum);
                            if (targetPin != null) targetPin.setVisibility(View.VISIBLE);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, names);
                    slotSpinner.setAdapter(adapter);

                    if (slotList.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "No parking slots are currently enabled.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Slot>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadVehicleTypes() {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.getVehicleTypes().enqueue(new Callback<List<VehicleType>>() {
            @Override
            public void onResponse(Call<List<VehicleType>> call, Response<List<VehicleType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    vehicleList = response.body();
                    List<String> names = new ArrayList<>();
                    for (VehicleType v : vehicleList) names.add(v.getType_name());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, names);
                    vehicleSpinner.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<VehicleType>> call, Throwable t) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSlots();
        loadVehicleTypes();
    }
}