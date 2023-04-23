package com.example.ppbus.ui.bus;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ppbus.R;
import com.example.ppbus.data.Driver;
import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BusManagementAdapter extends RecyclerView.Adapter<BusManagementAdapter.MyViewHolder> {

    private List<Driver> driverList = new ArrayList<>();
    private ViewModel viewModel;
    private LifecycleOwner lifecycleOwner;
    private CountDownTimer countDownTimer;

    public BusManagementAdapter(ViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

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
        viewModel.getTDXRealTimeStation("Kaohsiung", driver.getRouteName()).observe(lifecycleOwner, new Observer<List<RealTimeNearStop>>() {
            @Override
            public void onChanged(List<RealTimeNearStop> realTimeNearStopList) {
                if (!realTimeNearStopList.isEmpty()){
                    holder.tvCurrentPosition.setText(realTimeNearStopList.get(0).getRealTimeStopName().getZh_tw());
                }
            }
        });
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((10000 - millisUntilFinished) / 100);
                holder.progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                viewModel.getTDXRealTimeStation("Kaohsiung", driver.getRouteName()).observe(lifecycleOwner, new Observer<List<RealTimeNearStop>>() {
                    @Override
                    public void onChanged(List<RealTimeNearStop> realTimeNearStopList) {
                        if (!realTimeNearStopList.isEmpty()){
                            holder.tvCurrentPosition.setText(realTimeNearStopList.get(0).getRealTimeStopName().getZh_tw());
                        }
                    }
                });
                holder.progressBar.setProgress(0);
                countDownTimer.start();
            }
        };
        // 啟動計時器
        countDownTimer.start();



        holder.expandedLayout.setVisibility(View.VISIBLE);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                driver.setVisibility(!driver.isVisibility());
//                notifyItemChanged(holder.getAdapterPosition());
//            }
//        });
//        boolean isVisible = driver.isVisibility();
//        holder.expandedLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlateNumb, tvRouteName, tvCurrentPosition, tvPackageNum, tvPassengerNum;
        ProgressBar progressBar;
        CardView cardView;
        ConstraintLayout expandedLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlateNumb = itemView.findViewById(R.id.tv_plate_numb);
            tvRouteName = itemView.findViewById(R.id.tv_route_name);
            tvCurrentPosition = itemView.findViewById(R.id.tv_current_position);
            tvPackageNum = itemView.findViewById(R.id.tv_package_num);
            tvPassengerNum = itemView.findViewById(R.id.tv_passenger_num);
            cardView = itemView.findViewById(R.id.cardView);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
            progressBar = itemView.findViewById(R.id.progressBar2);
        }
    }

}
