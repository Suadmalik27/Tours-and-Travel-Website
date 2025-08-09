package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HomePanel extends JPanel {
    public HomePanel(ActionListener navigationListener) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Navigation Bar (top)
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navBar.setBackground(new Color(30, 144, 255)); // Dodger Blue
        String[] navItems = {"Home", "Destinations", "Packages", "Hotels", "Trip Planner", "Contact Us", "Login/Signup"};
        for (String item : navItems) {
            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(30, 144, 255));
            btn.setBorderPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setActionCommand(item.replaceAll(" ", ""));
            btn.addActionListener(navigationListener);
            navBar.add(btn);
        }
        add(navBar, BorderLayout.NORTH);

        // Middle Navigation Bar (secondary menu)
        JPanel middleNavBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        middleNavBar.setBackground(new Color(169, 211, 240, 230)); // #A9D3F0 with 0.9 alpha
        String[] middleNavItems = {"Home", "Destinations", "Packages", "Hotels", "Trip Planner", "Contact Us", "Login/Signup"};
        for (String item : middleNavItems) {
            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(169, 211, 240, 230)); // lighter blue tone with transparency
            btn.setBorderPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setActionCommand(item.replaceAll(" ", ""));
            btn.addActionListener(navigationListener);
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(255, 179, 71)); // #FFB347 warm peach tone
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(169, 211, 240, 230));
                }
            });
            middleNavBar.add(btn);
        }
        add(middleNavBar, BorderLayout.NORTH);

        // Main content panel with vertical layout
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Hero Banner Section
        JPanel heroBanner = new JPanel();
        heroBanner.setBackground(new Color(70, 130, 180)); // Steel Blue
        heroBanner.setPreferredSize(new Dimension(0, 100));
        heroBanner.setLayout(new BoxLayout(heroBanner, BoxLayout.Y_AXIS));
        JLabel welcomeLabel = new JLabel("Your Next Adventure Awaits – Explore Now");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        heroBanner.add(Box.createVerticalGlue());
        heroBanner.add(welcomeLabel);
        heroBanner.add(Box.createRigidArea(new Dimension(0, 15)));
        JButton exploreNowBtn = new JButton("Explore Now");
        exploreNowBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exploreNowBtn.setFocusPainted(false);
        exploreNowBtn.setBackground(new Color(255, 165, 0)); // Orange
        exploreNowBtn.setForeground(Color.BLACK);
        exploreNowBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        exploreNowBtn.setActionCommand("ExploreNow");
        exploreNowBtn.addActionListener(navigationListener);
        heroBanner.add(exploreNowBtn);
        heroBanner.add(Box.createVerticalGlue());
        mainContent.add(heroBanner);
        mainContent.add(Box.createRigidArea(new Dimension(0, 25)));

        // Search Section
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        searchPanel.setBackground(Color.WHITE);
        JTextField destinationField = new JTextField(15);
        destinationField.setToolTipText("Enter destination");
        destinationField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String[] travelTypes = {"Adventure", "Beach", "City"};
        JComboBox<String> travelTypeCombo = new JComboBox<>(travelTypes);
        travelTypeCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField dateField = new JTextField(10);
        dateField.setToolTipText("Travel dates");
        dateField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JButton searchBtn = new JButton("Search");
        searchBtn.setActionCommand("Search");
        searchBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchBtn.setBackground(new Color(30, 144, 255));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.addActionListener(navigationListener);
        searchPanel.add(destinationField);
        searchPanel.add(travelTypeCombo);
        searchPanel.add(dateField);
        searchPanel.add(searchBtn);
        mainContent.add(searchPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 25)));

        // Featured Packages Section
        JPanel featuredPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        featuredPanel.setBackground(Color.WHITE);

        for (int i = 1; i <= 4; i++) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            card.setBackground(new Color(250, 250, 250));
            card.setPreferredSize(new Dimension(300, 300));

            JLabel imgLabel;
            if (i == 1) {
                imgLabel = new JLabel(new javax.swing.ImageIcon("img/rr.jpeg"));
            } else if (i == 2) {
                imgLabel = new JLabel(new javax.swing.ImageIcon("img/ff.jpeg"));
            } else if (i == 3) {
                imgLabel = new JLabel(new javax.swing.ImageIcon("img/kerela.jpeg"));
                
                }else if (i == 4) {
                imgLabel = new JLabel(new javax.swing.ImageIcon("img/mald.jpeg"));
                
                }
                else {
                imgLabel = new JLabel("Image " + i, SwingConstants.CENTER);
            }
            imgLabel.setPreferredSize(new Dimension(300, 200));
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imgLabel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            card.add(imgLabel);

            JLabel titleLabel = new JLabel("Package " + i);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(titleLabel);

            JLabel priceLabel = new JLabel("$" + (100 + i * 50));
            priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(priceLabel);

            JLabel durationLabel = new JLabel((3 + i) + " days");
            durationLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            durationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(durationLabel);

            JButton bookNowBtn = new JButton("Book Now");
            bookNowBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            bookNowBtn.setActionCommand("BookNow" + i);
            bookNowBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
            bookNowBtn.setBackground(new Color(30, 144, 255));
            bookNowBtn.setForeground(Color.WHITE);
            bookNowBtn.setFocusPainted(false);
            bookNowBtn.addActionListener(navigationListener);
            card.add(Box.createVerticalGlue());
            card.add(bookNowBtn);

            featuredPanel.add(card);
        }
        mainContent.add(featuredPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 25)));

        // Testimonials Section
        JPanel testimonialPanel = new JPanel();
        testimonialPanel.setBackground(new Color(230, 230, 250)); // Lavender
        testimonialPanel.setLayout(new BoxLayout(testimonialPanel, BoxLayout.Y_AXIS));
        testimonialPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel testimonialLabel1 = new JLabel("\"Best trip of my life!\"");
        JLabel testimonialLabel2 = new JLabel("\"Affordable and fun!\"");
        JLabel testimonialLabel3 = new JLabel("\"Highly recommend this service!\"");
        testimonialLabel1.setFont(new Font("SansSerif", Font.ITALIC, 14));
        testimonialLabel2.setFont(new Font("SansSerif", Font.ITALIC, 14));
        testimonialLabel3.setFont(new Font("SansSerif", Font.ITALIC, 14));
        testimonialLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        testimonialLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        testimonialLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        testimonialPanel.add(testimonialLabel1);
        testimonialPanel.add(testimonialLabel2);
        testimonialPanel.add(testimonialLabel3);
        mainContent.add(testimonialPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 25)));

        // Footer Section
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(new Color(245, 245, 245));
        JLabel aboutUs = new JLabel("About Us");
        JLabel privacyPolicy = new JLabel("Privacy Policy");
        JLabel contact = new JLabel("Contact");
        JLabel instagram = new JLabel("[Instagram]");
        JLabel facebook = new JLabel("[Facebook]");
        aboutUs.setForeground(new Color(30, 144, 255));
        privacyPolicy.setForeground(new Color(30, 144, 255));
        contact.setForeground(new Color(30, 144, 255));
        instagram.setForeground(new Color(30, 144, 255));
        facebook.setForeground(new Color(30, 144, 255));
        aboutUs.setFont(new Font("SansSerif", Font.BOLD, 14));
        privacyPolicy.setFont(new Font("SansSerif", Font.BOLD, 14));
        contact.setFont(new Font("SansSerif", Font.BOLD, 14));
        instagram.setFont(new Font("SansSerif", Font.BOLD, 14));
        facebook.setFont(new Font("SansSerif", Font.BOLD, 14));
        footerPanel.add(aboutUs);
        footerPanel.add(privacyPolicy);
        footerPanel.add(contact);
        footerPanel.add(instagram);
        footerPanel.add(facebook);
        mainContent.add(footerPanel);

        add(new JScrollPane(mainContent), BorderLayout.CENTER);
    }
}
