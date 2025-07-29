package com.mycompany.tours_and_travel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TopBarPanel extends JPanel {
    private JButton homeButton;
    private JButton destinationsButton;
    private JButton packagesButton;
    private JButton hotelsButton;
    private JButton tripPlannerButton;
    private JButton contactUsButton;
    private JButton loginSignupButton;

    public TopBarPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        setBackground(new Color(70, 130, 180)); // Steel Blue color for gradient base

        // Create buttons
        homeButton = createNavButton("Home");
        destinationsButton = createNavButton("Destinations");
        packagesButton = createNavButton("Packages");
        hotelsButton = createNavButton("Hotels");
        tripPlannerButton = createNavButton("Trip Planner");
        contactUsButton = createNavButton("Contact Us");
        loginSignupButton = createNavButton("Login/Signup");

        // Add buttons to panel
        add(homeButton);
        add(destinationsButton);
        add(packagesButton);
        add(hotelsButton);
        add(tripPlannerButton);
        add(contactUsButton);
        add(loginSignupButton);

        // Highlight active page example (Home)
        setActiveButton(homeButton);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Cornflower Blue
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != activeButton) {
                    button.setBackground(new Color(70, 130, 180));
                }
            }
        });

        // Click action example (can be customized)
        button.addActionListener(e -> setActiveButton(button));

        return button;
    }

    private JButton activeButton;

    private void setActiveButton(JButton button) {
        if (activeButton != null) {
            activeButton.setBackground(new Color(70, 130, 180));
        }
        activeButton = button;
        activeButton.setBackground(new Color(255, 165, 0)); // Orange highlight
    }
}
