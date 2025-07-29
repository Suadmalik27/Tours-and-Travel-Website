package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ExploreToursPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JPanel resultsPanel;
    private HotelDAO hotelDAO;
    private HotelSelectionListener hotelSelectionListener;
    private JButton backButton;
    private ActionListener navigationListener;

    public interface HotelSelectionListener {
        void onHotelSelected(Hotel hotel);
    }

    public ExploreToursPanel(HotelDAO hotelDAO, HotelSelectionListener listener, ActionListener navigationListener) {
        this.hotelDAO = hotelDAO;
        this.hotelSelectionListener = listener;
        this.navigationListener = navigationListener;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 255, 240));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(20);
        searchButton = new JButton("Search Hotels");
        searchButton.setBackground(new Color(60, 179, 113));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        backButton = new JButton("Back");
        backButton.addActionListener(navigationListener);
        backButton.setActionCommand("Home");

        topPanel.add(backButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search keyword.");
                return;
            }
            List<Hotel> hotels;
            try {
                hotels = hotelDAO.searchHotels(keyword);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error searching hotels: " + ex.getMessage());
                return;
            }
            resultsPanel.removeAll();
            if (hotels.isEmpty()) {
                JLabel noResultsLabel = new JLabel("No hotels found for the keyword: " + keyword);
                noResultsLabel.setForeground(Color.RED);
                resultsPanel.add(noResultsLabel);
            } else {
                for (Hotel hotel : hotels) {
                    JPanel hotelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
                    hotelPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    hotelPanel.add(new JLabel("Hotel: " + hotel.getName()));
                    hotelPanel.add(new JLabel("Location: " + hotel.getLocation()));
                    hotelPanel.add(new JLabel("Price per night: $" + hotel.getPrice()));
                    JButton selectButton = new JButton("Select");
                    hotelPanel.add(selectButton);
                    resultsPanel.add(hotelPanel);

                    selectButton.addActionListener(ev -> {
                        if (hotelSelectionListener != null) {
                            hotelSelectionListener.onHotelSelected(hotel);
                        }
                    });
                }
            }
            resultsPanel.revalidate();
            resultsPanel.repaint();
        });
    }
}
