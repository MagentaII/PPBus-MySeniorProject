package com.example.ppbus.data.stopOfRoute;

import com.google.gson.annotations.SerializedName;

public class Stops {
    @SerializedName("StopUID")
    private String stopUID;
    @SerializedName("StopID")
    private String stopID;
    @SerializedName("StopName")
    private StopName stopName;
    @SerializedName("StopSequence")
    private int stopSequence;
    @SerializedName("StopPosition")
    private StopPosition stopPosition;

    public String getStopUID() {
        return stopUID;
    }

    public void setStopUID(String stopUID) {
        this.stopUID = stopUID;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public StopName getStopName() {
        return stopName;
    }

    public void setStopName(StopName stopName) {
        this.stopName = stopName;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public StopPosition getStopPosition() {
        return stopPosition;
    }

    public void setStopPosition(StopPosition stopPosition) {
        this.stopPosition = stopPosition;
    }
}

