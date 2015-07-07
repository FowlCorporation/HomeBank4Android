package com.fowlcorp.homebank4android.model;

import java.io.Serializable;

/**
 * @author Ced
 */
public class Template implements Serializable {

    private double amount;
    private Account account;
    private int paymode;
    private int flags;
    private Payee payee;
    private Category category;
    private String wording;
    private int nextDateXml;
    private int every;
    private int unit;
    private int limit;
    private int weekend;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getPaymode() {
        return paymode;
    }

    public void setPaymode(int paymode) {
        this.paymode = paymode;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public int getNextDateXml() {
        return nextDateXml;
    }

    public void setNextDateXml(int nextDateXml) {
        this.nextDateXml = nextDateXml;
    }

    public int getEvery() {
        return every;
    }

    public void setEvery(int every) {
        this.every = every;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getWeekend() {
        return weekend;
    }

    public void setWeekend(int weekend) {
        this.weekend = weekend;
    }
}
