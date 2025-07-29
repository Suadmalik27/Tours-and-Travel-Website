package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class HeroSectionPanel extends JPanel {
    private JLabel messageLabel;
    private JButton exploreNowButton;
    private Timer carouselTimer;
    private String[] messages = {
        "Your Next Adventure Awaits",
        "Discover New Destinations",
        "Book Your Dream Vacation Today"
    };
    private int currentIndex = 0;

    public HeroSectionPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 200));
        setBackground(new Color(255, 255, 255));

        // Message Label
        messageLabel = new JLabel(messages[currentIndex], SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        messageLabel.setForeground(new Color(255, 140, 0)); // Dark Orange
        messageLabel.setBorder(new EmptyBorder(20, 10, 10, 10));
        add(messageLabel, BorderLayout.CENTER);

        // Explore Now Button
        exploreNowButton = new JButton("Explore Now");
        exploreNowButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        exploreNowButton.setBackground(new Color(255, 140, 0));
        exploreNowButton.setForeground(Color.WHITE);
        exploreNowButton.setFocusPainted(false);
        exploreNowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exploreNowButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        exploreNowButton.setOpaque(true);

        // Hover effect
        exploreNowButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exploreNowButton.setBackground(new Color(255, 165, 0)); // Lighter Orange
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exploreNowButton.setBackground(new Color(255, 140, 0));
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.add(exploreNowButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Carousel Timer to rotate messages every 4 seconds
        carouselTimer = new Timer(4000, e -> {
            currentIndex = (currentIndex + 1) % messages.length;
            messageLabel.setText(messages[currentIndex]);
        });
        carouselTimer.start();
    }
}
