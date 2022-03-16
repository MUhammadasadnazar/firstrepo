package com.zcorridor.expensetracker;

public class Incomemodalclass {
    String incomedescription;
    int incomeamount;

    public Incomemodalclass(String incomedescription, int incomeamount) {
        this.incomedescription = incomedescription;
        this.incomeamount = incomeamount;
    }

    public String getIncomedescription() {
        return incomedescription;
    }

    public void setIncomedescription(String incomedescription) {
        this.incomedescription = incomedescription;
    }

    public int getIncomeamount() {
        return incomeamount;
    }

    public void setIncomeamount(int incomeamount) {
        this.incomeamount = incomeamount;
    }
}
