package com.mycompany.tours_and_travel;

import java.util.ArrayList;
import java.util.List;

public class TourDAO {
    private List<Tour> tours = new ArrayList<>();
    private int nextId = 1;

    public TourDAO() {
        // Add some sample tours
        addTour(new Tour(0, "Beach Paradise", "Enjoy the sunny beaches", 299.99));
        addTour(new Tour(0, "Mountain Adventure", "Explore the mountains", 399.99));
    }

    public void addTour(Tour tour) {
        tour.setId(nextId++);
        tours.add(tour);
    }

    public List<Tour> getAllTours() {
        return new ArrayList<>(tours);
    }

    public Tour getTourById(int id) {
        for (Tour tour : tours) {
            if (tour.getId() == id) {
                return tour;
            }
        }
        return null;
    }

    public void updateTour(Tour tour) {
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).getId() == tour.getId()) {
                tours.set(i, tour);
                return;
            }
        }
    }

    public void deleteTour(int id) {
        tours.removeIf(tour -> tour.getId() == id);
    }
}
