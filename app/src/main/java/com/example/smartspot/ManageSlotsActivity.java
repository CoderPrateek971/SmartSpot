package com.example.smartspot;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspot.model.Slot;
import java.util.ArrayList;
import java.util.List;

public class ManageSlotsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SlotAdapter adapter;
    private List<Slot> slotList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_slots);

        recyclerView = findViewById(R.id.recyclerSlots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create Hardcoded Data
        setupHardcodedSlots();

        adapter = new SlotAdapter(this, slotList);
        recyclerView.setAdapter(adapter);
    }

    private void setupHardcodedSlots() {
        slotList.clear();
        for (int i = 1; i <= 6; i++) {
            String slotName = "A0" + i;
            // Assuming Slot constructor: Slot(String slotNumber, String status)
            // Using "1" for Available (Toggle ON)
            slotList.add(new Slot(slotName, "1"));
        }
    }
}