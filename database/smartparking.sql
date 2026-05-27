-- =====================================================
-- Smart Parking Management System - Database Schema
-- Database: H2 (In-Memory)
-- =====================================================

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mobile VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    vehicle_number VARCHAR(20) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Parking Locations Table
CREATE TABLE IF NOT EXISTS parking_locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    total_slots INT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Parking Slots Table
CREATE TABLE IF NOT EXISTS slots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slot_number VARCHAR(20) NOT NULL,
    vehicle_type VARCHAR(10) NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    location_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (location_id) REFERENCES parking_locations(id)
);

-- Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    entry_time TIMESTAMP,
    exit_time TIMESTAMP,
    status VARCHAR(20) DEFAULT 'BOOKED',
    qr_code TEXT,
    vehicle_number VARCHAR(20),
    user_id BIGINT,
    slot_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (slot_id) REFERENCES slots(id)
);

-- Payments Table
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    payment_time TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING',
    booking_id BIGINT UNIQUE,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

-- Towing Requests Table
CREATE TABLE IF NOT EXISTS towing_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    towing_date TIMESTAMP,
    towing_location VARCHAR(255),
    contact_info VARCHAR(100),
    reason VARCHAR(255),
    booking_id BIGINT UNIQUE,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);
