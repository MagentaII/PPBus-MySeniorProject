package com.example.ppbus.ui.bus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ppbus.R;
import com.example.ppbus.data.Driver;

import java.util.ArrayList;
import java.util.List;

public class BusManagementAdapter extends RecyclerView.Adapter<BusManagementAdapter.MyViewHolder> {

    private List<Driver> driverList = new ArrayList<>();

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cell_bus_management, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Driver driver = driverList.get(position);
        holder.tvPlateNumb.setText(driver.getPlateNumb());
        holder.tvRouteName.setText(driver.getRouteName());
        holder.tvPackageNum.setText(String.valueOf(driver.getPackageNum()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driver.setVisibility(!driver.isVisibility());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        boolean isVisible = driver.isVisibility();
        holder.expandedLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlateNumb, tvRouteName, tvLastStation, tvStation, tvNextStation, tvCurrentPosition, tvPackageNum, tvPassengerNum;
        CardView cardView;
        ConstraintLayout expandedLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlateNumb = itemView.findViewById(R.id.tv_plate_numb);
            tvRouteName = itemView.findViewById(R.id.tv_route_name);
            tvLastStation = itemView.findViewById(R.id.tv_last_station);
            tvStation = itemView.findViewById(R.id.tv_station);
            tvNextStation = itemView.findViewById(R.id.tv_next_station);
            tvCurrentPosition = itemView.findViewById(R.id.tv_current_position);
            tvPackageNum = itemView.findViewById(R.id.tv_package_num);
            tvPassengerNum = itemView.findViewById(R.id.tv_passenger_num);
            cardView = itemView.findViewById(R.id.cardView);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
        }
    }
}
