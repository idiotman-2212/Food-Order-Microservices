CREATE DATABASE FoodOrder;
CREATE TABLE users (
id INT NOT NULL AUTO_INCREMENT,
username VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
phone VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);

CREATE TABLE roles (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);

CREATE TABLE user_role (
user_id INT NOT NULL,
role_id INT NOT NULL,
PRIMARY KEY (user_id, role_id)
);

CREATE TABLE categories (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);

CREATE TABLE products (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
image VARCHAR(255) NOT NULL,
price DECIMAL(10,2) NOT NULL,
quantity INT NOT NULL,
description TEXT NOT NULL,
create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
category_id INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE carts (
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE favourites (
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
product_id INT NOT NULL,
create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users (id),
FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE orders (
id INT NOT NULL AUTO_INCREMENT,
order_fee DECIMAL(10,2) NOT NULL,
order_desc TEXT NOT NULL,
order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
product_id INT NOT NULL,
cart_id INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (product_id) REFERENCES products (id),
FOREIGN KEY (cart_id) REFERENCES carts (id)
);

CREATE TABLE product_order (
product_id INT NOT NULL,
order_id INT NOT NULL,
order_quantity INT NOT NULL,
PRIMARY KEY (product_id, order_id),
FOREIGN KEY (product_id) REFERENCES products (id),
FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE payments (
id INT NOT NULL AUTO_INCREMENT,
isPayed TINYINT(1) NOT NULL,
payment_status ENUM('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') NOT NULL,
payment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
order_id INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (order_id) REFERENCES orders (id)
);

INSERT INTO roles (name, create_date) VALUES ('admin', NOW());
INSERT INTO roles (name, create_date) VALUES ('user', NOW());

INSERT INTO users (username, email, password, phone, address, create_date) VALUES ('admin', 'admin@gmail.com', '123456', '0123456789', 'Ha Noi', NOW());
INSERT INTO users (username, email, password, phone, address, create_date) VALUES ('user', 'user@gmail.com', '123456', '0123456789', 'Ha Noi', NOW());

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO categories (name, create_date) VALUES ('Dien thoai', NOW());
INSERT INTO categories (name, create_date) VALUES ('May tinh', NOW());
INSERT INTO categories (name, create_date) VALUES ('Phu kien', NOW());

INSERT INTO products (name, image, price, quantity, description, create_date, category_id) VALUES ('iPhone 13 Pro Max', 'iphone-13-pro-max.jpg', 30000000, 100, 'Dien thoai iPhone 13 Pro Max 128GB chinh hang VN/A', NOW(), 1);
INSERT INTO products (name, image, price, quantity, description, create_date, category_id) VALUES ('Samsung Galaxy S22 Ultra', 'samsung-galaxy-s22-ultra.jpg', 25000000, 100, 'Dien thoai Samsung Galaxy S22 Ultra 128GB chinh hang', NOW(), 1);
INSERT INTO products (name, image, price, quantity, description, create_date, category_id) VALUES ('Oppo Reno7 Z 5G', 'oppo-reno7-z-5g.jpg', 10000000, 100, 'Dien thoai Oppo Reno7 Z 5G 128GB chinh hang', NOW(), 1);
INSERT INTO products (name, image, price, quantity, description, create_date, category_id) VALUES ('Macbook Air M1 2020', 'macbook-air-m1-2020.jpg', 25000000, 100, 'May tinh Macbook Air M1 2020 8GB/256GB chinh hang', NOW(), 2);
INSERT INTO products (name, image, price, quantity, description, create_date, category_id) VALUES ('Dell Inspiron 15 3520', 'dell-inspiron-15-3520.jpg', 15000000, 100, 'May tinh Dell Inspiron 15 3520 i5-1235U/8GB/256GB/Intel UHD Graphics/15.6 FHD/Win 11', NOW(), 2);
INSERT INTO products (name, image, price, quantity, description, create_date, category_id) VALUES ('Logitech G102', 'logitech-g102.jpg', 500000, 100, 'Chuot Logitech G102 Gaming Mouse 8000 DPI', NOW(), 3);
INSERT INTO products (name, image, price, quantity, description, create_date, category_id) VALUES ('Tai nghe Sony WH-1000XM5', 'sony-wh-1000xm5.jpg', 5000000, 100, 'Tai nghe Sony WH-1000XM5 Wireless Noise Cancelling Headphones', NOW(), 3);

INSERT INTO carts (user_id, create_date) VALUES (1, NOW());
INSERT INTO carts (user_id, create_date) VALUES (2, NOW());

INSERT INTO favourites (user_id, product_id, create_date) VALUES (1, 1, NOW());
INSERT INTO favourites (user_id, product_id, create_date) VALUES (1, 2, NOW());
INSERT INTO favourites (user_id, product_id, create_date) VALUES (2, 3, NOW());

INSERT INTO orders (order_fee, order_desc, order_date, product_id, cart_id) VALUES
(50, 'Đơn hàng cho 2 món', '2023-03-23 10:00:00', 2, 1),
(20, 'Đơn hàng cho 1 món', '2023-03-24 14:00:00', 3, 2),
(75, 'Đơn hàng cho 5 món', '2023-03-25 18:00:00', 4, 1),
(30, 'Đơn hàng cho 3 món', '2023-03-26 12:00:00', 5, 2);

INSERT INTO product_order (product_id, order_id, order_quantity) VALUES
(1, 1, 2),
(2, 1, 1),
(3, 2, 1),
(4, 3, 5),
(5, 4, 3);

INSERT INTO payments (isPayed, payment_status, payment_date, order_id) VALUES
(1, 'COMPLETED', '2023-03-23 11:00:00', 1),
(0, 'NOT_STARTED', '2023-03-24 15:00:00', 2),
(1, 'COMPLETED', '2023-03-25 19:00:00', 3),
(0, 'IN_PROGRESS', '2023-03-26 13:00:00', 4);