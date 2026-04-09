CREATE DATABASE REVSHOP;
USE REVSHOP;

-- USERS
CREATE TABLE USERS (
user_id INT PRIMARY KEY AUTO_INCREMENT,
email VARCHAR(100) UNIQUE NOT NULL,
password varchar(100) NOT NULL,
role VARCHAR(40) CHECK (ROLE IN ('BUYER','SELLER')) NOT NULL
);

-- PRODUCTS
CREATE TABLE products(
product_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100),
description TEXT,
price DOUBLE,
mrp DOUBLE ,
category VARCHAR(50),
stock INT,
seller_id INT ,
FOREIGN KEY(seller_id) references users(user_id) on delete cascade
);

CREATE TABLE CART(
cart_id INT PRIMARY KEY AUTO_INCREMENT,
user_id int,
FOREIGN KEY(user_id) references users(user_id) on delete cascade
);

CREATE TABLE cartitems(
cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
cart_id INT,
product_id INT,
quantity INT,
FOREIGN KEY(cart_id) references cart(cart_id) on delete cascade
);

CREATE TABLE orders(
order_id INT PRIMARY KEY AUTO_INCREMENT,
user_id INT,
total_amount INT,
status VARCHAR(50),
order_date TIMESTAMP DEFAULT current_timestamp,
FOREIGN KEY(user_id) references users(user_id) 
);

CREATE TABLE order_items(
order_item_id INT PRIMARY KEY AUTO_INCREMENT,
order_id INT,
product_id INT,
quantity INT,
price double,
FOREIGN KEY(order_id) references orders(order_id) on delete cascade,
 FOREIGN KEY (product_id) REFERENCES products(product_id)
);


