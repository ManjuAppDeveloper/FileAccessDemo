package com.example.fileaccessdemo.arch;

public class Item {
    private String name,place;

    public Item(String name, String place) {
        this.name = name;
        this.place = place;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
