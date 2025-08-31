package com.mycompany.tours_and_travel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {

    public List<Package> getAllPackages() throws SQLException {
        List<Package> packages = new ArrayList<>();
        String sql = "SELECT id, destination, duration, price, rating, description FROM packages";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Package pkg = new Package(
                    rs.getInt("id"),
                    rs.getString("destination"),
                    rs.getString("duration"),
                    rs.getDouble("price"),
                    rs.getDouble("rating"),
                    rs.getString("description")
                );
                packages.add(pkg);
            }
        }
        return packages;
    }

    public Package getPackageById(int id) throws SQLException {
        String sql = "SELECT id, destination, duration, price, rating, description FROM packages WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Package(
                        rs.getInt("id"),
                        rs.getString("destination"),
                        rs.getString("duration"),
                        rs.getDouble("price"),
                        rs.getDouble("rating"),
                        rs.getString("description")
                    );
                }
            }
        }
        return null;
    }

    public void addPackage(Package pkg) throws SQLException {
        String sql = "INSERT INTO packages (destination, duration, price, rating, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pkg.getDestination());
            stmt.setString(2, pkg.getDuration());
            stmt.setDouble(3, pkg.getPrice());
            stmt.setDouble(4, pkg.getRating());
            stmt.setString(5, pkg.getDescription());
            stmt.executeUpdate();
        }
    }

    public void updatePackage(Package pkg) throws SQLException {
        String sql = "UPDATE packages SET destination = ?, duration = ?, price = ?, rating = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pkg.getDestination());
            stmt.setString(2, pkg.getDuration());
            stmt.setDouble(3, pkg.getPrice());
            stmt.setDouble(4, pkg.getRating());
            stmt.setString(5, pkg.getDescription());
            stmt.setInt(6, pkg.getId());
            stmt.executeUpdate();
        }
    }

    public void deletePackage(int id) throws SQLException {
        String sql = "DELETE FROM packages WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Package> getPackagesByDestination(String destination) throws SQLException {
        List<Package> packages = new ArrayList<>();
        String sql = "SELECT id, destination, duration, price, rating, description FROM packages WHERE destination = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, destination);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Package pkg = new Package(
                        rs.getInt("id"),
                        rs.getString("destination"),
                        rs.getString("duration"),
                        rs.getDouble("price"),
                        rs.getDouble("rating"),
                        rs.getString("description")
                    );
                    packages.add(pkg);
                }
            }
        }
        return packages;
    }
}
