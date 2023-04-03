package com.example.ppbus.ui.logistic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ppbus.data.Packages;
import com.example.ppbus.R;
import com.example.ppbus.data.Packages2;
import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.data.realTimeNearStop.RealTimeStopName;
import com.example.ppbus.data.stopOfRoute.Stops;
import com.example.ppbus.viewmodel.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackagesFragment extends Fragment {
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private ViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_packages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeVariables(view);
        viewModel.getConfirmedPackages();
        viewModel.getConfirmedPackages2Live().observe(requireActivity(), new Observer<List<Packages2>>() {
            @Override
            public void onChanged(List<Packages2> packages2List) {
                packagesAdapter.setPackages2List(packages2List);
            }
        });
    }

    private void initializeVariables(View view) {
        recyclerView = view.findViewById(R.id.rv_packages);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        packagesAdapter = new PackagesAdapter();
        recyclerView.setAdapter(packagesAdapter);
    }
}