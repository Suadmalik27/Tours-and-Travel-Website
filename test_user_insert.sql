-- SQL insert statement to add a test user for login testing
INSERT INTO users (username, email, password_hash) VALUES ('testuser', 'testuser@example.com', 'testpassword');
-- Note: Password is stored in plain text as per current implementation. Use 'testpassword' as the password.

INSERT INTO users (username, email, password_hash) VALUES ('suad', 'suad@gmail.com', 'root');