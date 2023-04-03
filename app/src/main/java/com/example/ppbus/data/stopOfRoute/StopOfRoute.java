package com.example.ppbus.data.stopOfRoute;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StopOfRoute {

    @SerializedName("RouteUID")
    private String RouteUID;
    @SerializedName("RouteID")
    private String RouteID;
    @SerializedName("Direction")
    private int Direction;
    @SerializedName("Stops")
    private List<Stops> Stops;

    public String getRouteUID() {
        return RouteUID;
    }

    public void setRouteUID(String routeUID) {
        RouteUID = routeUID;
    }

    public String getRouteID() {
        return RouteID;
    }

    public void setRouteID(String routeID) {
        RouteID = routeID;
    }

    public List<com.example.ppbus.data.stopOfRoute.Stops> getStops() {
        return Stops;
    }

    public void setStops(List<com.example.ppbus.data.stopOfRoute.Stops> stops) {
        Stops = stops;
    }

    public int getDirection() {
        return Direction;
    }

    public void setDirection(int direction) {
        Direction = direction;
    }
}
