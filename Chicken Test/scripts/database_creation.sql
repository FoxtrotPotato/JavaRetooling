DROP DATABASE IF EXISTS chicken_test;
CREATE DATABASE chicken_test;
USE chicken_test;

CREATE TABLE farms (
  farm_id INT NOT NULL AUTO_INCREMENT,
  farm_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (farm_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE parameters (
  parameter_id INT NOT NULL AUTO_INCREMENT,
  parameter_name VARCHAR(45) DEFAULT NULL,
  parameter_value INT DEFAULT NULL,
  farm_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY (parameter_id),
  KEY FK_PARAMETERS_1 (farm_id),
  CONSTRAINT FK_PARAMETERS_1 FOREIGN KEY (farm_id) REFERENCES farms (farm_id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE users (
  user_id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(70) NOT NULL,
  email VARCHAR(45) DEFAULT NULL,
  user_observations VARCHAR(70) DEFAULT NULL,
  active TINYINT NOT NULL DEFAULT 1,
  farm_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY (user_id),
  KEY FK_USERS_1 (farm_id),
  CONSTRAINT FK_USERS_1 FOREIGN KEY (farm_id) REFERENCES farms (farm_id),
  KEY IDX_USERS_1 (username)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE authorities (
  username VARCHAR(45) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  UNIQUE KEY PK_AUTHORITIES (username, authority),
  CONSTRAINT FK_AUTHORITIES_1 FOREIGN KEY (username) REFERENCES users (username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE products (
  product_id INT NOT NULL AUTO_INCREMENT,
  product_name VARCHAR(45) NOT NULL,
  product_value DOUBLE(14,2) NOT NULL,
  product_stock INT DEFAULT NULL,
  farm_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY (product_id),
  KEY FK_PRODUCTS_1 (farm_id),
  CONSTRAINT FK_PRODUCTS_1 FOREIGN KEY (farm_id) REFERENCES farms (farm_id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE eggs (
  egg_id INT NOT NULL AUTO_INCREMENT,
  egg_birthday DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  product_id INT NOT NULL,
  farm_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY (egg_id),
  KEY FK_EGGS_1 (product_id),
  CONSTRAINT FK_EGGS_1 FOREIGN KEY (product_id) REFERENCES products (product_id),
  KEY FK_EGGS_2 (farm_id),
  CONSTRAINT FK_EGGS_2 FOREIGN KEY (farm_id) REFERENCES farms (farm_id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE chickens (
  chicken_id INT NOT NULL AUTO_INCREMENT,
  chicken_birthday DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  product_id INT NOT NULL,
  farm_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY (chicken_id),
  KEY FK_CHICKENS_1 (product_id),
  CONSTRAINT FK_CHICKENS_1 FOREIGN KEY (product_id) REFERENCES products (product_id),
  KEY FK_CHICKENS_2 (farm_id),
  CONSTRAINT FK_CHICKENS_2 FOREIGN KEY (farm_id) REFERENCES farms (farm_id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE transactions (
  transaction_id INT NOT NULL AUTO_INCREMENT,
  transaction_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  transaction_total DOUBLE(14,2),
  transaction_observations VARCHAR(60),
  farm_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY (transaction_id),
  KEY FK_TRANSACTIONS_1 (farm_id),
  CONSTRAINT FK_TRANSACTIONS_1 FOREIGN KEY (farm_id) REFERENCES farms (farm_id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE transaction_details (
  transaction_details_id INT NOT NULL AUTO_INCREMENT,
  quantity INT,
  price DOUBLE(14,2),
  subtotal DOUBLE(14,2),
  product_id INT,
  transaction_id INT NOT NULL,
  PRIMARY KEY (transaction_details_id),
  KEY FK_TRANSACTION_DETAILS_1 (transaction_id),
  CONSTRAINT FK_TRANSACTION_DETAILS_1 FOREIGN KEY (transaction_id) REFERENCES transactions (transaction_id) ON DELETE CASCADE,
  KEY FK_TRANSACTION_DETAILS_2 (product_id),
  CONSTRAINT FK_TRANSACTION_DETAILS_2 FOREIGN KEY (product_id) REFERENCES products (product_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE balances (
  balance_id INT NOT NULL AUTO_INCREMENT,
  balance_type VARCHAR(45) DEFAULT NULL,
  balance_total DOUBLE(14,2) DEFAULT NULL,
  transaction_id INT NOT NULL,
  farm_id INT DEFAULT 1,
  PRIMARY KEY (balance_id),
  KEY FK_BALANCES_1 (transaction_id),
  CONSTRAINT FK_BALANCES_1 FOREIGN KEY (transaction_id) REFERENCES transactions (transaction_id) ON DELETE CASCADE,
  KEY FK_BALANCES_2 (farm_id),
  CONSTRAINT FK_BALANCES_2 FOREIGN KEY (farm_id) REFERENCES farms (farm_id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE logs (
  log_id INT NOT NULL AUTO_INCREMENT,
  log_detail VARCHAR(250),
  log_timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_id INT NOT NULL,
  farm_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY (log_id),
  KEY FK_LOGS_1 (user_id),
  CONSTRAINT FK_LOGS_1 FOREIGN KEY (user_id) REFERENCES users (user_id),
  KEY FK_LOGS_2 (farm_id),
  CONSTRAINT FK_LOGS_2 FOREIGN KEY (farm_id) REFERENCES farms (farm_id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;











