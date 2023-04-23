package com.example.ppbus.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stops")
public class BusStopEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Stop_Name")
    private String name;

    public BusStopEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
