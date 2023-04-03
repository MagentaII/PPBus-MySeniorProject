package com.example.ppbus.model;

import android.content.Context;
import android.media.MediaCodec;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ppbus.data.Packages2;
import com.example.ppbus.data.User;
import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.data.stopOfRoute.StopOfRoute;
import com.example.ppbus.data.stopOfRoute.Stops;
import com.example.ppbus.retrofit.RetrofitManager;
import com.example.ppbus.retrofit.TDXAPIService;
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
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private final Context context;
    private DatabaseReference reference;
    private List<Packages2> packagesList = new ArrayList<>();
    private List<Packages2> confirmedPackagesList = new ArrayList<>();
    private List<Packages2> receivedPackagesList = new ArrayList<>();
    private List<Packages2> transitPackagesList = new ArrayList<>();
    private int currentId = 0;


    public Repository(Context context) {
        this.context = context;
    }


    //註冊帳號
    public void doRegister(User user, String username) {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(username).setValue(user);
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
                    Packages2 packages2 = new Packages2(currentId, consignor, consignee, address, 0, date);
                    reference.child(String.valueOf(currentId)).setValue(packages2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getPackage(final onGetPackageCallback callback) {
        reference = FirebaseDatabase.getInstance().getReference("packages");
        reference.addValueEventListener(new ValueEventListener() {
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
                    Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date);
                    packagesList.add(packages2);
                }
                callback.onGetPackage(packagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public interface onGetPackageCallback {
        void onGetPackage(List<Packages2> packages2List);
    }

    public void updateStatus(int id, int query){
        reference = FirebaseDatabase.getInstance().getReference("packages");
        reference.child(String.valueOf(id)).child("status").setValue(query);
    }

    public void getConfirmedPackages(final onGetConfirmedPackageCallback callback) {
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
                    Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date);
                    confirmedPackagesList.add(packages2);
                }
                callback.onGetConfirmedPackage(confirmedPackagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public interface onGetConfirmedPackageCallback {
        void onGetConfirmedPackage(List<Packages2> receivedPackagesList);
    }

    public void getReceivedPackages(final onGetReceivedPackageCallback callback) {
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
                    Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date);
                    receivedPackagesList.add(packages2);
                }
                callback.onGetReceivedPackage(receivedPackagesList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public interface onGetReceivedPackageCallback {
        void onGetReceivedPackage(List<Packages2> receivedPackagesList);
    }

    public void getTransitPackages(final onGetTransitPackageCallback callback) {
        reference = FirebaseDatabase.getInstance().getReference("packages");
        Query transitPackagesQuery = reference.orderByChild("status").equalTo(2);
        transitPackagesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transitPackagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int id = dataSnapshot.child("id").getValue(Integer.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String sender = dataSnapshot.child("sender").getValue(String.class);
                    String recipient = dataSnapshot.child("recipient").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    int status = dataSnapshot.child("status").getValue(Integer.class);
                    Packages2 packages2 = new Packages2(id, sender, recipient, address, status, date);
                    transitPackagesList.add(packages2);
                }
                callback.onGetTransitPackage(transitPackagesList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public interface onGetTransitPackageCallback {
        void onGetTransitPackage(List<Packages2> transitPackagesList);
    }

    public Query getTransitPackagesNum(){
        reference = FirebaseDatabase.getInstance().getReference("packages");
        Query transitPackagesNumQuery = reference.orderByChild("status").equalTo(2);
        return transitPackagesNumQuery;
    }








    private final TDXAPIService tdxapiService = RetrofitManager.getInstance().getAPI();
    public void getTDXStation(final onDataReadyCallback callback) {
        Call<List<StopOfRoute>> call = tdxapiService.getStopOfRoute();
        call.enqueue(new Callback<List<StopOfRoute>>() {
            @Override
            public void onResponse(Call<List<StopOfRoute>> call, Response<List<StopOfRoute>> response) {
                List<StopOfRoute> stopOfRouteList = response.body();
                for (StopOfRoute stopOfRoute : stopOfRouteList) {
                    if (stopOfRoute.getDirection() == 0) {
                        callback.onDataReady(stopOfRoute.getStops());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StopOfRoute>> call, Throwable t) {

            }
        });
    }
    public interface onDataReadyCallback {
        void onDataReady(List<Stops> stopsList);
    }


    public void getTDXRealTimeStation(final onRealTimeDataReadyCallback callback) {
        Call<List<RealTimeNearStop>> call = tdxapiService.getRealTimeNearStop();
        call.enqueue(new Callback<List<RealTimeNearStop>>() {
            @Override
            public void onResponse(Call<List<RealTimeNearStop>> call, Response<List<RealTimeNearStop>> response) {
                callback.onRealTimeDataReady(response.body());
            }

            @Override
            public void onFailure(Call<List<RealTimeNearStop>> call, Throwable t) {

            }
        });
    }
    public interface onRealTimeDataReadyCallback {
        void onRealTimeDataReady(List<RealTimeNearStop> realTimeNearStopList);
    }

    public void putStopsToFirebase(String routeName, int direction, Map<Integer, String> stopsMap) {
        reference = FirebaseDatabase.getInstance().getReference("stops");
        reference.child(routeName).child(String.valueOf(direction)).setValue(stopsMap);
    }

}
