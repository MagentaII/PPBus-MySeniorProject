package com.example.ppbus.data.stopOfRoute;

import com.google.gson.annotations.SerializedName;

public class StopName {

    @SerializedName("Zh_tw")
    private String Zh_tw;

    public String getZh_tw() {
        return Zh_tw;
    }

    public void setZh_tw(String zh_tw) {
        Zh_tw = zh_tw;
    }
}
