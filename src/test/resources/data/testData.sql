USE device_loans;

-- 1. Roles
INSERT INTO roles (id, name) VALUES
   (1, 'ADMIN'),
   (2, 'TEACHER'),
   (3, 'STUDENT');

-- 2. Permissions
INSERT INTO permissions (id, name) VALUES
   (1, 'VIEW_DEVICES'),
   (2, 'BORROW_DEVICE'),
   (3, 'MANAGE_DEVICES');

-- 3. Role Permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 2),
    (3, 1);

-- 4. Departments
INSERT INTO departments (id, name, description) VALUES
    (1, 'Computer Science', 'Department of Computer Science');

-- 5. Users
INSERT INTO users (id, username, email, password_hash, enabled) VALUES
     (1, 'alice123', 'alice@example.com', 'hashed-password', TRUE),
     (2, 'bobsmith01', 'bob@example.com', 'hash-pass', TRUE),
     (3, 'peter1', 'peter@example.com', 'hash-pass', TRUE);

-- 6. User Profiles
INSERT INTO user_profiles (user_id, first_name, last_name, phone_number, address, department_id) VALUES
      (1, 'Alice', 'Johnson', '1234567890', '123 Elm St', 1),
      (2, 'Bob', 'Smith', '9876543210', '456 Oak Ave', 1),
      (3, 'Peter', 'Constanza', '1934040044', '4555 Chatam St', 1);

-- 7. User Roles
INSERT INTO user_roles (user_id, role_id) VALUES
      (1, 1),
      (2, 2),
      (3, 3);

-- 8. Device Types
INSERT INTO device_types (id, name, description) VALUES
     (1, 'COMPUTER', 'Desktop or laptop computer'),
     (2, 'ACCESSORY', 'Accessories such as adapters or dongles'),
     (3, 'MOBILE', 'Mobile devices such as tablets or phones'),
     (4, 'MEDIA_EQUIPMENT', 'Projectors, cameras and recording equipment'),
     (5, 'PERIPHERAL', 'Input/output peripherals like keyboards or mice'),
     (6, 'POWER', 'Power supplies, chargers and batteries'),
     (7, 'NETWORK', 'Networking equipment like routers or switches'),
     (8, 'SPECIALIZED', 'Specialized academic or lab equipment');

-- 9. Devices
INSERT INTO devices (id, name, serial_number, model, description, device_type_id, status) VALUES
     (1, 'Dell Latitude 5400', 'DL5400SN01', '5400', '14-inch business laptop', 1, 'AVAILABLE'),
     (2, 'Dell Latitude 5400', 'DL5400SN07', '5400', '14-inch business laptop', 1, 'AVAILABLE');

-- 10. Loans
INSERT INTO loans (id, user_id, start_date, due_date, request_date, extended_due_date, return_date, status) VALUES
     (1, 2, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY), NULL, NULL, 'APPROVED'),
     (2, 1, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY), NULL, NULL, 'APPROVED');

-- 11. Loan Devices
INSERT INTO loan_devices (loan_id, device_id) VALUES
     (1, 1),
     (2, 2);

-- 12. Loan Comments
INSERT INTO loan_comments (loan_id, comment) VALUES
     (1, 'Loan approved and ready to pick up.');


