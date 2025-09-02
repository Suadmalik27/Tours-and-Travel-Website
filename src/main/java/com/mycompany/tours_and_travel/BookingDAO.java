package com.mycompany.tours_and_travel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

public List<Booking> getBookingsByUserId(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT id, user_id, package_id, customer_name, customer_email, booking_date, status FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("package_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getDate("booking_date"),
                        rs.getString("status")
                    );
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }

public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT id, user_id, package_id, customer_name, customer_email, booking_date, status FROM bookings";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("package_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getDate("booking_date"),
                    rs.getString("status")
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public void addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, package_id, customer_name, customer_email, booking_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getPackageId());
            stmt.setString(3, booking.getCustomerName());
            stmt.setString(4, booking.getCustomerEmail());
            stmt.setDate(5, new java.sql.Date(booking.getBookingDate().getTime()));
            stmt.setString(6, "Pending");
            stmt.executeUpdate();
        }
    }

    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        }
    }

    public void deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        }
    }
}
