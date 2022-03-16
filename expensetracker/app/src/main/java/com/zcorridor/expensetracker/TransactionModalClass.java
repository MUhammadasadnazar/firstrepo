package com.zcorridor.expensetracker;

public class TransactionModalClass {
    int transamnt;
    String transtype;
    String goalname;
    String date;

    public TransactionModalClass(int transamnt, String transtype, String goalname, String date) {
        this.transamnt = transamnt;
        this.transtype = transtype;
        this.goalname = goalname;
        this.date = date;
    }

    public int getTransamnt() {
        return transamnt;
    }

    public void setTransamnt(int transamnt) {
        this.transamnt = transamnt;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getGoalname() {
        return goalname;
    }

    public void setGoalname(String goalname) {
        this.goalname = goalname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
