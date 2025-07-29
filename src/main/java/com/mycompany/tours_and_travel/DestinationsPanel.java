package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DestinationsPanel extends JPanel {
    private JComboBox<String> regionComboBox;
    private JCheckBox adventureCheckBox;
    private JCheckBox sightseeingCheckBox;
    private JCheckBox relaxationCheckBox;
    private JSlider budgetSlider;
    private JButton filterButton;
    private JList<String> destinationsList;
    private DefaultListModel<String> listModel;
    private JPanel detailsPanel;
    private JButton viewPackagesButton;
    private ActionListener navigationListener;

    private List<Destination> allDestinations;

    public DestinationsPanel(ActionListener navigationListener) {
        this.navigationListener = navigationListener;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 255));

        // Sample destinations data
        allDestinations = new ArrayList<>();
        allDestinations.add(new Destination("Paris", "Europe", "Sightseeing", 1500, "paris.jpg", "The city of lights and love."));
        allDestinations.add(new Destination("Bali", "Asia", "Relaxation", 1200, "bali.jpg", "Tropical paradise with beaches."));
        allDestinations.add(new Destination("Swiss Alps", "Europe", "Adventure", 2000, "swiss_alps.jpg", "Mountain adventures and skiing."));
        allDestinations.add(new Destination("New York", "North America", "Sightseeing", 1800, "new_york.jpg", "The city that never sleeps."));
        allDestinations.add(new Destination("Sydney", "Australia", "Sightseeing", 1700, "sydney.jpg", "Iconic landmarks and beaches."));

        // Filters panel
        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        regionComboBox = new JComboBox<>(new String[] {"All Regions", "Europe", "Asia", "North America", "Australia"});
        filtersPanel.add(new JLabel("Region:"));
        filtersPanel.add(regionComboBox);

        adventureCheckBox = new JCheckBox("Adventure");
        sightseeingCheckBox = new JCheckBox("Sightseeing");
        relaxationCheckBox = new JCheckBox("Relaxation");
        filtersPanel.add(new JLabel("Activity Type:"));
        filtersPanel.add(adventureCheckBox);
        filtersPanel.add(sightseeingCheckBox);
        filtersPanel.add(relaxationCheckBox);

        budgetSlider = new JSlider(500, 3000, 3000);
        budgetSlider.setMajorTickSpacing(500);
        budgetSlider.setPaintTicks(true);
        budgetSlider.setPaintLabels(true);
        filtersPanel.add(new JLabel("Max Budget:"));
        filtersPanel.add(budgetSlider);

        filterButton = new JButton("Filter");
        filtersPanel.add(filterButton);

        add(filtersPanel, BorderLayout.NORTH);

        // Destinations list
        listModel = new DefaultListModel<>();
        destinationsList = new JList<>(listModel);
        destinationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(destinationsList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Destinations"));
        add(listScrollPane, BorderLayout.WEST);

        // Details panel
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        add(detailsPanel, BorderLayout.CENTER);

        viewPackagesButton = new JButton("View Packages");
        viewPackagesButton.setEnabled(false);
        detailsPanel.add(viewPackagesButton);

        // Load all destinations initially
        loadDestinations(allDestinations);

        // List selection listener
        destinationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selected = destinationsList.getSelectedValue();
                    if (selected != null) {
                        Destination dest = findDestinationByName(selected);
                        showDetails(dest);
                        viewPackagesButton.setEnabled(true);
                    } else {
                        clearDetails();
                        viewPackagesButton.setEnabled(false);
                    }
                }
            }
        });

        // Filter button action
        filterButton.addActionListener(e -> {
            List<Destination> filtered = new ArrayList<>();
            String selectedRegion = (String) regionComboBox.getSelectedItem();
            boolean adv = adventureCheckBox.isSelected();
            boolean sight = sightseeingCheckBox.isSelected();
            boolean relax = relaxationCheckBox.isSelected();
            int maxBudget = budgetSlider.getValue();

            for (Destination d : allDestinations) {
                if (!selectedRegion.equals("All Regions") && !d.getRegion().equals(selectedRegion)) {
                    continue;
                }
                if (adv || sight || relax) {
                    if (!(adv && d.getActivityType().equals("Adventure")) &&
                        !(sight && d.getActivityType().equals("Sightseeing")) &&
                        !(relax && d.getActivityType().equals("Relaxation"))) {
                        continue;
                    }
                }
                if (d.getBudget() > maxBudget) {
                    continue;
                }
                filtered.add(d);
            }
            loadDestinations(filtered);
            clearDetails();
            viewPackagesButton.setEnabled(false);
        });

        // View Packages button action
        viewPackagesButton.addActionListener(e -> {
            String selected = destinationsList.getSelectedValue();
            if (selected != null) {
                // For now, just show a message dialog
                javax.swing.JOptionPane.showMessageDialog(this, "View packages for " + selected);
            }
        });
    }

    private void loadDestinations(List<Destination> destinations) {
        listModel.clear();
        for (Destination d : destinations) {
            listModel.addElement(d.getName());
        }
    }

    private Destination findDestinationByName(String name) {
        for (Destination d : allDestinations) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    private void showDetails(Destination dest) {
        detailsPanel.removeAll();
        if (dest != null) {
            // Image placeholder
            JLabel imageLabel = new JLabel();
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + dest.getImagePath()));
            imageLabel.setIcon(icon);
            detailsPanel.add(imageLabel);

            // Description
            JLabel descLabel = new JLabel("<html><p style=\"width:200px\">" + dest.getDescription() + "</p></html>");
            detailsPanel.add(descLabel);

            // View Packages button
            detailsPanel.add(viewPackagesButton);
        }
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    private void clearDetails() {
        detailsPanel.removeAll();
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    // Inner class for Destination data
    private static class Destination {
        private String name;
        private String region;
        private String activityType;
        private int budget;
        private String imagePath;
        private String description;

        public Destination(String name, String region, String activityType, int budget, String imagePath, String description) {
            this.name = name;
            this.region = region;
            this.activityType = activityType;
            this.budget = budget;
            this.imagePath = imagePath;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getRegion() {
            return region;
        }

        public String getActivityType() {
            return activityType;
        }

        public int getBudget() {
            return budget;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getDescription() {
            return description;
        }
    }
}
