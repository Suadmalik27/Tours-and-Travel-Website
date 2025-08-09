package com.mycompany.tours_and_travel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class StyleUtilities {
    
    public static void styleButton(JButton button) {
        styleButton(button, ThemeColors.BUTTON_PRIMARY);
    }
    
    public static void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(ThemeColors.HEADING_TEXT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        
        // Rounded corners effect
        button.setBorder(new RoundedBorder(25));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ThemeColors.BUTTON_HOVER);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
    
    public static void stylePanel(JPanel panel) {
        panel.setBackground(ThemeColors.CARD_BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
    }
    
    public static void styleSectionPanel(JPanel panel) {
        panel.setBackground(ThemeColors.SECTION_BACKGROUND);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
    }
    
    public static void styleNavigationPanel(JPanel panel) {
        panel.setBackground(ThemeColors.NAVIGATION_BAR);
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
    }
    
    public static void styleLabel(JLabel label, boolean isHeading) {
        if (isHeading) {
            label.setFont(new Font("SansSerif", Font.BOLD, 18));
            label.setForeground(ThemeColors.HEADING_TEXT);
        } else {
            label.setFont(new Font("SansSerif", Font.PLAIN, 14));
            label.setForeground(ThemeColors.BODY_TEXT);
        }
    }
    
    public static void styleTextField(JTextField textField) {
        textField.setBackground(ThemeColors.INPUT_BACKGROUND);
        textField.setForeground(ThemeColors.BODY_TEXT);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBorder(new RoundedBorder(15));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    public static void styleTextArea(JTextArea textArea) {
        textArea.setBackground(ThemeColors.INPUT_BACKGROUND);
        textArea.setForeground(ThemeColors.BODY_TEXT);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
    
    // Custom rounded border class
    static class RoundedBorder extends LineBorder {
        private int radius;
        
        public RoundedBorder(Color color, int radius) {
            super(color);
            this.radius = radius;
        }
        
        public RoundedBorder(int radius) {
            super(ThemeColors.BORDER_SUBTLE);
            this.radius = radius;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getLineColor());
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
        
        @Override
        public java.awt.Insets getBorderInsets(Component c) {
            return new java.awt.Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }
    }
}
