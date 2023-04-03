package com.example.ppbus.data;

public class Packages {
    String id, date, name, pickup, destination, status;
    boolean visibility;

    public Packages(String id, String date, String name, String pickup, String destination, String status) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.pickup = pickup;
        this.destination = destination;
        this.status = status;
        this.visibility = false;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
