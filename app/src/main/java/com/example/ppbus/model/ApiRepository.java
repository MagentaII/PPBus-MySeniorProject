package com.example.ppbus.model;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ppbus.data.BusStopEntity;
import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.data.stopOfRoute.StopName;
import com.example.ppbus.data.stopOfRoute.StopOfRoute;
import com.example.ppbus.data.stopOfRoute.Stops;
import com.example.ppbus.retrofit.RetrofitManager;
import com.example.ppbus.retrofit.TDXAPIService;
import com.example.ppbus.room.BusStopDao;
import com.example.ppbus.room.BusStopDatabase;
import com.example.ppbus.viewmodel.ViewModel;

import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private final Context context;
    private final TDXAPIService tdxapiService;
    private BusStopDao busStopDao;


    public ApiRepository(Context context) {
        this.context = context;
        tdxapiService = RetrofitManager.getInstance().getAPI();
        busStopDao = BusStopDatabase.getDatabase(context.getApplicationContext()).getBusStopDao();
    }

    public LiveData<Boolean> getStops(String city, String routeId){
        MutableLiveData<Boolean> nextFragmentLive = new MutableLiveData<>();
        nextFragmentLive.setValue(false);
        Call<List<StopOfRoute>> call = tdxapiService.getStopOfRoute(city, routeId);
        call.enqueue(new Callback<List<StopOfRoute>>() {
            @Override
            public void onResponse(Call<List<StopOfRoute>> call, Response<List<StopOfRoute>> response) {
                if (response.isSuccessful()){
                    for (StopOfRoute data : response.body()){
                        if (data.getDirection() == 0){
                            insertStop(data.getStops(), new ViewModel.OnDataSavedListener() {
                                @Override
                                public void onDataSaved() {
                                    nextFragmentLive.setValue(true);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StopOfRoute>> call, Throwable t) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
        return nextFragmentLive;
    }

    public LiveData<List<RealTimeNearStop>> getTDXRealTimeStation(String city, String routeId) {
        MutableLiveData<List<RealTimeNearStop>> realTimeNearStopLive = new MutableLiveData<>();
        Call<List<RealTimeNearStop>> call = tdxapiService.getRealTimeNearStop(city, routeId);
        call.enqueue(new Callback<List<RealTimeNearStop>>() {
            @Override
            public void onResponse(Call<List<RealTimeNearStop>> call, Response<List<RealTimeNearStop>> response) {
                realTimeNearStopLive.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<RealTimeNearStop>> call, Throwable t) {

            }
        });
        return realTimeNearStopLive;
    }




    public void insertStop(List<Stops> stops, ViewModel.OnDataSavedListener listener){
        new DeleteAsyncTask(busStopDao).execute();
        for (Stops data : stops){
            StopName stopName = data.getStopName();
            String zhTw = stopName.getZhTw();
            BusStopEntity stopEntity = new BusStopEntity(zhTw);
            new InsertAsyncTask(busStopDao).execute(stopEntity);
        }
        listener.onDataSaved();
    }

    public LiveData<List<BusStopEntity>> getAllStops() {
        return busStopDao.getAllStops();
    }

    static class InsertAsyncTask extends AsyncTask<BusStopEntity, Void, Void>{
        private BusStopDao busStopDao;

        public InsertAsyncTask(BusStopDao busStopDao) {
            this.busStopDao = busStopDao;
        }

        @Override
        protected Void doInBackground(BusStopEntity... busStopEntities) {
            busStopDao.insertStop(busStopEntities);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void>{
        private BusStopDao busStopDao;

        public DeleteAsyncTask(BusStopDao busStopDao) {
            this.busStopDao = busStopDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            busStopDao.deleteAllStops();
            return null;
        }
    }
}
