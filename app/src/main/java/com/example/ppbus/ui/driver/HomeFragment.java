package com.example.ppbus.ui.driver;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ppbus.R;
import com.example.ppbus.viewmodel.ViewModel;

public class HomeFragment extends Fragment {
    private CardView cvPackages;
    private TextView tvPackageNum;
    private ViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeVariable(view);
        cvPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_home_to_transitFragment);
            }
        });

        viewModel.getTransitPackagesNum().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvPackageNum.setText(String.valueOf(integer) + "件");
            }
        });


    }

    private void initializeVariable(View view) {
        cvPackages = view.findViewById(R.id.cv_packages);
        tvPackageNum = view.findViewById(R.id.tv_package_num);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
    }
}