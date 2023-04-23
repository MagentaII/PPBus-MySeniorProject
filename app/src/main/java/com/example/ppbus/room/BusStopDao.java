package com.example.ppbus.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ppbus.data.BusStopEntity;
import com.example.ppbus.data.stopOfRoute.Stops;

import java.util.List;

@Dao
public interface BusStopDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStop(BusStopEntity... busStopEntity);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(List<Stops> stops);

    @Query("SELECT * FROM stops ORDER BY ID ASC")
    LiveData<List<BusStopEntity>> getAllStops();

    @Query("DELETE FROM stops")
    void deleteAllStops();

}
