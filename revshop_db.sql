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
product_id INT,users
quantity INT,
price double,
FOREIGN KEY(order_id) references orders(order_id) on delete cascade,
 FOREIGN KEY (product_id) REFERENCES products(product_id)
);
 use revshop
select * from users;

select * from products;
select *from orders;


DESC orders;
ALTER TABLE orders MODIFY total_amount DOUBLE;
Alter table orders add column shipping_address varchar(200);
Alter table orders add column payment_method varchar(200);




-- REVIEWS
CREATE TABLE reviews(
review_id int auto_increment primary key,
user_id int,product_id int,
rating int check(rating>=1 and rating<=5),
comment text,
created_at timestamp default current_timestamp,
foreign key (user_id) references users(user_id),
foreign key(product_id) references products(product_id)
)

select * from reviews;

alter table products add threshold_stock int default 10;


CREATE TABLE favor (
    user_id INT,
    product_id INT,
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);



ALTER TABLE users 
ADD COLUMN security_question VARCHAR(255),
ADD COLUMN security_answer VARCHAR(255);

ALTER TABLE users 
ADD COLUMN password_hint VARCHAR(255);



ALTER TABLE users 
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;




select * from users;

delete from users where user_id=2;




ALTER TABLE favor 
DROP FOREIGN KEY favor_ibfk_1;


ALTER TABLE favor 
ADD CONSTRAINT favor_ibfk_1 
FOREIGN KEY (user_id) 
REFERENCES users(user_id) 
ON DELETE CASCADE;

SHOW CREATE TABLE favor;
SELECT TABLE_NAME, CONSTRAINT_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE REFERENCED_TABLE_NAME = 'users';


select * from users;

delete from users where user_id=4;
select * from products;
delete from reviews where review_id=27;
select * from reviews
