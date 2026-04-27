package com.example.smartspot;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
import com.example.smartspot.model.Slot;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {

    private Context context;
    private List<Slot> slotList;
    private ApiService apiService;

    public SlotAdapter(Context context, List<Slot> slotList) {
        this.context = context;
        this.slotList = slotList;
        // Initialize API Service
        this.apiService = ApiClient.getClient().create(ApiService.class);
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slot_item, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        Slot slot = slotList.get(position);

        if (slot != null) {
            holder.tvSlotNumber.setText("Slot: " + slot.getSlot_number());

            // Check if slot is active/enabled.
            // Using isActive() because HomeActivity filters by it!
            boolean isAvailable = (slot.getIsActive() == 1) || "1".equals(slot.getStatus());

            // 1. Reset listener to null before setting state to avoid logic loops
            holder.switchStatus.setOnCheckedChangeListener(null);
            holder.switchStatus.setChecked(isAvailable);
            updateStatusText(holder.tvStatus, isAvailable);

            // 2. Set the actual listener
            holder.switchStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {

                // Temporarily save old values in case API fails
                int oldIsActive = slot.getIsActive();
                String oldStatus = slot.getStatus();

                // Optimistically update the local model & UI
                slot.setIsActive(isChecked ? 1 : 0);
                slot.setStatus(isChecked ? "1" : "0");
                updateStatusText(holder.tvStatus, isChecked);

                // 3. Make the API Call to backend
                apiService.updateSlotStatus(slot).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Slot " + slot.getSlot_number() + " Updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            // If backend returns an error (404, 500)
                            Log.e("API_ERROR", "Update Failed. Code: " + response.code());
                            Toast.makeText(context, "Failed to update database", Toast.LENGTH_SHORT).show();

                            // Revert the changes
                            revertSwitchState(holder, slot, oldIsActive, oldStatus);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("API_ERROR", "Network Error: " + t.getMessage());
                        Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();

                        // Revert the changes
                        revertSwitchState(holder, slot, oldIsActive, oldStatus);
                    }
                });
            });
        }
    }

    // Helper method to safely revert the UI if the backend call fails
    private void revertSwitchState(SlotViewHolder holder, Slot slot, int oldIsActive, String oldStatus) {
        slot.setIsActive(oldIsActive);
        slot.setStatus(oldStatus);

        // Trigger a safe UI refresh for this specific item
        int currentPos = holder.getAdapterPosition();
        if (currentPos != RecyclerView.NO_POSITION) {
            notifyItemChanged(currentPos);
        }
    }

    private void updateStatusText(TextView textView, boolean isAvailable) {
        if (textView == null) return;

        if (isAvailable) {
            textView.setText("Available");
            textView.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            textView.setText("Disabled");
            textView.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return slotList != null ? slotList.size() : 0;
    }

    public static class SlotViewHolder extends RecyclerView.ViewHolder {
        TextView tvSlotNumber, tvStatus;
        SwitchCompat switchStatus;

        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSlotNumber = itemView.findViewById(R.id.tvSlotNumber);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            switchStatus = itemView.findViewById(R.id.switchStatus);
        }
    }
}