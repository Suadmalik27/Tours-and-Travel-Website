package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PackagesPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> categoryFilterComboBox;
    private JSlider maxBudgetSlider;
    private JButton searchButton;
    private JPanel packagesListPanel;
    private JScrollPane scrollPane;

    private List<Package> allPackages; // Assume this is populated from backend or DAO
    private ActionListener navigationListener;

    public PackagesPanel(ActionListener navigationListener) {
        this.navigationListener = navigationListener;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Travel Packages"));

        // Search and Filters Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        searchField = new JTextField(20);
        searchField.setToolTipText("Enter keywords to search packages");
        searchPanel.add(searchField);

        categoryFilterComboBox = new JComboBox<>(new String[]{"All", "Beach", "Mountain", "City", "Adventure"});
        categoryFilterComboBox.setToolTipText("Filter by category");
        searchPanel.add(categoryFilterComboBox);

        maxBudgetSlider = new JSlider(100, 3000, 3000);
        maxBudgetSlider.setMajorTickSpacing(500);
        maxBudgetSlider.setMinorTickSpacing(100);
        maxBudgetSlider.setPaintTicks(true);
        maxBudgetSlider.setPaintLabels(true);
        maxBudgetSlider.setToolTipText("Set maximum budget");
        maxBudgetSlider.setPreferredSize(new Dimension(200, 50));
        searchPanel.add(maxBudgetSlider);

        searchButton = new JButton("Search");
        searchButton.setToolTipText("Search packages");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Packages List Panel
        packagesListPanel = new JPanel();
        packagesListPanel.setLayout(new BoxLayout(packagesListPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(packagesListPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Load all packages (placeholder)
        allPackages = new ArrayList<>();
        loadSamplePackages();

        // Display all packages initially
        displayPackages(allPackages);

        // Search button action
        searchButton.addActionListener(e -> performSearch());
    }

    private void loadSamplePackages() {
        // Sample packages - in real app, fetch from DAO/backend
        allPackages.add(new Package(1, "Beach Paradise", "5 days", 1200.0, 4.5, "Relax on sunny beaches with crystal clear water."));
        allPackages.add(new Package(2, "Mountain Adventure", "7 days", 1500.0, 4.7, "Explore rugged mountains and thrilling hikes."));
        allPackages.add(new Package(3, "City Explorer", "4 days", 900.0, 4.2, "Discover vibrant city life and cultural landmarks."));
        allPackages.add(new Package(4, "Safari Expedition", "10 days", 2500.0, 4.9, "Experience wildlife safaris and nature tours."));
    }

    private void displayPackages(List<Package> packages) {
        packagesListPanel.removeAll();

        for (Package pkg : packages) {
            JPanel card = createPackageCard(pkg);
            packagesListPanel.add(card);
            packagesListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        packagesListPanel.revalidate();
        packagesListPanel.repaint();
    }

    private JPanel createPackageCard(Package pkg) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(new CompoundBorder(
            new LineBorder(Color.GRAY, 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        // Image
        JLabel imageLabel = new JLabel();
        try {
            String imagePath;
                if (pkg.getDestination().equalsIgnoreCase("Beach Paradise")) {
                    imagePath = "img/bp.jpg";
                } else if (pkg.getDestination().equalsIgnoreCase("Mountain Adventure")) {
                    imagePath = "img/md.jpeg";
                } else if (pkg.getDestination().equalsIgnoreCase("City Explorer")) {
                    imagePath = "img/city.jpeg";
                } else if (pkg.getDestination().equalsIgnoreCase("Safari Expedition")) {
                    imagePath = "img/saf.jpeg";
                } else {
                    imagePath = "img/" + pkg.getDestination().toLowerCase().replace(" ", "_") + ".jpg";
                }
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("[No Image]");
        }
        imageLabel.setPreferredSize(new Dimension(120, 80));
        card.add(imageLabel, BorderLayout.WEST);

        // Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel(pkg.getDestination());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        detailsPanel.add(nameLabel);

        JLabel priceLabel = new JLabel("Price: $" + pkg.getPrice());
        priceLabel.setForeground(new Color(255, 69, 0)); // OrangeRed
        detailsPanel.add(priceLabel);

        JLabel durationLabel = new JLabel(pkg.getDuration());
        detailsPanel.add(durationLabel);

        // Tooltip with short description
        detailsPanel.setToolTipText(pkg.getDescription());

        card.add(detailsPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton wishlistButton = new JButton("❤️ Add to Wishlist");
        JButton bookNowButton = new JButton("📘 Book Now");
        bookNowButton.setBackground(new Color(30, 144, 255)); // Dodger Blue
        bookNowButton.setForeground(Color.WHITE);
        bookNowButton.setFocusPainted(false);
        bookNowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonsPanel.add(wishlistButton);
        buttonsPanel.add(bookNowButton);

        card.add(buttonsPanel, BorderLayout.SOUTH);

        // Add hover effect for card
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(245, 245, 245));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
            }
        });

        // Button actions (placeholders)
        wishlistButton.addActionListener(e -> JOptionPane.showMessageDialog(this, pkg.getDestination() + " added to wishlist!"));
        bookNowButton.setActionCommand("BookNowPackage" + pkg.getId());
        bookNowButton.addActionListener(navigationListener);

        return card;
    }

    private void performSearch() {
        String keyword = searchField.getText().trim().toLowerCase();
        String category = (String) categoryFilterComboBox.getSelectedItem();
        int maxBudget = maxBudgetSlider.getValue();

        List<Package> filtered = new ArrayList<>();
        for (Package pkg : allPackages) {
            boolean matchesKeyword = keyword.isEmpty() || pkg.getDestination().toLowerCase().contains(keyword);
            boolean matchesCategory = category.equals("All") || pkg.getDuration().toLowerCase().contains(category.toLowerCase());
            boolean matchesBudget = pkg.getPrice() <= maxBudget;

            if (matchesKeyword && matchesCategory && matchesBudget) {
                filtered.add(pkg);
            }
        }

        displayPackages(filtered);
    }
}
