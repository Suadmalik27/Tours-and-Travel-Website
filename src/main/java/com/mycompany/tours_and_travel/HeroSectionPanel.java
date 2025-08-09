package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
    private BufferedImage backgroundImage;
    private static final String IMAGE_PATH = "img/sky2.jpeg";
    private boolean imageLoaded = false;

    public HeroSectionPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 200));
        
        // Load background image
        try {
            backgroundImage = ImageIO.read(new File(IMAGE_PATH));
            System.out.println("Background image loaded successfully: " + IMAGE_PATH);
            imageLoaded = true;
        } catch (IOException e) {
            System.err.println("Could not load background image: " + IMAGE_PATH);
            e.printStackTrace();
            setBackground(Color.WHITE); // Fallback to white background
            imageLoaded = false;
        }

        // Message Label
        messageLabel = new JLabel(messages[currentIndex], SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        messageLabel.setForeground(Color.WHITE); // Changed to white for better contrast
        messageLabel.setBorder(new EmptyBorder(20, 10, 10, 10));
        messageLabel.setOpaque(false); // Make label transparent
        add(messageLabel, BorderLayout.CENTER);

        // Explore Now Button
        exploreNowButton = new JButton("Explore Now");
        exploreNowButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        exploreNowButton.setBackground(new Color(255, 140, 0));
        exploreNowButton.setForeground(Color.WHITE);
        exploreNowButton.setFocusPainted(false);
        exploreNowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exploreNowButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        exploreNowButton.setOpaque(false); // Make button transparent

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
        buttonPanel.setOpaque(false); // Make panel transparent
        buttonPanel.add(exploreNowButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Carousel Timer to rotate messages every 4 seconds
        carouselTimer = new Timer(4000, e -> {
            currentIndex = (currentIndex + 1) % messages.length;
            messageLabel.setText(messages[currentIndex]);
        });
        carouselTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent called in HeroSectionPanel");
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        if (imageLoaded && backgroundImage != null) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // Draw the background image scaled to fit the panel
            g2d.drawImage(backgroundImage, 0, 0, width, height, this);
            // Add semi-transparent white overlay
            Color overlayColor = new Color(255, 255, 255, 150); // White with ~60% opacity
            g2d.setColor(overlayColor);
            g2d.fillRect(0, 0, width, height);
        } else {
            // Draw placeholder background
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, width, height);
            // Draw error message
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            String errorMsg = "Image not found";
            int msgWidth = g2d.getFontMetrics().stringWidth(errorMsg);
            g2d.drawString(errorMsg, (width - msgWidth) / 2, height / 2);
        }
        g2d.dispose();
    }
}
