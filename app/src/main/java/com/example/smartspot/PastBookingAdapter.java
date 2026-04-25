package com.example.smartspot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspot.model.PastBooking;

import java.util.List;

public class PastBookingAdapter extends RecyclerView.Adapter<PastBookingAdapter.ViewHolder> {

    List<PastBooking> list;

    public PastBookingAdapter(List<PastBooking> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_past_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PastBooking b = list.get(position);

        holder.tvSlot.setText("Slot: " + b.getSlot_number());
        holder.tvDate.setText("Date: " + b.getDate());

        double hours = Double.parseDouble(b.getTotal_hours());
        int h = (int) hours;
        int mins = (int)((hours - h) * 60);

        holder.tvDuration.setText("Duration: " + h + " hr " + mins + " mins");
        holder.tvAmount.setText("Amount Paid: ₹" + b.getTotal_amount());
        holder.tvStatus.setText("Status Completed");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSlot, tvDate, tvDuration, tvAmount, tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSlot = itemView.findViewById(R.id.tvSlot);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}