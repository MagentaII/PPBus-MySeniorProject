package com.example.ppbus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ppbus.data.Driver;
import com.example.ppbus.data.Packages2;
import com.example.ppbus.data.User;
import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.data.stopOfRoute.Stops;
import com.example.ppbus.model.Repository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<Packages2>> packages2Live;
    private MutableLiveData<List<Packages2>> confirmedPackages2Live;
    private MutableLiveData<List<Packages2>> receivedPackages2Live;
    private MutableLiveData<List<Packages2>> transitPackages2Live;
    private MutableLiveData<List<Driver>> driverLive;
    private final MutableLiveData<List<Stops>> stopsLive;
    private final MutableLiveData<List<RealTimeNearStop>> realTimeNearStopLive;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        packages2Live = new MutableLiveData<>();
        confirmedPackages2Live = new MutableLiveData<>();
        receivedPackages2Live = new MutableLiveData<>();
        transitPackages2Live = new MutableLiveData<>();
        driverLive = new MutableLiveData<>();
        stopsLive = new MutableLiveData<>();
        realTimeNearStopLive = new MutableLiveData<>();
    }

    public MutableLiveData<List<Packages2>> getPackages2Live() {
        return packages2Live;
    }

    public MutableLiveData<List<Packages2>> getConfirmedPackages2Live() {
        return confirmedPackages2Live;
    }

    public MutableLiveData<List<Packages2>> getReceivedPackages2Live() {
        return receivedPackages2Live;
    }

    public MutableLiveData<List<Packages2>> getTransitPackages2Live() {
        return transitPackages2Live;
    }

    public MutableLiveData<List<Driver>> getDriverLive() {
        return driverLive;
    }

    public void doRegister(User user, String username){
        repository.doRegister(user, username);
    }

    public void addDriver(Driver driver, String username){
        repository.addDriver(driver, username);
    }

    public void addPackage(String consignor, String consignee, String address){
        repository.addPackage(consignor, consignee, address);
    }

    public void updateStatus(int id, int query){
        repository.updateStatus(id, query);
    }

    public void addPlateNumb(int id, String username){
        repository.addPlateNumb(id, username);
    }

    public void updateDriverPackageNum(String plateNumb, int packageNum){
        repository.updateDriverPackageNum(plateNumb, packageNum);
    }

    public void getPackage(){
        repository.getPackage(new Repository.onGetPackageCallback() {
            @Override
            public void onGetPackage(List<Packages2> packages2List) {
                packages2Live.setValue(packages2List);
            }
        });
    }

    public void getConfirmedPackages(){
        repository.getConfirmedPackages(new Repository.onGetConfirmedPackageCallback() {
            @Override
            public void onGetConfirmedPackage(List<Packages2> confirmedPackagesList) {
                confirmedPackages2Live.setValue(confirmedPackagesList);
            }
        });
    }

    public void getReceivedPackages(){
        repository.getReceivedPackages(new Repository.onGetReceivedPackageCallback() {
            @Override
            public void onGetReceivedPackage(List<Packages2> receivedPackagesList) {
                receivedPackages2Live.setValue(receivedPackagesList);
            }
        });
    }

    public void getTransitPackages(){
        repository.getTransitPackages(new Repository.onGetTransitPackageCallback() {
            @Override
            public void onGetTransitPackage(List<Packages2> transitPackagesList) {
                transitPackages2Live.setValue(transitPackagesList);
            }
        });
    }

    public LiveData<Integer> getTransitPackagesNum(){
        final MutableLiveData<Integer> transitNum = new MutableLiveData<>();
        repository.getTransitPackagesNum().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transitNum.setValue((int)snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return transitNum;
    }

    public void getdrivers(){
        repository.getdrivers(new Repository.onGetDriversCallback() {
            @Override
            public void onGetDriversPackage(List<Driver> driverList) {
                driverLive.setValue(driverList);
            }
        });
    }





    public MutableLiveData<List<Stops>> getStopsLive() {
        return stopsLive;
    }

    public MutableLiveData<List<RealTimeNearStop>> getRealTimeNearStopLive() {
        return realTimeNearStopLive;
    }

    public void getTDXStation(){
        repository.getTDXStation(new Repository.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Stops> stopsList) {
                stopsLive.setValue(stopsList);
            }
        });
    }

    public void getTDXRealTimeStation(){
        repository.getTDXRealTimeStation(new Repository.onRealTimeDataReadyCallback() {
            @Override
            public void onRealTimeDataReady(List<RealTimeNearStop> realTimeNearStopList) {
                realTimeNearStopLive.setValue(realTimeNearStopList);
            }
        });
    }

    public void putStops(String routeName, int direction, Map<Integer, String> stopsMap){
        repository.putStopsToFirebase(routeName, direction, stopsMap);
    }
}
