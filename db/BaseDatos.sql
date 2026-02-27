-- Crear base de datos
CREATE DATABASE IF NOT EXISTS bank_db;
USE bank_db;

-- Tabla Customers
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
    identification VARCHAR(50) NOT NULL UNIQUE,
    address VARCHAR(200),
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Accounts
CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type ENUM('SAVINGS', 'CHECKING') NOT NULL,
    initial_balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Validated Customers
CREATE TABLE validated_customers (
    id BIGINT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Movements
CREATE TABLE movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    movement_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    movement_type ENUM('DEPOSIT', 'WITHDRAWAL') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

USE bank_db;

-- ======================
-- Insert Customers
-- ======================
INSERT INTO customers (name, gender, identification, address, phone, password, status)
VALUES
('Marcelo Cazares', 'MALE', '123456789', 'Vicente Heredia', '0984578431', '1234', TRUE);

-- ======================
-- Insert Validated Customers
-- ======================
INSERT INTO validated_customers (id)
VALUES
(1);


-- ======================
-- Insert Accounts
-- ======================
INSERT INTO accounts (customer_id, account_number, account_type, initial_balance, status)
VALUES
(1, '11112222', 'SAVINGS', 2000.00, TRUE),
(1, '22223333', 'CHECKING', 100.00, TRUE);


-- ======================
-- Insert Movements
-- ======================
-- Movements for first account
INSERT INTO movements (account_id, movement_date, movement_type, amount, balance)
VALUES
(1, '2026-02-24 10:00:00', 'DEPOSIT', 500.00, 1500.00),
(1, '2026-02-25 15:30:00', 'WITHDRAWAL', 200.00, 1300.00);

