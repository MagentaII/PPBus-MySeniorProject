package com.example.ppbus.data;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("access_token")
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}
