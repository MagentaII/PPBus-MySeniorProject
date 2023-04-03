package com.example.ppbus.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ppbus.R;
import com.example.ppbus.data.Packages2;
import com.example.ppbus.viewmodel.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    private EditText etConsignor, etConsignee, etAddress;
    private Button btnConfirm;
    private int idNumber = 1; //當前ID的數字部分，例如：1、2、3
    private ViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initializeVariable();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consignor = etConsignor.getText().toString();
                String consignee = etConsignee.getText().toString();
                String address = etAddress.getText().toString();
                viewModel.addPackage(consignor, consignee, address);
            }
        });
    }

    private void initializeVariable() {
        etConsignor = findViewById(R.id.et_consignor);
        etConsignee = findViewById(R.id.et_consignee);
        etAddress = findViewById(R.id.et_address);
        btnConfirm = findViewById(R.id.btn_confirm);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
    }
}