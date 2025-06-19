-- 1. Permissions
INSERT INTO permissions (id, name, description)
VALUES (1, 'VIEW_DEVICES', 'Allows viewing device list');

-- 2. Departments
INSERT INTO departments (id, name, description)
VALUES (1, 'Computer Science', 'Department of Computer Science');

-- 3. User Details (used for users, employees, students, loans)
INSERT INTO user_details (id, full_name, first_name, last_name, email, phone_number, address)
VALUES
(1, 'Alice Johnson', 'Alice', 'Johnson', 'alice@example.com', '1234567890', '123 Elm St'),
(2, 'Bob Smith', 'Bob', 'Smith', 'bob@example.com', '9876543210', '456 Oak Ave'),
(3, 'Peter Constanza', 'Peter', 'Constanza', 'peter@example.com', '1934040044', '4555 Chatam St');

-- 4. Users
INSERT INTO users (id, username, password_hash, user_detail_id)
VALUES
(1, 'alice123', 'hashed-password', 1),
(2, 'bobsmith01', 'hash-pass', 2),
(3, 'peter1', 'hash-pass', 3);

-- 5. User Permissions
INSERT INTO user_permissions (user_id, permission_id)
VALUES (1, 1);

-- 6. Employees
INSERT INTO employees (id, user_detail_id, department_id, employee_type)
VALUES
(1, 1, 1, 'ADMIN'),
(2, 2, 1, 'TEACHER');

-- 7. Teachers
INSERT INTO teachers (id, employee_id, academic_title)
VALUES (1, 2, 'Professor');

-- 8. Students
INSERT INTO students (id, user_detail_id, department_id, career, grade)
VALUES
(1, 1, 1, 'Software Engineering', '3rd'),
(2, 2, 1, 'Software Engineering', '8th'),
(3, 3, 1, 'Software Engineering', '7th');


-- 9. Devices
INSERT INTO devices (id, name, serial_number, model, description, type, status)
VALUES
(1, 'Dell Latitude 5400', 'DL5400SN01', '5400', '14-inch business laptop', 'COMPUTER', 'AVAILABLE'),
(2, 'Dell Latitude 5400', 'DL5400SN07', '5400', '14-inch business laptop', 'COMPUTER', 'AVAILABLE');

-- 10. Loans
INSERT INTO loans (id, user_detail_id, start_date, due_date, request_date, extended_due_date, return_date, status)
VALUES
(1, 2, CURRENT_DATE, CURRENT_DATE + 7, CURRENT_DATE - 1, NULL, NULL, 'APPROVED'),
(2, 1, CURRENT_DATE, CURRENT_DATE + 7, CURRENT_DATE - 1, NULL, NULL, 'APPROVED');

-- 11. Loan Devices
INSERT INTO loan_devices (loan_id, device_id)
VALUES (1, 1);

-- 12. Loan Comments
INSERT INTO loan_comments (id, loan_id, comment, creation_date)
VALUES (1, 1, 'Loan approved and ready to pick up.', CURRENT_DATE);

ALTER TABLE permissions ALTER COLUMN id RESTART WITH 100;
ALTER TABLE departments ALTER COLUMN id RESTART WITH 100;
ALTER TABLE employees ALTER COLUMN id RESTART WITH 100;
ALTER TABLE user_details ALTER COLUMN id RESTART WITH 100;
ALTER TABLE users ALTER COLUMN id RESTART WITH 100;
ALTER TABLE devices ALTER COLUMN id RESTART WITH 100;
ALTER TABLE loans ALTER COLUMN id RESTART WITH 100;
ALTER TABLE loan_comments ALTER COLUMN id RESTART WITH 100;