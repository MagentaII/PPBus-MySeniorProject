package com.example.ppbus.login_and_register;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ppbus.ui.bus.BusActivity;
import com.example.ppbus.ui.driver.DriverActivity;
import com.example.ppbus.ui.logistic.LogisticActivity;
import com.example.ppbus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvChangeSignup;
    android.app.AlertDialog.Builder builder;
    android.app.AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeVariable(view);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new android.app.AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                dialog = builder.create();
                dialog.show();

                if(checkUsername() & checkPassword()){
                    checkUser();
                }
                dialog.dismiss();
            }
        });
        
    }

    private void initializeVariable(View view) {
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvChangeSignup = view.findViewById(R.id.tv_forgetpassword);
    }

    private Boolean checkUsername(){
        String check = etUsername.getText().toString();
        if(check.isEmpty()){
            etUsername.setError("帳號不得為空白");
            return false;
        }else{
            etUsername.setError(null);
            return true;
        }
    }

    private Boolean checkPassword(){
        String check = etPassword.getText().toString();
        if(check.isEmpty()){
            etPassword.setError("密碼不得為空白");
            return false;
        }else{
            etPassword.setError(null);
            return true;
        }
    }

    private void checkUser(){
        String userUsername = etUsername.getText().toString().trim(); // trim()去除頭尾空白
        String userPassword = etPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserIfEqualDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserIfEqualDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    etUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userPassword)){
                        etUsername.setError(null);
                        String roleFromDB = snapshot.child(userUsername).child("role").getValue(String.class);
                        switch (roleFromDB){
                            case "0":
                                Intent intent0 = new Intent(getContext(), BusActivity.class);
                                startActivity(intent0);
                                break;
                            case "1":
                                Intent intent1 = new Intent(getContext(), LogisticActivity.class);
                                startActivity(intent1);
                                break;
                            case "2":
                                SharedPreferences preferences = requireActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("username", userUsername);
                                editor.apply();
                                Intent intent2 = new Intent(getContext(), DriverActivity.class);
                                startActivity(intent2);
                                break;
                        }
                    }else{
                        etPassword.setError("Invalid Credentials");
                        etPassword.requestFocus();
                    }
                }else{
                    etUsername.setError("User does not exist");
                    etUsername.requestFocus();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }
}