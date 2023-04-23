package com.example.ppbus.ui.bus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ppbus.R;
import com.example.ppbus.data.Driver;
import com.example.ppbus.ui.driver.ReceivedAdapter;
import com.example.ppbus.viewmodel.ViewModel;

import java.util.List;

public class BusManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private BusManagementAdapter busManagementAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bus_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeVariables(view);
        viewModel.getDrivers().observe(requireActivity(), new Observer<List<Driver>>() {
            @Override
            public void onChanged(List<Driver> drivers) {
                busManagementAdapter.setDriverList(drivers);
            }
        });
    }

    private void initializeVariables(View view) {
        recyclerView = view.findViewById(R.id.rv_bus_management);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        busManagementAdapter = new BusManagementAdapter(viewModel, getViewLifecycleOwner());
        recyclerView.setAdapter(busManagementAdapter);
    }
}