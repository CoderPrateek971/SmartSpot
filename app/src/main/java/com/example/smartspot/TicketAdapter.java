package com.example.smartspot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspot.model.SupportTicket;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.VH> {

    List<SupportTicket> list;

    public TicketAdapter(List<SupportTicket> list) {
        this.list = list;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView ticketId, status;

        public VH(View v) {
            super(v);
            ticketId = v.findViewById(R.id.ticketId);
            status = v.findViewById(R.id.status);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH h, int i) {
        SupportTicket t = list.get(i);
        h.ticketId.setText("Ticket ID: #" + t.getTicket_id());
        h.status.setText("Status: " + t.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}