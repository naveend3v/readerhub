DROP TABLE IF EXISTS readerhub.cart;
DROP TABLE IF EXISTS readerhub.order_items;
DROP TABLE IF EXISTS readerhub.orders;
DROP TABLE IF EXISTS readerhub.bookslist;
DROP TABLE IF EXISTS readerhub.users;

-- Users table first, as it's referenced by others
CREATE TABLE readerhub.users (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  roles VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Books list
CREATE TABLE readerhub.bookslist (
  id INT NOT NULL AUTO_INCREMENT,
  book_name VARCHAR(255) DEFAULT NULL,
  author VARCHAR(255) DEFAULT NULL,
  description VARCHAR(2000) DEFAULT NULL,
  price DOUBLE DEFAULT NULL,
  published_date DATE DEFAULT NULL,
  category VARCHAR(255) DEFAULT NULL,
  image_path VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Orders table
CREATE TABLE readerhub.orders (
  id INT NOT NULL AUTO_INCREMENT,
  created_date DATE DEFAULT NULL,
  session_id VARCHAR(255) DEFAULT NULL,
  total_price DOUBLE DEFAULT NULL,
  user_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_orders_user (user_id),
  CONSTRAINT FK_orders_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

-- Order Items
CREATE TABLE readerhub.order_items (
  order_item_id INT NOT NULL AUTO_INCREMENT,
  book_id INT DEFAULT NULL,
  created_date DATE DEFAULT NULL,
  order_id INT DEFAULT NULL,
  price DOUBLE DEFAULT NULL,
  quantity INT DEFAULT NULL,
  book_name VARCHAR(255) DEFAULT NULL,
  total_price DOUBLE DEFAULT NULL,
  PRIMARY KEY (order_item_id),
  KEY FK_order_items_order (order_id),
  CONSTRAINT FK_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id)
) ENGINE=InnoDB;

-- Cart
CREATE TABLE readerhub.cart (
  id INT NOT NULL AUTO_INCREMENT,
  book_id INT DEFAULT NULL,
  user_id INT DEFAULT NULL,
  quantity INT NOT NULL,
  PRIMARY KEY (id),
  KEY FK_cart_book (book_id),
  KEY FK_cart_user (user_id),
  CONSTRAINT FK_cart_book FOREIGN KEY (book_id) REFERENCES bookslist (id),
  CONSTRAINT FK_cart_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;