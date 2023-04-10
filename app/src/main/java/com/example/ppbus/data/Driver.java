package com.example.ppbus.data;

public class Driver {
    String plateNumb, routeName;
    int packageNum;
    boolean visibility;

    public Driver(String plateNumb, String routeName, int packageNum) {
        this.plateNumb = plateNumb;
        this.routeName = routeName;
        this.packageNum = packageNum;
        this.visibility = false;
    }

    public String getPlateNumb() {
        return plateNumb;
    }

    public void setPlateNumb(String plateNumb) {
        this.plateNumb = plateNumb;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }
}
