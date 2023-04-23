package com.example.ppbus.model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ppbus.data.Driver;
import com.example.ppbus.data.Packages2;
import com.example.ppbus.data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseRepository {

    private final Context context;
    private DatabaseReference reference;
    private List<Packages2> packagesList = new ArrayList<>();
    private List<Packages2> confirmedPackagesList = new ArrayList<>();
    private List<Packages2> receivedPackagesList = new ArrayList<>();
    private List<Packages2> transitPackagesList = new ArrayList<>();
    private List<Driver> driverList = new ArrayList<>();
    private int currentId = 0;


    public FirebaseRepository(Context context) {
        this.context = context;
    }


    //註冊帳號
    public void doRegister(User user, String username) {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(username).setValue(user);
    }

    public void addDriver(Driver driver, String username) {
        reference = FirebaseDatabase.getInstance().getReference("drivers");
        reference.child(username).setValue(driver);
    }

    public void addPackage(String consignor, String consignee, String address) {
        reference = FirebaseDatabase.getInstance().getReference("packages");
        reference.orderByChild("id").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    currentId = Integer.parseInt(dataSnapshot.child("id").getValue().toString());
                    currentId++;
                    String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                    Packages2 packages2 = new Packages2(currentId, consignor, consignee, address, 0, date, "");
                    reference.child(String.valueOf(currentId)).setValue(packages2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public LiveData<List<Packages2>> getPackage() {
        MutableLiveData<List<Packages2>> packagesLive = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("packages");
        Query packagesQuery = reference.orderByChild("status").equalTo(0);
        packagesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                packagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int id = dataSnapshot.child("id").getValue(Integer.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String sender = dataSnapshot.child("sender").getValue(String.class);
                    String recipient = dataSnapshot.child("recipient").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    int status = dataSnapshot.child("status").getValue(Integer.class);
                    Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date, "");
                    packagesList.add(packages2);
                }
                packagesLive.postValue(packagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return packagesLive;
    }

    public void updateStatus(int id, int query){
        reference = FirebaseDatabase.getInstance().getReference("packages");
        reference.child(String.valueOf(id)).child("status").setValue(query);
    }

    public void addPlateNumb(int id, String username){
        reference = FirebaseDatabase.getInstance().getReference("packages");
        reference.child(String.valueOf(id)).child("plateNumb").setValue(username);
    }

    public void updateDriverPackageNum(String username, int packageNum){
        reference = FirebaseDatabase.getInstance().getReference("drivers");
        reference.child(username).child("packageNum").setValue(packageNum);
    }

    public void updateRouteName(String username, String routeName){
        reference = FirebaseDatabase.getInstance().getReference("drivers");
        reference.child(username).child("routeName").setValue(routeName);
    }

    public LiveData<List<Packages2>> getConfirmedPackages() {
        MutableLiveData<List<Packages2>> confirmedPackagesLive = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("packages");
        Query confirmedPackagesQuery = reference.orderByChild("status").startAt(1);
        confirmedPackagesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                confirmedPackagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int id = dataSnapshot.child("id").getValue(Integer.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String sender = dataSnapshot.child("sender").getValue(String.class);
                    String recipient = dataSnapshot.child("recipient").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    int status = dataSnapshot.child("status").getValue(Integer.class);
                    Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date, "");
                    confirmedPackagesList.add(packages2);
                }
                confirmedPackagesLive.postValue(confirmedPackagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return confirmedPackagesLive;
    }

    public LiveData<List<Packages2>> getReceivedPackages() {
        MutableLiveData<List<Packages2>> receivedPackagesLive = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("packages");
        Query receivedPackagesQuery = reference.orderByChild("status").equalTo(1);
        receivedPackagesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receivedPackagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int id = dataSnapshot.child("id").getValue(Integer.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String sender = dataSnapshot.child("sender").getValue(String.class);
                    String recipient = dataSnapshot.child("recipient").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    int status = dataSnapshot.child("status").getValue(Integer.class);
                    Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date, "");
                    receivedPackagesList.add(packages2);
                }
                receivedPackagesLive.postValue(receivedPackagesList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return receivedPackagesLive;
    }

    public LiveData<List<Packages2>> getTransitPackages(String username) {
        MutableLiveData<List<Packages2>> transitPackagesLive = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("packages");
        Query transitPackagesQuery = reference.orderByChild("status").equalTo(2);
        transitPackagesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transitPackagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String dataPlateNumb = dataSnapshot.child("plateNumb").getValue(String.class);
                    if (dataPlateNumb != null && !dataPlateNumb.isEmpty() && dataPlateNumb.equals(username)){
                        int id = dataSnapshot.child("id").getValue(Integer.class);
                        String date = dataSnapshot.child("date").getValue(String.class);
                        String sender = dataSnapshot.child("sender").getValue(String.class);
                        String recipient = dataSnapshot.child("recipient").getValue(String.class);
                        String address = dataSnapshot.child("address").getValue(String.class);
                        String plateNumb = dataSnapshot.child("plateNumb").getValue(String.class);
                        int status = dataSnapshot.child("status").getValue(Integer.class);

                        Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date, plateNumb);
                        transitPackagesList.add(packages2);
                    }

                }
                transitPackagesLive.postValue(transitPackagesList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "something error", Toast.LENGTH_SHORT).show();
            }
        });
        return transitPackagesLive;
    }


    public Query getTransitPackagesNum(){
        reference = FirebaseDatabase.getInstance().getReference("packages");
        Query transitPackagesNumQuery = reference.orderByChild("status").equalTo(2);
        return transitPackagesNumQuery;
    }

    public Query getDriverRouteName(String username){
        reference = FirebaseDatabase.getInstance().getReference("drivers");
        Query driverRouteNameQuery = reference.child(username);
        return driverRouteNameQuery;
    }

    public LiveData<List<Driver>> getDrivers() {
        MutableLiveData<List<Driver>> driverLive = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("drivers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                driverList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String plateNumb = dataSnapshot.child("plateNumb").getValue(String.class);
                    String routeName = dataSnapshot.child("routeName").getValue(String.class);
                    int packageNum = dataSnapshot.child("packageNum").getValue(Integer.class);
                    Driver driver = new Driver(plateNumb, routeName, packageNum);
                    driverList.add(driver);
                }
                driverLive.postValue(driverList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return driverLive;
    }
}
