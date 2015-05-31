package com.fowlcorp.homebank4android.model;

import java.io.Serializable;

/**
 * @author Ced
 */
public class Owner implements Serializable {

    private String title;
    private Category carCategory;
    private int autoSmode;
    private int autoWeekday;
    private int autoNbdays;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(Category carCategory) {
        this.carCategory = carCategory;
    }

    public int getAutoSmode() {
        return autoSmode;
    }

    public void setAutoSmode(int autoSmode) {
        this.autoSmode = autoSmode;
    }

    public int getAutoWeekday() {
        return autoWeekday;
    }

    public void setAutoWeekday(int autoWeekday) {
        this.autoWeekday = autoWeekday;
    }

    public int getAutoNbdays() {
        return autoNbdays;
    }

    public void setAutoNbdays(int autoNbdays) {
        this.autoNbdays = autoNbdays;
    }
}
