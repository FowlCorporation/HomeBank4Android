package com.fowlcorp.homebank4android.model;

/**
 * Created by Ced on 01/01/2015.
 */
public class Couple {
    private Category category;
    private double amount;

    public Couple(double amount, Category category) {
        this.amount = amount;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
