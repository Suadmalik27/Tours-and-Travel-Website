

package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class TripPlannerPanel extends JPanel {
    private int loggedInUserId = -1; // Add field to store logged-in user ID

    private JTextField startingLocationField;
    private JTextField destinationField;
    private JSpinner tripDurationSpinner;
    private JComboBox<String> travelModeComboBox;
    private JComboBox<String> tripTypeComboBox;
    private JComboBox<String> sortOptionsComboBox;
    private JComboBox<String> regionFilterComboBox;
    private JComboBox<String> activityFilterComboBox;
    private JButton generateItineraryButton;

    private JPanel itineraryPanel;
    private JPanel hotelRecommendationPanel;
    private JTextArea localTipsTextArea;
    private JLabel costEstimatorLabel;
    private JPanel destinationPreviewPanel;
    private JLabel destinationImageLabel;
    private JTextArea destinationDescriptionArea;
    private JPanel packagePreviewPanel;
    private JLabel packageDetailsLabel;
    private JButton packageBookButton;
    private JSlider budgetSlider;
    private JLabel budgetValueLabel;

    public TripPlannerPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        // Plan Your Trip Panel
        JPanel planPanel = new JPanel(new GridBagLayout());
        planPanel.setBorder(BorderFactory.createTitledBorder("Plan Your Trip"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        planPanel.add(new JLabel("Starting Location:"), gbc);
        gbc.gridx = 1;
        startingLocationField = new JTextField(15);
        startingLocationField.setToolTipText("Enter your trip starting location");
        planPanel.add(startingLocationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        planPanel.add(new JLabel("Destination:"), gbc);
        gbc.gridx = 1;
        destinationField = new JTextField(15);
        destinationField.setToolTipText("Enter your trip destination");
        planPanel.add(destinationField, gbc);

        // Destination Preview Panel
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        destinationPreviewPanel = new JPanel(new BorderLayout());
        destinationPreviewPanel.setBorder(BorderFactory.createTitledBorder("Destination Preview"));
        destinationImageLabel = new JLabel();
        destinationImageLabel.setPreferredSize(new Dimension(150, 100));
        destinationPreviewPanel.add(destinationImageLabel, BorderLayout.NORTH);
        destinationDescriptionArea = new JTextArea();
        destinationDescriptionArea.setEditable(false);
        destinationDescriptionArea.setLineWrap(true);
        destinationDescriptionArea.setWrapStyleWord(true);
        destinationPreviewPanel.add(new JScrollPane(destinationDescriptionArea), BorderLayout.CENTER);
        planPanel.add(destinationPreviewPanel, gbc);
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        planPanel.add(new JLabel("Trip Duration (days):"), gbc);
        gbc.gridx = 1;
        tripDurationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        tripDurationSpinner.setToolTipText("Select the duration of your trip in days");
        planPanel.add(tripDurationSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        planPanel.add(new JLabel("Travel Mode:"), gbc);
        gbc.gridx = 1;
        travelModeComboBox = new JComboBox<>(new String[]{"Flight", "Train", "Bus"});
        travelModeComboBox.setToolTipText("Select your mode of travel");
        planPanel.add(travelModeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        planPanel.add(new JLabel("Trip Type:"), gbc);
        gbc.gridx = 1;
        tripTypeComboBox = new JComboBox<>(new String[]{"Adventure", "Family", "Budget"});
        tripTypeComboBox.setToolTipText("Select the type of your trip");
        planPanel.add(tripTypeComboBox, gbc);

        // Sort Options ComboBox
        gbc.gridx = 0;
        gbc.gridy = 5;
        planPanel.add(new JLabel("Sort Destinations By:"), gbc);
        gbc.gridx = 1;
        sortOptionsComboBox = new JComboBox<>(new String[]{"Popularity", "Price", "Duration"});
        sortOptionsComboBox.setToolTipText("Sort destinations by selected criteria");
        planPanel.add(sortOptionsComboBox, gbc);

        // Filters for Region and Activity Type
        gbc.gridx = 0;
        gbc.gridy = 6;
        planPanel.add(new JLabel("Filter by Region:"), gbc);
        gbc.gridx = 1;
        regionFilterComboBox = new JComboBox<>(new String[]{"All", "Europe", "Asia", "America", "Africa"});
        regionFilterComboBox.setToolTipText("Filter destinations by region");
        planPanel.add(regionFilterComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        planPanel.add(new JLabel("Filter by Activity:"), gbc);
        gbc.gridx = 1;
        activityFilterComboBox = new JComboBox<>(new String[]{"All", "Adventure", "Relaxation", "Cultural"});
        activityFilterComboBox.setToolTipText("Filter destinations by activity type");
        planPanel.add(activityFilterComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        generateItineraryButton = new JButton("Generate Itinerary");
        generateItineraryButton.setToolTipText("Click to generate your trip itinerary");
        planPanel.add(generateItineraryButton, gbc);

        add(planPanel, BorderLayout.NORTH);

        // Itinerary Panel
        itineraryPanel = new JPanel();
        itineraryPanel.setLayout(new BoxLayout(itineraryPanel, BoxLayout.Y_AXIS));
        itineraryPanel.setBorder(BorderFactory.createTitledBorder("Your Itinerary"));
        JScrollPane itineraryScrollPane = new JScrollPane(itineraryPanel);
        itineraryScrollPane.setPreferredSize(new Dimension(400, 150));
        add(itineraryScrollPane, BorderLayout.CENTER);

        // Package Preview Panel
        packagePreviewPanel = new JPanel(new BorderLayout());
        packagePreviewPanel.setBorder(BorderFactory.createTitledBorder("Package Preview"));
        packageDetailsLabel = new JLabel("Select a destination to see package details");
        packagePreviewPanel.add(packageDetailsLabel, BorderLayout.CENTER);
        packageBookButton = new JButton("Book Package");
        packageBookButton.setEnabled(false);
        packagePreviewPanel.add(packageBookButton, BorderLayout.SOUTH);
        add(packagePreviewPanel, BorderLayout.EAST);

        // Bottom Panel for Hotel Recommendations, Local Tips, Cost Estimator, and Budget Slider
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints bottomGbc = new GridBagConstraints();
        bottomGbc.insets = new Insets(5, 5, 5, 5);
        bottomGbc.fill = GridBagConstraints.BOTH;

        // Hotel Recommendation Section
        hotelRecommendationPanel = new JPanel();
        hotelRecommendationPanel.setLayout(new BoxLayout(hotelRecommendationPanel, BoxLayout.Y_AXIS));
        hotelRecommendationPanel.setBorder(BorderFactory.createTitledBorder("Recommended Hotels"));
        hotelRecommendationPanel.setPreferredSize(new Dimension(200, 150));
        bottomGbc.gridx = 0;
        bottomGbc.gridy = 0;
        bottomGbc.weightx = 0.5;
        bottomGbc.weighty = 1.0;
        bottomPanel.add(hotelRecommendationPanel, bottomGbc);

        // Local Tips Panel
        localTipsTextArea = new JTextArea();
        localTipsTextArea.setEditable(false);
        localTipsTextArea.setLineWrap(true);
        localTipsTextArea.setWrapStyleWord(true);
        localTipsTextArea.setText("Local Food Suggestions:\n- Try the local biryani at XYZ street\n- Avoid traveling at night in remote areas");
        localTipsTextArea.setBorder(BorderFactory.createTitledBorder("Food & Local Tips"));
        JScrollPane tipsScrollPane = new JScrollPane(localTipsTextArea);
        tipsScrollPane.setPreferredSize(new Dimension(200, 150));
        bottomGbc.gridx = 1;
        bottomPanel.add(tipsScrollPane, bottomGbc);

        // Cost Estimator
        costEstimatorLabel = new JLabel("Estimated Cost: $0");
        costEstimatorLabel.setBorder(BorderFactory.createTitledBorder("Cost Estimator"));
        bottomGbc.gridx = 2;
        bottomPanel.add(costEstimatorLabel, bottomGbc);

        // Budget Slider
        budgetSlider = new JSlider(0, 10000, 0);
        budgetSlider.setMajorTickSpacing(2000);
        budgetSlider.setMinorTickSpacing(500);
        budgetSlider.setPaintTicks(true);
        budgetSlider.setPaintLabels(true);
        budgetSlider.setToolTipText("Adjust your budget");
        bottomGbc.gridx = 3;
        bottomPanel.add(budgetSlider, bottomGbc);

        // Budget Value Label
        budgetValueLabel = new JLabel("Budget: $0");
        budgetValueLabel.setBorder(BorderFactory.createTitledBorder("Budget"));
        bottomGbc.gridx = 4;
        bottomPanel.add(budgetValueLabel, bottomGbc);

        add(bottomPanel, BorderLayout.SOUTH);

        // Event Listeners
        generateItineraryButton.addActionListener(e -> generateItinerary());

        destinationField.addActionListener(e -> updateDestinationPreview());

        sortOptionsComboBox.addActionListener(e -> updateDestinationList());

        regionFilterComboBox.addActionListener(e -> updateDestinationList());

        activityFilterComboBox.addActionListener(e -> updateDestinationList());

        budgetSlider.addChangeListener(e -> updateBudgetValue());

        packageBookButton.addActionListener(e -> bookSelectedPackage());
    }

    // Add setter for loggedInUserId
    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }

    private void generateItinerary() {
        itineraryPanel.removeAll();

        int duration = (Integer) tripDurationSpinner.getValue();

        for (int day = 1; day <= duration; day++) {
            String activity;
            switch (day) {
                case 1:
                    activity = "Day 1 - Arrival and Hotel Check-in";
                    break;
                case 2:
                    activity = "Day 2 - City Tour and Sightseeing";
                    break;
                case 3:
                    activity = "Day 3 - Adventure Activities";
                    break;
                case 4:
                    activity = "Day 4 - Free Day / Shopping";
                    break;
                default:
                    activity = "Day " + day + " - Relax and Explore";
                    break;
            }
            JLabel dayLabel = new JLabel(activity);
            itineraryPanel.add(dayLabel);
        }

        itineraryPanel.revalidate();
        itineraryPanel.repaint();

        // Update hotel recommendations (sample data)
        hotelRecommendationPanel.removeAll();
        String[] hotels = {"Hotel Paradise - $100/night", "Seaside Resort - $120/night", "Mountain Inn - $90/night"};
        for (String hotel : hotels) {
            JPanel hotelPanel = new JPanel(new BorderLayout());
            JLabel hotelLabel = new JLabel(hotel);
            JButton bookButton = new JButton("Book");
            hotelPanel.add(hotelLabel, BorderLayout.CENTER);
            hotelPanel.add(bookButton, BorderLayout.EAST);
            hotelRecommendationPanel.add(hotelPanel);

            bookButton.addActionListener(e -> {
                // Implement actual booking logic here
                // For demonstration, create a booking with sample data and show success message

                // Extract hotel name (before the dash)
                String hotelName = hotel.split(" - ")[0];

                // Use loggedInUserId instead of hardcoded 0
                int userId = this.loggedInUserId;
                if (userId == -1) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Booking failed: No logged-in user found.");
                    return;
                }

                Booking booking = new Booking(0, userId, 0, "User", "user@example.com", new java.util.Date(), "Pending");
                // In a real app, you would link booking to the hotel and user properly

                try {
                    // Assuming BookingDAO is accessible, else pass it via constructor or setter
                    BookingDAO bookingDAO = new BookingDAO();
                    bookingDAO.addBooking(booking);
                    javax.swing.JOptionPane.showMessageDialog(this, "Hotel '" + hotelName + "' booked successfully!");
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Booking failed: " + ex.getMessage());
                }
            });
        }
        hotelRecommendationPanel.revalidate();
        hotelRecommendationPanel.repaint();

        // Update cost estimator
        int perDayCost = 100; // Sample per-day cost estimate
        int totalCost = perDayCost * duration;
        costEstimatorLabel.setText("Estimated Cost: $" + totalCost);
    }

    private void updateDestinationPreview() {
        String destination = destinationField.getText().trim();
        if (destination.isEmpty()) {
            destinationImageLabel.setIcon(null);
            destinationDescriptionArea.setText("");
            return;
        }
        // For demonstration, use placeholder image and description
        // In real app, fetch from backend or database
        destinationImageLabel.setIcon(new ImageIcon("images/" + destination.toLowerCase() + ".jpg"));
        destinationDescriptionArea.setText("Description for " + destination + " goes here. Explore the beautiful sights and attractions.");
    }

    private void updateDestinationList() {
        // Placeholder for updating destination list based on sort and filters
        // In real app, fetch filtered and sorted data from backend or database
        System.out.println("Updating destination list with sort: " + sortOptionsComboBox.getSelectedItem() +
                ", region: " + regionFilterComboBox.getSelectedItem() +
                ", activity: " + activityFilterComboBox.getSelectedItem());
    }

    private void updateBudgetValue() {
        int budget = budgetSlider.getValue();
        budgetValueLabel.setText("Budget: $" + budget);
        // Optionally update package recommendations based on budget
    }

    private void bookSelectedPackage() {
        // Placeholder for booking logic for selected package
        JOptionPane.showMessageDialog(this, "Package booked successfully!");
        // Implement actual booking persistence here
    }
}
