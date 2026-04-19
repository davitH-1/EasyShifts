CREATE DATABASE IF NOT EXISTS easyshifts;
USE easyshifts;

CREATE TABLE IF NOT EXISTS users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    name        VARCHAR(255),
    avatar_url  VARCHAR(512),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shifts (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255),
    location    VARCHAR(255),
    shift_date  DATE,
    start_time  VARCHAR(20),
    end_time    VARCHAR(20),
    hours       DECIMAL(4,2),
    status      ENUM('PENDING', 'CONFIRMED', 'REJECTED') DEFAULT 'PENDING',
    notes       TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shift_assignments (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    shift_id    BIGINT NOT NULL,
    user_id     BIGINT NOT NULL,
    status      ENUM('PENDING', 'CONFIRMED', 'DECLINED') DEFAULT 'PENDING',
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shift_id) REFERENCES shifts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
    UNIQUE KEY uq_shift_user (shift_id, user_id)
);