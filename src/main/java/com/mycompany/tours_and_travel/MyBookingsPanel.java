package com.mycompany.tours_and_travel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MyBookingsPanel extends JPanel {
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JTextField bookingIdField;
    private JTextField amountField;
    private JComboBox<String> paymentMethodComboBox;
    private JButton payButton;
    private JButton backButton;
    private JButton updateStatusButton;
    private JComboBox<String> statusComboBox;
    private ActionListener navigationListener;

    private BookingDAO bookingDAO;
    private PaymentDAO paymentDAO;

    public MyBookingsPanel(BookingDAO bookingDAO, PaymentDAO paymentDAO, ActionListener navigationListener) {
        this.bookingDAO = bookingDAO;
        this.paymentDAO = paymentDAO;
        this.navigationListener = navigationListener;

        setLayout(new BorderLayout());
        setBackground(new Color(255, 240, 230));

        // Bookings table
        String[] columnNames = {"Booking ID", "Tour ID", "Customer Name", "Customer Email", "Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Payment panel
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        backButton = new JButton("Back");
        backButton.addActionListener(navigationListener);
        backButton.setActionCommand("Home");
        paymentPanel.add(backButton, gbc);

        gbc.gridx = 1;
        paymentPanel.add(new JLabel("Booking ID:"), gbc);

        gbc.gridx = 2;
        bookingIdField = new JTextField(20);
        paymentPanel.add(bookingIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        paymentPanel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 2;
        amountField = new JTextField(20);
        paymentPanel.add(amountField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        paymentPanel.add(new JLabel("Payment Method:"), gbc);

        gbc.gridx = 2;
        paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "PayPal", "Cash"});
        paymentPanel.add(paymentMethodComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        paymentPanel.add(new JLabel("Booking Status:"), gbc);

        gbc.gridx = 2;
        statusComboBox = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});
        paymentPanel.add(statusComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        updateStatusButton = new JButton("Update Status");
        paymentPanel.add(updateStatusButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        payButton = new JButton("Make Payment");
        payButton.setBackground(new Color(255, 99, 71));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        paymentPanel.add(payButton, gbc);

        add(paymentPanel, BorderLayout.SOUTH);

        loadBookings();

        payButton.addActionListener(e -> {
            String bookingIdText = bookingIdField.getText().trim();
            String amountText = amountField.getText().trim();
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();

            if (bookingIdText.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Booking ID and Amount fields cannot be empty.");
                return;
            }

            int bookingId;
            double amount;
            try {
                bookingId = Integer.parseInt(bookingIdText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric Booking ID.");
                return;
            }

            try {
                amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be greater than zero.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric Amount.");
                return;
            }

            try {
                Payment payment = new Payment(0, bookingId, amount, paymentMethod, "Completed", null);
                paymentDAO.addPayment(payment);
                JOptionPane.showMessageDialog(this, "Payment successful!");
                bookingIdField.setText("");
                amountField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Payment failed: " + ex.getMessage());
            }
        });

        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookingIdText = bookingIdField.getText().trim();
                String selectedStatus = (String) statusComboBox.getSelectedItem();

                if (bookingIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(MyBookingsPanel.this, "Please enter a Booking ID to update.");
                    return;
                }

                int bookingId;
                try {
                    bookingId = Integer.parseInt(bookingIdText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MyBookingsPanel.this, "Please enter a valid numeric Booking ID.");
                    return;
                }

                try {
                    bookingDAO.updateBookingStatus(bookingId, selectedStatus);
                    JOptionPane.showMessageDialog(MyBookingsPanel.this, "Booking status updated successfully.");
                    loadBookings();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MyBookingsPanel.this, "Failed to update booking status: " + ex.getMessage());
                }
            }
        });
    }

    public void loadBookings() {
        List<Booking> bookings;
        try {
            bookings = bookingDAO.getAllBookings();
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load bookings: " + e.getMessage());
            return;
        }
        tableModel.setRowCount(0);
        for (Booking booking : bookings) {
            Object[] row = {
                booking.getId(),
                booking.getPackageId(),
                booking.getCustomerName(),
                booking.getCustomerEmail(),
                booking.getBookingDate(),
                booking.getStatus()
            };
            tableModel.addRow(row);
        }
    }
}
