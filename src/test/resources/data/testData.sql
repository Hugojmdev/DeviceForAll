
-- 1. Roles
INSERT INTO roles (id, name) VALUES
    (1, 'ADMIN'),
    (2, 'SUPERVISOR'),
    (3, 'STAFF'),
    (4, 'BORROWER');

-- 2. Permissions
INSERT INTO permissions (id, name) VALUES
    (1, 'DEVICE_READ'),
    (2, 'DEVICE_CREATE'),
    (3, 'DEVICE_UPDATE'),
    (4, 'DEVICE_RETIRE'),
    (5, 'LOAN_CREATE_OWN'),
    (6, 'LOAN_READ_OWN'),
    (7, 'LOAN_CANCEL_OWN'),
    (8, 'LOAN_READ_ALL'),
    (9, 'LOAN_APPROVE'),
    (10, 'LOAN_REJECT'),
    (11, 'LOAN_CHECK_OUT'),
    (12, 'LOAN_RETURN_PROCESS'),
    (13, 'LOAN_EXTEND'),
    (14, 'LOAN_PENALTY_WAIVE'),
    (15, 'USER_READ'),
    (16, 'USER_CONTACT_UPDATE'),
    (17, 'USER_BORROWING_STATUS_MANAGE'),
    (18, 'USER_ACCOUNT_STATUS_MANAGE'),
    (19, 'ACCESS_MANAGE');

-- 3. Role Permissions

-- ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT 1, id
FROM permissions;

-- SUPERVISOR
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, id
FROM permissions
WHERE name <> 'ACCESS_MANAGE';

-- STAFF
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, id
FROM permissions
WHERE name IN (
    'DEVICE_READ',
    'DEVICE_CREATE',
    'DEVICE_UPDATE',
    'LOAN_CREATE_OWN',
    'LOAN_READ_OWN',
    'LOAN_CANCEL_OWN',
    'LOAN_READ_ALL',
    'LOAN_APPROVE',
    'LOAN_REJECT',
    'LOAN_CHECK_OUT',
    'LOAN_RETURN_PROCESS',
    'LOAN_EXTEND',
    'USER_READ'
);

-- BORROWER
INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, id
FROM permissions
WHERE name IN (
    'DEVICE_READ',
    'LOAN_CREATE_OWN',
    'LOAN_READ_OWN',
    'LOAN_CANCEL_OWN'
);

-- 4. Departments
INSERT INTO departments (id, name, description) VALUES
    (1, 'Computer Science', 'Department of Computer Science');

-- 5. Users
INSERT INTO users (id, username, email, password_hash, enabled, created_at) VALUES
     (1, 'alice123', 'alice@example.com', 'hashed-password', TRUE, CURRENT_TIMESTAMP),
     (2, 'bobsmith01', 'bob@example.com', 'hash-pass', TRUE, CURRENT_TIMESTAMP),
     (3, 'peter1', 'peter@example.com', 'hash-pass', TRUE, CURRENT_TIMESTAMP);

-- 6. User Profiles
INSERT INTO user_profiles (user_id, first_name, last_name, phone_number, address, department_id) VALUES
      (1, 'Alice', 'Johnson', '1234567890', '123 Elm St', 1),
      (2, 'Bob', 'Smith', '9876543210', '456 Oak Ave', 1),
      (3, 'Peter', 'Constanza', '1934040044', '4555 Chatam St', 1);

-- 7. User Roles
INSERT INTO user_roles (user_id, role_id) VALUES
      (1, 1),
      (2, 3),
      (3, 4);

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
     (1, 2, CURRENT_DATE, DATEADD('DAY', 7, CURRENT_DATE), DATEADD('DAY', -1, CURRENT_DATE), NULL, NULL, 'APPROVED'),
     (2, 1, CURRENT_DATE, DATEADD('DAY', 7, CURRENT_DATE), DATEADD('DAY', -1, CURRENT_DATE), NULL, NULL, 'APPROVED');

-- 11. Loan Devices
INSERT INTO loan_devices (loan_id, device_id) VALUES
     (1, 1),
     (2, 2);

-- 12. Loan Comments
INSERT INTO loan_comments (loan_id, comment, created_at) VALUES
     (1, 'Loan approved and ready to pick up.',CURRENT_TIMESTAMP    );


