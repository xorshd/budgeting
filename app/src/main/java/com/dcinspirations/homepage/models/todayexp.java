package com.dcinspirations.homepage.models;

public class todayexp {
    public String name, category, amount, time, date;
    public int id;

    public todayexp(int id, String name, String category, String amount, String time, String date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.time = time;
        this.date = date;
    }

}
