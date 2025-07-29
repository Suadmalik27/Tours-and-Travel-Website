package com.mycompany.tours_and_travel;

public class Hotel {
    private int id;
    private String name;
    private String location;
    private String description;
    private double price;

    public Hotel(int id, String name, String location, String description, double price) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
