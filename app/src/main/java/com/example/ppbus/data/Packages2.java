package com.example.ppbus.data;

public class Packages2 {
    int id, status;
    String sender, recipient, address, date;
    boolean visibility;

    public Packages2(int id, String sender, String recipient, String address, int status, String date) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.address = address;
        this.status = status;
        this.date = date;
        this.visibility = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
