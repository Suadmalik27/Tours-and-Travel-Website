package com.mycompany.tours_and_travel;

public class Package {
    private int id;
    private String destination;
    private String duration;
    private double price;
    private double rating;
    private String description;

    public Package(int id, String destination, String duration, double price, double rating, String description) {
        this.id = id;
        this.destination = destination;
        this.duration = duration;
        this.price = price;
        this.rating = rating;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public String getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }
}
