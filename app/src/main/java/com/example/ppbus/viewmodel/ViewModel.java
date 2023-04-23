package com.example.ppbus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ppbus.data.BusStopEntity;
import com.example.ppbus.data.Driver;
import com.example.ppbus.data.Packages2;
import com.example.ppbus.data.User;
import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.model.ApiRepository;
import com.example.ppbus.model.FirebaseRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private final FirebaseRepository firebaseRepository;
    private final ApiRepository apiRepository;

    public ViewModel(@NonNull Application application) {
        super(application);
        firebaseRepository = new FirebaseRepository(application);
        apiRepository = new ApiRepository(application);
    }

    public void doRegister(User user, String username){
        firebaseRepository.doRegister(user, username);
    }

    public void addDriver(Driver driver, String username){
        firebaseRepository.addDriver(driver, username);
    }

    public void addPackage(String consignor, String consignee, String address){
        firebaseRepository.addPackage(consignor, consignee, address);
    }

    public void updateStatus(int id, int query){
        firebaseRepository.updateStatus(id, query);
    }

    public void addPlateNumb(int id, String username){
        firebaseRepository.addPlateNumb(id, username);
    }

    public void updateDriverPackageNum(String username, int packageNum){
        firebaseRepository.updateDriverPackageNum(username, packageNum);
    }

    public void updateRouteName(String username, String routeName){
        firebaseRepository.updateRouteName(username, routeName);
    }

    public LiveData<List<Packages2>> getPackage(){
        return firebaseRepository.getPackage();
    }

    public LiveData<List<Packages2>> getConfirmedPackages(){
        return firebaseRepository.getConfirmedPackages();
    }

    public LiveData<List<Packages2>> getReceivedPackages(){
        return firebaseRepository.getReceivedPackages();
    }

    public LiveData<List<Packages2>> getTransitPackages(String username){
        return firebaseRepository.getTransitPackages(username);
    }


    public LiveData<Integer> getTransitPackagesNum(String username){
        final MutableLiveData<Integer> transitNum = new MutableLiveData<>();
        firebaseRepository.getTransitPackagesNum().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int PackagesNum = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String dataPlateNumb = dataSnapshot.child("plateNumb").getValue(String.class);
                    if (dataPlateNumb != null && !dataPlateNumb.isEmpty() && dataPlateNumb.equals(username)){
                        PackagesNum++;
                    }
                }
                transitNum.setValue(PackagesNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return transitNum;
    }

    public LiveData<String> getDriverRouteName(String username){
        final MutableLiveData<String> driverRouteName = new MutableLiveData<>();
        firebaseRepository.getDriverRouteName(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String routeName = snapshot.child("routeName").getValue(String.class);
                driverRouteName.setValue(routeName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return driverRouteName;
    }

    public LiveData<List<Driver>> getDrivers(){
        return firebaseRepository.getDrivers();
    }




    public LiveData<Boolean> getStops(String city, String routeId){
        return apiRepository.getStops(city, routeId);
    }

    public LiveData<List<BusStopEntity>> getAllStops(){
        return apiRepository.getAllStops();
    }

    public LiveData<List<RealTimeNearStop>> getTDXRealTimeStation(String city, String routeId){
        return apiRepository.getTDXRealTimeStation(city, routeId);
    }


    public interface OnDataSavedListener {
        void onDataSaved();
    }
}
