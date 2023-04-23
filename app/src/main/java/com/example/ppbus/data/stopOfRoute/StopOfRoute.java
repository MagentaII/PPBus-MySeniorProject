package com.example.ppbus.data.stopOfRoute;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StopOfRoute {

    @SerializedName("Stops")
    private List<Stops> stops;
    @SerializedName("Direction")
    private int direction;

    public List<Stops> getStops() {
        return stops;
    }

    public void setStops(List<Stops> stops) {
        this.stops = stops;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
