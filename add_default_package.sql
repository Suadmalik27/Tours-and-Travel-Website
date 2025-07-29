-- SQL to add a default package with id=1 to satisfy foreign key constraint
INSERT INTO packages (id, destination, duration, price, rating, description) VALUES
(1, 'Default Package', '3 days', 299.99, 4.5, 'Default package description');
