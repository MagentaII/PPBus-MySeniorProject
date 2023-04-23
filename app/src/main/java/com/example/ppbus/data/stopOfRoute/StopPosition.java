package com.example.ppbus.data.stopOfRoute;

import com.google.gson.annotations.SerializedName;

public class StopPosition {
    @SerializedName("PositionLat")
    private double PositionLat;
    @SerializedName("PositionLon")
    private double PositionLon;

    public double getPositionLat() {
        return PositionLat;
    }

    public void setPositionLat(double positionLat) {
        PositionLat = positionLat;
    }

    public double getPositionLon() {
        return PositionLon;
    }

    public void setPositionLon(double positionLon) {
        PositionLon = positionLon;
    }
}
