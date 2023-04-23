package com.example.ppbus.data.realTimeNearStop;

import com.google.gson.annotations.SerializedName;

public class RealTimeNearStop {
    @SerializedName("StopSequence")
    private int StopSequence;

    @SerializedName("StopName")
    private RealTimeStopName realTimeStopName;

    @SerializedName("Direction")
    private int Direction;

    @SerializedName("PlateNumb")
    private String PlateNumb;

    public int getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(int stopSequence) {
        StopSequence = stopSequence;
    }

    public RealTimeStopName getRealTimeStopName() {
        return realTimeStopName;
    }

    public void setRealTimeStopName(RealTimeStopName realTimeStopName) {
        this.realTimeStopName = realTimeStopName;
    }

    public int getDirection() {
        return Direction;
    }

    public void setDirection(int direction) {
        Direction = direction;
    }

    public String getPlateNumb() {
        return PlateNumb;
    }

    public void setPlateNumb(String plateNumb) {
        PlateNumb = plateNumb;
    }
}
