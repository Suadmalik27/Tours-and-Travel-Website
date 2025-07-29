package com.mycompany.tours_and_travel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HotelBookingPanel extends JPanel {
    private JComboBox<Tour> roomComboBox;
    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JButton bookButton;
    private JButton backButton;
    private ActionListener navigationListener;

    private TourDAO tourDAO;
    private BookingDAO bookingDAO;

    public HotelBookingPanel(TourDAO tourDAO, BookingDAO bookingDAO, ActionListener navigationListener) {
        this.tourDAO = tourDAO;
        this.bookingDAO = bookingDAO;
        this.navigationListener = navigationListener;

        setLayout(new GridBagLayout());
        setBackground(new Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        backButton = new JButton("Back");
        backButton.addActionListener(navigationListener);
        backButton.setActionCommand("Home");
        add(backButton, gbc);

        gbc.gridx = 1;
        add(new JLabel("Select Room:"), gbc);

        gbc.gridx = 2;
        roomComboBox = new JComboBox<>();
        add(roomComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel("Customer Name:"), gbc);

        gbc.gridx = 2;
        customerNameField = new JTextField(20);
        add(customerNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(new JLabel("Customer Email:"), gbc);

        gbc.gridx = 2;
        customerEmailField = new JTextField(20);
        add(customerEmailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        bookButton = new JButton("Book Room");
        bookButton.setBackground(new Color(70, 130, 180));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        add(bookButton, gbc);

        loadRooms();

        bookButton.addActionListener(e -> {
            Tour selectedRoom = (Tour) roomComboBox.getSelectedItem();
            if (selectedRoom == null) {
                JOptionPane.showMessageDialog(this, "Please select a room.");
                return;
            }
            String customerName = customerNameField.getText().trim();
            String customerEmail = customerEmailField.getText().trim();
            if (customerName.isEmpty() || customerEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter customer name and email.");
                return;
            }
            if (!customerEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
                return;
            }
            Booking booking = new Booking(0, 0, selectedRoom.getId(), customerName, customerEmail, new java.util.Date(), "Pending");
            try {
                bookingDAO.addBooking(booking);
                JOptionPane.showMessageDialog(this, "Room booked successfully!");
                customerNameField.setText("");
                customerEmailField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Booking failed: " + ex.getMessage());
            }
        });
    }

    public void loadRooms() {
        DefaultComboBoxModel<Tour> model = new DefaultComboBoxModel<>();
        List<Tour> tours = tourDAO.getAllTours();
        for (Tour tour : tours) {
            model.addElement(tour);
        }
        roomComboBox.setModel(model);
    }

    public void loadRoomsForHotel(Hotel hotel) {
        // TODO: Implement loading rooms for the selected hotel
        DefaultComboBoxModel<Tour> model = new DefaultComboBoxModel<>();
        // For now, just load tours as placeholder
        List<Tour> tours = tourDAO.getAllTours();
        for (Tour tour : tours) {
            model.addElement(tour);
        }
        roomComboBox.setModel(model);
    }
}
