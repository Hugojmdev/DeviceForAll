

-- 1. alter table device_types
ALTER TABLE device_types
    ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE AFTER description;

-- 2. insert into device_types
INSERT IGNORE INTO device_types (name, description, active)
VALUES
    ('Laptop', 'Portable computer', TRUE),
    ('Desktop', 'Desktop computer', TRUE),
    ('Tablet', 'Tablet device', TRUE),
    ('Smartphone', 'Mobile phone', TRUE),
    ('Monitor', 'External display', TRUE);

-- 3. alter table devices
ALTER TABLE devices
    DROP FOREIGN KEY devices_ibfk_1,
    MODIFY COLUMN device_type_id BIGINT NOT NULL AFTER id,
    ADD COLUMN make VARCHAR(100) AFTER model,
    MODIFY COLUMN status VARCHAR(50) NOT NULL,
    ADD COLUMN physical_condition VARCHAR(50) NOT NULL AFTER status,
    ADD COLUMN acquired_on DATE NULL AFTER physical_condition,
    ADD COLUMN warranty_expires_on DATE NULL AFTER acquired_on,
    ADD COLUMN retired_at TIMESTAMP NULL AFTER warranty_expires_on,
    ADD COLUMN retirement_reason VARCHAR(255) NULL AFTER retired_at,
    ADD CONSTRAINT fk_devices_device_type
        FOREIGN KEY (device_type_id) REFERENCES device_types(id);
