package com.zcorridor.expensetracker;

public class Targetmodalclass {
    String imagepath;
    String targetdes;
    int targetamount;
    int amountspent;
    long days;

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public Targetmodalclass(String imagepath, String targetdes, int targetamount, int amountspent, long days) {
        this.imagepath = imagepath;
        this.targetdes = targetdes;
        this.targetamount = targetamount;
        this.amountspent = amountspent;
        this.days = days;
    }
//    public Targetmodalclass(String imagepath, String targetdes, int targetamount, int amountspent) {
//        this.imagepath = imagepath;
//        this.targetdes = targetdes;
//        this.targetamount = targetamount;
//        this.amountspent = amountspent;
//    }

    public int getAmountspent() {
        return amountspent;
    }

    public void setAmountspent(int amountspent) {
        this.amountspent = amountspent;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getTargetdes() {
        return targetdes;
    }

    public void setTargetdes(String targetdes) {
        this.targetdes = targetdes;
    }

    public int getTargetamount() {
        return targetamount;
    }

    public void setTargetamount(int targetamount) {
        this.targetamount = targetamount;
    }

}
