CREATE DATABASE IF NOT EXISTS device_loans;
USE device_loans;

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 2. Departments
CREATE TABLE IF NOT EXISTS departments (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) UNIQUE NOT NULL,
   description TEXT
);

-- 3. Users (login credentials + permission role)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. User profile (common info)
CREATE TABLE IF NOT EXISTS user_profiles (
    user_id BIGINT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    department_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- 5. User roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- 6. Permissions
CREATE TABLE IF NOT EXISTS permissions (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) NOT NULL UNIQUE
);

-- 7. Role permissions
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

-- 8. Device types
CREATE TABLE IF NOT EXISTS device_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(250)
);

-- 9. Devices
CREATE TABLE IF NOT EXISTS devices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) UNIQUE NOT NULL,
    model VARCHAR(100),
    description VARCHAR(250),
    status ENUM('AVAILABLE', 'IN_USE', 'RESERVED', 'UNDER_MAINTENANCE', 'LOST', 'RETIRED', 'DAMAGED', 'PENDING_RETURN'),
    device_type_id BIGINT,
    FOREIGN KEY (device_type_id) REFERENCES device_types(id)
);

-- 10. Loans (for students or teachers)
CREATE TABLE IF NOT EXISTS loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- reference to user, can be student or teacher
    start_date DATE NOT NULL,
    due_date DATE NOT NULL,
    request_date DATE,
    extended_due_date DATE,
    return_date DATE,
    status ENUM('REQUESTED', 'APPROVED', 'READY_TO_PICK_UP', 'REJECTED', 'BORROWED', 'RETURNED', 'LATE_RETURN', 'CANCELLED', 'NOT_RETURNED', 'EXPIRED', 'IN_REVIEW'),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 11. Loan devices (for students or teachers)
CREATE TABLE IF NOT EXISTS loan_devices (
    loan_id BIGINT NOT NULL,
    device_id BIGINT NOT NULL,
    PRIMARY KEY (loan_id, device_id),
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE
);

-- 12. Loan comments
CREATE TABLE IF NOT EXISTS loan_comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    comment VARCHAR(250),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE
);

SET FOREIGN_KEY_CHECKS = 1;