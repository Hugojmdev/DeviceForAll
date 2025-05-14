CREATE DATABASE IF NOT EXISTS device_loans;
USE device_loans;

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Permissions
CREATE TABLE IF NOT EXISTS permissions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- 2. Departments
CREATE TABLE IF NOT EXISTS departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- 3. Users (login credentials + permission role)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_detail_id INT UNIQUE NOT NULL,
    FOREIGN KEY (user_detail_id) REFERENCES user_details(id) ON DELETE CASCADE
);
-- 4. User permissions
CREATE TABLE IF NOT EXISTS user_permissions (
    user_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- 5. User details (common info)
CREATE TABLE IF NOT EXISTS user_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    address TEXT
);

-- 6. Employees
CREATE TABLE IF NOT EXISTS employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_detail_id INT UNIQUE NOT NULL,
    department_id INT NOT NULL,
    employee_type ENUM('TEACHER', 'ADMIN', 'LIBRARY_STAFF', 'IT') NOT NULL, -- 'teacher', 'admin', etc.
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE,
    FOREIGN KEY (user_detail_id) REFERENCES user_details(id) ON DELETE CASCADE
);

-- 7. Teachers (extension of employee)
CREATE TABLE IF NOT EXISTS teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNIQUE NOT NULL,
    academic_title VARCHAR(100),
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- 8. Students
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_detail_id INT UNIQUE NOT NULL,
    department_id INT NOT NULL,
    career VARCHAR(100),
    grade VARCHAR(20),
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE,
    FOREIGN KEY (user_detail_id) REFERENCES user_details(id) ON DELETE CASCADE
);

-- 9. Devices
CREATE TABLE IF NOT EXISTS devices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) UNIQUE NOT NULL,
    model VARCHAR(100),
    description VARCHAR(250),
    type ENUM('COMPUTER', 'ACCESSORY', 'MOBILE', 'MEDIA_EQUIPMENT', 'PERIPHERAL', 'POWER', 'NETWORK', 'SPECIALIZED'),
    status ENUM('AVAILABLE', 'IN_USE', 'RESERVED', 'UNDER_MAINTENANCE', 'LOST', 'RETIRED', 'DAMAGED', 'PENDING_RETURN')
);

-- 10. Loans (for students or teachers)
CREATE TABLE IF NOT EXISTS loans (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_detail_id INT NOT NULL, -- reference to user_details, can be student or teacher
    start_date DATE NOT NULL,
    due_date DATE NOT NULL,
    request_date DATE,
    extended_due_date DATE,
    return_date DATE,
    status ENUM('REQUESTED', 'APPROVED', 'READY_TO_PICK_UP', 'REJECTED', 'BORROWED', 'RETURNED', 'LATE_RETURN', 'CANCELLED', 'NOT_RETURNED', 'EXPIRED', 'IN_REVIEW'),
    FOREIGN KEY (user_detail_id) REFERENCES user_details(id) ON DELETE CASCADE
);

-- 11. Loan devices (for students or teachers)
CREATE TABLE IF NOT EXISTS loan_devices (
    loan_id INT NOT NULL,
    device_id INT NOT NULL,
    PRIMARY KEY (loan_id, device_id),
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE
);

-- 12. Loan comments
CREATE TABLE IF NOT EXISTS loan_comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    loan_id INT NOT NULL,
    comment VARCHAR(250),
    creation_date DATE NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE
);

SET FOREIGN_KEY_CHECKS = 1;