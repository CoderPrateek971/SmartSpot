package com.example.smartspot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartspot.model.Slot;
import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {

    private Context context;
    private List<Slot> slotList;

    public SlotAdapter(Context context, List<Slot> slotList) {
        this.context = context;
        this.slotList = slotList;
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
            // Initial UI state based on "1" (Available) or "0" (Occupied)
            boolean isAvailable = slot.getStatus().equals("1");

            holder.switchStatus.setOnCheckedChangeListener(null); // Clear listener before setting state
            holder.switchStatus.setChecked(isAvailable);
            updateStatusText(holder.tvStatus, isAvailable);

            // Local Toggle Logic
            holder.switchStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    slot.setStatus("1");
                } else {
                    slot.setStatus("0");
                }
                updateStatusText(holder.tvStatus, isChecked);
            });
        }
    }

    private void updateStatusText(TextView textView, boolean isAvailable) {
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
        return slotList.size();
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