package com.example.ppbus.ui.driver;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ppbus.R;
import com.example.ppbus.viewmodel.ViewModel;

public class RouteFragment extends Fragment {

    private Button btnConfirm;
    private EditText etRoute;
    private String username;
    private ViewModel viewModel;
    private String route;

    public RouteFragment() {
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
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = requireActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        username = preferences.getString("username", "");



        initializeVariable(view);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route = String.valueOf(etRoute.getText());
                SharedPreferences preferences = requireActivity().getSharedPreferences("myRoute", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("route", route);
                editor.apply();
                viewModel.updateRouteName(username, route);
                viewModel.getStops("Kaohsiung", route).observe(requireActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean){
                            NavController navController = Navigation.findNavController(v);
                            navController.navigate(R.id.action_routeFragment_to_nav_home);
                        }
                    }
                });
            }
        });


    }

    private void initializeVariable(View view) {
        btnConfirm = view.findViewById(R.id.btn_confirm);
        etRoute = view.findViewById(R.id.et_route);
    }
}