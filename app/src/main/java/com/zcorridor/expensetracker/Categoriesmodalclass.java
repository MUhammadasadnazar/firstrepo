package com.zcorridor.expensetracker;

public class Categoriesmodalclass {
    String category;
    int budgetspent;
    int budgetplanned;

    public Categoriesmodalclass(String category, int budgetspent, int budgetplanned) {
        this.category = category;
        this.budgetspent = budgetspent;
        this.budgetplanned = budgetplanned;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBudgetspent() {
        return budgetspent;
    }
    public void setBudgetspent(int budgetspent) {
        this.budgetspent = budgetspent;
    }

    public int getBudgetplanned() {
        return budgetplanned;
    }

    public void setBudgetplanned(int budgetplanned) {
        this.budgetplanned = budgetplanned;
    }
}

