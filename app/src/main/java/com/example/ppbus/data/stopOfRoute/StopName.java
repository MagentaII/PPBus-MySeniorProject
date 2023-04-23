package com.example.ppbus.data.stopOfRoute;

import com.google.gson.annotations.SerializedName;

public class StopName {
    @SerializedName("Zh_tw")
    private String zhTw;
    @SerializedName("En")
    private String en;

    public String getZhTw() {
        return zhTw;
    }

    public void setZhTw(String zhTw) {
        this.zhTw = zhTw;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
