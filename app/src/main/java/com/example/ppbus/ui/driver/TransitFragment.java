package com.example.ppbus.ui.driver;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import com.example.ppbus.data.Packages2;
import com.example.ppbus.viewmodel.ViewModel;

import java.util.List;

public class TransitFragment extends Fragment {

    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private TransitAdapter transitAdapter;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = requireActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        username = preferences.getString("username", "");

        initializeVariables(view);
        viewModel.getTransitPackages(username).observe(requireActivity(), new Observer<List<Packages2>>() {
            @Override
            public void onChanged(List<Packages2> packages2List) {
                transitAdapter.setPackages2List(packages2List);
            }
        });
    }

    private void initializeVariables(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        transitAdapter = new TransitAdapter(viewModel);
        recyclerView.setAdapter(transitAdapter);
    }
}