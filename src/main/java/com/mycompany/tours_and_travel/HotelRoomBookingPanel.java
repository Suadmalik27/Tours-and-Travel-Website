package com.mycompany.tours_and_travel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HotelRoomBookingPanel extends JPanel {
    private JComboBox<Tour> roomComboBox;
    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JTextField checkInDateField;
    private JTextField checkOutDateField;
    private JComboBox<Integer> numberOfGuestsComboBox;
    private JTextArea specialRequestsArea;
    private JLabel totalPriceLabel;
    private JButton bookRoomButton;
    private JButton backButton;
    private ActionListener navigationListener;

    private TourDAO tourDAO;
    private BookingDAO bookingDAO;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private int loggedInUserId = -1; // Field to store logged-in user ID

    public HotelRoomBookingPanel(TourDAO tourDAO, BookingDAO bookingDAO, ActionListener navigationListener) {
        this.tourDAO = tourDAO;
        this.bookingDAO = bookingDAO;
        this.navigationListener = navigationListener;

        setLayout(new GridBagLayout());
        setBackground(new Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        backButton = new JButton("Back");
        backButton.addActionListener(navigationListener);
        backButton.setActionCommand("PreviousScreen");
        add(backButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Select Room:"), gbc);

        gbc.gridx = 1;
        roomComboBox = new JComboBox<>();
        add(roomComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Customer Name:"), gbc);

        gbc.gridx = 1;
        customerNameField = new JTextField(20);
        add(customerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Customer Email:"), gbc);

        gbc.gridx = 1;
        customerEmailField = new JTextField(20);
        add(customerEmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Check-in Date (dd-MM-yyyy):"), gbc);

        gbc.gridx = 1;
        checkInDateField = new JTextField(20);
        add(checkInDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Check-out Date (dd-MM-yyyy):"), gbc);

        gbc.gridx = 1;
        checkOutDateField = new JTextField(20);
        add(checkOutDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Number of Guests:"), gbc);

        gbc.gridx = 1;
        numberOfGuestsComboBox = new JComboBox<>();
        for (int i = 1; i <= 5; i++) {
            numberOfGuestsComboBox.addItem(i);
        }
        add(numberOfGuestsComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Special Requests:"), gbc);

        gbc.gridx = 1;
        specialRequestsArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(specialRequestsArea);
        add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Total Price:"), gbc);

        gbc.gridx = 1;
        totalPriceLabel = new JLabel("$0");
        add(totalPriceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        bookRoomButton = new JButton("Book Room");
        add(bookRoomButton, gbc);

        loadRooms();

        // Add listeners
        bookRoomButton.addActionListener(e -> bookRoom());
        checkInDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                updateTotalPrice();
            }
        });
        checkOutDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                updateTotalPrice();
            }
        });
        roomComboBox.addActionListener(e -> updateTotalPrice());
    }

    public void loadRoomsForHotel(Hotel hotel) {
        // Implementation to load rooms for the selected hotel
        // For now, just reload all tours (rooms)
        loadRooms();
    }

    public void setLoggedInUserId(int userId) {
        System.out.println("HotelRoomBookingPanel: Setting loggedInUserId to " + userId);
        this.loggedInUserId = userId;
    }

    public void setBookingEnabled(boolean enabled) {
        bookRoomButton.setEnabled(enabled);
        if (!enabled) {
            JOptionPane.showMessageDialog(this, "Please login to book a room.");
        }
    }

    private void loadRooms() {
        DefaultComboBoxModel<Tour> model = new DefaultComboBoxModel<>();
        for (Tour tour : tourDAO.getAllTours()) {
            model.addElement(tour);
        }
        roomComboBox.setModel(model);
    }

    private void updateTotalPrice() {
        String checkInStr = checkInDateField.getText().trim();
        String checkOutStr = checkOutDateField.getText().trim();
        int selectedIndex = roomComboBox.getSelectedIndex();
        if (checkInStr.isEmpty() || checkOutStr.isEmpty() || selectedIndex < 0) {
            totalPriceLabel.setText("$0");
            return;
        }
        try {
            Date checkInDate = dateFormat.parse(checkInStr);
            Date checkOutDate = dateFormat.parse(checkOutStr);
            if (!checkOutDate.after(checkInDate)) {
                totalPriceLabel.setText("Invalid dates");
                return;
            }
            long diff = checkOutDate.getTime() - checkInDate.getTime();
            int nights = (int) (diff / (1000 * 60 * 60 * 24));
            Tour selectedTour = tourDAO.getAllTours().get(selectedIndex);
            double totalPrice = nights * selectedTour.getPrice();
            totalPriceLabel.setText("$" + totalPrice);
        } catch (ParseException e) {
            totalPriceLabel.setText("Invalid date format");
        }
    }

    private void bookRoom() {
        int selectedIndex = roomComboBox.getSelectedIndex();
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a room.");
            return;
        }
        String customerName = customerNameField.getText().trim();
        String customerEmail = customerEmailField.getText().trim();
        String checkInStr = checkInDateField.getText().trim();
        String checkOutStr = checkOutDateField.getText().trim();
        int numberOfGuests = (Integer) numberOfGuestsComboBox.getSelectedItem();
        String specialRequests = specialRequestsArea.getText().trim();

        if (customerName.isEmpty() || customerEmail.isEmpty() || checkInStr.isEmpty() || checkOutStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }
        if (!customerEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }
        try {
            Date checkInDate = dateFormat.parse(checkInStr);
            Date checkOutDate = dateFormat.parse(checkOutStr);
            if (!checkOutDate.after(checkInDate)) {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.");
                return;
            }
            long diff = checkOutDate.getTime() - checkInDate.getTime();
            int nights = (int) (diff / (1000 * 60 * 60 * 24));
            Tour selectedTour = tourDAO.getAllTours().get(selectedIndex);
            double totalPrice = nights * selectedTour.getPrice();

            int userId = loggedInUserId;
            if (userId == -1) {
                JOptionPane.showMessageDialog(this, "Booking failed: No logged-in user found.");
                return;
            }
            // Use default packageId = 1 to satisfy foreign key constraint
            int defaultPackageId = 1;
            Booking booking = new Booking(0, userId, defaultPackageId, customerName, customerEmail, new java.util.Date(), "Pending");
            bookingDAO.addBooking(booking);

            JOptionPane.showMessageDialog(this, "Room booked successfully! Total price: $" + totalPrice);
            clearFields();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use dd-MM-yyyy.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Booking failed: " + ex.getMessage());
        }
    }

    private void clearFields() {
        customerNameField.setText("");
        customerEmailField.setText("");
        checkInDateField.setText("");
        checkOutDateField.setText("");
        numberOfGuestsComboBox.setSelectedIndex(0);
        specialRequestsArea.setText("");
        totalPriceLabel.setText("$0");
    }
}
