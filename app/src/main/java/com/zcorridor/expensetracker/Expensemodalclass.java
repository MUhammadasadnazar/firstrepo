package com.zcorridor.expensetracker;

public class Expensemodalclass {
    String catname;
    String Description;
    int Amount;
    String date;

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public Expensemodalclass(String catname, String description, int amount, String date) {
        this.catname = catname;
        Description = description;
        Amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
