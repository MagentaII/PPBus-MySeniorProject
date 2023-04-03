package com.example.ppbus.ui.bus;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusManagementAdapter extends RecyclerView.Adapter<BusManagementAdapter.BusManagementViewHolder> {
    @NonNull
    @Override
    public BusManagementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BusManagementViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class BusManagementViewHolder extends RecyclerView.ViewHolder {
        public BusManagementViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
