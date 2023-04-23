package com.example.ppbus.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ppbus.data.BusStopEntity;

@Database(entities = {BusStopEntity.class}, version = 1, exportSchema = false)
public abstract class BusStopDatabase  extends RoomDatabase {
    private static BusStopDatabase INSTANCE;
    public static synchronized BusStopDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BusStopDatabase.class, "stations_database")
                    .build();
        }
        return INSTANCE;
    }


    public abstract BusStopDao getBusStopDao();
}
