package com.example.ppbus.ui.logistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ppbus.data.Packages;
import com.example.ppbus.R;
import com.example.ppbus.data.Packages2;

import java.util.ArrayList;
import java.util.List;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder> {

    private List<Packages2> packages2List = new ArrayList<>();

    public void setPackages2List(List<Packages2> packages2List) {
        this.packages2List = packages2List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PackagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cell_packages, parent, false);
        return new PackagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackagesViewHolder holder, int position) {
        Packages2 packages2 = packages2List.get(position);
        holder.tvPackageId.setText(String.valueOf(packages2.getId()));
        switch (packages2.getStatus()){
            case 1:
                holder.tvStatus.setText("訂單成立，待收貨");
                break;
            case 2:
                holder.tvStatus.setText("已裝貨，運送中");
                break;
            case 3:
                holder.tvStatus.setText("已到達");
                break;
        }

        holder.tvDate.setText(packages2.getDate());
        holder.tvSender.setText(packages2.getSender());
        holder.tvRecipient.setText(packages2.getRecipient());
        holder.tvAddress.setText(packages2.getAddress());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packages2.setVisibility(!packages2.isVisibility());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        boolean isVisible = packages2.isVisibility();
        holder.expandedLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return packages2List.size();
    }

    static class PackagesViewHolder extends RecyclerView.ViewHolder {
        TextView tvPackageId, tvStatus, tvSender, tvRecipient, tvDate, tvAddress;
        CardView cardView;
        ConstraintLayout expandedLayout;

        public PackagesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPackageId = itemView.findViewById(R.id.tv_package_id);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvSender = itemView.findViewById(R.id.tv_sender);
            tvRecipient = itemView.findViewById(R.id.tv_recipient);
            tvDate = itemView.findViewById(R.id.tv_send_date);
            tvAddress = itemView.findViewById(R.id.tv_address);
            cardView = itemView.findViewById(R.id.cardView);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
        }
    }

}
