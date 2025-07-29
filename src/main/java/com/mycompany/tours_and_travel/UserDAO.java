package com.mycompany.tours_and_travel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public User authenticate(String usernameOrEmail, String password) throws Exception {
        String sql = "SELECT id, username, email, password_hash FROM users WHERE (username = ? OR email = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    if (verifyPassword(password, storedHash)) {
                        return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            storedHash
                        );
                    }
                }
            }
        }
        return null;
    }

    private boolean verifyPassword(String password, String storedHash) {
        // TODO: Implement password hash verification, e.g., using BCrypt
        // For now, plain text comparison (not secure)
        return password.equals(storedHash);
    }
}
