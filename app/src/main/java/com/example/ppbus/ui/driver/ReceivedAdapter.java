package com.example.ppbus.ui.driver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ppbus.R;
import com.example.ppbus.data.Packages2;
import com.example.ppbus.ui.logistic.ConfirmedAdapter;
import com.example.ppbus.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReceivedAdapter extends RecyclerView.Adapter<ReceivedAdapter.MyViewHolder> {

    private List<Packages2> packages2List = new ArrayList<>();
    private ViewModel viewModel;

    public ReceivedAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setPackages2List(List<Packages2> packages2List) {
        this.packages2List = packages2List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cell_received, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Packages2 packages2 = packages2List.get(position);
        holder.tvId.setText(String.valueOf(packages2.getId()));
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
        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = packages2.getId();
                viewModel.updateStatus(id, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packages2List.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvDate, tvSender, tvRecipient, tvAddress;
        private Button btnConfirm;
        CardView cardView;
        ConstraintLayout expandedLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_package_id);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvSender = itemView.findViewById(R.id.tv_sender);
            tvRecipient = itemView.findViewById(R.id.tv_recipient);
            tvAddress = itemView.findViewById(R.id.tv_address);
            btnConfirm = itemView.findViewById(R.id.btn_confirm);
            cardView = itemView.findViewById(R.id.cardView);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
        }
    }
}
