package com.example.ppbus.ui.driver;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ppbus.R;
import com.example.ppbus.data.BusStopEntity;
import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.data.stopOfRoute.StopOfRoute;
import com.example.ppbus.data.stopOfRoute.Stops;
import com.example.ppbus.viewmodel.ViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private CardView cvPackages, cvRouteName;
    private TextView tvPackageNum, tvRouteName, tvLastStation, tvStation, tvNextStation, tvCurrentPosition;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private ViewModel viewModel;
    private String username, route, nowStopName;
    private List<String> stopNameList = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        SharedPreferences preferences = requireActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        username = preferences.getString("username", "");
        SharedPreferences preferences1 = requireActivity().getSharedPreferences("myRoute", MODE_PRIVATE);
        route = preferences1.getString("route", "");
        return view;
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

        viewModel.getTransitPackagesNum(username).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvPackageNum.setText(String.valueOf(integer) + "件");
                viewModel.updateDriverPackageNum(username, integer);
            }
        });

        tvRouteName.setText(route);

        callAPI();

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((10000 - millisUntilFinished) / 100);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                callAPI();
                progressBar.setProgress(0);
                countDownTimer.start();
            }
        };

        // 啟動計時器
        countDownTimer.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getAllStops().removeObservers(getViewLifecycleOwner());
        viewModel.getTDXRealTimeStation("Kaohsiung", route).removeObservers(getViewLifecycleOwner());
    }

    private void initializeVariable(View view) {
        cvPackages = view.findViewById(R.id.cv_packages);
        cvRouteName = view.findViewById(R.id.cv_routeName);
        tvPackageNum = view.findViewById(R.id.tv_package_num);
        tvRouteName = view.findViewById(R.id.tv_routeName);
        tvLastStation = view.findViewById(R.id.tv_last_station);
        tvStation = view.findViewById(R.id.tv_station);
        tvNextStation = view.findViewById(R.id.tv_next_station);
        tvCurrentPosition = view.findViewById(R.id.tv_current_position);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
    }

    private void callAPI() {
        // 在這裡呼叫 API，並處理返回的數據
        viewModel.getAllStops().observe(getViewLifecycleOwner(), new Observer<List<BusStopEntity>>() {
            @Override
            public void onChanged(List<BusStopEntity> busStopEntities) {
                if (!busStopEntities.isEmpty()){
                    for (int i = 0; i < busStopEntities.size(); i++){
                        stopNameList.add(busStopEntities.get(i).getName());
                    }
                    viewModel.getTDXRealTimeStation("Kaohsiung", route).observe(requireActivity(), new Observer<List<RealTimeNearStop>>() {
                        @Override
                        public void onChanged(List<RealTimeNearStop> realTimeNearStopList) {
                            nowStopName = realTimeNearStopList.get(0).getRealTimeStopName().getZh_tw();
                            tvCurrentPosition.setText("目前位置：" + nowStopName);
//                            for (RealTimeNearStop data : realTimeNearStopList){
//                                nowStopName = data.getRealTimeStopName().getZh_tw();
//                            }
                            int index = stopNameList.indexOf(nowStopName);
                            tvLastStation.setText(stopNameList.get(index));
                            if (realTimeNearStopList.get(0).getDirection() == 0){
                                if (index + 1 >= 0 && index + 1 < stopNameList.size()){
                                    tvStation.setText(stopNameList.get(index + 1));

                                }else {
                                    tvStation.setText("XXX");
                                }
                                if (index + 2 >= 0 && index + 2 < stopNameList.size()){
                                    tvNextStation.setText(stopNameList.get(index + 2));

                                }else{
                                    tvNextStation.setText("XXX");
                                }
                            } else if(realTimeNearStopList.get(0).getDirection() == 1){
                                if (index - 1 >= 0 && index - 1 < stopNameList.size()){
                                    tvStation.setText(stopNameList.get(index + 1));

                                }else {
                                    tvStation.setText("XXX");
                                }
                                if (index - 2 >= 0 && index - 2 < stopNameList.size()){
                                    tvNextStation.setText(stopNameList.get(index + 2));

                                }else{
                                    tvNextStation.setText("XXX");
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("請輸入路線");

        final EditText input = new EditText(requireActivity());
        input.setText(tvRouteName.getText().toString());
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newRoute = input.getText().toString();
                tvRouteName.setText(newRoute);
                viewModel.updateRouteName(username, newRoute);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}