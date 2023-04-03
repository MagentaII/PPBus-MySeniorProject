package com.example.ppbus.data.stopOfRoute;

import com.example.ppbus.data.stopOfRoute.StopName;
import com.google.gson.annotations.SerializedName;

public class Stops {

    @SerializedName("StopUID")
    private String StopUID;

    @SerializedName("StopName")
    private StopName StopName;

    @SerializedName("StopSequence")
    private int StopSequence;

    public String getStopUID() {
        return StopUID;
    }

    public void setStopUID(String stopUID) {
        StopUID = stopUID;
    }

    public com.example.ppbus.data.stopOfRoute.StopName getStopName() {
        return StopName;
    }

    public void setStopName(com.example.ppbus.data.stopOfRoute.StopName stopName) {
        StopName = stopName;
    }

    public int getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(int stopSequence) {
        StopSequence = stopSequence;
    }
}
