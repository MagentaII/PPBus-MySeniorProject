package com.example.ppbus.login_and_register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ppbus.R;
import com.example.ppbus.data.Driver;
import com.example.ppbus.data.User;
import com.example.ppbus.viewmodel.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText etEmail, etUsername, etPassword;
    private Button btnRegister;
    private Spinner spnRole;
    private String role;
    private ViewModel viewModel;

//    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        initializeVariable(view);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User(email, username, password, role);
                if (Objects.equals(role, "2")){
                    Driver driver = new Driver(username, "", 0);
                    addDriver(driver, username);
                }
                doRegister(user, username);
            }
        });
        
    }

    private void initializeVariable(View view) {
        etEmail = view.findViewById(R.id.et_email);
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnRegister = view.findViewById(R.id.btn_register);
        spnRole = view.findViewById(R.id.spn_role);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRole.setAdapter(adapter);
        spnRole.setOnItemSelectedListener(this); // 需要 implements一個OnItemSelectedListener介面
    }

    private void doRegister(User user, String username){
        viewModel.doRegister(user, username);
        Toast.makeText(getContext(), "You have signup successfully!!", Toast.LENGTH_SHORT).show();
    }

    private void addDriver(Driver driver, String username){
        viewModel.addDriver(driver, username);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        role = String.valueOf(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}