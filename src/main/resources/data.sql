-- =====================================================
-- Smart Parking Management System - Sample Data
-- =====================================================

-- Admin User
INSERT INTO users (name, email, mobile, password, vehicle_number, role, created_at)
VALUES ('Admin', 'admin@smartparking.com', '9999999999', 'admin123', 'ADMIN-001', 'ADMIN', NOW());

-- Sample Users
INSERT INTO users (name, email, mobile, password, vehicle_number, role, created_at)
VALUES ('Rahul Sharma', 'rahul@example.com', '9876543210', 'password123', 'MH-12-AB-1234', 'USER', NOW());

INSERT INTO users (name, email, mobile, password, vehicle_number, role, created_at)
VALUES ('Priya Patel', 'priya@example.com', '9876543211', 'password123', 'DL-05-CD-5678', 'USER', NOW());

-- Parking Locations
INSERT INTO parking_locations (name, address, total_slots, active, created_at)
VALUES ('City Center Parking', '123 Main Street, Downtown', 3, TRUE, NOW());

INSERT INTO parking_locations (name, address, total_slots, active, created_at)
VALUES ('Airport Parking Zone', '456 Airport Road, Terminal 1', 3, TRUE, NOW());

-- Parking Slots for Location 1 (City Center Parking)
INSERT INTO slots (slot_number, vehicle_type, status, location_id, created_at)
VALUES ('A-01', 'CAR', 'AVAILABLE', 1, NOW());

INSERT INTO slots (slot_number, vehicle_type, status, location_id, created_at)
VALUES ('A-02', 'BIKE', 'AVAILABLE', 1, NOW());

INSERT INTO slots (slot_number, vehicle_type, status, location_id, created_at)
VALUES ('A-03', 'TRUCK', 'AVAILABLE', 1, NOW());

-- Parking Slots for Location 2 (Airport Parking Zone)
INSERT INTO slots (slot_number, vehicle_type, status, location_id, created_at)
VALUES ('B-01', 'CAR', 'AVAILABLE', 2, NOW());

INSERT INTO slots (slot_number, vehicle_type, status, location_id, created_at)
VALUES ('B-02', 'BIKE', 'AVAILABLE', 2, NOW());

INSERT INTO slots (slot_number, vehicle_type, status, location_id, created_at)
VALUES ('B-03', 'TRUCK', 'AVAILABLE', 2, NOW());
