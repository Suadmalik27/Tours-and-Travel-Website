package com.mycompany.tours_and_travel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    public List<Hotel> getAllHotels() throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT id, name, location, description, price_per_night FROM hotels";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Hotel hotel = new Hotel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getString("description"),
                    rs.getDouble("price_per_night")
                );
                hotels.add(hotel);
            }
        }
        return hotels;
    }

    public Hotel getHotelById(int id) throws SQLException {
        String sql = "SELECT id, name, location, description, price_per_night FROM hotels WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getDouble("price_per_night")
                    );
                }
            }
        }
        return null;
    }

    public void addHotel(Hotel hotel) throws SQLException {
        String sql = "INSERT INTO hotels (name, location, description, price_per_night) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getLocation());
            stmt.setString(3, hotel.getDescription());
            stmt.setDouble(4, hotel.getPrice());
            stmt.executeUpdate();
        }
    }

    public void updateHotel(Hotel hotel) throws SQLException {
        String sql = "UPDATE hotels SET name = ?, location = ?, description = ?, price_per_night = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getLocation());
            stmt.setString(3, hotel.getDescription());
            stmt.setDouble(4, hotel.getPrice());
            stmt.setInt(5, hotel.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteHotel(int id) throws SQLException {
        String sql = "DELETE FROM hotels WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Hotel> searchHotels(String keyword) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT id, name, location, description, price_per_night FROM hotels WHERE name LIKE ? OR location LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Hotel hotel = new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getDouble("price_per_night")
                    );
                    hotels.add(hotel);
                }
            }
        }
        return hotels;
    }
}
